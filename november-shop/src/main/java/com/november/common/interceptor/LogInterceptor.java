package com.november.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.november.common.utils.SpringUtil;
import com.november.log.service.NovLogService;
import com.november.log.service.impl.NovLogServiceImpl;

public class LogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private NovLogService novLogService = SpringUtil.getBean("novLogServiceImpl");
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		novLogService.createLog(request, handler.toString(), ex, null);
	}
}
