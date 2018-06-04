package com.ziwow.scrmapp.common.exception;

import com.ziwow.scrmapp.common.constants.Constant;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2017-12-26 14:06
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    private Logger LOG = Logger.getLogger(ExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";
        if (ex instanceof ParamException || ex instanceof RuntimeException) {
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("returnCode", Constant.FAIL);
            result.put("returnMsg", ex.getMessage());
            mv = new ModelAndView(new MappingJackson2JsonView(), result);
        } else {
            LOG.error("unknow exception, url:" + url, ex);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("returnCode", Constant.FAIL);
            result.put("returnMsg", defaultMsg);
            mv = new ModelAndView(new MappingJackson2JsonView(), result);
        }
        return mv;
    }
}
