package com.ziwow.scrmapp.common.aop;

import com.ziwow.scrmapp.common.annotation.Fuse;
import com.ziwow.scrmapp.common.exception.ThirdException;
import com.ziwow.scrmapp.common.service.impl.ThirdPartyServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 *  熔断处理
 * @author songkaiqi
 * @since 2019/06/10/上午10:35
 */
@Component
@Aspect
public class FuseAop extends ApplicationObjectSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ThirdPartyServiceImpl.class);

    @Value("${third.fuse.corePoolSize}")
    private Integer corePoolSize;

    @Value("${third.fuse.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${third.fuse.keepAliveTime}")
    private Integer keepAliveTime;

    @Value("${third.fuse.maxQueueSize}")
    private Integer maxQueueSize;

    @Value("${third.fuse.timeoutInMilliSeconds}")
    private Integer timeoutInMilliSeconds;

    private static ExecutorService service;

    @PostConstruct
    public void initThreadPool(){
        service = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(maxQueueSize), new ThreadPoolExecutor.CallerRunsPolicy());
    }


    @Pointcut("execution(* com.ziwow.scrmapp.common.service.impl.ThirdPartyServiceImpl.*(..))")
    public void conference(){}

    @Pointcut("@annotation(com.ziwow.scrmapp.common.annotation.Fuse)")
    public void anConference(){}

    /**
     * @param point
     * @return
     */
    @Around("conference()")
    public Object exec(final ProceedingJoinPoint point) throws Exception {
        Object result = null;
            final Future<Object> future = service.submit(new Callable<Object>() {
                Object pro = null;
                @Override
                public Object call() {
                    try {
                        final long st = System.currentTimeMillis();
                        pro = point.proceed();
                        final long et = System.currentTimeMillis();
                        long t = et-st;
                        MethodSignature methodSignature = (MethodSignature)point.getSignature();
                        LOG.info(methodSignature.getName()+"方法执行时间为===="+t);
                    } catch (Throwable throwable) {
                        LOG.error("aop调用第三方出现异常,异常信息",throwable);
                    }
                    return pro;
                }
            });
            try {
                result = future.get(3000,TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                LOG.error("请求第三方超时! TimeoutException:",e);
                future.cancel(true);
                throw new ThirdException("请求超时,请稍后再试");
            }catch (InterruptedException e){
                //当前线程被中断 log throw
                LOG.info("因超时线程强行停止运行");
                e.printStackTrace();
            }catch (ExecutionException e){
                //task在执行的时候因为抛出一个异常导致结果被abort log throw
                LOG.error("execute error reason",e);
                Exception ex = new Exception();
                ex.initCause(e);
                throw ex;
            }
        return result;
    }



    /**
     * 注解形式的
     * controller的
     * @param point
     * @return
     */
    @Around("anConference()")
    public Object exec2(final ProceedingJoinPoint point){
        Object result = null;
        try {
            final Future<Object> future = service.submit(new Callable<Object>() {
                Object pro = null;
                @Override
                public Object call() {
                    try {
                        pro = point.proceed();
                    } catch (Throwable throwable) {
                        LOG.error("aop调用第三方出现异常,异常信息:{}",throwable);
                    }
                    return pro;
                }
            });

            try {
                result = future.get(timeoutInMilliSeconds,TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                LOG.error("请求第三方超时!");
                future.cancel(true);
                MethodSignature methodSignature = (MethodSignature)point.getSignature();
                final Annotation annotation = methodSignature.getMethod().getAnnotation(Fuse.class);
                //执行方法的类
                final Class<?> aClass = point.getTarget().getClass();
                //注解标识降级的方法
                final Method method = aClass.getMethod(((Fuse) annotation).fallbackMethod());
                final ApplicationContext applicationContext = getApplicationContext();
                //spring容器中获取要执行降级方法的bean
                final Object bean = applicationContext.getBean(aClass);
                result = method.invoke(bean);
            }
        } catch (Throwable throwable) {
            LOG.error("aop调用第三方出现异常,异常信息:{}",throwable);
        }
        return result;
    }






}
