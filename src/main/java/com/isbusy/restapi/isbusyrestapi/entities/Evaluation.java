package com.isbusy.restapi.isbusyrestapi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Evaluation implements Serializable {
	/**
	 * @var Final Wight Values
	 */
	public static final double WEIGHT_FERME = 343 / 25;
	public static final double WEIGHT_PLEIN = 49 / 5;
	public static final double WEIGHT_MOYEN = 7;
	public static final double WEIGHT_VIDE = 5;

	public static final double FERME = 3;
	public static final double PLEIN = 2;
	public static final double MOYEN = 1;
	public static final double VIDE = 0;

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int vote;
	private long idUser;
	private String description;
	@ManyToOne
	private Emplacement emplacement;
	private String jour;
	private int heure;

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public int getHeure() {
		return heure;
	}

	public void setHeure(int heure) {
		this.heure = heure;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Emplacement getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(Emplacement emplacement) {
		this.emplacement = emplacement;
	}

	/**
	 * Weight Mapper : Maps vote value
	 */
	public double weightMapper() {
		int vote = getVote();
		if (vote == FERME)
			return WEIGHT_FERME;
		else if (vote == PLEIN)
			return WEIGHT_PLEIN;
		else if (vote == MOYEN)
			return WEIGHT_MOYEN;
		else
			return WEIGHT_VIDE;
	}
}
