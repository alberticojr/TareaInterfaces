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
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
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
		
		String region = regionPaField.getText();
		String nombreResponsable = nombreReField.getText();
		String contraResponsable = contraReField.getText();
		
		boolean credencialesCorrectas = ValidacionesService.comprobarCredenciales(nombreResponsable, contraResponsable, nombreParada, region);
		boolean responsableExiste = credencialeService.credencialExiste(nombreResponsable);
		
			if (credencialesCorrectas) {
				
				char regionParada = regionPaField.getText().charAt(0);
				boolean paradaExiste = paradaService.existeParada(nombreParada, regionParada);
				
				if (!paradaExiste) {
					if (!responsableExiste) {
						if (AlertasServices.altConfirmacion()) {
						
						Credenciales cre = new Credenciales(nombreResponsable, contraResponsable, "parada");
						credencialeService.save(cre);

						Parada pa = new Parada(nombreParada, regionParada, nombreResponsable);
						paradaService.save(pa);

						pa.setCredenciales(cre);
						paradaService.save(pa);
						
						AlertasServices.altParadaCreada();
						nombrePaField.setText(null);
						regionPaField.setText(null);
						nombreReField.setText(null);
						contraReField.setText(null);
						}
						else {
							nombrePaField.setText(null);
							regionPaField.setText(null);
							nombreReField.setText(null);
							contraReField.setText(null);
						}
						
					} else {
						AlertasServices.altUsuarioExiste();
						nombreReField.setText(null);
						contraReField.setText(null);
					}
				}
				else {
					AlertasServices.altParadaExiste();
					nombrePaField.setText(null);
					regionPaField.setText(null);
				}
			} else {
					
			}
		
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
