package com.luisdbb.tarea3AD2024base.modelo;



import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ObsListaParadas {
	
	private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleStringProperty nombreParada = new SimpleStringProperty();
    private SimpleStringProperty regionParada = new SimpleStringProperty();
    private SimpleStringProperty nombreResponsable = new SimpleStringProperty();
    
    //CONSTRUCTOR
	public ObsListaParadas(SimpleLongProperty id, SimpleStringProperty nombreParada, SimpleStringProperty regionParada,
			SimpleStringProperty nombreResponsable) {
		super();
		this.id = id;
		this.nombreParada = nombreParada;
		this.regionParada = regionParada;
		this.nombreResponsable = nombreResponsable;
	}

	public ObsListaParadas() {
		super();
	}

	//GETTERS Y SETTERS
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

	public String getRegionParada() {
	    return regionParada.get();
	}

	public void setRegionParada(String regionParada) {
	    this.regionParada.set(regionParada);
	}

	public SimpleStringProperty regionParadaProperty() {
	    return regionParada;
	}

	public String getNombreResponsable() {
	    return nombreResponsable.get();
	}

	public void setNombreResponsable(String nombreResponsable) {
	    this.nombreResponsable.set(nombreResponsable);
	}

	public SimpleStringProperty nombreResponsableProperty() {
	    return nombreResponsable;
	}

    
	
	
    

}
