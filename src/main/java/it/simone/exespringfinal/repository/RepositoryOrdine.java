package it.simone.exespringfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Ordine;

public interface RepositoryOrdine extends JpaRepository<Ordine, Long> {

}
