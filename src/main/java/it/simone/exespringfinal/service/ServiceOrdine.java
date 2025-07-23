package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Ordine;
import it.simone.exespringfinal.repository.RepositoryOrdine;

@Service
public class ServiceOrdine extends AbstractService<Ordine, Long> {

	public final RepositoryOrdine repository;

	public ServiceOrdine(RepositoryOrdine repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<Ordine, Long> getRepository() {
		return repository;
	}

	@Override
	protected String getEntityName() {
		return "Ordine";
	}

	@Override
	public Ordine updateById(Long id, Ordine entityDetails) {
		return getRepository().findById(id).map(ordine -> {
			ordine.setData(entityDetails.getData());
			ordine.setServizio(entityDetails.getServizio());
			ordine.setCliente(entityDetails.getCliente());
			return save(ordine);
		}).orElseThrow(() -> new RuntimeException("Ordine con id: " + id + " non trovato"));

	}

}
