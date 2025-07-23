package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.TipoServizio;
import it.simone.exespringfinal.repository.RepositoryTipoServizio;

@Service
public class ServiceTipoServizio extends AbstractService<TipoServizio, Long> {

	public final RepositoryTipoServizio repository;

	public ServiceTipoServizio(RepositoryTipoServizio repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<TipoServizio, Long> getRepository() {
		return repository;
	}

	@Override
	protected String getEntityName() {
		return "Tipo Servizio";
	}

	public TipoServizio updateById(Long id, TipoServizio entityDetails) {
		return getRepository().findById(id).map(tipoServizio -> {
			tipoServizio.setNome(entityDetails.getNome());
			tipoServizio.setServizi(entityDetails.getServizi());
			return save(tipoServizio);
		}).orElseThrow(() -> new RuntimeException("TipoServizio con id: " + id + " non trovato"));
	}

}
