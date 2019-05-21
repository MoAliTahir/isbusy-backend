package com.isbusy.restapi.isbusyrestapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.repositories.EmplacementRepository;

@Service
public class EmplacementService {
	@Autowired
	private EmplacementRepository emplacementRepository;

	public List<Emplacement> getAllEmplacements() {
		List<Emplacement> emplacements = new ArrayList<>();
		emplacementRepository.findAll().forEach(emplacements::add);
		return emplacements;
	}

	public Emplacement getEmplacement(String id) {
		return emplacementRepository.findById(id).get();// we can use .orElse(Null) or .orElse(new Topic);
	}

	public void addEmplacement(Emplacement e) {
		emplacementRepository.save(e);
	}

	// check
	public void deleteEmplacement(String id) {
		emplacementRepository.deleteById(id);
	}

	// check
	public void updateEmplacement(String id, Emplacement e) {
		emplacementRepository.save(e);
	}

}
