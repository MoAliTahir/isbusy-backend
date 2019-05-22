package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.Classes.GenericEmplacement;
import com.isbusy.restapi.isbusyrestapi.Foursquare.Request;
import com.isbusy.restapi.isbusyrestapi.JSON.JSONException;
import com.isbusy.restapi.isbusyrestapi.services.EmplacementService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class EmplacementController {

	@Autowired
	private EmplacementService emplacementService;

	// index
	@RequestMapping("/emplacements")
	public List<Emplacement> getAllEmplacements() {
		return emplacementService.getAllEmplacements();
	}

	// ------------------------------user searching for places
	// around----------------------------------------------------------
	// user provided latlong to get specific places around
	@RequestMapping("/emplacement/{keyword}/{lat}/{lon}")
	public void getEmplacementsByLatLong(@PathVariable String keyword, @PathVariable String lat,
			@PathVariable String lon) {
		System.out.println(keyword + "," + lat + "," + lon);
		// getId from API
		// check if ID is in table
		// send object if yes
		// get object from API(if not found )
		// insert it in db
		// return object
	}

	// show
	@RequestMapping("/emplacements/{id}")
	@CrossOrigin
	public Emplacement getEmplacement(@PathVariable String id) {
		return emplacementService.getEmplacement(id);
	}

	// create
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public Object test() {
		System.out.println("-----------------------");
		System.out.println("connected");
		System.out.println("------------------------");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return auth.getPrincipal();
	}

	// create
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/create")
	public void addEmplacement(@RequestBody Emplacement emplacement) {
		emplacementService.addEmplacement(emplacement);
	}

	// create
	@RequestMapping(method = RequestMethod.PUT, value = "/emplacements/update/{id}")
	public void updateEmplacement(@RequestBody Emplacement emplacement, @PathVariable String id) {
		emplacementService.updateEmplacement(id, emplacement);
	}

	// delete
	@RequestMapping(method = RequestMethod.DELETE, value = "/emplacements/delete/{id}")
	public void deleteEmplacement(@PathVariable String id) {
		emplacementService.deleteEmplacement(id);
	}

	/**
	 * Testing Foursquare API
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/test")
	public ArrayList<GenericEmplacement> testAPI() {
		Request request = new Request();
		try {
			ArrayList<GenericEmplacement> places = request.getNearbyGenericEmplacements("category",
					"4bf58dd8d48988d16d941735", "10000", "35.581186,-5.350481");
			return places;
		} catch (JSONException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
