package com.luisdbb.tarea3AD2024base.repositorios;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;
import org.w3c.dom.Document;

import com.luisdbb.tarea3AD2024base.modelo.ListaDatosCarnet;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2025base.conexiones.ConexiónExistDB;


@Repository
public class ExistDBRepository {

	Collection coleccionPrincipal = ConexiónExistDB.getInstance();

	public void crearSubColeccion(String collectionName) throws Exception {

		// Obtener la colección principal (base de datos) para crear subcolecciones
		Collection col = coleccionPrincipal;

		try {

			if (col == null) {
				throw new Exception("No se pudo conectar a la base de datos");
			}

			CollectionManagementService mgtService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
			mgtService.createCollection(collectionName);
			AlertasServices.altGeneralInformation(
					"Subcoleccion Creada",
					"Subcoleccion Creada",
					"La subcoleccion "+collectionName+" se ha creado correctamente");

		} catch (XMLDBException e) {
			e.printStackTrace();
			throw new Exception("Error al crear la colección", e);
		}

	}
	
	public Collection getSubColeccion(String nombreParada) {
		
		ConexiónExistDB.inicializarDriver();
		

			String uri = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas/"+nombreParada;
			String usuario = "admin";
			String contra = "admin";

			Collection col = null;
			try {
				col = DatabaseManager.getCollection(uri, usuario, contra);
				
				
				if (col == null) {
					System.out.println("no se encontro la SubColeccion");
				}
				else { System.out.println("conectado a la subColeccion"); }
			}
			catch (XMLDBException e) {e.printStackTrace();}
			
			return col;

		}
	
	public void guardarXML (Document documento, String nombrePa, String nombrePe) {
		Collection col = getSubColeccion(nombrePa);
		
		if (col != null) {
			
			try {
				XMLResource res = (XMLResource) col.createResource("carnet-" + nombrePe + "-.xml", "XMLResource");
				res.setContentAsDOM(documento);
				col.storeResource(res);
				AlertasServices.altGeneralInformation(
						"XML Almecenado",
						"XML Almecenado",
						"El XML se ha almacenado en la subcolecion correctamente");
			} catch (XMLDBException e) { e.printStackTrace(); }
			
		}
		else {
			System.out.println("No se ha podido almacenar el XML");
			AlertasServices.altGeneralWarning(
					"XML NO Almecenado",
					"XML NO Almecenado",
					"El XML no se ha almacenado en la subcolecion correctamente");
		}
		
		
	}
	
	public List<ListaDatosCarnet> getDatosXML (String nombreParada) {
		
		List <ListaDatosCarnet> datosResource = new ArrayList<>();
		
		ConexiónExistDB.inicializarDriver();
		
		Collection col = getSubColeccion(nombreParada);
			
			if (col != null) {
				try {
					
					String resources[];
					resources = col.listResources();
					
					//System.out.println(resources.length);
					
					for (int i = 0; i < resources.length; i++) {
						
						String nombreRecurso [] = resources[i].split("-");
						String nombreUsuCarnet = nombreRecurso[1];
						
						//System.out.println(nombreUsuCarnet);
						
						XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
						
						String ruta = "/db/Paradas/"+nombreParada;
						//ruta = java.net.URLEncoder.encode(ruta, "UTF-8");
					
						String query = "for $p in collection('"+ruta+"')/"+nombreUsuCarnet+"carnet[nombre='"+nombreUsuCarnet+"'] "+
						"return concat($p/id, ',', $p/nombre, ',', $p/nacionalidad, ',', $p/fechaExpedicion)";
			            ResourceSet result = service.query(query);
			            
			            ResourceIterator it = result.getIterator();
			            
			            //System.out.println(ruta);
			            
			            while (it.hasMoreResources()) {
			            	
			                Resource res = it.nextResource();
			                
			                String [] datos = res.getContent().toString().split(",");
			                
			                ListaDatosCarnet objetoDato = new ListaDatosCarnet (datos[0], datos[1], datos[2], datos[3]);
			                
			                System.out.println(objetoDato.toString());
			                
			                datosResource.add(objetoDato);

			            }
					
					}
				}
				catch (XMLDBException  e) { e.printStackTrace(); }
			}
			
			else {
				System.out.println("no se ha encontrado esa subColeccion");
			}
			
			
			return datosResource;
			
		
	}

	}

