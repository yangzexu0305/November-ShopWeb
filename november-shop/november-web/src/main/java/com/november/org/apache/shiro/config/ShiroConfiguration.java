package com.november.org.apache.shiro.config;

import java.util.LinkedHashMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.november.org.apache.shiro.AuthRealm;

/**
 * 
 * TODO shiro公共配置类
 * @author yangzexu
 * @date 2018年1月5日 
 * @version 1.0
 */
@Configuration
public class ShiroConfiguration {
	
	/**
	 * 缓存管理
	 * @return
	 */
	@Bean(name = "ehCacheManager")
	public CacheManager getCacheManage() {
		return new EhCacheManager();
	}
	
	
	/**
	 * 配置自定义权限登陆器
	 * @param credentialsMatcher
	 * @return
	 */
	@Bean(name="authRealm")
	public AuthRealm authRealm(@Qualifier("credentialsMatcher")CredentialsMatcher credentialsMatcher){
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCredentialsMatcher(credentialsMatcher);
		return authRealm;
	}
	
	/**
	 * 配置核心安全事务管理器
	 * @param authRealm
	 * @return
	 */
	@Bean(name="securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm")AuthRealm authRealm){
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);
		manager.setCacheManager(getCacheManage());
		SecurityUtils.setSecurityManager(manager);
		return manager;
	}

	/**
	 * shiro过滤器
	 * @return
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager")SecurityManager manager){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager( manager);
		//配置登陆的url和登陆成功的url
		bean.setLoginUrl("/login");
		bean.setSuccessUrl("/index");
		bean.setUnauthorizedUrl("/403");
		//配置访问权限
		LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/static/**", "anon");//表示可以匿名访问
		filterChainDefinitionMap.put("/docs/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/logout*", "logout");
		filterChainDefinitionMap.put("/**", "anon");//表示需要认证才能访问
		filterChainDefinitionMap.put("/blog", "anon");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}
	
	@Bean(name = "sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
		scheduler.setInterval(900000);
		return scheduler;
	}
	
	/**
	 * 会话ID
	 * @return
	 */
	@Bean(name = "sessionIdCookie")
	public SimpleCookie getSessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie("sid");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(180000);
		return cookie;
	}
	
	
	
	@Bean(name = "rememberMeCookie")
	public SimpleCookie getRememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(604800);//保存7天<!-- 单位为秒   --><!-- 最小为30分钟 -->
		return simpleCookie;
	}
	
	@Bean(name="rememberMeManager")
	public CookieRememberMeManager getRememberManager(){
		CookieRememberMeManager meManager = new CookieRememberMeManager();
		meManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		meManager.setCookie(getRememberMeCookie());
		return meManager;
	}
	
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager getSessionManage() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(getSessionIdCookie());
		EnterpriseCacheSessionDAO cacheSessionDAO = new EnterpriseCacheSessionDAO();
		cacheSessionDAO.setCacheManager(getCacheManage());
		sessionManager.setSessionDAO(cacheSessionDAO);
		// -----可以添加session 创建、删除的监听器
		return sessionManager;
	}
	
	/**
	 * 自定义密码比较器
	 * @return
	 */
	@Bean(name="credentialsMatcher")
	public CredentialsMatcher credentialsMatcher(){
		return new CredentialsMatcher();
	}
	
	/**
	 * shiro生命周期处理器
	 * @return
	 */
	@Bean(name="lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 实现spring的自动代理
	 * @return
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);//当shiro不进入自定义realm的权限认证方法时。
		return creator;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager manager){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}
}
