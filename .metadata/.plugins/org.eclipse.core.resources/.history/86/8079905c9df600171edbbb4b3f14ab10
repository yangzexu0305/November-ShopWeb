package com.november.org.apache.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.november.system.domain.User;
import com.november.system.service.UserService;

/**
 * 自定义用户授权  登陆认证
 * @author yangzexu
 *
 */
public class AuthRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService;

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		
		return null;
	}

	//登陆认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken uToken = (UsernamePasswordToken) token;
		String username = uToken.getUsername();
		User u = new User();
		u.setLoginName(username);
		User user = userService.findUserByName(username);
		String password = new String((char[])uToken.getCredentials());
		if(user == null){
			throw new UnknownAccountException("用户名不存在!");
		}else if(!password.equals(user.getPassword())){//密码错误
			throw new IncorrectCredentialsException("密码不正确！");
		}else if(user.getLocked().equals("1")){
			throw new LockedAccountException("帐号已被锁定，请联系管理员！");
		}
		return new SimpleAuthenticationInfo(user,password,getName());
	}
	
	
	
}
