package com.luisdbb.tarea3AD2024base.modelo;

public class Sesion {

	private String nombre;
	private String perfil;
	
	//CONSTRUCTOR
	public Sesion(String nombre, String perfil) {
		super();
		this.nombre = nombre;
		this.perfil = perfil;
	}
	
	public Sesion() {
		super();
	}

	//GETTES Y SETTERS
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	//METODOS
	@Override
	public String toString() {
		return "Sesion [nombre=" + nombre + ", perfil=" + perfil + "]";
	}
	
	
	
	
}
