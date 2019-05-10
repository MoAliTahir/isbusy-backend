package com.isbusy.restapi.isbusyrestapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository; 
	
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	
	public User getUser(long id) {
		return userRepository.findById(id).get();//we can use .orElse(Null) or .orElse(new Topic);
	}

	public void addUser(User u) {
		userRepository.save(u);
	}

	public void updateUser(User u) {
		userRepository.save(u);
	}

	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}
}
