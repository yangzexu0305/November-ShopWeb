package com.november.log.domain;

import java.io.Serializable;

import org.springframework.http.HttpMethod;

public class NovLogConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993525496792063129L;

	private String code;// 唯一代码

	private String name;// 配置项名称

	private String descript;// 备注

	private String resString;// 资源字符串（URL或方法名)

	private Boolean enable;// 是否启用日志

	private String method = HttpMethod.GET.name();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getResString() {
		return resString;
	}

	public void setResString(String resString) {
		this.resString = resString;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
