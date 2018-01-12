package com.november.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.november.auth.domain.User;
import com.november.auth.mapper.UserMapper;
import com.november.auth.service.UserService;

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
