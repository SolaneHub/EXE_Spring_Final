package it.simone.exespringfinal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Ordine;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceOrdine;

@RestController
@RequestMapping("/ordini")
public class ControllerOrdine extends AbstractController<Ordine, Long> {

	private final ServiceOrdine service;

	public ControllerOrdine(ServiceOrdine service) {
		this.service = service;
	}

	@Override
	protected AbstractService<Ordine, Long> getService() {
		return service;
	}

}