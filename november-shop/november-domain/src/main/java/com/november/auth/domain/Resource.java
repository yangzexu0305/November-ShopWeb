package com.november.auth.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 资源表   用于配置权限
 *
 * TODO
 * @author yangzexu
 * 2018年1月10日
 *
 */
public class Resource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6509189439775736830L;
	
	
	private Integer resId;//主键
	private String resType;//资源类型
	private String resString;//资源唯一字符串
	private String perString;//shiro 授权字符串
	private String descript;//描述
	private String enabled;//是否启用
	private Set<Role> roles;
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	public Integer getResId() {
		return resId;
	}
	public void setResId(Integer resId) {
		this.resId = resId;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResString() {
		return resString;
	}
	public void setResString(String resString) {
		this.resString = resString;
	}
	public String getPerString() {
		return perString;
	}
	public void setPerString(String perString) {
		this.perString = perString;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}