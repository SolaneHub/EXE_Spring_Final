package it.simone.exespringfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Fattura;

public interface RepositoryFattura extends JpaRepository<Fattura, Long> {

}
