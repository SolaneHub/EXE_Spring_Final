package it.simone.exespringfinal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Indirizzo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String via;
	private String civico;
	private String cap;
	@ManyToOne
	@JoinColumn(name = "id_comune")
	private Comune comune;
	

	public Indirizzo() {

	}

	public Indirizzo(String via, String civico, String cap, Comune comune) {
		this.via = via;
		this.civico = civico;
		this.cap = cap;
		this.comune = comune;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public Comune getComune() {
		return comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}

	public Long getId() {
		return id;
	}

}
