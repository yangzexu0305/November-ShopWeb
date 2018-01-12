package com.november.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.november.system.User;

/**
 * shiro工具类
 * @author yangzexu
 *
 */
public class ShiroUtils {
	/**
	 * 获取当前登陆用户
	 * @return
	 */
	public static User getCurrentUser(){
		return (User) getSubject().getPrincipal();
	}
	/**
	 * 
	 * @return
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	public static Integer getUserId(){
		return getCurrentUser().getId();
	}
	/**
	 * 登出
	 */
	public static void logout(){
		getSubject().logout();
	}
}
