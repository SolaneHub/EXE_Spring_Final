package it.simone.exespringfinal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.Fattura;
import it.simone.exespringfinal.entity.StatoFattura;
import it.simone.exespringfinal.service.ServiceCliente;
import it.simone.exespringfinal.service.ServiceFattura;

//@Component
@Order(7)
public class CLRFattura implements CommandLineRunner {

	private final ServiceFattura serviceFattura;
	private final ServiceCliente serviceCliente;

	public CLRFattura(ServiceFattura serviceFattura, ServiceCliente serviceCliente) {
		this.serviceFattura = serviceFattura;
		this.serviceCliente = serviceCliente;
	}

	@Override
	public void run(String... args) throws Exception {
		if (serviceFattura.count() == 0) {
			Cliente cliente1 = serviceCliente.findByIdOrThrow(1L);
			serviceFattura.save(new Fattura("2025-01-15", 1200.00, StatoFattura.PAGATA, cliente1));
			Cliente cliente2 = serviceCliente.findByIdOrThrow(2L);
			serviceFattura.save(new Fattura("2025-02-01", 450.50, StatoFattura.NON_PAGATA, cliente2));
			Cliente cliente3 = serviceCliente.findByIdOrThrow(3L);
			serviceFattura.save(new Fattura("2025-03-10", 3000.00, StatoFattura.PAGATA, cliente3));
			Cliente cliente4 = serviceCliente.findByIdOrThrow(4L);
			serviceFattura.save(new Fattura("2025-03-20", 750.75, StatoFattura.NON_EMESSA, cliente4));
			Cliente cliente5 = serviceCliente.findByIdOrThrow(5L);
			serviceFattura.save(new Fattura("2025-04-05", 980.40, StatoFattura.NON_PAGATA, cliente5));
			Cliente cliente6 = serviceCliente.findByIdOrThrow(6L);
			serviceFattura.save(new Fattura("2025-04-25", 1875.90, StatoFattura.PAGATA, cliente6));
			Cliente cliente7 = serviceCliente.findByIdOrThrow(7L);
			serviceFattura.save(new Fattura("2025-05-10", 112.00, StatoFattura.NON_EMESSA, cliente7));
			Cliente cliente8 = serviceCliente.findByIdOrThrow(8L);
			serviceFattura.save(new Fattura("2025-06-15", 645.30, StatoFattura.PAGATA, cliente8));
			Cliente cliente9 = serviceCliente.findByIdOrThrow(9L);
			serviceFattura.save(new Fattura("2025-07-01", 250.00, StatoFattura.NON_PAGATA, cliente9));
			Cliente cliente10 = serviceCliente.findByIdOrThrow(10L);
			serviceFattura.save(new Fattura("2025-07-10", 1540.00, StatoFattura.PAGATA, cliente10));
		}
	}
}
