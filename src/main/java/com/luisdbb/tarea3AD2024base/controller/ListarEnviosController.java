package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.ObjectDBService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ListarEnviosController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private Button btnAyuda;
	
	@Autowired
    private ParadaService paradaService;
	
	@Autowired
	private ObjectDBService objectDBService;
	
	@FXML
	private TableView<EnvioACasa> tableListaEnvios;
	
	@FXML
	private TableColumn<EnvioACasa, Long> columnId;
	
	@FXML
	private TableColumn <EnvioACasa, String> columnDireccion = new TableColumn<>("Dirección");
	
	@FXML
	private TableColumn  <EnvioACasa, String> columnLocalidad = new TableColumn<>("Localidad");
	
	@FXML
	private TableColumn <EnvioACasa, Double> columnPeso;
	
	@FXML
	private TableColumn <EnvioACasa, String > columnVolumen = new TableColumn<>("Volumen");
	
	@FXML
	private TableColumn<EnvioACasa, Boolean> columnUrgente;
	
	@FXML
	private Label lblInformacion;
	
	Parada parada;
	private List <EnvioACasa> listaEnvios;
	
	@FXML
	private void pulsaVolver() {
		stageManager.switchScene(FxmlView.MenuResponsable);
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
		
		parada = paradaService.findByResponsable(LoginNuevoController.sesion.getNombre());
		listaEnvios = objectDBService.listaEnvios();
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		
		
		ObservableList<EnvioACasa> ObservableListaEnvios = FXCollections.observableArrayList();
		
		if (listaEnvios.size() != 0) {
			for (EnvioACasa e : listaEnvios) {

				if (e.getIdParada() != null) {
					if (e.getIdParada() == parada.getId()) {
						ObservableListaEnvios.add(e);
						System.out.println(e.toString());
					}
				}

			}
		} else {System.out.println("no hay envios en el sistema");}
		
		tableListaEnvios.setItems(ObservableListaEnvios);
		
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getDireccion()));
		columnLocalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getLocalidad()));
		columnPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		columnVolumen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().volumenAString()));
		columnUrgente.setCellValueFactory(new PropertyValueFactory<>("urgente"));
		
		lblInformacion.setText("Viendo lista de envios de la parada "+parada.getNombre()+" | "+parada.getRegion());
		
	}
}
