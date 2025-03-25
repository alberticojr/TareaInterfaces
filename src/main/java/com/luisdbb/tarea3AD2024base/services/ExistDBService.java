package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.Collection;

import com.luisdbb.tarea3AD2024base.modelo.ListaDatosCarnet;
import com.luisdbb.tarea3AD2024base.repositorios.ExistDBRepository;

@Service
public class ExistDBService {
	
	@Autowired
	private ExistDBRepository existdbRepository;
	
	public void crearSubColeccion (String nombreParada) {
		try {
			existdbRepository.crearSubColeccion(nombreParada);
		} catch (Exception e) {}
	}
	
	public Collection getSubColeccion (String nombreParada) {
		
		return existdbRepository.getSubColeccion(nombreParada);
	}
	
	public void guardarXML (Document documento, String nombrePa, String nombrePe) {
		
		existdbRepository.guardarXML(documento, nombrePa, nombrePe);
	}
	
	public List<ListaDatosCarnet> getDatosXML (String nombreParada) {
		return existdbRepository.getDatosXML(nombreParada);
	}

}
