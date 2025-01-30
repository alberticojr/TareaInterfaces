package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


@Controller
public class LoginNuevoController implements Initializable {
	
	public static Sesion sesion = new Sesion("invitado", "invitado");
	
	@FXML
	private TextField usufield;
	
	@FXML
	private TextField contrafield;
	
	@FXML
	private Label lblIncorrecto1;
	
	@FXML
	private Label lblIncorrecto2;
	
	@FXML
	private Hyperlink hplinkRegistrarse;
	
	@Autowired
    private CredencialesService credencialesService;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private void pulsaRegistrarse () {
		stageManager.switchScene(FxmlView.RegistroPeregrino);
	}
	
	@FXML
    private void login(ActionEvent event) throws IOException{
    	if(credencialesService.authenticate(usufield.getText(), contrafield.getText())){
    		
    		String perfilUsuario = credencialesService.findByNombre(usufield.getText()).getPerfil();
    		
    		if (perfilUsuario.equals("peregrino")) {
    			
    			sesion.setNombre(usufield.getText());
    			sesion.setPerfil("peregrino");
    			
    			stageManager.switchScene(FxmlView.MenuPeregrino);
    		}
    		else if (perfilUsuario.equals("parada")) {
    			
    			sesion.setNombre(usufield.getText());
    			sesion.setPerfil("parada");
    			
    			stageManager.switchScene(FxmlView.MenuResponsable);
    		}
    		else if (perfilUsuario.equals("administrador")) {
    			
    			sesion.setNombre(usufield.getText());
    			sesion.setPerfil("administrador");
    			
    			stageManager.switchScene(FxmlView.MenuAdministrador);
    		}
    		
    	}else{
    		lblIncorrecto1.setVisible(true);
    		lblIncorrecto2.setVisible(true);
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
