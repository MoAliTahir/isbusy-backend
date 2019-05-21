package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.isbusy.restapi.isbusyrestapi.entities.User;

public interface UserRepository extends  CrudRepository<User,Long>{

	Optional<User> findByUsername(String username);
	
	//public boolean exists(Long primaryKey);

}
