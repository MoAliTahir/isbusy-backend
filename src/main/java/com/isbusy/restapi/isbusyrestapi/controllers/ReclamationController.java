package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.isbusy.restapi.isbusyrestapi.responses.ReclamationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.isbusy.restapi.isbusyrestapi.entities.Categorie;
import com.isbusy.restapi.isbusyrestapi.entities.Reclamation;
import com.isbusy.restapi.isbusyrestapi.entities.Role;
import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.responses.UserResponse;
import com.isbusy.restapi.isbusyrestapi.services.ReclamationService;;

@RestController
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/reclamations")
    public ResponseEntity<ReclamationResponse> getAllReclamations() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("status", "200");
        headers.add("message", "OK");

        return ResponseEntity.ok().headers(headers).body(new ReclamationResponse(null, "Liste des reclamations", 200, reclamationService.getAllReclamations()));
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/reclamations/add")
    public ResponseEntity<ReclamationResponse> addReclamation(@RequestBody Reclamation reclamation) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("status", "200");
        headers.add("message", "OK");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        reclamation.setId_user(currentUser.getId());
        reclamationService.addReclamation(reclamation);
            return ResponseEntity.ok().headers(headers).body(new ReclamationResponse(reclamation, "Reclamation enregistrée avec succès", 200, null));
    }

}

