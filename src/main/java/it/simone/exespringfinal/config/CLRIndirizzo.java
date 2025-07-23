package it.simone.exespringfinal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.simone.exespringfinal.entity.Indirizzo;
import it.simone.exespringfinal.service.ServiceComune;
import it.simone.exespringfinal.service.ServiceIndirizzo;

@Component
@Order(2)
public class CLRIndirizzo implements CommandLineRunner {

	private final ServiceIndirizzo serviceIndirizzo;
	private final ServiceComune serviceComune;

	public CLRIndirizzo(ServiceIndirizzo serviceIndirizzo, ServiceComune serviceComune) {
		this.serviceIndirizzo = serviceIndirizzo;
		this.serviceComune = serviceComune;
	}

	@Override
	public void run(String... args) throws Exception {
		if (serviceIndirizzo.count() == 0) {
			serviceIndirizzo.save(new Indirizzo("Via Roma", "12", "10011", serviceComune.findByIdOrThrow(1L)));
			serviceIndirizzo.save(new Indirizzo("Via Torino", "45", "10012", serviceComune.findByIdOrThrow(2L)));
			serviceIndirizzo.save(new Indirizzo("Via Milano", "7A", "10013", serviceComune.findByIdOrThrow(3L)));
			serviceIndirizzo.save(new Indirizzo("Corso Dante", "23", "10014", serviceComune.findByIdOrThrow(4L)));
			serviceIndirizzo.save(new Indirizzo("Piazza Garibaldi", "3", "10015", serviceComune.findByIdOrThrow(5L)));
			serviceIndirizzo.save(new Indirizzo("Via Verdi", "56", "10016", serviceComune.findByIdOrThrow(6L)));
			serviceIndirizzo.save(new Indirizzo("Via Manzoni", "89", "10017", serviceComune.findByIdOrThrow(7L)));
			serviceIndirizzo.save(new Indirizzo("Via Mazzini", "101", "10018", serviceComune.findByIdOrThrow(8L)));
			serviceIndirizzo.save(new Indirizzo("Viale dei Tigli", "5B", "10019", serviceComune.findByIdOrThrow(9L)));
			serviceIndirizzo.save(new Indirizzo("Via Leopardi", "77", "10020", serviceComune.findByIdOrThrow(10L)));

		}
	}
}
