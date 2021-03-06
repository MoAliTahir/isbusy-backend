package com.isbusy.restapi.isbusyrestapi.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nom;
	private String prenom;
	private String username;
	private String motDePasse;
	private String email;
	private String adresse;
	private String ville;
	private int active;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)

	@JoinTable(name = "user_role", 
				joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
				inverseJoinColumns ={ @JoinColumn(name = "role_id", referencedColumnName = "id")})
	private List<Role> roles;	

	@ManyToMany(mappedBy = "users")
	private List<Emplacement> emplacements;

	public User() {
	}
	
	public User(User user) {
		this.active=user.getActive();
		this.email=user.getEmail();
		this.roles=user.getRoles();
		this.adresse=user.getAdresse();
		this.nom=user.getNom();
		this.prenom=user.getPrenom();
		this.id=user.id;
		this.ville=user.getVille();
		this.username=user.getUsername();
		this.motDePasse=user.getMotDePasse();
	}

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getMotDePasse() {
		return motDePasse;
	}



	public void setMotDePasse(String motDePasse) {
		this.motDePasse = BCrypt.hashpw(motDePasse, BCrypt.gensalt(4));
	}

	public void setPassword(String password){
		this.motDePasse = password;
	}

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getAdresse() {
		return adresse;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public String getVille() {
		return ville;
	}



	public void setVille(String ville) {
		this.ville = ville;
	}



	public int getActive() {
		return active;
	}



	public void setActive(int active) {
		this.active = active;
	}



	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
