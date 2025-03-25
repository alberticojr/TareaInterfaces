package com.luisdbb.tarea3AD2025base.conexiones;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConexionObjectDB {
	
	private static ConexionObjectDB INSTANCE = null;
	private final static String PATH = "objectdb://localhost:6136/baseOBD.odb;user=admin;password=admin";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	private ConexionObjectDB () {
		emf = Persistence.createEntityManagerFactory(PATH);
		em = emf.createEntityManager();
	}
	
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConexionObjectDB();
		}
	}
	
	public static EntityManager getInstance() {
		if (INSTANCE == null) {
		createInstance();
		}
        return em;
    }
	
	public void closeConnection() {
		em.close();
  }
	
}
	