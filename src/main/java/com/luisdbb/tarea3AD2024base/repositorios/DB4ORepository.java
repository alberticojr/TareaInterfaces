package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.luisdbb.tarea3AD2024base.modelo.ConexionDB4O;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class DB4ORepository {
	
	private static  ObjectContainer db = ConexionDB4O.getInstance();
	
	
	
	//METODO PARA CREAR UN SERVICIO
	public void crearServicio (Servicio servicio) {
		db.store(servicio);
	}
	
	//METODO PARA CREAR UN CONJUNTOCONTRATADO
	public void crearConjuntoContratado (ConjuntoContratado conjuntoContratado) {
		db.store(conjuntoContratado);
	}
	
	//METODO QUE DEVULEVE UNA LISTA DE TODOS LOS SERVICIOS
	public List<Servicio> listaServicios () {
	        try {
	            Query query = db.query();
	            query.constrain(Servicio.class);
	            query.descend("id").orderAscending();
	            List<Servicio> resultado = query.execute();
	            return resultado;
	        } catch (Exception e) {
	            return null;
	        }
	}
	

	//METODO PARA BUSCAR UN SERVICIO POR SU ID
	public Servicio buscarServicioPorID(Long id) {
        
        try {
            Query query = db.query();
            query.constrain(Servicio.class);
            query.descend("id").constrain(id);
            List<Servicio> resultado = query.execute();
            return resultado.get(0);
        } catch (Exception e) {
            return null;
        }
    }
	
	public void editarServicio (long id, Servicio servicioNuevo) {
		
		try {
            // Buscar la parada con el ID especificado
            Query query = db.query();
            query.constrain(Servicio.class);
            query.descend("id").constrain(id);
            
            ObjectSet<Servicio> resultado = query.execute();

            if (!resultado.isEmpty()) {
            	
            	Servicio servicio = resultado.next();
            	
            	servicio.setNombre(servicioNuevo.getNombre());
            	servicio.setPrecio(servicioNuevo.getPrecio());
            	servicio.setIdListaParadas(servicioNuevo.getIdListaParadas());

                db.store(servicio);
                
                System.out.println("Parada actualizada con éxito.");
            } else {
                System.out.println("No se encontró una parada con ID: " + id);
            }
        } catch (Exception e) {
			System.out.println("error");
		} 
		
	}
	
	

}
