package it.simone.exespringfinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Fattura;
import it.simone.exespringfinal.entity.StatoFattura;

public interface RepositoryFattura extends JpaRepository<Fattura, Long> {

	List<Fattura> findByClienteRagioneSociale(String ragioneSociale);

	List<Fattura> findByStato(StatoFattura stato);

}
