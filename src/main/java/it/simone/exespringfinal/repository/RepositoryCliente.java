package it.simone.exespringfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.TipoCliente;

import java.util.List;


public interface RepositoryCliente extends JpaRepository<Cliente, Long>{
	
	List<Cliente> findByRagioneSociale(String ragioneSociale);
	List<Cliente> findByFatturatoAnnualeBetween(Double min, Double max);
	List<Cliente> findByTipoCliente(TipoCliente tipoCliente);
	List<Cliente> findByIndirizzo_Comune_Provincia_Nome(String nomeProvincia);

}
