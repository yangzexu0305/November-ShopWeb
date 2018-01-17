package com.november.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * servletContext监听器
 * TODO
 * @author yangzexu
 * 2018年1月15日
 */
@WebListener
public class NovServletContextListener implements ServletContextListener{
	
	private Logger logger = LoggerFactory.getLogger(NovServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("---------------------spring监听器初始化---------------------");
		logger.info("--------------------ServerInfo :"+sce.getServletContext().getServerInfo());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("---------------------spring监听器销毁---------------------");
	}

	
	
}
