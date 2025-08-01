package it.simone.exespringfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.service.UserService;

/* CORS
Spring fornisce il supporto a CORS, offrendo un modo semplice e potente per configurarlo in qualsiasi applicazione Web Spring o Spring Boot.
@CrossOrigin, può essere utilizzata a livello di classe Controller o dei singoli metodi 
Le impostazioni predefinite:
- Sono ammesse tutte le origini.
- I metodi HTTP consentiti sono specificabili nell'annotazione @RequestMapping 
- Il tempo in cui la risposta preflight viene memorizzata nella cache (maxAge) è di 30 minuti
*/

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 1800)
//@CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"}, maxAge = 1800)
public class TestController {

	@Autowired
	UserService userService;

	@GetMapping("/public")
	public String allAccess() {
		return "Benvenuto all'endpoint con accesso Pubblico.";
	}

	@GetMapping("/user")
	// Verifica l'autorizzazione prima di entrare nel metodo in base al ruolo
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return "Benvenuto all'endpoint con accesso riservato a User e Admin.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Benvenuto all'endpoint con accesso riservato all'Admin";
	}

	// Esempi Delete
	@GetMapping("/deleteuserbyid/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteUserById(@PathVariable Long id) {
		userService.deleteById(id);
		return "User rimosso";
	}

	@GetMapping("/deleteuserbyname/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteByUsername(@PathVariable String username) {
		userService.deleteByUsername(username);
		return "User rimosso";
	}

}
