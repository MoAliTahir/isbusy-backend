package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.services.UserService;
import com.isbusy.restapi.isbusyrestapi.controllers.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class UserController {
	/*
	 * @ExceptionHandler(ResourceNotFoundException.class)
	 * 
	 * @ResponseStatus(HttpStatus.NOT_FOUND) public String
	 * handleResourceNotFoundException() { return "user/notfound"; }
	 */

	// injecting the TopicService singleton
	@Autowired
	private UserService userService;

	// index
	@CrossOrigin
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/users")
	public ResponseEntity<List> getAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		if (userService.getAllUsers() == null) {
			// String body = "No users found";
			headers.add("Status", "404");
			headers.add("Message", "No users found");
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		} else {
			// userService.getAllUsers();
			headers.add("Status", "404");
			headers.add("Message", "Users found");
			return new ResponseEntity<>(userService.getAllUsers(), headers, HttpStatus.OK);
		}
	}

	@CrossOrigin
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable long id) {
		HttpHeaders headers = new HttpHeaders();
		if (!userService.userExists(id)) {
			headers.add("Status", "404");
			headers.add("Message", "User not found");
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		} else
			headers.add("Status", "404");
		headers.add("Message", "Users found");
		return new ResponseEntity<>(userService.getUser(id), headers, HttpStatus.OK);
	}

	/*
	 * public List<User> getAllUsers() { return userService.getAllUsers(); }
	 */
	@CrossOrigin
	@RequestMapping("/users/me")
	public ResponseEntity<Object> getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		if (auth.getPrincipal() == null) {
			// String body = "No users found";
			headers.add("Status", "404");
			headers.add("Message", "User Not found");
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		} else {
			headers.add("Status", "200");
			headers.add("Messgae", "User found");
			return new ResponseEntity<>(auth.getPrincipal(), headers, HttpStatus.OK);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<String> login() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Status", "200");
		headers.add("Message", "Logged successfully");
		return new ResponseEntity<>("Logged Successfully", headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users/register")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Status", "200");
		headers.add("Message", "User Created successfully");
		userService.addUser(user);
		return new ResponseEntity<>(headers, HttpStatus.OK);

	}

	// modifier
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/users/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		long currentId = currentUser.getId();
		HttpHeaders headers = new HttpHeaders();
		if (user.getId() != currentId) {
			headers.add("Status", "404");
			headers.add("Message", "You can't update this user");
			return new ResponseEntity<>(user, headers, HttpStatus.FORBIDDEN);
		} else
			headers.add("Status", "200");
		headers.add("Message", "User Updated successfully");
		userService.updateUser(user);
		return new ResponseEntity<>(user, headers, HttpStatus.OK);

	}

	// suppprimer
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/delete")
	public ResponseEntity<String> deleteUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		long currentId = currentUser.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Status", "200");
		headers.add("Message", "User Deleted successfully");
		SecurityContextHolder.getContext().setAuthentication(null);
		userService.deleteUser(currentId);
		return new ResponseEntity<>("User deleted", headers, HttpStatus.OK);

	}

}
