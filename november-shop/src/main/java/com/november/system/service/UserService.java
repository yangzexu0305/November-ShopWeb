package com.november.system.service;

import com.november.system.User;

/**
 * 用户查询接口
 * @author yangzexu
 *
 */
public interface UserService {

	/**
	 * 根据名称查询用户
	 * @param username
	 * @return
	 */
	public User findUserByName(String username);
}
