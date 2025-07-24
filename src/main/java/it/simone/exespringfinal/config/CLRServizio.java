package it.simone.exespringfinal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.simone.exespringfinal.entity.Servizio;
import it.simone.exespringfinal.service.ServiceCliente;
import it.simone.exespringfinal.service.ServiceServizio;
import it.simone.exespringfinal.service.ServiceTipoServizio;

@Component
@Order(5)
public class CLRServizio implements CommandLineRunner {

	public final ServiceServizio serviceServizio;
	public final ServiceTipoServizio serviceTipoServizio;

	public CLRServizio(ServiceServizio serviceServizio, ServiceTipoServizio serviceTipoServizio,
			ServiceCliente serviceCliente) {
		this.serviceServizio = serviceServizio;
		this.serviceTipoServizio = serviceTipoServizio;
	}

	public void run(String... args) throws Exception {
		if (serviceServizio.count() == 0) {

			serviceServizio.save(new Servizio("Consulenza legale", 200.0, "Supporto legale per contratti",
					serviceTipoServizio.findByIdOrThrow(1L)));
			serviceServizio.save(new Servizio("Consulenza IT", 150.0, "Analisi infrastruttura IT",
					serviceTipoServizio.findByIdOrThrow(1L)));
			
			serviceServizio.save(
					new Servizio("Corso Java", 300.0, "Corso base Java SE", serviceTipoServizio.findByIdOrThrow(2L)));
			serviceServizio.save(new Servizio("Corso Spring Boot", 400.0, "Corso avanzato Spring Boot",
					serviceTipoServizio.findByIdOrThrow(2L)));

			serviceServizio.save(new Servizio("Manutenzione Hardware", 120.0, "Controllo e sostituzione componenti",
					serviceTipoServizio.findByIdOrThrow(3L)));
			serviceServizio.save(new Servizio("Manutenzione Software", 180.0, "Aggiornamenti e sicurezza software",
					serviceTipoServizio.findByIdOrThrow(3L)));
		}
	}
}
