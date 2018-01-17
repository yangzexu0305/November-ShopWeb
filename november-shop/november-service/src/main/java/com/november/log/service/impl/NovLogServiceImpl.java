package com.november.log.service.impl;

import java.net.InetAddress;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.november.common.useragent.Browser;
import com.november.common.useragent.OperatingSystem;
import com.november.common.useragent.UserAgent;
import com.november.common.useragent.Version;
import com.november.common.utils.ShiroUtils;
import com.november.common.utils.json.JsonMapper;
import com.november.log.domain.NovLog;
import com.november.log.mapper.NovLogMapper;
import com.november.log.service.NovLogService;

@Service("novLogServiceImpl")
@Transactional
public class NovLogServiceImpl implements NovLogService {

	@Autowired
	private NovLogMapper novLogMapper;

	@Override
	public void createLog(HttpServletRequest request, String handle, Exception ex, String descript) {
		String errorMsg = null;
		if (ex != null) {
			errorMsg = ex.getMessage();
		}
		createLog(request, handle, errorMsg, descript);
	}

	@Override
	public void createLog(HttpServletRequest request, String handle, String errorMsg, String descript) {
		// 比较前一段访问的URL和时间，反复刷新同一URL的特殊处理 否则将对数据库造成巨大压力
		String url = WebUtils.getPathWithinApplication(request);
		String method = request.getMethod();

		NovLog log = new NovLog();

		String loginName = ShiroUtils.getLoginName();
		if(StringUtils.isEmpty(loginName)) return;
		String sessionId = ShiroUtils.getSession().getId().toString();
		log.setSessionId(sessionId);
		String clientIp = getIpAddr(request);

		log.setLoginName(loginName);

		log.setClientIp(clientIp);
		log.setTime(new Date());
		log.setDescript(descript);
		log.setMethod(method);

		String parameters = JsonMapper.nonDefaultMapper().toJson(request.getParameterMap());
		log.setParameters(parameters);
		log.setUrl(url);
		log.setRefererURL(request.getHeader("REFERER"));
		log.setCreateTime(new Date());
		log.setUpdateTime(new Date());
		try {
			// 设置服务器信息
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverIp = inetAddress.getHostAddress();
			log.setServerIp(serverIp);

		} catch (Exception e) {
			e.printStackTrace();
		}

		setBrowserAndOS(request, log);
		createLog(log);
	}

	private void setBrowserAndOS(HttpServletRequest request, NovLog novLog) {
		String uaStr = request.getHeader("User-Agent");
		UserAgent userAgent = new UserAgent(uaStr);
		// 浏览器
		Browser browser = userAgent.getBrowser();
		// 浏览器版本
		Version version = userAgent.getBrowserVersion();
		// 操作系统
		OperatingSystem os = userAgent.getOperatingSystem();

		if (browser != null) {
			String browserVersion = browser.getName();
			if (version != null) {
				browserVersion += " version:" + version.getVersion();
			}
			novLog.setBrowserVersion(browserVersion);
		}

		if (os != null) {
			novLog.setOsVersion(os.getName());
		}

	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Override
	public void createLog(NovLog novLog) {
		novLogMapper.createLog(novLog);
	}

}
