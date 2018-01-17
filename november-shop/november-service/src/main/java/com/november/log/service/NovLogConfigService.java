package com.november.log.service;

import java.util.Map;

import com.november.log.domain.NovLogConfig;

public interface NovLogConfigService {

	void createNovLogConfig(NovLogConfig hyLogConfig);

	NovLogConfig findLogConfigById(Long id);

    void updateHyLogConfig(NovLogConfig id);

    void deleteHyLogConfig(Long id);

    void deleteHyLogConfig(NovLogConfig hyLogConfig);
    
    boolean checkEnable(String url,String method);
    
    String getAction(String url,String method);
    
    Map<String, String> getEnableHyLogConfigMap();
    
}
