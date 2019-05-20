package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.services.UserService;

@RestController
public class UserController {
	
	//injecting the TopicService singleton
			@Autowired 
			private UserService userService;
			
			
			//index 
			@PreAuthorize("hasRole('ADMIN')")
			@RequestMapping("/admin/users")
			//by default, it is a default request, if we need to use an other methode we have specify it !
			public List<User> getAllUsers() {
				//as we have annotated this as a RestController the list returned 
				//is automatically converted to JSon, daaaaamn !		
				return userService.getAllUsers();		
			}
			
			//show 
			@RequestMapping("/users/{id}")
			public User getUser(@PathVariable long id) {
				return userService.getUser(id);
			}
			
			//TODO : make public
			//create 
			@RequestMapping(method=RequestMethod.POST,value="/users/create")
			public void addUser(@RequestBody User user) {
			  //json code comes in then gets converted to a
			  //Topic instance then inserted
			  userService.addUser(user);
			}
			
			//modifier 
			@RequestMapping(method=RequestMethod.PUT,value="/users/update/{id}")
			public void updateUser(@RequestBody User user, @PathVariable long id) {
			//json code comes in then gets converted to a
			//Topic instance then inserted
				userService.updateUser(user);
			}
			
			//suppprimer 
			@RequestMapping(method=RequestMethod.DELETE,value="/users/delete/{id}")
			public void deleteUser(@PathVariable long id) {
				userService.deleteUser(id);
			}

}
