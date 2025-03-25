package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class CarnetService {
	
	@Autowired
	private CarnetRepository carnetRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	public Carnet actualizar(Carnet carnet) {
		return entityManager.merge(carnet);
		
	}
	
	public Carnet save(Carnet entity) {
		return carnetRepository.save(entity);
	}

	public Carnet update(Carnet entity) {
		return carnetRepository.save(entity);
	}

	public void delete(Carnet entity) {
		carnetRepository.delete(entity);
	}

	public void delete(Long id) {
		carnetRepository.deleteById(id);
	}

	public Carnet find(Long id) {
		return carnetRepository.findById(id).get();
	}

	public List<Carnet> findAll() {
		return carnetRepository.findAll();
	}
	
	//BUSCAR CARNET POR EL ID DEL PEREGRINO
	public Carnet findByPeregrinoId (long id) {
		return carnetRepository.findByPeregrinoId(id);
	}

}
