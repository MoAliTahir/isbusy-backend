package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;
import java.util.Iterator;

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
/*
    @RequestMapping(method = RequestMethod.GET, value = "/reclamations")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReclamationResponse> getAllReclamations() {
		HttpHeaders headers = new HttpHeaders();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		Iterator iterator = currentUser.getRoles().iterator();
		Role role = (Role) iterator.next();
		if (!role.getRole().equals("ADMIN")) {
			headers.add("status", "401");
			headers.add("message", "Unauthorized");

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers)
					.body(new UserResponse(currentUser, "Lack of privileges", 401));
		}

		headers.add("status", "200");
		headers.add("message", "OK");
		return ResponseEntity.ok().headers(headers).body(new UserResponse(userService.getAllUsers(), "Succes", 200));
    }
    }*/
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/reclamations")
    public List<Reclamation> getAllEvaluations() {
        return reclamationService.getAllReclamations();
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/reclamations/add")
    public Reclamation addReclamation(@RequestBody Reclamation reclamation) {
        reclamationService.addReclamation(reclamation);
            return reclamation;
    }

}

