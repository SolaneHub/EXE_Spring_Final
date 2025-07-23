package it.simone.exespringfinal.config;

import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import it.simone.exespringfinal.entity.Provincia;
import it.simone.exespringfinal.service.ServiceProvincia;

@Component
@Order(0)
public class CLRProvincia implements CommandLineRunner {

	private final ServiceProvincia service;

	public CLRProvincia(ServiceProvincia service) {
		this.service = service;
	}

	@Override
	public void run(String... args) throws Exception {
		// Esegui solo se il DB non contiene province (prima esecuzione)
		if (service.count() == 0) {

			// Carica il file CSV dalla cartella src/main/resources/data
			ClassPathResource resource = new ClassPathResource("data/province.csv");

			// Configura il parser CSV per usare il punto e virgola come separatore
			CSVParser parser = new CSVParserBuilder().withSeparator(';') // perché il CSV usa ";" e non ","
					.build();

			// Crea un reader che legge il file CSV riga per riga
			try (Reader reader = new InputStreamReader(resource.getInputStream());
					CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser) // Applica il parser con il
																								// separatore custom
							.build()) {

				String[] nextRecord; // Array che conterrà ogni riga del file CSV
				boolean isFirstLine = true; // Flag per saltare la riga di intestazione

				// Cicla su ogni riga del CSV
				while ((nextRecord = csvReader.readNext()) != null) {

					if (isFirstLine) {
						isFirstLine = false; // Salta la prima riga (intestazione)
						continue;
					}

					// nextRecord[0] = sigla, nextRecord[1] = nome provincia
					String sigla = nextRecord[0].trim(); // Esempio: "AG"
					String nome = nextRecord[1].trim(); // Esempio: "Agrigento"

					// Crea e salva la provincia nel database tramite il service
					service.save(new Provincia(nome, sigla));
				}

			} catch (CsvValidationException e) {
				// Gestione errori nel parsing CSV
				e.printStackTrace();
			}
		}
	}
}
