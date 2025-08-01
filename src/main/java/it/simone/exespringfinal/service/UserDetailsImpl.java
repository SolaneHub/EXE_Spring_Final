package it.simone.exespringfinal.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.simone.exespringfinal.entity.User;

/* 
Implementa l’interfaccia UserDetails fornita dal modulo Spring Security.
UserDetails è un contenitore per le informazioni principali dell'utente. 
Memorizza le informazioni dell'utente che vengono successivamente incapsulate negli oggetti di autenticazione. 
*/
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = -7130684040991684077L;
	private Long id;
	private String username;
	private String email;
	// Esclude dalla serializzazione
	@JsonIgnore
	private String password;

	// Proprietà dell'utente: abilitato, bloccato, scaduto, password scaduta
	// Locked: account sospeso automaticamente a causa di tentativi di accesso non
	// validi
	// Expired: account disattivato amministrativamente in base a un timing (es.
	// abbonamento scaduto)
	// CredentialsNonExpired: scadenza delle credenziali (password)
	private boolean enabled = true;
	private boolean accountNonLocked = true;
	private boolean accountNonExpired = false;
	private boolean credentialsNonExpired = true;
	private Date expirationTime;
	// GrantedAuthority, rappresenta un'autorizzazione concessa (lettura, scrittura,
	// ecc)
	private Collection<? extends GrantedAuthority> authorities;

	// Costruttore
	public UserDetailsImpl(Long id, String username, String email, String password, boolean enabled,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.accountNonLocked = enabled;
		this.accountNonExpired = enabled;
		this.credentialsNonExpired = enabled;
		this.enabled = enabled;
		this.authorities = authorities;
	}

	// Builder
	public static UserDetailsImpl build(User user) {
		// SimpleGrantedAuthority: implementazione base di GrantedAuthority
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority((role.getRoleType()).name())).collect(Collectors.toList());
		// Restituisce i dati dello user e la lista delle sue autorizzazioni
		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
				user.getActive(), authorities);
	}

	/* Getters & Setters */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setExpirationTime(Date exp) {
		expirationTime = exp;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}
}
