package it.simone.exespringfinal.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Comune;
import it.simone.exespringfinal.entity.Indirizzo;
import it.simone.exespringfinal.entity.Provincia;
import it.simone.exespringfinal.repository.RepositoryComune;
import it.simone.exespringfinal.repository.RepositoryIndirizzo;
import it.simone.exespringfinal.repository.RepositoryProvincia;

@Service
public class ServiceIndirizzo extends AbstractService<Indirizzo, Long> {

    private final RepositoryIndirizzo repositoryIndirizzo;
    private final RepositoryComune repositoryComune;
    private final RepositoryProvincia repositoryProvincia;

    public ServiceIndirizzo(RepositoryIndirizzo repositoryIndirizzo, RepositoryComune repositoryComune, RepositoryProvincia repositoryProvincia) {
        this.repositoryIndirizzo = repositoryIndirizzo;
        this.repositoryComune = repositoryComune;
        this.repositoryProvincia = repositoryProvincia;
    }

    @Override
    protected JpaRepository<Indirizzo, Long> getRepository() {
        return repositoryIndirizzo;
    }

    @Override
    protected String getEntityName() {
        return "Indirizzo";
    }

    // Funzione di preparazione e salvataggio dell'indirizzo con validazione
    public Indirizzo prepareAndSave(Indirizzo indirizzo) {
        if (indirizzo.getComune() != null && indirizzo.getComune().getProvincia() != null) {
            // Verifica che la provincia esista
            Provincia provincia = indirizzo.getComune().getProvincia();
            if (provincia.getNome() == null || provincia.getSigla() == null) {
                throw new RuntimeException("Nome e Sigla della Provincia sono obbligatori.");
            }

            Optional<Provincia> existingProvinciaOpt = repositoryProvincia.findByNomeAndSigla(provincia.getNome(), provincia.getSigla());
            if (existingProvinciaOpt.isEmpty()) {
                throw new RuntimeException("Provincia con Nome '" + provincia.getNome() + "' e Sigla '" + provincia.getSigla() + "' non trovata.");
            }

            // Verifica che il comune esista
            Comune comune = indirizzo.getComune();
            if (comune.getNome() == null) {
                throw new RuntimeException("Il Nome del Comune è obbligatorio.");
            }

            Optional<Comune> existingComuneOpt = repositoryComune.findByNomeAndProvincia(comune.getNome(), provincia);
            if (existingComuneOpt.isEmpty()) {
                throw new RuntimeException("Comune con Nome '" + comune.getNome() + "' e Provincia '" + provincia.getNome() + "' non trovato.");
            }

            // Se il comune esiste, lo settiamo
            indirizzo.setComune(existingComuneOpt.get());
        } else {
            throw new RuntimeException("Il Comune e la sua Provincia devono essere associati all'indirizzo.");
        }

        // Salviamo l'indirizzo
        return repositoryIndirizzo.save(indirizzo);
    }

    @Override
    public Indirizzo save(Indirizzo entity) {
        // Invoca la funzione prepareAndSave prima di procedere con il salvataggio
        return prepareAndSave(entity);
    }

    @Override
    public Indirizzo updateById(Long id, Indirizzo entityDetails) {
        return getRepository().findById(id).map(indirizzo -> {
            indirizzo.setVia(entityDetails.getVia());
            indirizzo.setCivico(entityDetails.getCivico());
            indirizzo.setCap(entityDetails.getCap());

            // Salviamo l'indirizzo con validazione
            return prepareAndSave(indirizzo);
        }).orElseThrow(() -> new RuntimeException("Indirizzo con id: " + id + " non trovato"));
    }
}
