package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.repositorios.ObjectDBRepository;

@Service
public class ObjectDBService {
	
	@Autowired
	private ObjectDBRepository objectDBRepository;
	
	public void crearEnvioACasa (EnvioACasa envio) {
		objectDBRepository.crearEnvio(envio);
	}
	
	public List <EnvioACasa> listaEnvios () {
		return objectDBRepository.listaEnvios();
	}

}
