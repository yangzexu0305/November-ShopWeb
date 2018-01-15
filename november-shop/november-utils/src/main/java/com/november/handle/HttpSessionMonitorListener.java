package com.november.handle;

import java.text.SimpleDateFormat;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听器 TODO
 * 
 * @author yangzexu 2018年1月9日
 *
 */

@WebListener
public class HttpSessionMonitorListener implements HttpSessionListener {

	Logger logger = LoggerFactory.getLogger(HttpSessionMonitorListener.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		logger.info("-----------------------Session Created-----------------");
		logger.info("--------------------Session Id----------------:" + session.getId());
		// 创建时间
		logger.info("------------Session CreationTime :" + session.getCreationTime());
		logger.info("------------Session LastAccessTime :" + session.getLastAccessedTime());
		// 超时时间
		long maxInterval = session.getMaxInactiveInterval();
		logger.info("sessionMaxInactiveInterval(s):  " + session.getMaxInactiveInterval() + "=="
				+ formatTime(maxInterval * 1000));
		// 截止时间
		logger.info("sessionExpirtion:               " + (session.getCreationTime() + maxInterval * 1000) + "=="
				+ sdf.format(session.getCreationTime() + maxInterval * 1000));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		logger.info("----------------------------------sessionDestroyed");
		logger.info("sessionId:              " + session.getId());
		logger.info(
				"sessionCreationTime:    " + session.getCreationTime() + "==" + sdf.format(session.getCreationTime()));
		logger.info("sessionLastAccessedTime:" + session.getLastAccessedTime() + "=="
				+ sdf.format(session.getLastAccessedTime()));
	}

	public static String formatTime(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		// String strDay = day < 10 ? "0" + day : "" + day; // 天
		// String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		return strMinute + " 分 " + strSecond + " 秒";
	}
}