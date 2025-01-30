package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "Carnet")
public class Carnet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	private Date fechaexp;
	private Double distancia = 0.0;
	private int nvips = 0;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn
	private Peregrino peregrino;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn
	private Parada paradaInicial;
	
	
	//CONSTRUCTOR
	public Carnet(Date fechaexp, Double distancia, int nvips) {
		super();
		this.fechaexp = fechaexp;
		this.distancia = distancia;
		this.nvips = nvips;
		
	}
	
	public Carnet() {
		super();
	}

	
	//GETTERS Y SETTERS
	public Date getFechaexp() {
		return fechaexp;
	}

	public void setFechaexp(Date fechaexp) {
		this.fechaexp = fechaexp;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public int getNvips() {
		return nvips;
	}

	public void setNvips(int nvips) {
		this.nvips = nvips;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParadaInicial() {
		return paradaInicial;
	}

	public void setParadaInicial(Parada paradaInicial) {
		this.paradaInicial = paradaInicial;
	}

	@Override
	public String toString() {
		return "Carnet [id=" + id + ", fechaexp=" + fechaexp + ", distancia=" + distancia + ", nvips=" + nvips + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(distancia, fechaexp, id, nvips);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carnet other = (Carnet) obj;
		return Objects.equals(distancia, other.distancia) && Objects.equals(fechaexp, other.fechaexp) && id == other.id
				&& nvips == other.nvips;
	}
	
}
