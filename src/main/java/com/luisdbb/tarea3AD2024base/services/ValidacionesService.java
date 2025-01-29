package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	public static boolean comprobarCredenciales(String usuario, String contrasenia) {

		boolean credencialesCorrectas = true;

		for (int i = 0; i < usuario.length(); i++) {
			if (Character.isSpaceChar(usuario.charAt(i)) || Character.isDigit(usuario.charAt(i))) {
				System.out.println("NO se pueden escribir ni espacios, ni numeros en el nombre.");
				AlertasServices.altNombreConEspacioONumero();
				credencialesCorrectas = false;
				break;
			}
		}
		
		for (int i = 0; i < contrasenia.length(); i++) {
			if (Character.isSpaceChar(contrasenia.charAt(i))) {
				System.out.println("NO se pueden escribir espacios en la contrasenia.");
				AlertasServices.altContraConEspacio();
				credencialesCorrectas = false;
				break;
			}
		}
		
		if (usuario.length() == 0) { AlertasServices.altNombreVacio(); credencialesCorrectas = false;}
		
		if (contrasenia.length() == 0) { AlertasServices.altContraVacia(); credencialesCorrectas = false;}
		
		return credencialesCorrectas;

	}
	
	

}
