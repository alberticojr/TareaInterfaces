package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRespository;

@Service
public class ParadaService {
	
	@Autowired
	private ParadaRespository paradaRespository;
	
	public Parada save(Parada entity) {
		return paradaRespository.save(entity);
	}

	public Parada update(Parada entity) {
		return paradaRespository.save(entity);
	}

	public void delete(Parada entity) {
		paradaRespository.delete(entity);
	}

	public void delete(Long id) {
		paradaRespository.deleteById(id);
	}

	public Parada find(Long id) {
		return paradaRespository.findById(id).get();
	}

	public List<Parada> findAll() {
		return paradaRespository.findAll();
	}
	
	public List <Parada> listaParadas (){
		
		return paradaRespository.findAll();
	}
	
	public boolean existeParada(String nombre, char region) {
		
		Parada parada = paradaRespository.findByNameAndRegion(nombre, region);
		
		if (parada == null) { return false; }
		else { return true; }
	}
	
	public Parada findByNameAndRegion (String nombre, char region) {
		return paradaRespository.findByNameAndRegion(nombre, region);
	}
	
	public Parada findByResponsable (String nombre) {
		return paradaRespository.findByResponsable(nombre);
	}
}
