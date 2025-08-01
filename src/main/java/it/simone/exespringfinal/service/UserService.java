package it.simone.exespringfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	// Es. alcuni metodi
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	public void deleteByUsername(String username) {
		userRepository.deleteByUsername(username);
	}

}
