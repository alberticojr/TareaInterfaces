package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.ListaEstancias;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class ExportarDatosParadaController implements Initializable{
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private EstanciaService estanciaService;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	Parada parada;
	
	@FXML
	private Label lblId;
	
	@FXML
	private Label lblNombre;
	
	@FXML
	private Label lblRegion;
	
	@FXML
	private DatePicker DPfechaInicio;
	
	@FXML
	private DatePicker DPfechaFin;
	
	@FXML
	private TableView<ListaEstancias> TblEstancias;
	
	@FXML
	private TableColumn<ListaEstancias, Integer>  columnId;
	
	@FXML
	private TableColumn<ListaEstancias, String> columnParada;
	
	@FXML
	private TableColumn<ListaEstancias, String> columnPeregrino;
	
	@FXML
	private TableColumn<ListaEstancias, LocalDate> columnFecha;
	
	@FXML
	private TableColumn<ListaEstancias, Boolean> columnVip;
	
	
	@FXML
	private void pulsaExportar() {
		List <Estancia> listaEstancias = estanciaService.findAll();
		ObservableList<ListaEstancias> listaEstanciasFiltrada = FXCollections.observableArrayList();
		
		LocalDate fechaInicioDP = DPfechaInicio.getValue();
		LocalDate fechaFinDP = DPfechaFin.getValue();
		
		if (fechaInicioDP.isBefore(fechaFinDP)) {
			for (Estancia e : listaEstancias) {
				
				LocalDate FInicio = e.getFecha().toLocalDate();
				LocalDate FFin = e.getFecha().toLocalDate();
				

				if (e.getParada().getId() == parada.getId()
						&& (FInicio.isAfter(fechaInicioDP) || FInicio.isEqual(fechaInicioDP))
						&& (FFin.isBefore(fechaFinDP) || FFin.isEqual(fechaFinDP))) {

					ListaEstancias estanciaTransformada = new ListaEstancias();

					estanciaTransformada.setId(e.getId());
					estanciaTransformada.setNombreParada(e.getNombreParada());
					estanciaTransformada.setNombrePeregrino(e.getPeregrino().getNombre());
					estanciaTransformada.setFecha(e.getFecha().toLocalDate());
					estanciaTransformada.setVip(e.isVip());

					listaEstanciasFiltrada.add(estanciaTransformada);
				}

			}
			TblEstancias.setItems(listaEstanciasFiltrada);
		}
		else if (fechaInicioDP.equals(fechaFinDP)) { AlertasServices.altFechasIguales(); }
		else { AlertasServices.altFechasAlReves(); }
	}
	
	@FXML
	private void pulsaCancelar() {
		stageManager.switchScene(FxmlView.MenuResponsable);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parada = paradaService.findByResponsable(LoginNuevoController.sesion.getNombre());
		
		lblId.setText(parada.getId()+"");
		lblNombre.setText(parada.getNombre());
		lblRegion.setText(parada.getRegion()+"");
		
		DPfechaInicio.setValue(LocalDate.now());
		DPfechaFin.setValue(LocalDate.now());
		
		
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnParada.setCellValueFactory(new PropertyValueFactory<>("nombreParada"));
		columnPeregrino.setCellValueFactory(new PropertyValueFactory<>("nombrePeregrino"));
		columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		columnVip.setCellValueFactory(new PropertyValueFactory<>("vip"));
		
	}
}
