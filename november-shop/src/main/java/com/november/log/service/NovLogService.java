package com.november.log.service;

import javax.servlet.http.HttpServletRequest;

import com.november.log.domain.NovLog;

/**
 * 日志类
 * TODO
 * @author yangzexu
 * 2018年1月12日
 *
 */
public interface NovLogService {
	
	/**
	 * 添加日志
	 * @param request
	 * @param handle
	 * @param ex
	 * @param descript
	 */
	public void createLog(HttpServletRequest request,String handle,Exception ex,String descript);
	
	/**
	 * 
	 * @param request
	 * @param handle
	 * @param errorMsg
	 * @param descript
	 */
	public void createLog(HttpServletRequest request,String handle,String errorMsg,String descript);
	
	/**
	 * 入库
	 * @param novLog
	 */
	public void createLog(NovLog novLog);
}
