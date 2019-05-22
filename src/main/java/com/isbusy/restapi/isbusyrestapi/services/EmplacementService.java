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
		return emplacementRepository.findById(id).get();
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

	// Check if Emplacement exists By ID
	public boolean emplacementExists(String id) {
		return emplacementRepository.existsById(id);
	}

	// Check if Emplacement exists byName
	public boolean emplacementExistsByName(String name) {
		return emplacementRepository.existsByNomEmplacement(name);
	}

	// Get Emplacement By Name
	public ArrayList<Emplacement> getEmplacementsByName(String name) {
		ArrayList<Emplacement> emplacements = new ArrayList<>();
		emplacements.addAll(emplacementRepository.findAllByNomEmplacement(name));
		return emplacements;
	}

	// Get Emplacement by Categorie
	public ArrayList<Emplacement> getEmplacementsByCategorie(String categorieId) {
		ArrayList<Emplacement> emplacements = new ArrayList<>();
		emplacements.addAll(emplacementRepository.findAllByCategorie(categorieId));
		return emplacements;
	}
}
