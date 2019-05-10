package com.isbusy.restapi.isbusyrestapi.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.isbusy.restapi.isbusyrestapi.repositories.UserRepository;
import com.isbusy.restapi.isbusyrestapi.services.CustomUserDetailsService;


@EnableGlobalMethodSecurity(prePostEnabled= true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses =  UserRepository.class)
@Configuration 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("**/admin/**").authenticated()
			.anyRequest().authenticated()
			.and()
		.formLogin().and()
		.httpBasic();
		//TODO get login POST request !
	}
	
	
	
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return true;
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				// TODO Password needs to be encoded
				return rawPassword.toString();
				//TODO Hacher le password !
			}
		};
	}
}
