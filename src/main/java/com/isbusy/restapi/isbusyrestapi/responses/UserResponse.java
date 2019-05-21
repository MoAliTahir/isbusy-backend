package com.isbusy.restapi.isbusyrestapi.responses;

import com.isbusy.restapi.isbusyrestapi.entities.User;

public class UserResponse {
	
	private User user;
	
	public UserResponse(User user) {
		this.setUser(user);
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
