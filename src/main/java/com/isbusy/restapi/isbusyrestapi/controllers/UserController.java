package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
		return "user/notfound";
	}

	// injecting the TopicService singleton
	@Autowired
	private UserService userService;

	// index
	@PreAuthorize("hasRole('ADMIN')")

	@RequestMapping("/admin/users")
	// by default, it is a default request, if we need to use an other methode we
	// have specify it !
	public List<User> getAllUsers() {
		// as we have annotated this as a RestController the list returned
		// is automatically converted to JSon, daaaaamn !
		return userService.getAllUsers();
	}

	// show

	@RequestMapping("/users/me")
	public Object getCurrentUser() {

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return auth.getPrincipal();}

	@RequestMapping("/users/{id}")
	public User getUser(@PathVariable long id) {

		if (!userService.userExists(id))
			throw new ResourceNotFoundException();
		else
			return userService.getUser(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login() {
		return "Loged IN";
	}

	// TODO : make public
	// create

	@RequestMapping(method = RequestMethod.POST, value = "/users/create")
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}

	// modifier

	@RequestMapping(method = RequestMethod.PUT, value = "/users/update/{id}")
	public void updateUser(@RequestBody User user, @PathVariable long id) {
		userService.updateUser(user);
	}

	// suppprimer

	@RequestMapping(method = RequestMethod.DELETE, value = "/users/delete/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
	}

}
