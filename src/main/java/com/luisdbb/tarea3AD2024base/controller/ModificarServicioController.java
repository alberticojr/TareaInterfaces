package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ObsListaParadas;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.DB4OService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ModificarServicioController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private DB4OService db4oService;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private TextField fieldNombreServicio;
	
	@FXML
	private TextField fieldPrecioServicio;
	
	@FXML
	private ComboBox<String> CBServicios;
	
	@FXML
	private TableView<ObsListaParadas> tableParadas;
	
	@FXML
	private TableColumn <ObsListaParadas, Long> ColumnId;
	
	@FXML
	private TableColumn <ObsListaParadas, String> ColumnNombre;
	
	@FXML
	private TableColumn <ObsListaParadas, String> ColumnRegion;
	
	@FXML
	private TableColumn <ObsListaParadas, String> ColumnResponsable;
	
	private ObservableList<Long> selectedIds = FXCollections.observableArrayList();
	
	private List <Servicio> listaServicios;
	
	private Long idServicioActual = -0L;
	private Servicio ServicioActual;
	
	
	@FXML
	private void pulsaEditar () {
		String nombreServicio = fieldNombreServicio.getText();
		String precioServicio = fieldPrecioServicio.getText();

		boolean nombreValido = ValidacionesService.comprobarCredenciales("a", "a", nombreServicio, "a");
		boolean numeroValido = precioServicio.matches("^\\d+\\.\\d{2}$");
		boolean nombreRepetido = false;

		List<Servicio> listaServicios = db4oService.listaServicios();

		if (listaServicios != null) {
			for (Servicio s : listaServicios) {

				if (s.getNombre().equals(nombreServicio) && !s.getNombre().equals(ServicioActual.getNombre())) {
					nombreRepetido = true;
				}

			}
		}
			
		if (nombreValido) {
			if (!nombreRepetido) {
				if (numeroValido) {

					Double Doubleprecio = Double.parseDouble(precioServicio);

					Servicio servicio = new Servicio();
					servicio.setId(db4oService.findServicioLastId());
					servicio.setNombre(nombreServicio);
					servicio.setPrecio(Doubleprecio);

					if (selectedIds.size() != 0) {
						for (Long idParada : selectedIds) {
							servicio.getIdListaParadas().add(idParada);
						}
					}

					if (ServicioActual != null) {

						db4oService.editarServicio(ServicioActual.getId(), servicio);

						CBServicios.getItems().clear();
						cargarListaServicios();
						CBServicios.setValue(listaServicios.get(0).getId() + " | " + listaServicios.get(0).getNombre()
								+ " | " + listaServicios.get(0).getPrecio());
						
						AlertasServices.altGeneralInformation(
								"Servicio Editado",
								"Servicio Editado",
								"El servicio se ha editado correctamente");
						
					} else {
						
						AlertasServices.altGeneralWarning(
								"Servicio vacio",
								"Servicio vacio",
								"Selecciona un servicio antes de darle al boton de editar");
					}

				} else {
					AlertasServices.altGeneralWarning(
							"Cantidad no valida",
							"Cantidad no valida",
							"El precio del servicio debe contener solo numeros y dos decimales");
				}
			} else {
				AlertasServices.altGeneralWarning(
						"Nombre repetido",
						"Nombre repetido",
						"El nombre del servicio ya existe en el sistema");
			}
		} else {
			AlertasServices.altGeneralWarning(
					"Nombre no valido",
					"Nombre no valido",
					"El nombre del servicio no puede contener numeros");
		}
		
	}
	
	@FXML
	private void pulsaCancelar(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.MenuAdministrador);
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
			AlertasServices.altGeneralWarning(
					"No se encuentra el HTML",
					"No se encuentra el HTML",
					"El sistema no fue capaz de encontrar el HTML");
		}
	}
	
	
	
	public void cargarListaServicios() {
		
		for (Servicio s : listaServicios) {

			CBServicios.getItems().add(s.getId() + " | " + s.getNombre() + " | " + s.getPrecio());

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		listaServicios = db4oService.listaServicios();
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		
		List<Parada> listaParadas = paradaService.findAll();

		ObservableList<ObsListaParadas> ObservableListaParadas = FXCollections.observableArrayList();

		for (Parada p : listaParadas) {

			ObsListaParadas paradaTransformada = new ObsListaParadas();
			paradaTransformada.setId(p.getId());
			paradaTransformada.setNombreParada(p.getNombre());
			paradaTransformada.setRegionParada(p.getRegion() + "");
			paradaTransformada.setNombreResponsable(p.getResponsable());

			ObservableListaParadas.add(paradaTransformada);
		
		}
		
		tableParadas.setItems(ObservableListaParadas);
		
		ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreParada"));
		ColumnRegion.setCellValueFactory(new PropertyValueFactory<>("regionParada"));
		ColumnResponsable.setCellValueFactory(new PropertyValueFactory<>("nombreResponsable"));
		
		tableParadas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		if (listaServicios != null) {
			cargarListaServicios();
			CBServicios.setValue( listaServicios.get(0).getId()+ " | " +listaServicios.get(0).getNombre() + " | " + listaServicios.get(0).getPrecio());
			idServicioActual = 1L;
			ServicioActual = db4oService.buscarServicioPorID(idServicioActual);
		}
		else {
		AlertasServices.altGeneralWarning(
				"Servicios no disponibles",
				"Servicios no disponibles",
				"No hay servicios disponibles en el sistema");
		}
		
		
		CBServicios.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	String[] servi = newValue.split(" ");
            	idServicioActual = Long.parseLong(servi[0]);
            	ServicioActual = db4oService.buscarServicioPorID(idServicioActual);
            	
            	fieldNombreServicio.setText(ServicioActual.getNombre());
            	fieldPrecioServicio.setText(ServicioActual.getPrecio()+"");
            	
            	tableParadas.getSelectionModel().clearSelection();
            	
            	for (Long idParada: ServicioActual.getIdListaParadas()) {
            		
            		tableParadas.getSelectionModel().select(idParada.intValue()-1);
            		
            	}
            	
                //System.out.println(ServicioActual.toString());
            }
        });
		
		tableParadas.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ObsListaParadas>) change -> {
			
			selectedIds.clear();
			
			for (ObsListaParadas paradasSeleccionada : tableParadas.getSelectionModel().getSelectedItems()) {
				selectedIds.add(paradasSeleccionada.getId());
			}
			
			//System.out.println("IDs seleccionados: " + selectedIds);
		});
		
		
		
	}
}
