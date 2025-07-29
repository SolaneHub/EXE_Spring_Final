package it.simone.exespringfinal.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class TipoServizio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@JsonIgnore
	@OneToMany(mappedBy = "tipoServizio", cascade = CascadeType.ALL)
	private List<Servizio> servizi = new ArrayList<Servizio>();

	public TipoServizio() {

	}

	@Override
	public String toString() {
		return "TipoServizio [id=" + id + ", nome=" + nome + ", servizi=" + servizi + "]";
	}

	public TipoServizio(String nome, List<Servizio> servizi) {
		this.nome = nome;
		this.servizi = servizi;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Servizio> getServizi() {
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi.clear(); // Rimuove i precedenti (orphanRemoval)
		for (Servizio s : servizi) {
			s.setTipoServizio(this); // Assicura relazione bidirezionale
			this.servizi.add(s);
		}
	}

	public Long getId() {
		return id;
	}

}
