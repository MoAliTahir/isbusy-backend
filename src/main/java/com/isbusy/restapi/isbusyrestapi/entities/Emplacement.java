package com.isbusy.restapi.isbusyrestapi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Emplacement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String nomEmplacement;
	private String categorie;
	private double latitude;
	private double longitude;


	public Emplacement(String idEmplacement, String nomEmplacement, String categorie, float latitude, float longitude) {
		super();
		this.id = idEmplacement;
		this.nomEmplacement = nomEmplacement;
		this.categorie = categorie;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Emplacement() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomEmplacement() {
		return nomEmplacement;
	}

	public void setNomEmplacement(String nomEmplacement) {
		this.nomEmplacement = nomEmplacement;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
