package com.luisdbb.tarea3AD2024base.modelo;

public class ListaDatosCarnet {
	
	private String id;
	private String nombre;
	private String nacionalidad;
	private String fechaExp;
	
	//Contructor
	public ListaDatosCarnet(String id, String nombre, String nacionalidad, String fechaExp) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.fechaExp = fechaExp;
	}

	//getters y setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(String fechaExp) {
		this.fechaExp = fechaExp;
	}

	@Override
	public String toString() {
		return "ListaDatosCarnet [id=" + id + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", fechaExp="
				+ fechaExp + "]";
	}
	
	
	
	
	

}
