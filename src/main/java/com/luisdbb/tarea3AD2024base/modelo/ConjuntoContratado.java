package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

public class ConjuntoContratado implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private double precioTotal;
	private char modoPago;
	private String extra = null;
	private List<Long> idListaServicios = new ArrayList <>();
	private long idEstancia;
	
	//CONSTRUCTORES
	public ConjuntoContratado() {
		super();
	}

	public ConjuntoContratado(long id, double precioTotal, char modoPago, String extra, List<Long> idListaServicios,
			long idEstancia) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
		this.idListaServicios = idListaServicios;
		this.idEstancia = idEstancia;
	}

	
	//GETTERS Y SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public char getModoPago() {
		return modoPago;
	}

	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public List<Long> getIdListaServicios() {
		return idListaServicios;
	}

	public void setIdListaServicios(List<Long> idListaServicios) {
		this.idListaServicios = idListaServicios;
	}

	public long getIdEstancia() {
		return idEstancia;
	}

	public void setIdEstancia(long idEstancia) {
		this.idEstancia = idEstancia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precioTotal=" + precioTotal + ", modoPago=" + modoPago + ", extra="
				+ extra + ", idListaServicios=" + idListaServicios + ", idEstancia=" + idEstancia + "]";
	}
	
	
	
	

}
