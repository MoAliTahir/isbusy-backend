package com.isbusy.restapi.isbusyrestapi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Emplacement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nomEmplacement;
	private String categorie;
	private double latitude;
	private double longitude;
	private int status;


	public Emplacement(long idEmplacement, String nomEmplacement, String categorie, float latitude, float longitude, int status) {
		super();
		this.id = idEmplacement;
		this.nomEmplacement = nomEmplacement;
		this.categorie = categorie;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;

	}

	public Emplacement() {
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
