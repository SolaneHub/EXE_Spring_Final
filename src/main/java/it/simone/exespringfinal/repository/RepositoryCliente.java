package it.simone.exespringfinal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.TipoCliente;

public interface RepositoryCliente extends JpaRepository<Cliente, Long> {

	List<Cliente> findByRagioneSociale(String ragioneSociale);

	List<Cliente> findByFatturatoAnnualeBetween(Double min, Double max);

	List<Cliente> findByTipoCliente(TipoCliente tipoCliente);

	List<Cliente> findByIndirizzoComuneProvinciaNome(String nomeProvincia);

	Page<Cliente> findByFatturatoAnnualeBetween(Double min, Double max, Pageable pageable);

	Page<Cliente> findByTipoCliente(TipoCliente tipoCliente, Pageable pageable);

	Page<Cliente> findByIndirizzoComuneProvinciaNome(String nomeProvincia, Pageable pageable);

}
