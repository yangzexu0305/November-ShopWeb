package com.november.listener;

import javax.servlet.annotation.WebListener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class OnlineUserSessionListener implements SessionListener {
	
	private static final Logger log = LoggerFactory.getLogger(OnlineUserSessionListener.class);
	
	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub
		log.debug("session start:"+session.getHost());
		log.info("------------------------sessionId :"+session.getId());
	}

	@Override
	public void onStop(Session session) {

	}

	@Override
	public void onExpiration(Session session) {

	}

}
