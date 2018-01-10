package com.november.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.november.system.domain.User;
import com.november.system.mapper.UserMapper;
import com.november.system.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper userMapper;

	@Override
	public User findUserByName(String username) {
		User user = userMapper.findUserByName(username);
		
		return user;
	}

	
	
}
