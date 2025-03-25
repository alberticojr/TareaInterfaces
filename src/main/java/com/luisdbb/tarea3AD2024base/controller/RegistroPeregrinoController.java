package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ExistDBService;
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
	
	@Autowired
	private ExistDBService existdbService;
	
	
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

		String[] nombreYRegion = datosParada.split("-");
		String nombreParada = nombreYRegion[0];
		char regionParada = nombreYRegion[1].charAt(0);


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
							
							String nombrePaColeccion = nombreParada.replace(" ", "_");
							persistirXML(p,nombrePaColeccion+"-"+regionParada);
							

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
	
	private void persistirXML(Peregrino p, String nombrePa) {
		
		//Collection col = existdbService.getSubColeccion(nombreP);
		
			try {
			DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
	        DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
	        DOMImplementation implementacion = constructorDocumento.getDOMImplementation();
	        
	        
	        Document documento = implementacion.createDocument(null, p.getNombre()+"carnet", null);
	        
	        Element carnet = documento.getDocumentElement();
	        
	        //CREAMOS TODAS LAS ETIQUETAS Y VALORES
	        Element id,fechaexp,expedicion,nombre,nacionalidad,fechaActual,distanciaTotal,paradas,paradaF,orden,nombreParada,region,estancias,estancia,idEstancia,fecha,paradaEs,vip;
	        Text idV,fechaexpV,expedicionV,nombreV,nacionalidadV,fechaActualV,distanciaTotalV,ordenV,nombreParadaV,regionV,idEstanciaV,fechaV,paradaEsV,vipV;
	        
	        //AÑADIMOS TODAS LAS ETQUETAS AL CARNET
	        id = documento.createElement("id");
	        carnet.appendChild(id);
	        idV= documento.createTextNode(p.getId()+"");
	        id.appendChild(idV);
	        
	        nombre = documento.createElement("nombre");
	        carnet.appendChild(nombre);
	        nombreV= documento.createTextNode(p.getNombre());
	        nombre.appendChild(nombreV);
	        
	        nacionalidad = documento.createElement("nacionalidad");
	        carnet.appendChild(nacionalidad);
	        nacionalidadV= documento.createTextNode(p.getNacionalidad());
	        nacionalidad.appendChild(nacionalidadV);
	        
	        expedicion = documento.createElement("expedidoEn");
	        carnet.appendChild(expedicion);
	        expedicionV= documento.createTextNode(p.getCarnet().getParadaInicial().getNombre());
	        expedicion.appendChild(expedicionV);
	        
	        fechaexp = documento.createElement("fechaExpedicion");
	        carnet.appendChild(fechaexp);
	        fechaexpV= documento.createTextNode(p.getCarnet().getFechaexp().toString());
	        fechaexp.appendChild(fechaexpV);
	        
	        fechaActual = documento.createElement("fechaSellado");
	        carnet.appendChild(fechaActual);
	        fechaActualV= documento.createTextNode(LocalDate.now().toString());
	        fechaActual.appendChild(fechaActualV);
	        
	        distanciaTotal = documento.createElement("distanciaTotal");
	        carnet.appendChild(distanciaTotal);
	        distanciaTotalV= documento.createTextNode(p.getCarnet().getDistancia()+"");
	        distanciaTotal.appendChild(distanciaTotalV);
	        
	        vip = documento.createElement("numeroVIPS");
	        carnet.appendChild(vip);
	        vipV= documento.createTextNode(p.getCarnet().getNvips()+" ");
	        vip.appendChild(vipV);
	        
	        
	        paradas = documento.createElement("paradas");
	        carnet.appendChild(paradas);
	        
	        int orde=1;
	        for(PeregrinoParada pp: p.getPeregrinoParada()) {
	        	
	        Parada pa = paradaService.find(pp.getParada().getId());
	        
	        paradaF = documento.createElement("parada");
	        paradas.appendChild(paradaF);
	        
	        orden = documento.createElement("orden");
	        paradaF.appendChild(orden);
	        ordenV= documento.createTextNode(orde+"");orde++;
	        orden.appendChild(ordenV);
	        
	        nombreParada = documento.createElement("nombre");
	        paradaF.appendChild(nombreParada);
	        nombreParadaV= documento.createTextNode(pa.getNombre());
	        nombreParada.appendChild(nombreParadaV);
	        
	        region = documento.createElement("region");
	        paradaF.appendChild(region);
	        regionV= documento.createTextNode(pa.getRegion()+"");
	        region.appendChild(regionV);
	        
	        }
	        
	        estancias = documento.createElement("estancias");
	        carnet.appendChild(estancias);
	        
	        for(Estancia es: p.getListaEstancia()) {

	   		   		estancia = documento.createElement("estancia");
	   		   		estancias.appendChild(estancia);
	           
	   		   		idEstancia = documento.createElement("id");
	   		   		estancia.appendChild(idEstancia);
	   		   		idEstanciaV= documento.createTextNode(es.getId()+"");
	   		   		idEstancia.appendChild(idEstanciaV);
	   		   		
	   		   		paradaEs = documento.createElement("parada");
	   		   		estancia.appendChild(paradaEs);
	   		   		paradaEsV= documento.createTextNode(es.getParada().getNombre()+" | "+es.getParada().getRegion());
	   		   		paradaEs.appendChild(paradaEsV);
	           
	   		   		fecha = documento.createElement("fecha");
	   		   		estancia.appendChild(fecha);
	   		   		fechaV= documento.createTextNode(es.getFecha().toString());
	   		   		fecha.appendChild(fechaV);
	             
	   		   		vip = documento.createElement("vip");
		   			estancia.appendChild(vip);
		   			
	   		   		if(es.isVip()) {
	   		   			vipV= documento.createTextNode("Verdadero");
	   		   			vip.appendChild(vipV);
	   		   		}
	   		   		else {
	   		   			vipV= documento.createTextNode("Falso");
	   		   			vip.appendChild(vipV);
	   		   		}
		   	   }
	          
	        existdbService.guardarXML(documento, nombrePa, p.getNombre());
	        
			}
			catch (ParserConfigurationException ex) { System.out.println("Error: " + ex.getMessage()); }
			
			AlertasServices.altExportacionCorrecta();
		
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

				chbParada.getItems().add(parada.getNombre()+"-"+parada.getRegion());
			}
		}
		else {
			AlertasServices.altNoParadas();
			btnCrear.setDisable(true);
		}
		
		
		
		chbPaises.setValue(map.get("ES"));
		
		
	}
	
	
}
