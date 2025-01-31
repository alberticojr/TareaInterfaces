package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

@Controller
public class SellarCarnetController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
    private PeregrinoService peregrinoService;
	
	@Autowired
    private ParadaService paradaService;
	
	@Autowired
    private PeregrinoParadaService peregrinoParadaService;
	
	Parada parada;
	
	@FXML
	private ComboBox<String> coBoxPeregrino;
	
	@FXML
	private CheckBox chBoxEstancia;
	
	@FXML
	private CheckBox chBoxVip;
	
	@FXML
	private Button btnSellar;
	
	@FXML
	private void pulsaSellar () {
		
		List <PeregrinoParada> listaPP = peregrinoParadaService.listaParadasPorFecha(Date.valueOf(LocalDate.now()));
		
		if (coBoxPeregrino != null && chBoxEstancia.isSelected() && chBoxVip.isSelected()) {
			
			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];
			
			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);
			
			boolean selloRepetido = false;
			
			LocalDate fechaActual = LocalDate.now();
			
			for (PeregrinoParada PP: listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId() && PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}
			
			if (!selloRepetido) {
			PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
			Estancia estancia = new Estancia(parada.getNombre(),Date.valueOf(LocalDate.now()), true, peregrino, parada);
			
			peregrino.getPeregrinoParada().add(PP);
			peregrino.getListaEstancia().add(estancia);
			
			double distActual = peregrino.getCarnet().getDistancia();
			int vipstActual = peregrino.getCarnet().getNvips();
			
			peregrino.getCarnet().setDistancia(distActual+10.0);
			peregrino.getCarnet().setNvips(vipstActual+1);
			
			
			peregrinoService.save(peregrino);
			}
			else {
				AlertasServices.altSellarInvalido();
			}
		}
		
		
		else if (coBoxPeregrino != null && chBoxEstancia.isSelected()) {
			
			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];
			
			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);
			
			boolean selloRepetido = false;
			LocalDate fechaActual = LocalDate.now();
			
			for (PeregrinoParada PP: listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId() && PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}
			
			if (!selloRepetido) {
			PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
			Estancia estancia = new Estancia(parada.getNombre(),Date.valueOf(LocalDate.now()), false, peregrino, parada);
			
			
			peregrino.getPeregrinoParada().add(PP);
			peregrino.getListaEstancia().add(estancia);
			
			double distActual = peregrino.getCarnet().getDistancia();
			peregrino.getCarnet().setDistancia(distActual+10.0);
			
			peregrinoService.save(peregrino);
			}
			else {
				AlertasServices.altSellarInvalido();
			}
			
		}
		else if (coBoxPeregrino != null) {
			
			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];
			
			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);
			
			boolean selloRepetido = false;
			LocalDate fechaActual = LocalDate.now();
			
			for (PeregrinoParada PP: listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId() && PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}
			
			if (!selloRepetido) {
			PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
			peregrino.getPeregrinoParada().add(PP);
			
			peregrinoService.save(peregrino);
			
			AlertasServices.altSelladoCorrecto();
			
			}
			else {
				AlertasServices.altSellarInvalido();
			}
		}
		else {
			AlertasServices.altNoPeregrinos();
		}
		
	}
	
	@FXML
	private void pulsaCancelar() {
		stageManager.switchScene(FxmlView.MenuResponsable);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		parada = paradaService.findByResponsable(LoginNuevoController.sesion.getNombre());
		
		
		
		List<Peregrino> listaPeregrinos = peregrinoService.findAll();

		if (!listaPeregrinos.isEmpty()) {
			Peregrino peregrinoDefault = listaPeregrinos.get(0);
			coBoxPeregrino.setValue(peregrinoDefault.getId() + " | " + peregrinoDefault.getNombre() + " | " + peregrinoDefault.getNacionalidad());
			
			for (Peregrino p : listaPeregrinos) {
				coBoxPeregrino.getItems().add(p.getId() + " | " + p.getNombre() + " | " + p.getNacionalidad());
			}
			
		} else {
			AlertasServices.altNoPeregrinos();
			btnSellar.setDisable(true);
		}
		
		chBoxEstancia.selectedProperty().addListener((observable, oldValue, newValue) -> {
			
	        chBoxVip.setDisable(!newValue);
	        
	        if (!newValue) { chBoxVip.setSelected(false); }
	    });

	    
	    chBoxVip.setDisable(true);
		
		
	}

}
