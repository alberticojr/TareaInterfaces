package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {
	
	private String direccion;
	private String localidad;
	
	//CONSTRUCTORES
	public Direccion(String direccion, String localidad) {
		super();
		this.direccion = direccion;
		this.localidad = localidad;
	}

	public Direccion() {
		super();
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	

}
