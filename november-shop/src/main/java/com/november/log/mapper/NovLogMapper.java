package com.november.log.mapper;

import org.apache.ibatis.annotations.Insert;

import com.november.log.domain.NovLog;

public interface NovLogMapper {
	
	@Insert("insert into nov_log(login_name,session_id,url,client_ip,server_ip,browser_version,os_version,"
			+ "time,action,method,parameters,error_msg,descript,refererURL,createTime,updateTime) values(#{loginName},#{sessionId},#{url},"
			+ "#{clientIp},#{serverIp},#{browserVersion},#{osVersion},#{time},#{action},#{method},#{parameters},#{errorMsg},#{descript},"
			+ "#{refererURL},#{createTime},#{updateTime})")
	public void createLog(NovLog log);
}
