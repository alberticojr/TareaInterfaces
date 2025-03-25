package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2025base.conexiones.ConexionObjectDB;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class ObjectDBRepository {

	private static EntityManager em = ConexionObjectDB.getInstance();
	
	//Repositorio que crea un envio a casa
	public void crearEnvio (EnvioACasa envio) {
		em.getTransaction().begin();
		em.persist(envio);
		em.getTransaction().commit();
	}
	
	//Repositorio que devuelve una lista de envios a casa
	public List <EnvioACasa> listaEnvios (){
		
		TypedQuery<EnvioACasa> query =
			    em.createQuery("SELECT s FROM EnvioACasa s", EnvioACasa.class);
		
			  List<EnvioACasa> results = query.getResultList();
			  
		return results;
	}
}
