package it.simone.exespringfinal.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.Indirizzo;
import it.simone.exespringfinal.entity.TipoCliente;
import it.simone.exespringfinal.repository.RepositoryCliente;
import it.simone.exespringfinal.repository.RepositoryIndirizzo;
import it.simone.exespringfinal.repository.RepositoryComune;
import it.simone.exespringfinal.repository.RepositoryProvincia;

@Service
public class ServiceCliente extends AbstractService<Cliente, Long> {

	private RepositoryCliente repositoryCliente;
	private final ServiceIndirizzo serviceIndirizzo;

	public ServiceCliente(RepositoryCliente repositoryCliente, RepositoryIndirizzo repositoryIndirizzo,
			RepositoryComune repositoryComune, RepositoryProvincia repositoryProvincia,
			ServiceIndirizzo serviceIndirizzo) {
		this.repositoryCliente = repositoryCliente;
		this.serviceIndirizzo = serviceIndirizzo;
	}

	@Override
	protected JpaRepository<Cliente, Long> getRepository() {
		return repositoryCliente;
	}

	protected String getEntityName() {
		return "Cliente";
	}

	@Override
	public Cliente updateById(Long id, Cliente entityDetails) {
		return getRepository().findById(id).map(cliente -> {
			cliente.setRagioneSociale(entityDetails.getRagioneSociale());
			cliente.setTipoCliente(entityDetails.getTipoCliente());
			cliente.setPartitaIva(entityDetails.getPartitaIva());

			if (entityDetails.getIndirizzo() != null) {
				Indirizzo indirizzoSalvato = serviceIndirizzo.prepareAndSave(entityDetails.getIndirizzo());
				cliente.setIndirizzo(indirizzoSalvato);
			} else {
				cliente.setIndirizzo(null);
			}

			cliente.setEmail(entityDetails.getEmail());
			cliente.setTelefono(entityDetails.getTelefono());
			cliente.setFatturatoAnnuale(entityDetails.getFatturatoAnnuale());
			return save(cliente);
		}).orElseThrow(() -> new RuntimeException("Cliente con id: " + id + " non trovato"));
	}

	@Override
	public Cliente save(Cliente entity) {
		if (entity.getIndirizzo() != null) {
			Indirizzo indirizzoSalvato = serviceIndirizzo.prepareAndSave(entity.getIndirizzo());
			entity.setIndirizzo(indirizzoSalvato);
		}
		return super.save(entity);
	}

	public List<Cliente> findByRagioneSociale(String ragioneSociale) {
		return repositoryCliente.findByRagioneSociale(ragioneSociale);
	}

	public List<Cliente> findByFatturatoAnnualeBetween(Double min, Double max) {
		return repositoryCliente.findByFatturatoAnnualeBetween(min, max);
	}

	public List<Cliente> findByTipoCliente(TipoCliente tipoCliente) {
		return repositoryCliente.findByTipoCliente(tipoCliente);
	}

	public List<Cliente> findByIndirizzo_Comune_Provincia_Nome(String nomeProvincia) {
		return repositoryCliente.findByIndirizzo_Comune_Provincia_Nome(nomeProvincia);
	}
}
