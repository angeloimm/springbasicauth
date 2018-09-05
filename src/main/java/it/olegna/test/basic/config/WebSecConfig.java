package it.olegna.test.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.olegna.test.basic.security.RestAuthEntryPoint;

@Configuration
@EnableWebSecurity
@Import(value= {WebMvcConfig.class, DBConfig.class})
@ComponentScan(basePackages= {"it.olegna.test.basic"})
public class WebSecConfig extends WebSecurityConfigurerAdapter {
	@Autowired private RestAuthEntryPoint authenticationEntryPoint;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//		.inMemoryAuthentication()
//		.withUser("test")
//		.password(passwordEncoder().encode("testpwd"))
//		.authorities("ROLE_USER");
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/securityNone")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic()
		.authenticationEntryPoint(authenticationEntryPoint);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
