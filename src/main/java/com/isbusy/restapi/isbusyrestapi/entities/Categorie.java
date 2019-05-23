package com.isbusy.restapi.isbusyrestapi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.isbusy.restapi.isbusyrestapi.Classes.GenericEmplacement;

@Entity
@Table(name = "categorie")

public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    @Id
    private String name;
    private String shortName = "";

    // Constructors
    public Categorie() {

    }

    public Categorie(String id, String name, String shortName) {
        setId(id);
        setName(name);
        setShortName(shortName);
    }

    public Categorie(Categorie c) {
        setId(c.getId());
        setName(c.getName());
        setShortName(c.getShortName());
    }

    /**
     * 
     * Getters
     * 
     * @return String
     */
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }

    /**
     * Setters
     * 
     * @param String
     */
    public void setId(String value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setShortName(String value) {
        this.shortName = value;
    }

}
