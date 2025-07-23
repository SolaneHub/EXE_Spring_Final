package it.simone.exespringfinal.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Servizio {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private Double prezzo;
	private String descrizione;
    @ManyToOne
    @JoinColumn(name = "id_tiposervizio")
    private TipoServizio tipoServizio;
    @OneToMany(mappedBy = "servizio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ordine> ordini;

	public Servizio() {

	}
	
	@Override
	public String toString() {
		return "Servizio [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + ", descrizione=" + descrizione
				+ ", tipoServizio=" + tipoServizio + ", ordini=" + ordini + "]";
	}

	public Servizio(String nome, Double prezzo, String descrizione, TipoServizio tipoServizio) {
		this.nome = nome;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.tipoServizio = tipoServizio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public TipoServizio getTipoServizio() {
		return tipoServizio;
	}

	public void setTipoServizio(TipoServizio tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	public Long getId() {
		return id;
	}

}
