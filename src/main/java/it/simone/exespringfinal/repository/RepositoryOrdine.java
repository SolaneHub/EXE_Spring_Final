package it.simone.exespringfinal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Ordine;

public interface RepositoryOrdine extends JpaRepository<Ordine, Long> {

	List<Ordine> findByClienteRagioneSociale(String ragioneSociale);

	List<Ordine> findByServizioNome(String nome);

	Page<Ordine> findByCliente_RagioneSociale(String ragioneSociale, Pageable pageable);

	Page<Ordine> findByServizio_Nome(String nome, Pageable pageable);
}
