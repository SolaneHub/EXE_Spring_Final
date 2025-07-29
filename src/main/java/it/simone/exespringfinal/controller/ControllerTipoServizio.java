package it.simone.exespringfinal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.TipoServizio;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceTipoServizio;

@RestController
@RequestMapping("/tiposervizio")
public class ControllerTipoServizio extends AbstractController<TipoServizio, Long> {

	private final ServiceTipoServizio service;

	public ControllerTipoServizio(ServiceTipoServizio service) {
		this.service = service;
	}

	@Override
	protected AbstractService<TipoServizio, Long> getService() {
		return service;
	}

}
