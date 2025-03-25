package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "credenciales")
public class Credenciales {
	
	//COLUMNAS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String nombre;
	private String contra;
	private String perfil;
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Peregrino peregrino;
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Parada parada;
	
	
	
	//CONSTRUCTOR
	public Credenciales() {
		super();
	}
	
	public Credenciales(String nombre, String contra, String perfil) {
		super();
		this.nombre = nombre;
		this.contra = contra;
		this.perfil = perfil;
	}



	//GETTERS Y SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contra, id, nombre, parada, peregrino, perfil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credenciales other = (Credenciales) obj;
		return Objects.equals(contra, other.contra) && id == other.id && Objects.equals(nombre, other.nombre)
				&& Objects.equals(parada, other.parada) && Objects.equals(peregrino, other.peregrino)
				&& Objects.equals(perfil, other.perfil);
	}

	
	
	
	

}
