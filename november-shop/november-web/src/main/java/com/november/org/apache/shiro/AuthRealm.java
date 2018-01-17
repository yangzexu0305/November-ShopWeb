package com.november.org.apache.shiro;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.november.auth.domain.Resource;
import com.november.auth.domain.Role;
import com.november.auth.domain.User;
import com.november.auth.service.UserService;

/**
 * 自定义用户授权 登陆认证
 * 
 * @author yangzexu
 *
 */
public class AuthRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调.
	 *
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * 
	 * @param principal
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		/*
		 * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行， 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
		 * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了， 缓存过期之后会再次执行。
		 */
		User user = (User) principal.getPrimaryPrincipal();
		user.setLastLoginedTime(new Date());
		user.setUpdateTime(new Date());
		user.setLoginNum(user.getLoginNum() + 1);
		userService.updateUser(user);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Role role : user.getRoles()) {
			// 设置角色
			info.addRole(role.getRoleName());
			for (Resource res : role.getResuorces()) {
				// 设置权限
				info.addStringPermission(res.getPerString());
			}
		}
		return info;
	}

	// 登陆认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		User user = userService.findUserByName(username);
		String password = new String((char[]) token.getCredentials());
		if (user == null) {
			throw new UnknownAccountException("用户名不存在!");
		} else if (user.getLocked().equals("1")) {
			throw new LockedAccountException("帐号已被锁定，请联系管理员！");
		} else if (!password.equals(user.getPassword())) {// 密码错误
			throw new IncorrectCredentialsException("密码不正确！");
		}
		return new SimpleAuthenticationInfo(user, password, getName());
	}

}
