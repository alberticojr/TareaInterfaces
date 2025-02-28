package com.luisdbb.tarea3AD2024base.modelo;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;


public class ConexionDB4O {
	private static ConexionDB4O INSTANCE = null;
	private final static String PATH = "./bd/BaseDB4O";
	private static ObjectContainer db;

	private ConexionDB4O() {
	}

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConexionDB4O();
			INSTANCE.performConnection();
		}
	}

	public static ObjectContainer getInstance() {
		if (INSTANCE == null)
			createInstance();
		return db;
	}

	public void performConnection() {
		// EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), PATH);
	}

	 public void closeConnection() {
         db.close();
     }
} // Fin Conexion
