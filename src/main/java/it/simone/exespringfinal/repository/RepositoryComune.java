package it.simone.exespringfinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Comune;
import it.simone.exespringfinal.entity.Provincia;

public interface RepositoryComune extends JpaRepository<Comune, Long> {
	
	Optional<Comune> findByNomeAndProvincia(String nome, Provincia provincia);

}
