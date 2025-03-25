package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ListaEstancias {
	
	private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleStringProperty nombreParada = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDate> fecha = new SimpleObjectProperty<LocalDate>();
    private SimpleBooleanProperty vip = new SimpleBooleanProperty();
    private SimpleStringProperty nombrePeregrino = new SimpleStringProperty();
    

    // Constructor
    public ListaEstancias(SimpleLongProperty id, SimpleStringProperty nombreParada,
			SimpleObjectProperty<LocalDate> fecha, SimpleBooleanProperty vip, SimpleStringProperty nombrePeregrino) {
		super();
		this.id = id;
		this.nombreParada = nombreParada;
		this.fecha = fecha;
		this.vip = vip;
		this.nombrePeregrino = nombrePeregrino;
	}

	public ListaEstancias() {
		super();
	}

	// Getters y Setters
    public long getId() {
        return id.get();
    }

	public void setId(long id) {
        this.id.set(id);
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getNombreParada() {
        return nombreParada.get();
    }

    public void setNombreParada(String nombreParada) {
        this.nombreParada.set(nombreParada);
    }

    public SimpleStringProperty nombreParadaProperty() {
        return nombreParada;
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public SimpleObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }

    public boolean isVip() {
        return vip.get();
    }

    public void setVip(boolean vip) {
        this.vip.set(vip);
    }

    public SimpleBooleanProperty vipProperty() {
        return vip;
    }
	 
	public String getNombrePeregrino() {
        return nombrePeregrino.get();
    }

    public void setNombrePeregrino(String nombreParada) {
        this.nombrePeregrino.set(nombreParada);
    }

    public SimpleStringProperty nombrePeregrinoProperty() {
        return nombrePeregrino;
    }

    
}
