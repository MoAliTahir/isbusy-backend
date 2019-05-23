package com.isbusy.restapi.isbusyrestapi.responses;

import com.isbusy.restapi.isbusyrestapi.entities.User;

import java.util.List;

public class UserResponse {
	
	private User user;
	private String message;
	private int status;
	private List<User> users;

	public UserResponse(String message, int status){
		this.user = null;
		this.message = message;
		this.status = status;
		this.users = null;
	}

	public UserResponse(User user, String message, int status) {
		this.user = user;
		this.message = message;
		this.status = status;
		this.users = null;
	}


	public UserResponse(List<User> users, String message, int status) {
		this.user = null;
		this.message = message;
		this.status = status;
		this.users = users;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<User> getUsers() {
		return users;
	}



	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
