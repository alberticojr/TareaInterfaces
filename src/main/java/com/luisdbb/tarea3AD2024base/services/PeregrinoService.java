package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class PeregrinoService {
	
	@Autowired
	private PeregrinoRepository peregrinoRepository;
	
	public Peregrino save(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}

	public Peregrino update(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}

	public void delete(Peregrino entity) {
		peregrinoRepository.delete(entity);
	}

	public void delete(Long id) {
		peregrinoRepository.deleteById(id);
	}

	public Peregrino find(Long id) {
		return peregrinoRepository.findById(id).get();
	}

	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}
	
	public boolean existePeregrino(String nombre) {
		Peregrino p = peregrinoRepository.findByNombre(nombre);
		
		if (p == null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public Peregrino findByNombre (String nombre) {
		return peregrinoRepository.findByNombre(nombre);
	}

}
