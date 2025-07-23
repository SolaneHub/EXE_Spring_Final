package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Comune;
import it.simone.exespringfinal.repository.RepositoryComune;
import it.simone.exespringfinal.repository.RepositoryProvincia;

@Service
public class ServiceComune extends AbstractService<Comune, Long> {

	public final RepositoryComune repositoryComune;
	public final RepositoryProvincia repositoryProvincia;

	public ServiceComune(RepositoryComune repositoryComune, RepositoryProvincia repositoryProvincia) {
		this.repositoryComune = repositoryComune;
		this.repositoryProvincia = repositoryProvincia;
	}

	@Override
	protected JpaRepository<Comune, Long> getRepository() {
		return repositoryComune;
	}
	
	@Override
	protected String getEntityName() {
	    return "Comune";
	}
	
	@Override
	public Comune save(Comune entity) {
		if (entity.getProvincia().getId() == null) {
			repositoryProvincia.save(entity.getProvincia());
		}
		return super.save(entity);
	}

	@Override
	public Comune updateById(Long id, Comune entityDetails) {
		return getRepository().findById(id).map(comune -> {
			comune.setNome(entityDetails.getNome());
			comune.setProvincia(entityDetails.getProvincia());
			return save(comune);
		}).orElseThrow(() -> new RuntimeException("Comune con id: " + id + " non trovato"));

	}

}
