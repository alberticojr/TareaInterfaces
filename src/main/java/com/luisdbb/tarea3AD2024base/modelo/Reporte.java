package com.luisdbb.tarea3AD2024base.modelo;

public class Reporte {
	private long idCarnet;
	private String nombreCompleto;
	private String nombreUsuario;
	private String correo;
	private String nacionalidad;
	private int numVips;
	private double distancia;
	private String nombreParadaInicial;
	private char regionParadaInicial;
	
	//CONSTRUCTOR
	public Reporte(long idCarnet, String nombreCompleto, String nombreUsuario, String correo, String nacionalidad,
			int numVips, double distancia, String nombreParadaInicial, char regionParadaInicial) {
		super();
		this.idCarnet = idCarnet;
		this.nombreCompleto = nombreCompleto;
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.nacionalidad = nacionalidad;
		this.numVips = numVips;
		this.distancia = distancia;
		this.nombreParadaInicial = nombreParadaInicial;
		this.regionParadaInicial = regionParadaInicial;
	}

	//GETTERS Y SETTERS
	public long getIdCarnet() {
		return idCarnet;
	}

	public void setIdCarnet(long idCarnet) {
		this.idCarnet = idCarnet;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public int getNumVips() {
		return numVips;
	}

	public void setNumVips(int numVips) {
		this.numVips = numVips;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public String getNombreParadaInicial() {
		return nombreParadaInicial;
	}

	public void setNombreParadaInicial(String nombreParadaInicial) {
		this.nombreParadaInicial = nombreParadaInicial;
	}

	public char getRegionParadaInicial() {
		return regionParadaInicial;
	}

	public void setRegionParadaInicial(char regionParadaInicial) {
		this.regionParadaInicial = regionParadaInicial;
	}
	
	
	

}
