package com.november.auth.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7627747109552629359L;
	private Integer id;
	private String  loginName;//登陆名称
	private String name;// 名称
	private String password;//密码
	private String gender;// 性别
	private String birthday;//生日
	private String province;//省份
	private String city;//城市
	private String area;//区
	private String address;//详细地址
	private String mobile;//移动电话
	private String tell;//固定电话
	private String safeQues;//安全问题
	private String safeAns;//安全答案
	private String qq;
	private String email;//电子邮箱
	private String level;//等级
	private BigDecimal balance;//余额
	private BigDecimal consume;//消费
	private BigDecimal integral;//剩余积分
	private Integer loginNum;//登陆次数
	private Date registerDate;// 注册日期
	private Date firstLoginedTime;// 首次登录时间
	private Date lastLoginedTime;// 最后一次登录时间
	private Date pswChangeDate;// 密码修改时间
	private String locked;// 帐号是否可用    0-启用   1-锁定
	private String status;//审核状态
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	
	private Set<Role> roles;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTell() {
		return tell;
	}
	public void setTell(String tell) {
		this.tell = tell;
	}
	public String getSafeQues() {
		return safeQues;
	}
	public void setSafeQues(String safeQues) {
		this.safeQues = safeQues;
	}
	public String getSafeAns() {
		return safeAns;
	}
	public void setSafeAns(String safeAns) {
		this.safeAns = safeAns;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getIntegral() {
		return integral;
	}
	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}
	public Integer getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getFirstLoginedTime() {
		return firstLoginedTime;
	}
	public void setFirstLoginedTime(Date firstLoginedTime) {
		this.firstLoginedTime = firstLoginedTime;
	}
	public Date getLastLoginedTime() {
		return lastLoginedTime;
	}
	public void setLastLoginedTime(Date lastLoginedTime) {
		this.lastLoginedTime = lastLoginedTime;
	}
	public Date getPswChangeDate() {
		return pswChangeDate;
	}
	public void setPswChangeDate(Date pswChangeDate) {
		this.pswChangeDate = pswChangeDate;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public BigDecimal getConsume() {
		return consume;
	}
	public void setConsume(BigDecimal consume) {
		this.consume = consume;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
