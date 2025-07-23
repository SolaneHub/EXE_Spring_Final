package it.simone.exespringfinal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.TipoCliente;
import it.simone.exespringfinal.service.ServiceCliente;
import it.simone.exespringfinal.service.ServiceIndirizzo;

@Component
@Order(3)
public class CLRCliente implements CommandLineRunner {

	private final ServiceCliente serviceCliente;
	private final ServiceIndirizzo serviceIndirizzo;

	public CLRCliente(ServiceCliente serviceCliente, ServiceIndirizzo serviceIndirizzo) {
		this.serviceCliente = serviceCliente;
		this.serviceIndirizzo = serviceIndirizzo;
	}

	@Override
	public void run(String... args) throws Exception {
		if (serviceCliente.count() == 0) {
			serviceCliente.save(new Cliente("Alpha Technologies", TipoCliente.SRL, "01234567890",
					serviceIndirizzo.findByIdOrThrow(1L), "info@alpha.it", "0234567890", 1200000.0));
			serviceCliente.save(new Cliente("Beta Consulting", TipoCliente.SPA, "09876543210",
					serviceIndirizzo.findByIdOrThrow(2L), "contact@beta.it", "0398765432", 2150000.0));
			serviceCliente.save(new Cliente("Delta Tech", TipoCliente.PA, "23456789012",
					serviceIndirizzo.findByIdOrThrow(3L), "support@delta.it", "0277345566", 3300000.0));
			serviceCliente.save(new Cliente("Epsilon Solutions", TipoCliente.SAS, "34567890123",
					serviceIndirizzo.findByIdOrThrow(4L), "epsilon@solutions.it", "0312345678", 1850000.0));
			serviceCliente.save(new Cliente("Eta Systems", TipoCliente.SRL, "56789012345",
					serviceIndirizzo.findByIdOrThrow(5L), "eta@systems.it", "0255887744", 1450000.0));
			serviceCliente.save(new Cliente("Gamma Group", TipoCliente.SPA, "12345678901",
					serviceIndirizzo.findByIdOrThrow(6L), "info@gammagroup.it", "0365987412", 990000.0));
			serviceCliente.save(new Cliente("Iota Industries", TipoCliente.PA, "78901234567",
					serviceIndirizzo.findByIdOrThrow(7L), "industries@iota.it", "0377994466", 740000.0));
			serviceCliente.save(new Cliente("Kappa Ventures", TipoCliente.SAS, "89012345678",
					serviceIndirizzo.findByIdOrThrow(8L), "info@kappa.it", "0266778899", 1980000.0));
			serviceCliente.save(new Cliente("Theta Logistics", TipoCliente.SRL, "67890123456",
					serviceIndirizzo.findByIdOrThrow(9L), "logistics@theta.it", "0388776655", 2720000.0));
			serviceCliente.save(new Cliente("Zeta Innovation", TipoCliente.SPA, "45678901234",
					serviceIndirizzo.findByIdOrThrow(10L), "info@zeta.it", "0288991122", 620000.0));
		}
	}
}
