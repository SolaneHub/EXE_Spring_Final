package it.simone.exespringfinal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Servizio;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceServizio;

@RestController
@RequestMapping("/servizio")
public class ControllerServizio extends AbstractController<Servizio, Long> {

	private final ServiceServizio service;

	public ControllerServizio(ServiceServizio service) {
		this.service = service;
	}

	@Override
	protected AbstractService<Servizio, Long> getService() {
		return service;
	}

}
