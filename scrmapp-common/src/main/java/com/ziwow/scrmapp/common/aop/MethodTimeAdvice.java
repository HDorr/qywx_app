package com.ziwow.scrmapp.common.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodTimeAdvice {
	private static final Logger Log = LoggerFactory
			.getLogger(MethodTimeAdvice.class);

	public static final String POINT = "execution(* com.ziwow.scrmapp.common.service.impl.ThirdPartyServiceImpl.*(..))";

	@Around(POINT)
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		// 获取代理方法对象
		Object result = null;
		String methodName = pjp.getSignature().getName();
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = pjp.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}
		// 判断该方法是否加了@LoginRequired 注解
		if (method.isAnnotationPresent(LoginRequired.class)) {
			// 用 commons-lang 提供的 StopWatch 计时
			StopWatch clock = new StopWatch();
			clock.start(); // 计时开始
			result = pjp.proceed();
			clock.stop(); // 计时结束
			Log.info("cost:" + clock.getTime() + " ms ["
					+ method.getDeclaringClass().getName() + "."
					+ method.getName() + "] ");
		}
		return result == null ? pjp.proceed() : result;
	}
}
