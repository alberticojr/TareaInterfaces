package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Controller
public class MenuPeregrinoController implements Initializable{
	
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
		
	}
}
