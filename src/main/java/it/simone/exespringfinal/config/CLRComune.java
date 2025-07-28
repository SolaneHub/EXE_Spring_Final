package it.simone.exespringfinal.config;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import it.simone.exespringfinal.entity.Comune;
import it.simone.exespringfinal.entity.Provincia;
import it.simone.exespringfinal.service.ServiceComune;
import it.simone.exespringfinal.service.ServiceProvincia;

@Component
@Order(1)
public class CLRComune implements CommandLineRunner {

	private final ServiceComune serviceComune;
	private final ServiceProvincia serviceProvincia;

	public CLRComune(ServiceComune serviceComune, ServiceProvincia serviceProvincia) {
		this.serviceComune = serviceComune;
		this.serviceProvincia = serviceProvincia;
	}

	@Override
	public void run(String... args) throws Exception {
		if (serviceComune.count() == 0) {
			ClassPathResource resource = new ClassPathResource("data/comuni.csv");

			CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

			try (Reader reader = new InputStreamReader(resource.getInputStream());
					CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()) {
				String[] nextRecord;
				boolean isFirstLine = true;
				List<Comune> comuniDaInserire = new ArrayList<>();

				// Cache delle province già trovate per non interrogare il DB ogni volta
				Map<String, Provincia> cacheProvince = new HashMap<>();

				while ((nextRecord = csvReader.readNext()) != null) {
					if (isFirstLine) {
						isFirstLine = false;
						continue;
					}

					String nomeComune = nextRecord[2].trim();
					String nomeProvincia = nextRecord[3].trim();

					Provincia provincia = cacheProvince.computeIfAbsent(nomeProvincia,
							k -> serviceProvincia.findByNome(k));

					if (provincia == null) {
						System.err.println("⚠️ Provincia non trovata: " + nomeProvincia + ". Comune '" + nomeComune
								+ "' ignorato.");
						continue;
					}

					comuniDaInserire.add(new Comune(nomeComune, provincia));
				}

				// Salva tutti i comuni in un'unica operazione
				serviceComune.saveAll(comuniDaInserire);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
