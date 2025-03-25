package com.luisdbb.tarea3AD2024base.controller;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;


@Controller
public class MenuPeregrinoController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private PeregrinoService peregrinoService;
	
	@FXML
	private Label lblId;
	
	@FXML
	private Label lblNombreCom;
	
	@FXML
	private Label lblNombreUsu;
	
	@FXML
	private Label lblNacionalidad;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private void logout(ActionEvent event) throws IOException {
		
		LoginNuevoController.sesion.setNombre("invitado");
		LoginNuevoController.sesion.setPerfil("invitado");
		
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	@FXML
	private void pulsaModificar(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.ModificarPeregrino);
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
	private void pulsaVerCarnet () {
		generarInformePeregrino(p);
		
	}

	Peregrino p;
	
	@FXML
	private void exportarCarnet() {
		
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
        
        
        
        Source fuente = new DOMSource(documento);
        String nomFichero = ("src\\main\\resources\\exportaciones\\"+p.getNombre()+"_peregrino.xml");
        File fichero = new File (nomFichero);
        Result resultado = new StreamResult(fichero);
        TransformerFactory fabricaTransformador= TransformerFactory.newInstance();
        Transformer transformador = fabricaTransformador.newTransformer();
        transformador.transform(fuente, resultado);
        
		}
		catch (ParserConfigurationException ex) { System.out.println("Error: " + ex.getMessage()); }
		catch (TransformerException e) { e.printStackTrace(); }

		
		AlertasServices.altExportacionCorrecta();
	}
	
	
	public void generarInformePeregrino(Peregrino peregrino) {
	    try {
	        //Crear el JasperDesign
	        JasperDesign jasperDesign = new JasperDesign();
	        jasperDesign.setName("Informe_Peregrino_" + peregrino.getNombre_completo());
	        jasperDesign.setPageWidth(595);
	        jasperDesign.setPageHeight(842);
	        jasperDesign.setColumnWidth(515);
	        jasperDesign.setColumnSpacing(0);
	        jasperDesign.setLeftMargin(40);
	        jasperDesign.setRightMargin(40);
	        jasperDesign.setTopMargin(50);
	        jasperDesign.setBottomMargin(50);

	        //Definicion de parámetros
	        JRDesignParameter paramNombre = new JRDesignParameter();
	        paramNombre.setName("nombreCompleto");
	        paramNombre.setValueClass(String.class);
	        jasperDesign.addParameter(paramNombre);

	        JRDesignParameter paramNacionalidad = new JRDesignParameter();
	        paramNacionalidad.setName("nacionalidad");
	        paramNacionalidad.setValueClass(String.class);
	        jasperDesign.addParameter(paramNacionalidad);

	        JRDesignParameter paramCorreo = new JRDesignParameter();
	        paramCorreo.setName("correo");
	        paramCorreo.setValueClass(String.class);
	        jasperDesign.addParameter(paramCorreo);

	        JRDesignParameter paramTotalParadas = new JRDesignParameter();
	        paramTotalParadas.setName("totalParadas");
	        paramTotalParadas.setValueClass(Integer.class);
	        jasperDesign.addParameter(paramTotalParadas);

	        JRDesignParameter paramTotalEstancias = new JRDesignParameter();
	        paramTotalEstancias.setName("totalEstancias");
	        paramTotalEstancias.setValueClass(Integer.class);
	        jasperDesign.addParameter(paramTotalEstancias);

	        //banda de detalles
	        JRDesignBand detailBand = new JRDesignBand();
	        detailBand.setHeight(140);

	        // Etiquetas de información
	        JRDesignStaticText labelNombre = new JRDesignStaticText();
	        labelNombre.setText("Nombre Completo:");
	        labelNombre.setX(20);
	        labelNombre.setY(0);
	        labelNombre.setWidth(200);
	        labelNombre.setHeight(20);
	        detailBand.addElement(labelNombre);

	        JRDesignStaticText labelNacionalidad = new JRDesignStaticText();
	        labelNacionalidad.setText("Nacionalidad:");
	        labelNacionalidad.setX(20);
	        labelNacionalidad.setY(30);
	        labelNacionalidad.setWidth(200);
	        labelNacionalidad.setHeight(20);
	        detailBand.addElement(labelNacionalidad);

	        JRDesignStaticText labelCorreo = new JRDesignStaticText();
	        labelCorreo.setText("Correo:");
	        labelCorreo.setX(20);
	        labelCorreo.setY(60);
	        labelCorreo.setWidth(200);
	        labelCorreo.setHeight(20);
	        detailBand.addElement(labelCorreo);

	        JRDesignStaticText labelTotalParadas = new JRDesignStaticText();
	        labelTotalParadas.setText("Total de Paradas:");
	        labelTotalParadas.setX(20);
	        labelTotalParadas.setY(90);
	        labelTotalParadas.setWidth(200);
	        labelTotalParadas.setHeight(20);
	        detailBand.addElement(labelTotalParadas);

	        JRDesignStaticText labelTotalEstancias = new JRDesignStaticText();
	        labelTotalEstancias.setText("Total de Estancias:");
	        labelTotalEstancias.setX(20);
	        labelTotalEstancias.setY(120);
	        labelTotalEstancias.setWidth(200);
	        labelTotalEstancias.setHeight(20);
	        detailBand.addElement(labelTotalEstancias);

	        // Campos dinámicos
	        JRDesignTextField textFieldNombre = new JRDesignTextField();
	        textFieldNombre.setExpression(new JRDesignExpression("$P{nombreCompleto}"));
	        textFieldNombre.setX(230);
	        textFieldNombre.setY(0);
	        textFieldNombre.setWidth(250);
	        textFieldNombre.setHeight(20);
	        detailBand.addElement(textFieldNombre);

	        JRDesignTextField textFieldNacionalidad = new JRDesignTextField();
	        textFieldNacionalidad.setExpression(new JRDesignExpression("$P{nacionalidad}"));
	        textFieldNacionalidad.setX(230);
	        textFieldNacionalidad.setY(30);
	        textFieldNacionalidad.setWidth(250);
	        textFieldNacionalidad.setHeight(20);
	        detailBand.addElement(textFieldNacionalidad);

	        JRDesignTextField textFieldCorreo = new JRDesignTextField();
	        textFieldCorreo.setExpression(new JRDesignExpression("$P{correo}"));
	        textFieldCorreo.setX(230);
	        textFieldCorreo.setY(60);
	        textFieldCorreo.setWidth(250);
	        textFieldCorreo.setHeight(20);
	        detailBand.addElement(textFieldCorreo);

	        JRDesignTextField textFieldTotalParadas = new JRDesignTextField();
	        textFieldTotalParadas.setExpression(new JRDesignExpression("$P{totalParadas}"));
	        textFieldTotalParadas.setX(230);
	        textFieldTotalParadas.setY(90);
	        textFieldTotalParadas.setWidth(250);
	        textFieldTotalParadas.setHeight(20);
	        detailBand.addElement(textFieldTotalParadas);

	        JRDesignTextField textFieldTotalEstancias = new JRDesignTextField();
	        textFieldTotalEstancias.setExpression(new JRDesignExpression("$P{totalEstancias}"));
	        textFieldTotalEstancias.setX(230);
	        textFieldTotalEstancias.setY(120);
	        textFieldTotalEstancias.setWidth(250);
	        textFieldTotalEstancias.setHeight(20);
	        detailBand.addElement(textFieldTotalEstancias);

	        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

	        // Generar el informe
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

	        //Obtener datos del peregrino
	        int totalParadas = peregrino.getPeregrinoParada().size();
	        int totalEstancias = peregrino.getListaEstancia().size();

	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("nombreCompleto", peregrino.getNombre_completo());
	        parameters.put("nacionalidad", peregrino.getNacionalidad());
	        parameters.put("correo", peregrino.getCorreo());
	        parameters.put("totalParadas", totalParadas);
	        parameters.put("totalEstancias", totalEstancias);

	        //Rellenar el informe
	        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        //Exportar a PDF
	        String outputFile = "src/main/resources/informes/Informe_Peregrino_" + peregrino.getNombre() + ".pdf";
	        JasperExportManager.exportReportToPdfFile(print, outputFile);

	     // Abrir el PDF en el navegador
	        File archivoPdf = new File(outputFile);
	        if (archivoPdf.exists()) {
	            try {
	                String filePath = archivoPdf.getAbsolutePath();
	                String os = System.getProperty("os.name").toLowerCase();

	                if (os.contains("win")) {
	                    // Windows: Usa el comando para abrir en el navegador predeterminado
	                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "chrome", filePath});
	                } else if (os.contains("mac")) {
	                    // macOS: Usa el comando para abrir en el navegador predeterminado
	                    Runtime.getRuntime().exec(new String[]{"open", "-a", "Safari", filePath});
	                } else if (os.contains("nix") || os.contains("nux") || os.contains("bsd")) {
	                    // Linux y derivados: Intenta abrir con xdg-open (o firefox/chrome si está disponible)
	                    Runtime.getRuntime().exec(new String[]{"xdg-open", filePath});
	                } else {
	                    throw new UnsupportedOperationException("Sistema operativo no compatible");
	                }
	            } catch (Exception e) {
	                AlertasServices.altGeneralWarning("Error al abrir", "Error al abrir", "No se pudo abrir el archivo en el navegador.");
	                e.printStackTrace();
	            }
	        } else {
	            AlertasServices.altGeneralWarning("Error al abrir", "Error al abrir", "El carnet no se ha generado correctamente.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		p = peregrinoService.findByNombre(LoginNuevoController.sesion.getNombre());

		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		p = peregrinoService.findByNombre(LoginNuevoController.sesion.getNombre());
		

		lblId.setText(p.getId()+"");
		lblNombreCom.setText(p.getNombre_completo());
		lblNombreUsu.setText(p.getNombre());
		lblNacionalidad.setText(p.getNacionalidad());
	}

}
