package it.simone.exespringfinal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import it.simone.exespringfinal.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByNome(String nome, Pageable pageable);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Transactional
	void deleteByUsername(String username);

	@Transactional
	void deleteById(Long id);

}
