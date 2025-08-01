package it.simone.exespringfinal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.simone.exespringfinal.entity.User;
import it.simone.exespringfinal.repository.UserRepository;

/* UserDetailsServiceImpl 
Implementa l’interfaccia UserDetailsService fornita dal modulo Spring Security, 
utilizzata per recuperare i dati relativi all'utente. 
Il metodo loadUserByUsername() può essere sovrascritto per personalizzare il processo di ricerca dell'utente
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	// Cerca l'utente nel DB e ritorna l'utente tramite l'implementazione di
	// UserDetailsImpl oppure un'eccezione
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			return UserDetailsImpl.build(user.get());
		} else {
			throw new UsernameNotFoundException("Non esiste un utente con questo username: " + username);
		}
	}

}
