package it.simone.exespringfinal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String data;
	@ManyToOne
	@JoinColumn(name = "id_servizio")
	private Servizio servizio;
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	public Ordine() {

	}

	public Ordine(String data, Servizio servizio, Cliente cliente) {
		this.data = data;
		this.servizio = servizio;
		this.cliente = cliente;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

}
