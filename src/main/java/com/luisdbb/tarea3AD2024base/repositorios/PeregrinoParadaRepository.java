package com.luisdbb.tarea3AD2024base.repositorios;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;

@Repository
public interface PeregrinoParadaRepository extends JpaRepository<PeregrinoParada, Long>{
	List <PeregrinoParada> findByFecha(Date fecha);
	
}
