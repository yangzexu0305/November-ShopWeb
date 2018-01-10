package com.november.system.mapper;

import org.apache.ibatis.annotations.Select;

import com.november.system.domain.User;

public interface UserMapper {
	
	/**
	 * 根据名称查询用户
	 * @param username
	 * @return
	 */
	public User findUserByName(String username);
}
