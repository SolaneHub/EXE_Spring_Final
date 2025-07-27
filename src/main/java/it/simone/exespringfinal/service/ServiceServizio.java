package it.simone.exespringfinal.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Servizio;
import it.simone.exespringfinal.repository.RepositoryServizio;
import it.simone.exespringfinal.repository.RepositoryTipoServizio;

@Service
public class ServiceServizio extends AbstractService<Servizio, Long> {

	public final RepositoryServizio repositoryServizio;
	public final RepositoryTipoServizio repositoryTipoServizio;

	public ServiceServizio(RepositoryServizio repositoryServizio, RepositoryTipoServizio repositoryTipoServizio) {
		this.repositoryServizio = repositoryServizio;
		this.repositoryTipoServizio = repositoryTipoServizio;
	}

	@Override
	protected JpaRepository<Servizio, Long> getRepository() {
		return repositoryServizio;
	}

	@Override
	protected String getEntityName() {
		return "Servizio";
	}

	@Override
	public Servizio save(Servizio entity) {
		if (entity.getTipoServizio() != null) {
			if (entity.getTipoServizio().getId() == null) {
				var nuovoTipoServizio = repositoryTipoServizio.save(entity.getTipoServizio());
				entity.setTipoServizio(nuovoTipoServizio);
			} else {
				var tipoServizioEsistente = repositoryTipoServizio.findById(entity.getTipoServizio().getId())
						.orElseThrow(() -> new RuntimeException(
								"TipoServizio con id " + entity.getTipoServizio().getId() + " non trovato"));
				entity.setTipoServizio(tipoServizioEsistente);
			}
		}

		return super.save(entity);
	}

	public Servizio updateById(Long id, Servizio entityDetails) {
		return getRepository().findById(id).map(servizio -> {
			servizio.setNome(entityDetails.getNome());
			servizio.setPrezzo(entityDetails.getPrezzo());
			servizio.setDescrizione(entityDetails.getDescrizione());
			return save(servizio);
		}).orElseThrow(() -> new RuntimeException("Servizio con id: " + id + " non trovato"));
	}

	@Override
	public void deleteById(Long id) {
		Servizio servizio = repositoryServizio.findById(id)
				.orElseThrow(() -> new RuntimeException("Servizio con id: " + id + " non trovato"));

		var tipoServizio = servizio.getTipoServizio();

		super.deleteById(id);

		if (tipoServizio != null) {
			repositoryTipoServizio.delete(tipoServizio);
		}
	}

}
