package it.simone.exespringfinal.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Role;
import it.simone.exespringfinal.entity.RoleType;
import it.simone.exespringfinal.entity.User;
import it.simone.exespringfinal.repository.RoleRepository;
import it.simone.exespringfinal.repository.UserRepository;
import it.simone.exespringfinal.service.UserDetailsImpl;
import it.simone.payload.request.LoginRequest;
import it.simone.payload.request.SignupRequest;
import it.simone.payload.response.LoginResponse;
import it.simone.payload.response.SignupResponse;
import it.simone.security.JwtUtils;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	// @Autowired
	// JwtUtils jwtUtils;
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/myroletype")
	public Optional<Role> getRoleType() {
		return roleRepository.findByRoleType(RoleType.ROLE_ADMIN);
	}

	@PostMapping("/login")
	// @Valid: verifica che il formato della request corrisponda al modello
	// (LoginRequest)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		// Usa l'AuthenticationManager per autenticare i parametri della request
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		// Ottiene i privilegi dell'utente
		authentication.getAuthorities();
		// Ottiene il SecurityContext e vi imposta l'autenticazione
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Genera il token
		String jwt = JwtUtils.generateJwtToken(authentication);
		// getPrincipal(), ottiene i dati dell'utente
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		// Restituisce la response con status 200, token e dati dell'utente
		return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles, userDetails.getExpirationTime()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		// Verifica l'esistenza di Username e Email già registrate
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Attenzione: Username già in uso!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Attenzione: Email già in uso!"));
		}
		// Crea un nuovo user dai dati della request codificando la password
		User user = new User(null, signUpRequest.getUsername(), signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getNome(),
				signUpRequest.getCognome());
		// Ottiene l'eventuale ruolo presente nella request
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		// Se il ruolo è presente nella request lo assegna allo user. In caso contrario
		// assegna il ROLE_USER come default
		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByRoleType(RoleType.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new SignupResponse("User registrato con successo!"));
	}

}
