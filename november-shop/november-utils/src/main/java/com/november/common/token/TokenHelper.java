package com.november.common.token;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

@Component
public class TokenHelper {

	@Autowired
	TokenConfig config;

	public String createToken(String subject,Map<String,Object> info) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// 生成签名密钥 就是一个base64加密后的字符串？
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(config.getSeed());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("type", "JWT").setIssuedAt(now) // 创建时间
				.setSubject(subject) 
				.setIssuer(config.getIssuer())
				.signWith(signatureAlgorithm, signingKey); // 估计是第三段密钥  设置算法
		for (String key : info.keySet()) {
			builder.claim(key, info.get(key));
		}
		
		// 添加Token过期时间
		if (config.getTTL() >= 0) {
			// 过期时间
			long expMillis = nowMillis + config.getTTL();
			// 现在是什么时间
			Date exp = new Date(expMillis);
			// 系统时间之前的token都是不可以被承认的
			builder.setExpiration(exp).setNotBefore(now);
		}
		// 生成JWT  这个是全部设置完成后拼成jwt串的方法
		return builder.compact();
	}

	public Claims parseToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(config.getSeed()))
					.parseClaimsJws(token).getBody();//得到body后我们可以从body中获取我们需要的信息
			 //比如 获取主题,当然，这是我们在生成jwt字符串的时候就已经存进来的
//		    String subject = body.getSubject();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}
}
