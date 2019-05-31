package com.isbusy.restapi.isbusyrestapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.repositories.UserRepository;
import com.isbusy.restapi.isbusyrestapi.repositories.EmplacementRepository;

@Service
public class UserService<Favorie> {
	@Autowired
	private UserRepository userRepository; 
	@Autowired
	private EmplacementRepository emplacementRepository;
	
	
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

	public boolean userExists(Long id){
		return userRepository.existsById(id);
	}


	public ArrayList<Emplacement> getAllFavoris(long userId) {
		ArrayList<Emplacement> favoris = new ArrayList<>();

		List<Emplacement> e = emplacementRepository.findFavoris(userId);
		favoris.addAll(e);
		return favoris;
	}

	public void addFavoris(String emplacementId, long userId) {
		emplacementRepository.addFavoris(emplacementId, userId);

	}

	public void deleteFavoris(String emplacementId) {
		emplacementRepository.deleteFavorisByEmplacementId(emplacementId);
	}

	public int countUsers(){
		 return userRepository.getCountUsers();
	}
}
