package it.simone.exespringfinal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Fattura;
import it.simone.exespringfinal.entity.StatoFattura;
import it.simone.exespringfinal.repository.RepositoryFattura;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceFattura;

@RestController
@RequestMapping("/fatture")
public class ControllerFatture extends AbstractController<Fattura, Long> {

	private final RepositoryFattura repositoryFattura;

	private final ServiceFattura service;

	public ControllerFatture(ServiceFattura service, RepositoryFattura repositoryFattura) {
		this.service = service;
		this.repositoryFattura = repositoryFattura;
	}

	@Override
	protected AbstractService<Fattura, Long> getService() {
		return service;
	}

	@GetMapping("/cliente")
	public List<Fattura> findByClienteRagioneSociale(@RequestParam String ragioneSociale) {
		return repositoryFattura.findByClienteRagioneSociale(ragioneSociale);
	}

	@GetMapping("/statofattura")
	public List<Fattura> findByStato(@RequestParam StatoFattura stato) {
		return repositoryFattura.findByStato(stato);
	}

}