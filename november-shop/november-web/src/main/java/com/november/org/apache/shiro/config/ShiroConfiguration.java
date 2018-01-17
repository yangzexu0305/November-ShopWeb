package com.november.org.apache.shiro.config;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
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

import com.november.listener.OnlineUserSessionListener;
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
	 * shiro过滤器
	 * @return
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager")SecurityManager manager){
		// 拦截器.
        //rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等。
        //port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
        //perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
        //roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
        //anon：比如/admins/**=anon 没有参数，表示可以匿名使用。
        //authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
        //authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
        //ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
        //user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
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
		filterChainDefinitionMap.put("/api/**", "anon");//表示需要认证才能访问
		filterChainDefinitionMap.put("/login*", "authc");
		filterChainDefinitionMap.put("/blog", "anon");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
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
		//注入记住我管理器;
		manager.setRememberMeManager(getRememberManager());
		//注入缓存管理器;
        //注意:开发时请先关闭，如不关闭热启动会报错
//		manager.setCacheManager(getCacheManage());
//		SecurityUtils.setSecurityManager(manager);
		return manager;
	}
	
	/**
	 * 缓存管理
	 * @return
	 */
	@Bean(name = "ehCacheManager")
	public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

	/**
	 * 自定义密码比较器
	 * @return
	 */
	@Bean(name="credentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);// 散列的次数，比如散列两次，相当于md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        return hashedCredentialsMatcher;
    }
	
	/**
	 * 配置自定义权限登陆器
	 * @param credentialsMatcher
	 * @return
	 */
	@Bean(name="authRealm")
	public AuthRealm authRealm(@Qualifier("credentialsMatcher")HashedCredentialsMatcher credentialsMatcher){
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCredentialsMatcher(credentialsMatcher);
		return authRealm;
	}
	
	
	@Bean(name = "sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
		scheduler.setInterval(900000);
		return scheduler;
	}
	/**
	 * 会话ID生成器
	 * @return
	 */
	@Bean(name="sessionIdGenerator")
	public JavaUuidSessionIdGenerator avaUuidSessionIdGenerator(){
		return new JavaUuidSessionIdGenerator();
	}
	
	/**
	 * 会话DAO
	 * @return
	 */
	@Bean(name="sessionDAO")
	public EnterpriseCacheSessionDAO nterpriseCacheSessionDAO(){
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setCacheManager(ehCacheManager());
		enterpriseCacheSessionDAO.setSessionIdGenerator(avaUuidSessionIdGenerator());
		return enterpriseCacheSessionDAO;
	}
	
//	/**
//	 * 会话验证调度器
//	 * @return
//	 */
//	@Bean(name="QuartzSessionValidationScheduler")
//	public QuartzSessionValidationScheduler quartzSessionValidationScheduler(){
//		return new QuartzSessionValidationScheduler();
//	}
	
	
	/**
	 * 会话 Cookie 模板
	 * @return
	 */
	@Bean(name = "sessionIdCookie")
	public SimpleCookie getSessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie("sid");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(180000);
		return cookie;
	}
	
	
	/**
	 * cookie对象
	 * @return
	 */
	@Bean(name = "rememberMeCookie")
	public SimpleCookie getRememberMeCookie() {
		 //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
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
	
//	@Bean
//	public OnlineUserSessionListener onlineUserSessionListener(){
//		return new OnlineUserSessionListener();
//	}
	
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager getSessionManage() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(getSessionIdCookie());
		sessionManager.setSessionDAO(nterpriseCacheSessionDAO());
//		Set<SessionListener> listeners = new HashSet<>();
//		listeners.add(onlineUserSessionListener());
		// -----可以添加session 创建、删除的监听器
//		sessionManager.setSessionListeners(listeners);
		return sessionManager;
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
	
	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * @param manager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")DefaultWebSecurityManager manager){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}
}
