package com.november.auth.service;

import com.november.auth.domain.User;

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
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(User user);
}
