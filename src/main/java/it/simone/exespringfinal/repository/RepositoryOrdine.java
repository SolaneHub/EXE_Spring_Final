package it.simone.exespringfinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.Ordine;
import it.simone.exespringfinal.entity.Servizio;

public interface RepositoryOrdine extends JpaRepository<Ordine, Long> {

	List<Ordine> findByCliente(Cliente cliente);

	List<Ordine> findByServizio(Servizio servizio);

}
