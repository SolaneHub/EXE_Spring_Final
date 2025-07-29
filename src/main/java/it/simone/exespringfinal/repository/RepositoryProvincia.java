package it.simone.exespringfinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Provincia;

public interface RepositoryProvincia extends JpaRepository<Provincia, Long> {

	Optional<Provincia> findByNome(String nome);

	Optional<Provincia> findByNomeAndSigla(String nome, String sigla);
}
