package it.simone.exespringfinal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.simone.exespringfinal.entity.Cliente;
import it.simone.exespringfinal.entity.Comune;
import it.simone.exespringfinal.entity.Indirizzo;
import it.simone.exespringfinal.entity.Provincia;
import it.simone.exespringfinal.entity.TipoCliente;
import it.simone.exespringfinal.repository.RepositoryCliente;
import it.simone.exespringfinal.repository.RepositoryIndirizzo;
import it.simone.exespringfinal.repository.RepositoryComune;
import it.simone.exespringfinal.repository.RepositoryProvincia;

@Service
public class ServiceCliente extends AbstractService<Cliente, Long> {

	private RepositoryCliente repositoryCliente;
	private RepositoryIndirizzo repositoryIndirizzo;
	private RepositoryComune repositoryComune;
	private RepositoryProvincia repositoryProvincia;

	public ServiceCliente(RepositoryCliente repositoryCliente, RepositoryIndirizzo repositoryIndirizzo, RepositoryComune repositoryComune, RepositoryProvincia repositoryProvincia) {
		this.repositoryCliente = repositoryCliente;
		this.repositoryIndirizzo = repositoryIndirizzo;
		this.repositoryComune = repositoryComune;
		this.repositoryProvincia = repositoryProvincia;
	}

	@Override
	protected JpaRepository<Cliente, Long> getRepository() {
		return repositoryCliente;
	}
	
	protected String getEntityName() {
	    return "Cliente";
	}

	@Override
	public Cliente updateById(Long id, Cliente entityDetails) {
		return getRepository().findById(id).map(cliente -> {
			cliente.setRagioneSociale(entityDetails.getRagioneSociale());
			cliente.setTipoCliente(entityDetails.getTipoCliente());
			cliente.setPartitaIva(entityDetails.getPartitaIva());
			
			if (entityDetails.getIndirizzo() != null) {
				Indirizzo indirizzo = entityDetails.getIndirizzo();
				
				if (indirizzo.getComune() != null) {
					Comune comune = indirizzo.getComune();
					
					if (comune.getProvincia() != null) {
						Provincia provincia = comune.getProvincia();
						if (provincia.getNome() == null || provincia.getSigla() == null) {
							throw new RuntimeException("Nome e Sigla della Provincia sono obbligatori.");
						}
						Optional<Provincia> existingProvinciaOpt = repositoryProvincia.findByNomeAndSigla(provincia.getNome(), provincia.getSigla());
						
						if (existingProvinciaOpt.isPresent()) {
							comune.setProvincia(existingProvinciaOpt.get());
						} else {
							throw new RuntimeException("Provincia con Nome '" + provincia.getNome() + "' e Sigla '" + provincia.getSigla() + "' non trovata.");
						}
					} else {
						throw new RuntimeException("Il Comune deve avere una Provincia associata.");
					}


					if (indirizzo.getComune() != null) {
						if (comune.getNome() == null) {
							throw new RuntimeException("Il Nome del Comune è obbligatorio.");
						}
						Optional<Comune> existingComuneOpt = repositoryComune.findByNomeAndProvincia(comune.getNome(), comune.getProvincia());
						
						if (existingComuneOpt.isPresent()) {
							indirizzo.setComune(existingComuneOpt.get());
						} else {
							throw new RuntimeException("Comune con Nome '" + comune.getNome() + "' e Provincia '" + comune.getProvincia().getNome() + "' non trovato.");
						}
					} else {
						throw new RuntimeException("L'Indirizzo deve avere un Comune associato.");
					}
				}

				if (indirizzo.getId() != null && repositoryIndirizzo.existsById(indirizzo.getId())) {
					Indirizzo existingIndirizzo = repositoryIndirizzo.findById(indirizzo.getId())
							.orElseThrow(() -> new RuntimeException("Indirizzo con id: " + indirizzo.getId() + " non trovato"));
					existingIndirizzo.setVia(indirizzo.getVia());
					existingIndirizzo.setCivico(indirizzo.getCivico());
					existingIndirizzo.setCap(indirizzo.getCap());
					existingIndirizzo.setComune(indirizzo.getComune());
					cliente.setIndirizzo(repositoryIndirizzo.save(existingIndirizzo));
				} else {

					cliente.setIndirizzo(repositoryIndirizzo.save(indirizzo));
				}
			} else {
				cliente.setIndirizzo(null); 
			}
			
			cliente.setEmail(entityDetails.getEmail());
			cliente.setTelefono(entityDetails.getTelefono());
			cliente.setFatturatoAnnuale(entityDetails.getFatturatoAnnuale());
			return save(cliente);
		}).orElseThrow(() -> new RuntimeException("Cliente con id: " + id + " non trovato"));
	}
	
	@Override
	public Cliente save(Cliente entity) {
		if (entity.getIndirizzo() != null) {
			Indirizzo indirizzo = entity.getIndirizzo();
			
			if (indirizzo.getComune() != null) {
				Comune comune = indirizzo.getComune();
				
				if (comune.getProvincia() != null) {
					Provincia provincia = comune.getProvincia();

					if (provincia.getNome() == null || provincia.getSigla() == null) {
						throw new RuntimeException("Nome e Sigla della Provincia sono obbligatori per la creazione di un nuovo Cliente.");
					}
					Optional<Provincia> existingProvinciaOpt = repositoryProvincia.findByNomeAndSigla(provincia.getNome(), provincia.getSigla());
					
					if (existingProvinciaOpt.isPresent()) {
	
						comune.setProvincia(existingProvinciaOpt.get());
					} else {

						throw new RuntimeException("Provincia con Nome '" + provincia.getNome() + "' e Sigla '" + provincia.getSigla() + "' non trovata.");
					}
				} else {
					throw new RuntimeException("Il Comune deve avere una Provincia associata.");
				}
				

				if (indirizzo.getComune() != null) {

					if (comune.getNome() == null) {
						throw new RuntimeException("Il Nome del Comune è obbligatorio per la creazione di un nuovo Cliente.");
					}
					Optional<Comune> existingComuneOpt = repositoryComune.findByNomeAndProvincia(comune.getNome(), comune.getProvincia());
					
					if (existingComuneOpt.isPresent()) {

						indirizzo.setComune(existingComuneOpt.get());
					} else {

						throw new RuntimeException("Comune con Nome '" + comune.getNome() + "' e Provincia '" + comune.getProvincia().getNome() + "' non trovato.");
					}
				} else {
					throw new RuntimeException("L'Indirizzo deve avere un Comune associato.");
				}
			}
			

			Indirizzo savedIndirizzo = repositoryIndirizzo.save(indirizzo);
			entity.setIndirizzo(savedIndirizzo);
		}
		return super.save(entity);
	}
	
	
	public List<Cliente> findByRagioneSociale(String ragioneSociale){
		return repositoryCliente.findByRagioneSociale(ragioneSociale);
	}
	
	public List<Cliente> findByFatturatoAnnualeBetween(Double min, Double max) {
	    return repositoryCliente.findByFatturatoAnnualeBetween(min, max);
	}
	
	public List<Cliente> findByTipoCliente(TipoCliente tipoCliente){
		return repositoryCliente.findByTipoCliente(tipoCliente);
	}
	
	
	public List<Cliente> findByIndirizzo_Comune_Provincia_Nome(String nomeProvincia){
	    return repositoryCliente.findByIndirizzo_Comune_Provincia_Nome(nomeProvincia);
	}
}
