package it.simone.exespringfinal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Indirizzo;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceIndirizzo;

@RestController
@RequestMapping("/indirizzi")
public class ControllerIndirizzo extends AbstractController<Indirizzo, Long>{

	private final ServiceIndirizzo service;

	public ControllerIndirizzo(ServiceIndirizzo service) {
		this.service = service;
	}

	@Override
	protected AbstractService<Indirizzo, Long> getService() {
		return service;
	}
}
