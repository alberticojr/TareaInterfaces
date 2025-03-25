package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ListaDatosCarnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.ExistDBService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

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
public class ListaCarnetsExpedidosController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private Label lblInformacion;
	
	@FXML
	private TableView <ListaDatosCarnet> tablaCarnets;
	
	@FXML
	private TableColumn <ListaDatosCarnet, String> columnId;
	
	@FXML
	private TableColumn <ListaDatosCarnet, String> columnNombre;
	
	@FXML
	private TableColumn <ListaDatosCarnet, String> columnNacionalidad;
	
	@FXML
	private TableColumn <ListaDatosCarnet, String> columnFechaExp;
	
	@Autowired
    private ParadaService paradaService;
	
	@Autowired
	private ExistDBService existDBservice;
	
	Parada parada;
	
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
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		parada = paradaService.findByResponsable(LoginNuevoController.sesion.getNombre());
		
		lblInformacion.setText("Viendo lista de carnets de la parada "+parada.getNombre()+" | "+parada.getRegion());
		
		String nombrePaColeccion = parada.getNombre().replace(" ", "_");
		
		List <ListaDatosCarnet> listaDatosCarnet = existDBservice.getDatosXML(nombrePaColeccion+"-"+parada.getRegion());
		
		
		ObservableList<ListaDatosCarnet> ObservableListaCarnet = FXCollections.observableArrayList();
		
		if (listaDatosCarnet != null) {
			
			for (ListaDatosCarnet carnet: listaDatosCarnet) {
				ObservableListaCarnet.add(carnet);
				
			}
		}
		
		tablaCarnets.setItems(ObservableListaCarnet);
		
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		columnNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
		columnFechaExp.setCellValueFactory(new PropertyValueFactory<>("fechaExp"));
		
		
	}
}


