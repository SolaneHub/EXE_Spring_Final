package it.simone.exespringfinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Ordine;

public interface RepositoryOrdine extends JpaRepository<Ordine, Long> {

	List<Ordine> findByClienteRagioneSociale(String ragioneSociale);

	List<Ordine> findByServizioNome(String nome);

}
