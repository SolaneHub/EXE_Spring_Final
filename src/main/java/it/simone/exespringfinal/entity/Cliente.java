package it.simone.exespringfinal.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
//Fa diventare la classe una tabella nel database
public class Cliente {

	@Id
	// Chiave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Valore autoincrementante gestito direttamente dal DB
	private Long id;

	private String ragioneSociale;
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;
	private String partitaIva;
	// Rimossa la configurazione cascade
	@OneToOne
    @JoinColumn(name = "id_indirizzo")
    private Indirizzo indirizzo;
	private String email;
	private String telefono;
	private Double fatturatoAnnuale;
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Fattura> fatture;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ordine> ordini;
	
	public Cliente() {
		
	}

	public Cliente(String ragioneSociale, TipoCliente tipoCliente, String partitaIva, Indirizzo indirizzo, String email, String telefono, Double fatturatoAnnuale) {
		this.ragioneSociale = ragioneSociale;
		this.tipoCliente = tipoCliente;
		this.partitaIva = partitaIva;
		this.indirizzo = indirizzo;
		this.email = email;
		this.telefono = telefono;
		this.fatturatoAnnuale = fatturatoAnnuale;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Double getFatturatoAnnuale() {
		return fatturatoAnnuale;
	}

	public void setFatturatoAnnuale(Double fatturatoAnnuale) {
		this.fatturatoAnnuale = fatturatoAnnuale;
	}

	public Long getId() {
		return id;
	}

}
