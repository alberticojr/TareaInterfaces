package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

@Controller
public class MenuResponsableController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private Label lblId;
	
	@FXML
	private Label lblNombreUsu;
	
	@FXML
	private Label lblNombrePar;
	
	@FXML
	private Label lblRegion;
	
	@Autowired
	private ParadaService paradaService;
	
	Parada par;
	
	@FXML
	private void pulsaExportar (ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ExportarDatosParada);
	}
	
	@FXML
	private void pulsaSellar (ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SellarCarnet);
	}
	
	@FXML
	private void logout(ActionEvent event) throws IOException {
		
		LoginNuevoController.sesion.setNombre("invitado");
		LoginNuevoController.sesion.setPerfil("invitado");
		
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		par = paradaService.findByResponsable(LoginNuevoController.sesion.getNombre());
		
		lblId.setText(par.getId()+"");
		lblNombreUsu.setText(par.getResponsable());
		lblNombrePar.setText(par.getNombre());
		lblRegion.setText(par.getRegion()+"");
	}
}
