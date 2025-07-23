package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Indirizzo;
import it.simone.exespringfinal.repository.RepositoryComune;
import it.simone.exespringfinal.repository.RepositoryIndirizzo;

@Service
public class ServiceIndirizzo extends AbstractService<Indirizzo, Long> {

	private final RepositoryIndirizzo repositoryIndirizzo;
	
	private final RepositoryComune repositoryComune;

	public ServiceIndirizzo(RepositoryIndirizzo repositoryIndirizzo, RepositoryComune repositoryComune) {
		this.repositoryIndirizzo = repositoryIndirizzo;
		this.repositoryComune = repositoryComune;
	}

	@Override
	protected JpaRepository<Indirizzo, Long> getRepository() {
		return repositoryIndirizzo;
	}
	
	@Override
	protected String getEntityName() {
	    return "Indirizzo";
	}
	
	@Override
	public Indirizzo save(Indirizzo entity) {
		if (entity.getComune().getId() == null) {
			repositoryComune.save(entity.getComune());
		}
		return super.save(entity);
	}



	@Override
	public Indirizzo updateById(Long id, Indirizzo entityDetails) {
		return getRepository().findById(id).map(indirizzo -> {
			indirizzo.setVia(entityDetails.getVia());
			indirizzo.setCivico(entityDetails.getCivico());
			indirizzo.setCap(entityDetails.getCap());
			indirizzo.setComune(entityDetails.getComune());
			return save(indirizzo);
		}).orElseThrow(() -> new RuntimeException("Indirizzo con id: " + id + " non trovato"));
	}

}
