package com.november.log.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.november.log.domain.NovLogConfig;
import com.november.log.mapper.NovLogConfigMapper;
import com.november.log.service.NovLogConfigService;

@Service
@Transactional
public class NovLogConfigServiceImpl implements NovLogConfigService{

	@Autowired
	private NovLogConfigMapper novLogConfigMapper;
	
	@Override
	@CacheEvict(value = "LogCfgCache", allEntries = true)
	public void createNovLogConfig(NovLogConfig hyLogConfig) {
		
	}

	@Override
	public NovLogConfig findLogConfigById(Long id) {
		return null;
	}

	@Override
	@CacheEvict(value = "LogCfgCache", allEntries = true)
	public void updateHyLogConfig(NovLogConfig id) {
		
	}

	@Override
	public void deleteHyLogConfig(Long id) {
		
	}

	@Override
	@CacheEvict(value = "LogCfgCache", allEntries = true)
	public void deleteHyLogConfig(NovLogConfig hyLogConfig) {
		
	}

	@Override
	public boolean checkEnable(String url, String method) {
		Map<String,String> map = getAllEnableLogConfigs();
		return map.containsKey(url+method);
	}

	/**
	 * 获取所有有效的日志配置
	 * @return
	 */
	private Map<String,String> getAllEnableLogConfigs(){
		List<NovLogConfig> configs = novLogConfigMapper.findAllLogConfig();
		Map<String,String> map = new HashMap<String,String>();
		for (NovLogConfig config : configs) {
			if(StringUtils.isNotEmpty(config.getResString())){
				map.put(config.getResString()+config.getMethod(), config.getName());
			}
		}
		return map;
	}
	
	@Override
	public String getAction(String url, String method) {
		return null;
	}

	@Override
	public Map<String, String> getEnableHyLogConfigMap() {
		return null;
	}
	
	
}
