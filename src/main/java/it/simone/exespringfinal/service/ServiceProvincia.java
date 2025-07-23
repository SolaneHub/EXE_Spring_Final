package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Provincia;
import it.simone.exespringfinal.repository.RepositoryProvincia;

@Service
public class ServiceProvincia extends AbstractService<Provincia, Long> {

	public final RepositoryProvincia repository;

	public ServiceProvincia(RepositoryProvincia repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<Provincia, Long> getRepository() {
		return repository;
	}
	
	@Override
	protected String getEntityName() {
	    return "Provincia";
	}


	@Override
	public Provincia updateById(Long id, Provincia entityDetails) {
		return getRepository().findById(id).map(provincia->{
			provincia.setNome(entityDetails.getNome());
			provincia.setSigla(entityDetails.getSigla());
			return save(provincia);
		}).orElseThrow(() -> new RuntimeException("Provincia con id: " + id + " non trovata"));
	}
	
	public Provincia findByNome(String nome) {
	    return repository.findByNome(nome).orElse(null);
	}

}
