package it.simone.exespringfinal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.TipoCliente;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceCliente;

@RestController
@RequestMapping("/clienti")
public class ControllerCliente extends AbstractController<Cliente, Long> {

	private final ServiceCliente service;

	public ControllerCliente(ServiceCliente service) {
		this.service = service;
	}

	@Override
	protected AbstractService<Cliente, Long> getService() {
		return service;
	}

	@GetMapping("/ragionesociale")
	public List<Cliente> getClientiByRagioneSociale(@RequestParam String ragioneSociale) {
		return service.findByRagioneSociale(ragioneSociale);
	}

	@GetMapping("/fatturato")
	public List<Cliente> getClientiByFatturato(@RequestParam Double min, @RequestParam Double max) {
		return service.findByFatturatoAnnualeBetween(min, max);
	}
	
	@GetMapping("/tipocliente")
	public List<Cliente> getClientiByTipoCliente(@RequestParam TipoCliente tipoCliente){
		return service.findByTipoCliente(tipoCliente);
	}

	@GetMapping("/provincia")
	public List<Cliente> getClientiByComune_Provincia_Nome(@RequestParam String provincia) {
		return service.findByIndirizzo_Comune_Provincia_Nome(provincia);
	}
}
