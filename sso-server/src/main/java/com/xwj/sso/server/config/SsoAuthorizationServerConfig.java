package com.xwj.sso.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 配置认证服务器
 */
@Configuration
@EnableAuthorizationServer // 开启认证服务
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * 用来配置客户端详情服务(给谁发送令牌)
	 */
	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() // 使用in-memory存储
				.withClient("myid1") // client_id(这里配置了之后，那么客户端配置的这个就不起作用了)
				.secret("mysecret1")// client_secret
				.accessTokenValiditySeconds(7200) // 发出去的令牌有效时间(秒)
				.authorizedGrantTypes("authorization_code", "password") // 该client允许的授权类型
				.scopes("all") // 允许的授权范围(如果是all，则请求中可以不要scope参数，否则必须加上scopes中配置的)
				.autoApprove(true) // 自动审核
				.and().withClient("myid2") // client_id(这里配置了之后，那么客户端配置的这个就不起作用了)
				.secret("mysecret2")// client_secret
				.accessTokenValiditySeconds(7200) // 发出去的令牌有效时间(秒)
				.authorizedGrantTypes("authorization_code", "password") // 该client允许的授权类型
				.scopes("all") // 允许的授权范围(如果是all，则请求中可以不要scope参数，否则必须加上scopes中配置的)
				.autoApprove(true); // 自动审核
	}

	/**
	 * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(jwtTokentStore()).accessTokenConverter(jwtAccessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 访问tokenKey时，需要进行身份认证
		security.tokenKeyAccess("isAuthenticated()");
	}

	@Bean
	public TokenStore jwtTokentStore() {
		// JWT方式存储Token
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("xwj"); // 设置Token签名
		return converter;
	}

}
