package com.november.auth.mapper;

import org.apache.ibatis.annotations.Param;

import com.november.auth.domain.User;

public interface UserMapper {
	
	/**
	 * 根据名称查询用户
	 * @param username
	 * @return
	 */
	public User findUserByName(@Param("username")String username);
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(User user);
}
