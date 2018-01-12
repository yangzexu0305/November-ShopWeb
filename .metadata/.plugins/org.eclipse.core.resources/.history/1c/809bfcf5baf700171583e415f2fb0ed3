package com.november.common.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 登陆拦截器
 * TODO
 * @author yangzexu
 * 2018年1月12日
 *
 */
public class LoginInterceptor implements HandlerInterceptor{
	
	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//获取请求中的URI
		String requestUrl = request.getRequestURI();
		//获取上下文
		String ctx = request.getContextPath();
		logger.info("------------------ContextPath :"+ctx);
		//获取用户跳转到登陆页面前的 request
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		
		if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)){
			String requestBase = null, savedBase = null;
			//获取登陆前的url
			String savedUrl = savedRequest.getRequestUrl();
			logger.info("-------------------savedUrl :"+savedUrl);
			if(StringUtils.isNotEmpty(savedUrl)){
				//\s*  匹配任意数量的空格和换行和制表符
				Pattern pattern = Pattern.compile("(?<="+ctx+"/)[.\\s\\s]*?(?=/|$)");
				
				Matcher reqMatcher = pattern.matcher(requestUrl);
				Matcher savedMatcher = pattern.matcher(savedUrl);
				System.out.println("reqMatcher:"+reqMatcher);
				System.out.println("savedMatcher:"+savedMatcher);
				if(reqMatcher.find()){
					requestBase = reqMatcher.group();
					System.out.println("requestBase:"+requestBase);
				}
				
				if(savedMatcher.find()){
					savedBase = savedMatcher.group();
					System.out.println("savedBase:"+savedBase);
				}
				
				if(!StringUtils.equals(requestBase, savedBase)){
					WebUtils.getAndClearSavedRequest(request);
				}
				
			}
		}
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
