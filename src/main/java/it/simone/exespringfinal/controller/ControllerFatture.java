package it.simone.exespringfinal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Fattura;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceFattura;

@RestController
@RequestMapping("/fatture")
public class ControllerFatture extends AbstractController<Fattura, Long> {

	private final ServiceFattura service;

	public ControllerFatture(ServiceFattura service) {
		this.service = service;
	}

	@Override
	protected AbstractService<Fattura, Long> getService() {
		return service;
	}

}