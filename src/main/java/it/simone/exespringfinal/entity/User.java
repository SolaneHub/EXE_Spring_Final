package it.simone.exespringfinal.entity;

import java.util.HashSet;
import java.util.Set;

import it.simone.security.StringAttributeConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String nome;
	private String cognome;
	/*
	 * Una volta definito il converter, è possibile annotare le proprietà che si
	 * intende cifrare, in modo che il motore di persistenza JPA possa effettuare le
	 * operazioni di conversione
	 */
	@Convert(converter = StringAttributeConverter.class)
	private String email;
	private Boolean active = true;
	private String password;
	// JPA: propaga tutte le operazioni dall'entità padre all'entità figlio
	@ManyToMany(cascade = CascadeType.ALL)
	// JPA: mapping alla tebella del db
	@JoinTable(name = "user_roles")
	private Set<Role> roles = new HashSet<>();

	// Costruttore default
	public User() {
	}

	public User(Long id, String username, @Email String email, String password, String nome, String cognome) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
	}

	@Override
	public String toString() {
		return String.format("User: Id=%d, Nome=%s, Username=%s , Email=%d", id, nome, username, email);
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean getActive() {
		return active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;

	}

}
