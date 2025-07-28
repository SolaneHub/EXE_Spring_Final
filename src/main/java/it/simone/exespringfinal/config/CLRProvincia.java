package it.simone.exespringfinal.config;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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
		if (service.count() == 0) {

			ClassPathResource resource = new ClassPathResource("data/province.csv");

			CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

			try (Reader reader = new InputStreamReader(resource.getInputStream());
					CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()) {
				List<Provincia> provinceList = new ArrayList<>();

				String[] nextRecord;
				boolean isFirstLine = true;

				while ((nextRecord = csvReader.readNext()) != null) {
					if (isFirstLine) {
						isFirstLine = false;
						continue;
					}

					String sigla = nextRecord[0].trim();
					String nome = nextRecord[1].trim();

					provinceList.add(new Provincia(nome, sigla));
				}

				service.saveAll(provinceList); // Inserimento in batch

			} catch (CsvValidationException e) {
				e.printStackTrace();
			}
		}
	}

}
