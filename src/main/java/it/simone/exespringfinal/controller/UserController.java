package it.simone.exespringfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.User;
import it.simone.exespringfinal.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	// @PreAuthorize("hasRole('ADMIN')")
	public Page<User> userList(Pageable pageable) {
		Page<User> findAll = userRepository.findAll(pageable);
		findAll.forEach(usr -> usr.setPassword("********"));
		return findAll;
	}

}
