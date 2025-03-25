package com.luisdbb.tarea3AD2025base.conexiones;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

public class ConexiónExistDB {
	
	private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas";
	private static String usuario = "admin";
	private static String contra = "admin";
	
	// Driver de la base de datos
	private static String driver = "org.exist.xmldb.DatabaseImpl";

	// Método para obtener la colección de la base de datos
	public static Collection getInstance() {

		Collection col = null;

		try {

			// Inicializa el driver de la base de datos
			inicializarDriver();

			col = DatabaseManager.getCollection(URI, usuario, contra);
			System.out.println("conectado correctamente");

			// si la coleccion no existe la crea
			if (col == null) {

				Collection raiz = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", usuario, contra);

				CollectionManagementService mgtService = (CollectionManagementService) raiz.getService("CollectionManagementService", "1.0");
				
				mgtService.createCollection(URI.substring(URI.lastIndexOf("/") + 1)); //Devuelve Paradas

				col = DatabaseManager.getCollection(URI, usuario, contra);
				System.out.println("Conexion creada");
			}

		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		return col;

	}
	
	public static void inicializarDriver () {
		try {
		
		Class<?> cl = Class.forName(driver);
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
		
		} catch (XMLDBException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		
	}
	
	
	public static void closeCollection(Collection col) {
		if (col != null) {
			try {
				col.close();
			} catch (XMLDBException e) {
				e.printStackTrace();
			}
		}
	}
}
