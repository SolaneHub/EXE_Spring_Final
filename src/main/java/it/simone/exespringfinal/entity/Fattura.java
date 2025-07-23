package it.simone.exespringfinal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Fattura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String data;
	private Double importo;
	@Enumerated(EnumType.STRING)
	private StatoFattura stato;
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	public Fattura() {

	}

	public Fattura(String data, Double importo, StatoFattura stato, Cliente cliente) {
		this.data = data;
		this.importo = importo;
		this.stato = stato;
		this.cliente = cliente;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public StatoFattura getStato() {
		return stato;
	}

	public void setStato(StatoFattura stato) {
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
