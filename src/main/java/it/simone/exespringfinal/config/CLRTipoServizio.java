package it.simone.exespringfinal.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.simone.exespringfinal.entity.Servizio;
import it.simone.exespringfinal.entity.TipoServizio;
import it.simone.exespringfinal.service.ServiceTipoServizio;

@Component
@Order(4)
public class CLRTipoServizio implements CommandLineRunner {

	private final ServiceTipoServizio serviceTipoServizio;

	public CLRTipoServizio(ServiceTipoServizio serviceTipoServizio) {
		this.serviceTipoServizio = serviceTipoServizio;
	}

	@Override
	public void run(String... args) throws Exception {
		if (serviceTipoServizio.count() == 0) {
			List<Servizio> servizi = new ArrayList<>();
			TipoServizio consulenza = new TipoServizio("Consulenza", servizi);
			serviceTipoServizio.save(consulenza);
			TipoServizio formazione = new TipoServizio("Formazione", servizi);
			serviceTipoServizio.save(formazione);
			TipoServizio manutenzione = new TipoServizio("Manutenzione", servizi);
			serviceTipoServizio.save(manutenzione);

		}
	}

}
