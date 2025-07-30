package it.simone.exespringfinal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Servizio;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceServizio;

@RestController
@RequestMapping("/servizi")
public class ControllerServizio extends AbstractController<Servizio, Long> {

	private final ServiceServizio service;

	public ControllerServizio(ServiceServizio service) {
		this.service = service;
	}

	@Override
	protected AbstractService<Servizio, Long> getService() {
		return service;
	}

	@GetMapping("/tiposervizio")
	public List<Servizio> getServizioByTipoServizioNome(@RequestParam String nome) {
		return service.findByTipoServizioNome(nome);
	}

}
