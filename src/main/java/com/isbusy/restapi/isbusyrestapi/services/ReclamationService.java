package com.isbusy.restapi.isbusyrestapi.services;
import java.util.ArrayList;
import java.util.List;
import com.isbusy.restapi.isbusyrestapi.repositories.ReclamationRepository;

import com.isbusy.restapi.isbusyrestapi.entities.Reclamation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository;

    public List<Reclamation> getAllReclamations() {
        List<Reclamation> reclamations = new ArrayList<>();
        reclamationRepository.findAll().forEach(reclamations::add);
    return reclamations;    }
    
    public void addReclamation(Reclamation r) {
        reclamationRepository.save(r);
    }
}