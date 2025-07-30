package it.simone.exespringfinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Servizio;

public interface RepositoryServizio extends JpaRepository<Servizio, Long> {

	List<Servizio> findByTipoServizioNome(String nome);

}
