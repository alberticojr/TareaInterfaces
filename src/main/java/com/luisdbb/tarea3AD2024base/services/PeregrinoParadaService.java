package com.luisdbb.tarea3AD2024base.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoParadaRepository;

@Service
public class PeregrinoParadaService {
	
	@Autowired
	private PeregrinoParadaRepository peregrinoParadaRepository;
	
	public PeregrinoParada save(PeregrinoParada entity) {
		return peregrinoParadaRepository.save(entity);
	}

	public PeregrinoParada update(PeregrinoParada entity) {
		return peregrinoParadaRepository.save(entity);
	}

	public void delete(PeregrinoParada entity) {
		peregrinoParadaRepository.delete(entity);
	}

	public void delete(Long id) {
		peregrinoParadaRepository.deleteById(id);
	}

	public PeregrinoParada find(Long id) {
		return peregrinoParadaRepository.findById(id).get();
	}

	public List<PeregrinoParada> findAll() {
		return peregrinoParadaRepository.findAll();
	}
	
	public boolean existeParadaPeregrino (Date fecha) {
		if (peregrinoParadaRepository.findByFecha(fecha) == null) {
			return false;
		}
		else {
			return true;
		}
	}

}
