package com.november.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.november.common.utils.SpringUtil;
import com.november.log.service.NovLogService;

public class LogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private NovLogService novLogService = SpringUtil.getBean("novLogServiceImpl");
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		novLogService.createLog(request, handler.toString(), ex, null);
	}
}
