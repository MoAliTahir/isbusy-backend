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
	private int status;

	public Emplacement(String idEmplacement, String nomEmplacement, String categorie, float latitude, float longitude,
			int status) {
		super();
		this.id = idEmplacement;
		this.nomEmplacement = nomEmplacement;
		this.categorie = categorie;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;

	}

	public Emplacement(String id, double longitude, double latitude, String name, String category) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.nomEmplacement = name;
		this.categorie = category;
	}

	public Emplacement() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNomEmplacement(String nomEmplacement) {
		this.nomEmplacement = nomEmplacement;
	}

	public String getCategorie() {
		return this.categorie;

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
