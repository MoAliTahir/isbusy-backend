package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;
import com.isbusy.restapi.isbusyrestapi.entities.Categorie;
import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.Classes.GenericEmplacement;
import com.isbusy.restapi.isbusyrestapi.Foursquare.Request;
import com.isbusy.restapi.isbusyrestapi.JSON.JSONException;
import com.isbusy.restapi.isbusyrestapi.services.EmplacementService;
import com.isbusy.restapi.isbusyrestapi.services.EvaluationService;
import com.isbusy.restapi.isbusyrestapi.services.CategorieService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class CategorieController {
    // TODO : Add Response Entity
    /**
     * @var CategorieService singleton
     */
    @Autowired
    private CategorieService categorieService;

    @RequestMapping(method = RequestMethod.GET, value = "/categories/")
    public List<Categorie> allCategories() {
        return categorieService.getAllCategories();
    }

    /**
     * Get Categorie by Id
     * 
     * @param id
     * @return Categorie
     */
    @RequestMapping(method = RequestMethod.GET, value = "/categories/id/{id}")
    public Categorie getCategorieById(@PathVariable String id) {
        if (!categorieService.categorieExists(id))
            return new Categorie(); // TODO : Should be changed to ResponseEntity
        return categorieService.getById(id);
    }

    /**
     * Get Categorie by Id
     * 
     * @param id
     * @return Categorie
     */
    @RequestMapping(method = RequestMethod.GET, value = "/categories/name/{name}")
    public Categorie getCategorieByName(@PathVariable String name) {
        if (!categorieService.categorieExistsByName(name))
            return new Categorie(); // TODO : Should be changed to ResponseEntity
        return categorieService.getByName(name);
    }

    /**
     * Create new Categorie -- TODO : Add Response Entity
     * 
     * @param Categorie
     */
    @RequestMapping(method = RequestMethod.POST, value = "/categories/add")
    public Categorie addCategorie(@RequestBody Categorie categorie) {
        if (categorieService.addCategorie(categorie))
            return categorie;
        return new Categorie(); // TODO : Return Category does not exists in Response Entity
    }

    /**
     * Update Categorie - TODO : fix bug when id
     * 
     * @param Categorie
     */
    @RequestMapping(method = RequestMethod.PATCH, value = "/categories/{id}/update")
    public Categorie updateCategorie(@PathVariable String id, @RequestBody Categorie c) {
        if (!id.equals(c.getId()))
            return new Categorie(); // TODO : Ths should be a 400 error : bad request(id in route and Categorie id
                                    // do not match)

        if (!categorieService.updateCategorie(id, c)) {
            // TODO Categorie not found 404 => return error in Response Entity
            return new Categorie();
        }
        categorieService.updateCategorie(id, c);
        return new Categorie(c); // TODO All ok 200
        }
}
