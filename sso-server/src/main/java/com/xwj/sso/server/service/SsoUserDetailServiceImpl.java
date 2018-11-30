package com.xwj.sso.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService用于返回用户相关数据
 */
@Service
public class SsoUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private List<? extends GrantedAuthority> authorities = AuthorityUtils
			.commaSeparatedStringToAuthorityList("ROLE_USER");

	/**
	 * 根据username查询用户实体
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String dbPassword = passwordEncoder.encode("123456");
		return new User(username, dbPassword, authorities);
	}

}
