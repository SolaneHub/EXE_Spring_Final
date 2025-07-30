package it.simone.exespringfinal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Ordine;
import it.simone.exespringfinal.repository.RepositoryOrdine;

@Service
public class ServiceOrdine extends AbstractService<Ordine, Long> {

	private final RepositoryOrdine repositoryOrdine;

	public ServiceOrdine(RepositoryOrdine repositoryOrdine) {
		this.repositoryOrdine = repositoryOrdine;
	}

	@Override
	protected JpaRepository<Ordine, Long> getRepository() {
		return repositoryOrdine;
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

	public List<Ordine> findbyClienteRagioneSociale(String ragioneSociale) {
		return repositoryOrdine.findByClienteRagioneSociale(ragioneSociale);
	}

	public List<Ordine> findByServizioNome(String nome) {
		return repositoryOrdine.findByServizioNome(nome);
	}

	public Page<Ordine> getByClienteRagioneSociale(String ragioneSociale, Pageable pageable) {
		return repositoryOrdine.findByCliente_RagioneSociale(ragioneSociale, pageable);
	}

	public Page<Ordine> getByServizioNome(String nomeServizio, Pageable pageable) {
		return repositoryOrdine.findByServizio_Nome(nomeServizio, pageable);
	}

}
