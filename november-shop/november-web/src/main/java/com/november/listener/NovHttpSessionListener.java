package com.november.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * session监听器
 * TODO
 * @author yangzexu
 * 2018年1月15日
 *
 */
@WebListener
public class NovHttpSessionListener implements HttpSessionListener{

	Logger logger = LoggerFactory.getLogger(NovHttpSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("------------------Session 被创建--------------------"+se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info("------------------Session 被销毁--------------------");
	}

}
