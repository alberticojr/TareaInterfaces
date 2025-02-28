package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

public class Servicio implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private String nombre;
	private double precio;
	private List<Long>idListaConjuntosContrados  = new ArrayList <>();
	private List<Long>idListaParadas  = new ArrayList <>();
	
	//CONSTRUCTOR
	public Servicio() {
		super();
	}


	public Servicio(long id, String nombre, double precio, List<Long> idListaConjuntosContrados,
			List<Long> idListaParadas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.idListaConjuntosContrados = idListaConjuntosContrados;
		this.idListaParadas = idListaParadas;
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


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public List<Long> getIdListaConjuntosContrados() {
		return idListaConjuntosContrados;
	}


	public void setIdListaConjuntosContrados(List<Long> idListaConjuntosContrados) {
		this.idListaConjuntosContrados = idListaConjuntosContrados;
	}


	public List<Long> getIdListaParadas() {
		return idListaParadas;
	}


	public void setIdListaParadas(List<Long> idListaParadas) {
		this.idListaParadas = idListaParadas;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", idListaConjuntosContrados="
				+ idListaConjuntosContrados + ", idListaParadas=" + idListaParadas + "]";
	}

	
	
	
	
}
