package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class ValidacionesService {

	// METODO PARA LEER PAISES.XML Y ALMACENARLO EN UN MAP
	public static HashMap<String, String> diccionarioPaises = new HashMap<String, String>();

	public static void almacenarEnMap() {
		try {
			File paisesXML = new File("src\\main\\resources\\paises\\paises.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document documento = builder.parse(paisesXML);

			NodeList paises = documento.getElementsByTagName("pais");

			for (int i = 0; i < paises.getLength(); i++) {
				Node pais = paises.item(i);

				String idMap = pais.getFirstChild().getNextSibling().getTextContent();
				String paisMap = pais.getLastChild().getPreviousSibling().getTextContent();

				diccionarioPaises.put(idMap, paisMap);
			}

		}

		catch (ParserConfigurationException | SAXException | IOException ex) {
			System.err.println("Error: " + ex.getMessage());
		}
	}

	// METODO PARA COMPROBAR LAS CREDENCIALES
	public static boolean comprobarCredenciales(String usuario, String contrasenia, String nombreCompleto, String region) {

		boolean credencialesCorrectas = true;

		if (usuario == null || usuario.length() == 0) {
			AlertasServices.altNombreVacio();
			credencialesCorrectas = false;
		} else {
			for (int i = 0; i < usuario.length(); i++) {
				if (Character.isSpaceChar(usuario.charAt(i)) || Character.isDigit(usuario.charAt(i))) {
					AlertasServices.altNombreConEspacioONumero();
					credencialesCorrectas = false;
					break;
				}
			}
		}

		if (contrasenia == null || contrasenia.length() == 0) {
			AlertasServices.altContraVacia();
			credencialesCorrectas = false;
		} else {
			for (int i = 0; i < contrasenia.length(); i++) {
				if (Character.isSpaceChar(contrasenia.charAt(i))) {
					AlertasServices.altContraConEspacio();
					credencialesCorrectas = false;
					break;
				}
			}
		}
		
		if (nombreCompleto == null || nombreCompleto.length() == 0) { AlertasServices.altNombreCompleto(); credencialesCorrectas = false;}
		else {
		
		for (int i = 0; i < nombreCompleto.length(); i++) {
			if (Character.isDigit(nombreCompleto.charAt(i))) {
				AlertasServices.altNombreCompletoConNumeros();
				credencialesCorrectas = false;
				break;
			}
		}
		}
		if (region == null || region.length() == 0) { AlertasServices.altRegionVacia(); credencialesCorrectas = false;}
		return credencialesCorrectas;

	}
	
	public static boolean comprobarTextField(String texto) {
		
		boolean credencialesCorrectas = true;
		
		if (texto.length() == 0) { AlertasServices.altNombreVacio(); credencialesCorrectas = false;}
		
		return credencialesCorrectas;

	}
	
	

}
