package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Servizio;
import it.simone.exespringfinal.repository.RepositoryServizio;

@Service
public class ServiceServizio extends AbstractService<Servizio, Long> {

	public final RepositoryServizio repository;

	public ServiceServizio(RepositoryServizio repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<Servizio, Long> getRepository() {
		return repository;
	}

	@Override
	protected String getEntityName() {
		return "Servizio";
	}

	public Servizio updateById(Long id, Servizio entityDetails) {
		return getRepository().findById(id).map(servizio -> {
			servizio.setNome(entityDetails.getNome());
			servizio.setPrezzo(entityDetails.getPrezzo());
			servizio.setDescrizione(entityDetails.getDescrizione());
			return save(servizio);
		}).orElseThrow(() -> new RuntimeException("Servizio con id: " + id + " non trovato"));
	}

}
