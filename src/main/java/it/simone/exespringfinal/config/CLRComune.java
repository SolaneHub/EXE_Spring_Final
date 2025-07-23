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

                while ((nextRecord = csvReader.readNext()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }

                    // nextRecord[2] = nome del comune (es. "Agliè")
                    // nextRecord[3] = nome della provincia (es. "Torino")
                    String nomeComune = nextRecord[2].trim();
                    String nomeProvincia = nextRecord[3].trim();

                    Provincia provincia = serviceProvincia.findByNome(nomeProvincia);
                    if (provincia == null) {
                        System.err.println("⚠️ Provincia non trovata: " + nomeProvincia + ". Comune '" + nomeComune + "' ignorato.");
                        continue;
                    }

                    serviceComune.save(new Comune(nomeComune, provincia));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
