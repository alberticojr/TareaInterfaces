package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ModificarDatosController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private Label lblID;
	
	@FXML
	private Label lblNombreUsu;
	
	@FXML
	private Label lblParadaInicial;
	
	@FXML
	private TextField fieldNombreCompleto;
	
	@FXML
	private TextField fieldCorreo;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private ComboBox<String> CBoxNacionalidad;
	
	@Autowired
	private PeregrinoService peregrinoService;
	
	Peregrino p;
	
	
	@FXML
	private void pulsaEditar() {

		String nombreCompleto = fieldNombreCompleto.getText();
		String correo = fieldCorreo.getText();
		String nacionalidad = CBoxNacionalidad.getValue();

		boolean credencialesCorrectas = ValidacionesService.comprobarCredenciales("a", "a", nombreCompleto, "a");

		if (credencialesCorrectas) {

			if (correo != null) {
				boolean correoValido = ValidacionesService.validacionCorreo(correo);
				if (correoValido) {
					if (AlertasServices.altConfirmacion()) {

						p.setNombre_completo(nombreCompleto);
						p.setCorreo(correo);
						p.setNacionalidad(nacionalidad);
						
						AlertasServices.altPeregrinoEditado();

						peregrinoService.update(p);
						
					} else {

					}

				} else {
					AlertasServices.altCorreoIncorrecto();
					fieldCorreo.setText(null);
				}
			} else {
				AlertasServices.altCorreoIncorrecto();
				fieldCorreo.setText(null);
			}
		} else {
			fieldNombreCompleto.setText(null);
		}

	}
	
	@FXML
	private void volver () {
		stageManager.switchScene(FxmlView.MenuPeregrino);
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
	
	public void initialize(URL location, ResourceBundle resources) {
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		p = peregrinoService.findByNombre(LoginNuevoController.sesion.getNombre());
		
		ValidacionesService.almacenarEnMap();
		HashMap <String, String> map = ValidacionesService.diccionarioPaises;
		
		for (String pais : map.values()) {
			CBoxNacionalidad.getItems().add(pais);
			
		}
		
		lblID.setText(p.getId()+"");
		lblNombreUsu.setText(p.getNombre());
		lblParadaInicial.setText(p.getPeregrinoParada().get(0).getParada().getNombre());
		fieldNombreCompleto.setText(p.getNombre_completo());
		fieldCorreo.setText(p.getCorreo());
		CBoxNacionalidad.setValue(p.getNacionalidad());
	}

}
