package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

@Controller
public class RegistroParadaController implements Initializable{
	
	
	@FXML
	TextField nombrePaField;
	
	@FXML
	TextField regionPaField;
	
	@FXML
	TextField nombreReField;
	
	@FXML
	TextField contraReField;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private CredencialesService credencialeService;
	
	@FXML
	private void pulsaCrearParada(ActionEvent event) throws IOException {
		
		String nombreParada = nombrePaField.getText();
		char regionParada = regionPaField.getText().charAt(0);
		String nombreResponsable = nombreReField.getText();
		String contraResponsable = contraReField.getText();
		
		Credenciales c = new Credenciales(nombreResponsable, contraResponsable, "parada");
		credencialeService.save(c);
		
		Parada p = new Parada (nombreParada, regionParada, nombreResponsable);
		paradaService.save(p);
		
		p.setCredenciales(c);
		paradaService.save(p);
		
	}
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private void volver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.MenuAdministrador);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
