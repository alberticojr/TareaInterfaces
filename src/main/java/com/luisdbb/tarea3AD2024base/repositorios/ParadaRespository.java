package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;

@Repository
public interface ParadaRespository extends JpaRepository<Parada, Long>{
	
	List <Parada> findAll();
	Parada findByNombre(String nombre);
	Parada findByResponsable(String nombre);
	
	@Query("""
	        SELECT p FROM Parada p
	        WHERE (p.nombre) LIKE (:nombre) AND (p.region) = (:region)""")
	Parada findByNameAndRegion(String nombre, char region);
}
