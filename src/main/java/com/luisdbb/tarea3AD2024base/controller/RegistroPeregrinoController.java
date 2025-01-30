package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

@Controller
public class RegistroPeregrinoController implements Initializable{
	
	
	@FXML
	private TextField nombreField;
	
	@FXML
	private TextField usuField;
	
	@FXML
	private TextField contraField;
	
	@FXML
	private ChoiceBox <String> chbParada;
	
	@FXML
	private ChoiceBox <String> chbPaises;
	
	@FXML
	private Button btnCrear;
	
	//DECLARACION DE SERVICIOS 
	@Autowired
	private PeregrinoService peregrinoService;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private CredencialesService credencialeService;
	
	@Autowired
	private PeregrinoParadaService peregrinoParadaService;
	
	@Autowired
	private CarnetRepository carnetRepository;
	
	@FXML
	private void PulsaCrearPeregrino () {
		String nombreCompleto = nombreField.getText();
		String nombreUsuario = usuField.getText();
		String contraUsuario = contraField.getText();
		String datosParada = chbParada.getValue();
		String nacionalidad = chbPaises.getValue();
		
		boolean credencialesCorrectas = ValidacionesService.comprobarCredenciales(nombreUsuario, contraUsuario,nombreCompleto, "a");
		
		String[] nombreYRegion = datosParada.split(" ");
		String nombreParada = nombreYRegion[0];
		char regionParada = nombreYRegion[2].charAt(0);
		
		Parada paradaP = paradaService.findByNameAndRegion(nombreParada, regionParada);
		
		boolean peregrinoExiste = peregrinoService.existePeregrino(nombreUsuario);
		
		if (!peregrinoExiste) {
			
			if (credencialesCorrectas) {
				Credenciales c = new Credenciales(nombreUsuario, contraUsuario, "peregrino");
				credencialeService.save(c);

				Peregrino p = new Peregrino(nombreUsuario, nacionalidad, nombreCompleto);
				peregrinoService.save(p);

				p.setCredenciales(c);
				peregrinoService.save(p);

				Date fecha = Date.valueOf(LocalDate.now());
				
				PeregrinoParada pp = new PeregrinoParada(p, paradaP, fecha);
				peregrinoParadaService.save(pp);

				Carnet carnet = new Carnet(fecha, 10.0 , 0);
				carnetRepository.save(carnet);
				
				carnet.setParadaInicial(paradaP);
				carnet.setPeregrino(p);
				carnetRepository.save(carnet);
				
				
			} else {
				nombreField.setText(null);
				usuField.setText(null);
				contraField.setText(null);
			}
		}
		else {
			AlertasServices.altUsuarioExiste();
			nombreField.setText(null);
			usuField.setText(null);
			contraField.setText(null);
		}
		
	}
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private void logout(ActionEvent event) throws IOException {
		
		LoginNuevoController.sesion.setNombre("invitado");
		LoginNuevoController.sesion.setPerfil("invitado");
		
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ValidacionesService.almacenarEnMap();
		HashMap <String, String> map = ValidacionesService.diccionarioPaises;
		
		for (String pais : map.values()) {
			chbPaises.getItems().add(pais);
			
		}
		
		List <Parada> listaParadas = paradaService.findAll();
		
		if (!listaParadas.isEmpty()) {
			Parada pDefault = listaParadas.get(0);
			chbParada.setValue(pDefault.getNombre()+" - "+pDefault.getRegion());
			
			for(Parada parada: listaParadas) {
				chbParada.getItems().add(parada.getNombre()+" - "+parada.getRegion());
			}
		}
		else {
			AlertasServices.altNoParadas();
			btnCrear.setDisable(true);
		}
		
		
		
		chbPaises.setValue(map.get("ES"));
		
		
	}
	
	
}
