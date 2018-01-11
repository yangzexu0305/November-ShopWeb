package com.november;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguation {
	/*
	 * druid数据源状态监控.
	 */
	@Bean
	public ServletRegistrationBean statViewServle() {
		// 注册Servlet
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");
		// 白名单：(没有配置或者为空，则允许所有访问)
		servletRegistrationBean.addInitParameter("allow", "192.168.8.105,127.0.0.1");
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的即提示:Sorry, you are not
		// permitted to view this page.
		servletRegistrationBean.addInitParameter("deny", "192.168.8.1");
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", "druid");
		servletRegistrationBean.addInitParameter("loginPassword", "12345678");
		// 是否能够重置数据. 禁用HTML页面上的“Reset All”功能
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean statFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.jpg,*.png,*.css,*.gif,*.js,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	@Bean
	PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	// 配置数据库的基本链接信息
	@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource") // 可以在application.properties中直接导入
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean;
	}
}
