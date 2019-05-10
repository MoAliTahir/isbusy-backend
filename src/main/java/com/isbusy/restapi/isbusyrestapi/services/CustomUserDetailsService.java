package com.isbusy.restapi.isbusyrestapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isbusy.restapi.isbusyrestapi.entities.CustomUserDetails;
import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.repositories.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 		Optional<User> optionalUser = userRepository.findByUsername(username);
 		
 		
 		
 		optionalUser
 					.orElseThrow(()-> new UsernameNotFoundException("Nom d'utilisateur Erroné !"));
 		
		return optionalUser
						.map(CustomUserDetails::new).get();
	}

}
