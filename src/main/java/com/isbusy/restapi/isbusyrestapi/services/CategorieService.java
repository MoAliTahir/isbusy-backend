package com.isbusy.restapi.isbusyrestapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isbusy.restapi.isbusyrestapi.entities.Categorie;
import com.isbusy.restapi.isbusyrestapi.repositories.CategorieRepository;

@Service
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        categorieRepository.findAll().forEach(categories::add);
        return categories;
    }

    public boolean categorieExists(String id) {
        return categorieRepository.existsById(id);
    }

    public boolean categorieExistsByName(String name) {
        return categorieRepository.existsByName(name);
    }

    public Categorie getById(String id) {
        return categorieRepository.findById(id).get();
    }

    public Categorie getByName(String name) {
        return categorieRepository.findByName(name).get();
    }
}
