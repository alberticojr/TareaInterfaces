package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.DB4ORepository;
import com.luisdbb.tarea3AD2025base.conexiones.ConexionDB4O;

@Service
public class DB4OService {
	
	@Autowired
	private DB4ORepository db4oRepository;
	
	 public Long findServicioLastId() {
	        ObjectContainer db = ConexionDB4O.getInstance();
	        try {
	            Query query = db.query();
	            query.constrain(Servicio.class);
	            query.descend("id").orderDescending();
	            List<Servicio> resultado = query.execute();
	            return resultado.get(0).getId() + 1;
	        } catch (Exception e) {
	            return 1L;
	        }
	    }
	 
	 public Long findConjuntoContratadoLastId() {
	        ObjectContainer db = ConexionDB4O.getInstance();
	        try {
	            Query query = db.query();
	            query.constrain(ConjuntoContratado.class);
	            query.descend("id").orderDescending();
	            List<ConjuntoContratado> resultado = query.execute();
	            return resultado.get(0).getId() + 1;
	        } catch (Exception e) {
	            return 1L;
	        }
	    }
	 
	 public void crearServicio (Servicio servicio) {
		 db4oRepository.crearServicio(servicio);
	 }
	 
	 public List<Servicio> listaServicios () { 
		 return db4oRepository.listaServicios();
	 }
	 
	 public Servicio buscarServicioPorID (Long id) {
		 return db4oRepository.buscarServicioPorID(id);
	 }
	 
	 public void editarServicio (long id, Servicio servicioNuevo) {
		 db4oRepository.editarServicio(id, servicioNuevo);
	 }
	 
	 public void crearConjuntoContratado (ConjuntoContratado conjuntoContratado) {
		 db4oRepository.crearConjuntoContratado(conjuntoContratado);
	 }

}
