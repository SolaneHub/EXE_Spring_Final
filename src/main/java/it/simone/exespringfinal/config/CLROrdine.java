package it.simone.exespringfinal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.Ordine;
import it.simone.exespringfinal.entity.Servizio;
import it.simone.exespringfinal.service.ServiceCliente;
import it.simone.exespringfinal.service.ServiceOrdine;
import it.simone.exespringfinal.service.ServiceServizio;

//@Component
@Order(6)
public class CLROrdine implements CommandLineRunner {

	private final ServiceOrdine serviceOrdine;
	private final ServiceServizio serviceServizio;
	private final ServiceCliente serviceCliente;

	public CLROrdine(ServiceOrdine serviceOrdine, ServiceServizio serviceServizio, ServiceCliente serviceCliente) {
		this.serviceOrdine = serviceOrdine;
		this.serviceServizio = serviceServizio;
		this.serviceCliente = serviceCliente;
	}

	@Override
	public void run(String... args) throws Exception {
		if (serviceOrdine.count() == 0) {
			Servizio servizio1 = serviceServizio.findByIdOrThrow(1L);
			Cliente cliente1 = serviceCliente.findByIdOrThrow(1L);
			serviceOrdine.save(new Ordine("2025-01-10", servizio1, cliente1));
			Servizio servizio2 = serviceServizio.findByIdOrThrow(2L);
			Cliente cliente2 = serviceCliente.findByIdOrThrow(2L);
			serviceOrdine.save(new Ordine("2025-01-15", servizio2, cliente2));
			Servizio servizio3 = serviceServizio.findByIdOrThrow(3L);
			Cliente cliente3 = serviceCliente.findByIdOrThrow(3L);
			serviceOrdine.save(new Ordine("2025-01-20", servizio3, cliente3));
			Servizio servizio4 = serviceServizio.findByIdOrThrow(1L);
			Cliente cliente4 = serviceCliente.findByIdOrThrow(4L);
			serviceOrdine.save(new Ordine("2025-02-05", servizio4, cliente4));
			Servizio servizio5 = serviceServizio.findByIdOrThrow(2L);
			Cliente cliente5 = serviceCliente.findByIdOrThrow(5L);
			serviceOrdine.save(new Ordine("2025-02-10", servizio5, cliente5));
			Servizio servizio6 = serviceServizio.findByIdOrThrow(3L);
			Cliente cliente6 = serviceCliente.findByIdOrThrow(6L);
			serviceOrdine.save(new Ordine("2025-02-15", servizio6, cliente6));
			Servizio servizio7 = serviceServizio.findByIdOrThrow(1L);
			Cliente cliente7 = serviceCliente.findByIdOrThrow(7L);
			serviceOrdine.save(new Ordine("2025-03-01", servizio7, cliente7));
			Servizio servizio8 = serviceServizio.findByIdOrThrow(2L);
			Cliente cliente8 = serviceCliente.findByIdOrThrow(8L);
			serviceOrdine.save(new Ordine("2025-03-05", servizio8, cliente8));
			Servizio servizio9 = serviceServizio.findByIdOrThrow(3L);
			Cliente cliente9 = serviceCliente.findByIdOrThrow(9L);
			serviceOrdine.save(new Ordine("2025-03-10", servizio9, cliente9));
			Servizio servizio10 = serviceServizio.findByIdOrThrow(1L);
			Cliente cliente10 = serviceCliente.findByIdOrThrow(10L);
			serviceOrdine.save(new Ordine("2025-03-15", servizio10, cliente10));
		}
	}
}
