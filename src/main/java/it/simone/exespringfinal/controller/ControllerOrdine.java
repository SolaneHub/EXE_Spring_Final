package it.simone.exespringfinal.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.simone.exespringfinal.entity.Ordine;
import it.simone.exespringfinal.service.AbstractService;
import it.simone.exespringfinal.service.ServiceOrdine;

@RestController
@RequestMapping("/ordini")
public class ControllerOrdine extends AbstractController<Ordine, Long> {

	private final ServiceOrdine serviceOrdine;

	public ControllerOrdine(ServiceOrdine serviceOrdine) {
		this.serviceOrdine = serviceOrdine;
	}

	@Override
	protected AbstractService<Ordine, Long> getService() {
		return serviceOrdine;
	}

	@GetMapping("/cliente")
	public List<Ordine> getOrdiniByClienteRagioneSociale(@RequestParam String ragioneSociale) {
		return serviceOrdine.findbyClienteRagioneSociale(ragioneSociale);
	}

	@GetMapping("/servizio")
	public List<Ordine> findByServizio(@RequestParam String nome) {
		return serviceOrdine.findByServizioNome(nome);
	}

	@GetMapping("/cliente-paging")
	public Page<Ordine> getByClienteRagioneSociale(@RequestParam String ragioneSociale,
			@PageableDefault(size = 2, sort = "ragioneSociale") Pageable pageable) {
		return serviceOrdine.getByClienteRagioneSociale(ragioneSociale, pageable);
	}

	@GetMapping("/servizio-paging")
	public Page<Ordine> getByServizioNome(@RequestParam String nome,
			@PageableDefault(size = 2, sort = "nome") Pageable pageable) {
		return serviceOrdine.getByServizioNome(nome, pageable);
	}

}