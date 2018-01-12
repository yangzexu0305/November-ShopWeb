package com.november;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.november.common.utils.SpringUtil;


/**
 * spring配置
 * TODO
 * @author yangzexu
 * 2018年1月12日
 *
 */
@Configuration
public class SpringConfiguration {

	@Bean
	public SpringUtil springUtil(){
		return new SpringUtil();
	}
}
