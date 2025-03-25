package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class MongoDBService {
	
	@Autowired
	private PeregrinoRepository peregrinoRepository;
	
    @Autowired
    private MongoTemplate mongoTemplate;

    public void CrearBackup() {
    	
    	Document docPeregrinos = new Document();
    	String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String titulo = "BACKUP DE LOS PEREGRINOS (" +fecha+ ")";
        
    	docPeregrinos.append("TITULO", titulo);
    	
    	List<Peregrino> peregrinosDelSistema = peregrinoRepository.findAll();
    	List <Document> listaPeres = new ArrayList<>();
    	
    	for (Peregrino p: peregrinosDelSistema) {
    		
    		Document datosPere = new Document();
    		
    		datosPere.append("ID", p.getId());
    		datosPere.append("NOMBRE", p.getNombre());
    		datosPere.append("NACIONALIDAD", p.getNacionalidad());
    		datosPere.append("EXPEDIDO EN", p.getCarnet().getParadaInicial().getNombre());
    		datosPere.append("FECHA DE EXPEDICION", p.getCarnet().getFechaexp().toLocalDate()
    				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    		datosPere.append("DISTANCIA TOTAL", p.getCarnet().getDistancia());
    		datosPere.append("NUMERO DE VIPS", p.getCarnet().getNvips());
    		
    		List <Document> listaPara = new ArrayList<>();
    		
    		for (PeregrinoParada PP: p.getPeregrinoParada()) {
    			Document datosPara = new Document();
    			
    			datosPara.append("NOMBRE", PP.getParada().getNombre());
    			datosPara.append("REGION", PP.getParada().getRegion());
    			
    			listaPara.add(datosPara);
    		}
    		
    		List <Document> listaEstan = new ArrayList<>();
    		
    		for (Estancia estancia: p.getListaEstancia()) {
    			Document datosEstancia = new Document();
    			
    			datosEstancia.append("ID", estancia.getId());
    			datosEstancia.append("NOMBRE DE LA PARADA", estancia.getNombreParada());
    			datosEstancia.append("FECHA DE ESTANCIA", estancia.getFecha().toLocalDate()
    					.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    			datosEstancia.append("VIP", estancia.isVip());
    			
    			listaEstan.add(datosEstancia);
    			
    		}
    		
    		datosPere.append("LISTA PARADAS", listaPara);
    		datosPere.append("LISTA DE ESTANCIAS", listaEstan);
    		
    		listaPeres.add(datosPere);
    		
    	}
    	
    	docPeregrinos.append("Peregrino", listaPeres);
    	
		String nombreBackup = "Backup_Peregrinos_"+fecha;
    	mongoTemplate.getCollection(nombreBackup).insertOne(docPeregrinos);
    	
        System.out.println("Usuario insertado correctamente.");
    }

}
