package com.isbusy.restapi.isbusyrestapi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.entities.Role;
import com.isbusy.restapi.isbusyrestapi.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class UserController<Favorie> {

	// injecting the TopicService singleton
	@Autowired
	private UserService userService;

	// index
	@CrossOrigin
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/users")
	public ResponseEntity<UserResponse> getAllUsers() {
		HttpHeaders headers = new HttpHeaders();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		Iterator iterator = currentUser.getRoles().iterator();
		Role role = (Role) iterator.next();
		if (!role.getRole().equals("ADMIN")) {
			headers.add("status", "401");
			headers.add("message", "Unauthorized");

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers)
					.body(new UserResponse(currentUser, "Lack of privileges", 401));
		}

		headers.add("status", "200");
		headers.add("message", "OK");
		return ResponseEntity.ok().headers(headers).body(new UserResponse(userService.getAllUsers(), "Succes", 200));
	}

	@CrossOrigin
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/users/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable long id) {
		HttpHeaders headers = new HttpHeaders();
		if (!userService.userExists(id)) {
			headers.add("status", "404");
			headers.add("message", "Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers)
					.body(new UserResponse("User not found", 404));
		}

		headers.add("status", "200");
		headers.add("message", "OK");
		return ResponseEntity.ok().headers(headers).body(new UserResponse(userService.getUser(id), "Success", 200));
	}

	/*
	 * public List<User> getAllUsers() { return userService.getAllUsers(); }
	 */
	@CrossOrigin
	@RequestMapping("/users/me")
	public ResponseEntity<UserResponse> getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		headers.add("status", "200");
		headers.add("messgae", "OK");
		return ResponseEntity.ok().headers(headers).body(new UserResponse((User) auth.getPrincipal(), "Success", 200));
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/users/register")
	public ResponseEntity<UserResponse> addUser(@RequestBody User user) {

		List<User> allUsers = userService.getAllUsers();

		Iterator iterator = allUsers.iterator();

		while (iterator.hasNext()) {
			User utilisateur = (User) iterator.next();

			if (utilisateur.getUsername().equalsIgnoreCase(user.getUsername())) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("status", "409");
				headers.add("message", "Conflict");

				return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers)
						.body(new UserResponse(user, "Username already exist", 409));

			}
		}

		Iterator it = allUsers.iterator();

		while (it.hasNext()) {
			User utilisateur = (User) it.next();

			if (utilisateur.getEmail().equalsIgnoreCase(user.getEmail())) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("status", "409");
				headers.add("message", "Conflict");

				return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers)
						.body(new UserResponse(user, "Email already exist", 409));

			}
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("status", "200");
		headers.add("message", "OK");

		userService.addUser(user);
		return ResponseEntity.ok().headers(headers).body(new UserResponse(user, "User Created successfully", 200));

	}

	// modifier
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/users/update")
	public ResponseEntity<UserResponse> updateUser(@RequestBody User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		HttpHeaders headers = new HttpHeaders();

		if (String.valueOf(user.getId()).isEmpty() || currentUser.getId() != user.getId()) {
			headers.add("status", "400");
			headers.add("message", "Bad Request");

			// BAd request 400

			return ResponseEntity.badRequest().headers(headers)
					.body(new UserResponse(user, "You can't update this user", 400));
		}

		headers.add("status", "200");
		headers.add("message", "OK");

		userService.updateUser(user);
		return ResponseEntity.ok().headers(headers).body(new UserResponse(user, "User Updated successfully", 200));

	}

	// suppprimer
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/delete")
	public ResponseEntity<UserResponse> deleteUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		long currentId = currentUser.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.add("status", "200");
		headers.add("message", "OK");
		SecurityContextHolder.getContext().setAuthentication(null);
		userService.deleteUser(currentId);
		return ResponseEntity.ok().headers(headers).body(new UserResponse("User Deleted successfully", 200));

	}
	// Favoris
	@RequestMapping(method = RequestMethod.GET, value = "/favories")
	public ArrayList<Emplacement> getAllFavoris() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		long currentId = currentUser.getId();
		return userService.getAllFavoris(currentId);
	}
}
