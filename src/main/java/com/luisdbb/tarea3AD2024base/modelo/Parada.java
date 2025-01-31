package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "Parada")
public class Parada {

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String nombre;
	private char region;
	private String responsable;
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="parada")
    private List <PeregrinoParada> peregrinoParada = new ArrayList<>();
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn
	private Credenciales credenciales;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paradaInicial")
    private List<Carnet> carnets = new ArrayList<>();
	
	
	//CONSTRUCTORES
	
	public Parada(String nombre, char region, String responsable) {
		super();
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}
	

	public Parada() {
		super();
	}



	//GETTERS Y SETTERS
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
	
	public List<PeregrinoParada> getPeregrinoParada() {
		return peregrinoParada;
	}


	public void setPeregrinoParada(List<PeregrinoParada> peregrinoParada) {
		this.peregrinoParada = peregrinoParada;
	}


	public Credenciales getCredenciales() {
		return credenciales;
	}


	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}


	//METODOS INICIALES
	@Override
	public String toString() {
		return "Parada [nombre=" + nombre + ", region=" + region + ", responsable=" + responsable + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(carnets, credenciales, id, nombre, peregrinoParada, region, responsable);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parada other = (Parada) obj;
		return Objects.equals(carnets, other.carnets) && Objects.equals(credenciales, other.credenciales)
				&& id == other.id && Objects.equals(nombre, other.nombre)
				&& Objects.equals(peregrinoParada, other.peregrinoParada) && region == other.region
				&& Objects.equals(responsable, other.responsable);
	}


	

	
	
}
