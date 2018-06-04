package com.ziwow.scrmapp.aop.requestlimit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ziwow.scrmapp.core.cache.RedisService;

/**
 * 
* @ClassName: RequestLimitContract
* @author hogen
* @date 2017-1-17 下午2:34:56
*
 */
@Component
@Aspect
public class RequestLimitContract {
	private static final Logger logger = LoggerFactory.getLogger(RequestLimitContract.class);
	@Autowired
	private RedisService redisService;
	
	@Before("@annotation(com.ziwow.scrmapp.aop.requestlimit.RequestLimit)&& @annotation(limit)")
	public void requestLimit(final JoinPoint joinPoint, RequestLimit limit)
			throws RequestLimitException, RequestTokenException, ServletException, IOException {
			Object[] args = joinPoint.getArgs();
			HttpServletRequest request = null;
			HttpServletResponse response = null;
			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof HttpServletRequest) {
					request = (HttpServletRequest) args[i];
				}
				if (args[i] instanceof HttpServletResponse) {
					response = (HttpServletResponse) args[i];
				}
				if (request != null && response != null) {
					break;
				}
			}
			if (request == null || response == null) {
				throw new RequestLimitException("方法中缺失HttpServletRequest参数");
			}

			// 是否需要IP验证
				if (limit.ipRequestLimit()) {
					String ip = CusAccessObjectUtil.getIpAddress(request);
					String url = request.getRequestURL().toString();
					String key = "req_limit_".concat(url).concat(ip);
					long count = redisService.increment(key, 1L);
					if (count == 1) {
						redisService.setKeyExpire(key, limit.time(),
								TimeUnit.SECONDS);
					}
					if (count > limit.count()) {
						logger.info("用户IP[" + ip + "]访问地址[" + url
								+ "]超过了限定的次数[" + limit.count() + "]");
						request.getRequestDispatcher(
								"/scrmappAuth/error?errorCode=40003").forward(
								request, response);
						throw new RequestLimitException();
					}
				}
				// token验证
				if (limit.tokenAccessRequest()) {
					String accessToken = request.getParameter("access_token");
					if (accessToken != null && !"3454".equals(accessToken)) {
						request.getRequestDispatcher(
								"/scrmappAuth/error?errorCode=40005").forward(
								request, response);
						throw new RequestTokenException();
					}

				}


	}

}