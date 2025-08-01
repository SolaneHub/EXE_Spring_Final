package it.simone.exespringfinal;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class Preset implements CommandLineRunner {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(String... args) throws Exception {
		createRolesAndAdmin();
	}
		
	private void createRolesAndAdmin() {		
		/*
		// Crea ruoli
		String query = "INSERT INTO auth_roles(role_type) VALUES('ROLE_ADMIN');";
		jdbcTemplate.update(query);
		query = "INSERT INTO auth_roles(role_type) VALUES('ROLE_USER');";
		jdbcTemplate.update(query);
		*/
		/*
		// Crea User (Non eseguire se la password viene criptata)
		query = "INSERT INTO users(active, nome, cognome, email, password, username) VALUES (1, 'Ugo', 'Betori', 'ugo@betori.it', '1234', 'admin');";
		jdbcTemplate.update(query);
		// Associa User al ruolo Admin
		query = "INSERT INTO user_roles(user_id, roles_id) VALUES (1, 1);";
		jdbcTemplate.update(query);
		*/

	}

}
