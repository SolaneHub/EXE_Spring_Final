package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Fattura;
import it.simone.exespringfinal.repository.RepositoryFattura;

@Service
public class ServiceFattura extends AbstractService<Fattura, Long> {

	private final RepositoryFattura repository;

	public ServiceFattura(RepositoryFattura repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<Fattura, Long> getRepository() {
		return repository;
	}

	@Override
	protected String getEntityName() {
		return "Fattura";
	}

	@Override
	public Fattura updateById(Long id, Fattura entityDetails) {
		return getRepository().findById(id).map(fattura -> {
			fattura.setData(entityDetails.getData());
			fattura.setImporto(entityDetails.getImporto());
			fattura.setStato(entityDetails.getStato());
			fattura.setCliente(entityDetails.getCliente());
			return save(fattura);
		}).orElseThrow(() -> new RuntimeException("Fattura con id: " + id + " non trovato"));
	}

}
