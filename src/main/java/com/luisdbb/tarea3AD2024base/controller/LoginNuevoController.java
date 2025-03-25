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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;



@Controller
public class LoginNuevoController implements Initializable {
	
	public static Sesion sesion = new Sesion("invitado", "invitado");
	
	@FXML
	public TextField usufield;
	
	@FXML
	public TextField contrafield;
	
	@FXML
	public TextField contraPassField;
	
	@FXML
	public Label lblIncorrecto1;
	
	@FXML
	public Label lblIncorrecto2;
	
	@FXML
	private Hyperlink hplinkRegistrarse;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private Button btnVer;
	
	@Autowired
    private CredencialesService credencialesService;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	private Boolean ver = false;
	
	ImageView mostrar = null;
	ImageView noMostrar = null;
	
	@FXML
	private void pulsaRegistrarse () {
		stageManager.switchScene(FxmlView.RegistroPeregrino);
	}
	
	@FXML
	public void login(ActionEvent event) throws IOException{
		String contra = "";
		
		if (ver) {
			contra = contrafield.getText();
		}
		else {
			contra = contraPassField.getText();
		}
		
		if (usufield.getText().equals("admin") && contrafield.getText().equals("admin")) {
			
			sesion.setNombre(usufield.getText());
			sesion.setPerfil("administrador");
			
			stageManager.switchScene(FxmlView.MenuAdministrador);
		}
		else {
    	if(credencialesService.authenticate(usufield.getText(), contra)){
    		
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

    	}else{
    		lblIncorrecto1.setVisible(true);
    		lblIncorrecto2.setVisible(true);
    	}
		}
		
    }
	
	@FXML
	private void pulsaVer () {
		
		ver = !ver;
		
		if (ver) {
			btnVer.setGraphic(mostrar);
			
			if (contraPassField != null) {
				contrafield.setText(contraPassField.getText());
			}
			
			contraPassField.setVisible(false);
			contrafield.setVisible(true);
		}
		else {
			btnVer.setGraphic(noMostrar);
			
			if (contrafield != null) {
				contraPassField.setText(contrafield.getText());
			}
			
			contraPassField.setVisible(true);
			contrafield.setVisible(false);
			
			
			
		}
		
	}
	
	@FXML
	private void pulsaAyuda () {
		try {
			WebView webView = new WebView();
			
			String url = getClass().getResource("/ayuda/MenuPrincipal.html").toExternalForm();
			webView.getEngine().load(url);
			
			Stage helpStage = new Stage();
			helpStage.setTitle("Ayuda");
			
			Scene helpScene = new Scene (webView, 850, 520);
			
			helpStage.setScene(helpScene);
			
			helpStage.initModality(Modality.APPLICATION_MODAL);
			helpStage.setResizable(true);
			helpStage.show();
			
			
		}
		catch (NullPointerException e) {
			System.out.print("No se ha encontrado el HTML");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		URL linkVer = getClass().getResource("/images/iconos/ver.png");
		Image imgVer = new Image(linkVer.toString(),15, 15, false, true);
		mostrar = new ImageView(imgVer);
		
		URL linkNoVer = getClass().getResource("/images/iconos/noVer.png");
		Image imgNoVer = new Image(linkNoVer.toString(),15, 15, false, true);
		noMostrar = new ImageView(imgNoVer);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		btnVer.setGraphic(noMostrar);
		
		contrafield.setVisible(false);
		
	}
}
