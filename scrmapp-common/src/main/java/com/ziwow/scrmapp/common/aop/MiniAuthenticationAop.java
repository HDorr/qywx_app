package com.ziwow.scrmapp.common.aop;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.impl.ThirdPartyServiceImpl;
import com.ziwow.scrmapp.common.utils.SignUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 小程序请求服务号认证aop
 * @author songkaiqi
 * @since 2019/06/14/上午10:49
 */
@Component
@Aspect
public class MiniAuthenticationAop {

    private static final Logger LOG = LoggerFactory.getLogger(ThirdPartyServiceImpl.class);

    @Pointcut("@annotation(com.ziwow.scrmapp.common.annotation.MiniAuthentication)")
    public void point(){}

    @Around("point()")
    public Result exec(final ProceedingJoinPoint propoint){
        Result result = new BaseResult();
        final Object[] args = propoint.getArgs();
        boolean isLegal = false;
        try {
            isLegal = SignUtil.checkSignature((String)args[0], (String)args[1], Constant.AUTH_KEY);
        } catch (Exception e) {
            LOG.error("方法参数不正确,请检查是否含有signture和timeStamp参数,第一个参数必须为signture 第二个参数必须为timeStamp");
            e.printStackTrace();
            throw new RuntimeException();
        }
        if (!isLegal) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(Constant.ILLEGAL_REQUEST);
            return result;
        }
        try {
            result = (Result) propoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }




}
