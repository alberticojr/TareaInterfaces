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
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class RegistroPeregrinoController implements Initializable{
	
	
	
	@FXML
	public TextField nombreField;
	
	@FXML
	public TextField usuField;
	
	@FXML
	public TextField contraField;
	
	@FXML
	public TextField conf_Contra;
	
	@FXML
	public TextField correo;
	
	@FXML
	public ChoiceBox <String> chbParada;
	
	@FXML
	public ChoiceBox <String> chbPaises;
	
	@FXML
	private Button btnCrear;
	
	//DECLARACION DE SERVICIOS 
	@Autowired
	private PeregrinoService peregrinoService;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private CredencialesService credencialeService;
	
	
	@FXML
	public void PulsaCrearPeregrino() {
		String nombreCompleto = nombreField.getText();
		String nombreUsuario = usuField.getText();
		String contraUsuario = contraField.getText();
		String contraRep = conf_Contra.getText();
		String correoE = correo.getText();
		String datosParada = chbParada.getValue();
		String nacionalidad = chbPaises.getValue();

		boolean credencialesCorrectas = ValidacionesService.comprobarCredenciales(nombreUsuario, contraUsuario,
				nombreCompleto, "a");

		String[] nombreYRegion = datosParada.split(" ");
		String nombreParada = nombreYRegion[0];
		char regionParada = nombreYRegion[2].charAt(0);

		Parada paradaP = paradaService.findByNameAndRegion(nombreParada, regionParada);

		boolean peregrinoExiste = peregrinoService.existePeregrino(nombreUsuario);
		boolean correoValido = ValidacionesService.validacionCorreo(correoE);

		if (!peregrinoExiste) {

			if (correoValido && correoE != null) {

				if (contraUsuario.equals(contraRep)) {

					if (credencialesCorrectas) {

						if (AlertasServices.altConfirmacion()) {

							Credenciales c = new Credenciales(nombreUsuario, contraUsuario, "peregrino");
							credencialeService.save(c);

							Peregrino p = new Peregrino(nombreUsuario, nacionalidad, nombreCompleto, correoE);
							peregrinoService.save(p);

							p.setCredenciales(c);
							peregrinoService.save(p);

							Date fecha = Date.valueOf(LocalDate.now());

							PeregrinoParada pp = new PeregrinoParada(p, paradaP, fecha);
							p.getPeregrinoParada().add(pp);

							Carnet carnet = new Carnet(fecha, 10.0, 0);

							carnet.setParadaInicial(paradaP);
							carnet.setPeregrino(p);
							p.setCarnet(carnet);

							peregrinoService.save(p);

							AlertasServices.altPeregrinoCreado();
							nombreField.setText(null);
							usuField.setText(null);
							contraField.setText(null);
							conf_Contra.setText(null);
							correo.setText(null);
							
						} else {
							nombreField.setText(null);
							usuField.setText(null);
							contraField.setText(null);
						}

					} else {
						nombreField.setText(null);
						usuField.setText(null);
						contraField.setText(null);
					}
				} else {
					AlertasServices.altContrasNoCoinciden();
					contraField.setText(null);
					conf_Contra.setText(null);
				}

			} else {
				AlertasServices.altCorreoIncorrecto();
				correo.setText(null);
			}
		} else {
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
	
	@FXML
	private Button btnAyuda;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
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
