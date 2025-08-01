package it.simone.exespringfinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.simone.exespringfinal.entity.Role;
import it.simone.exespringfinal.entity.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleType(RoleType roletype);
}
