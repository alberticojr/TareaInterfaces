package com.luisdbb.tarea3AD2024base.modelo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ListaServicios {
	private SimpleDoubleProperty precio = new SimpleDoubleProperty();
    private SimpleStringProperty nombre = new SimpleStringProperty();
    
    //constructores
	public ListaServicios(SimpleDoubleProperty vip, SimpleStringProperty nombrePeregrino) {
		super();
		this.precio = vip;
		this.nombre = nombrePeregrino;
	}

	public ListaServicios() {
		super();
	}

	//getters y setters
	
	public double getPrecio() {
        return precio.get();
    }

	public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public SimpleDoubleProperty precioProperty() {
        return precio;
    }

	//asdasdasd
    public String getNombre() {
        return nombre.get();
    }

	public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }
    
    
}
