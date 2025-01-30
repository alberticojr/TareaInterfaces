package com.luisdbb.tarea3AD2024base.services;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertasServices {
	
	public static void altNombreConEspacioONumero() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nombre no Valido");
        alert.setHeaderText("Nombre no Valido");
        alert.setContentText("NO se pueden escribir ni espacios, ni numeros en el nombre.");
        alert.showAndWait();
    }
	
	public static void altNombreVacio() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nombre no Valido");
        alert.setHeaderText("Nombre no Valido");
        alert.setContentText("NO se puede dejar el nombre en blanco");
        alert.showAndWait();
    }
	
	public static void altContraConEspacio() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Contraseña no Valida");
        alert.setHeaderText("Contraseña no Valida");
        alert.setContentText("NO se pueden escribir espacios en la contrasenia.");
        alert.showAndWait();
    }
	
	public static void altContraVacia() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Contraseña no Valida");
        alert.setHeaderText("Contraseña no Valida");
        alert.setContentText("NO se puede dejar la contrasenia en blanco.");
        alert.showAndWait();
    }
	
	public static void altUsuarioExiste() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Usuario existente");
        alert.setHeaderText("Usuario existente");
        alert.setContentText("El usuario introducido ya existe");
        alert.showAndWait();
    }
	
	public static void altParadaExiste() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Parada existente");
        alert.setHeaderText("Parada existente");
        alert.setContentText("La parada introducida ya existe");
        alert.showAndWait();
    }
	
	public static void altRegionVacia() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Region incorrecta");
        alert.setHeaderText("Region incorrecta");
        alert.setContentText("La region introducida es incorrecta o vacia");
        alert.showAndWait();
    }
	
	public static void altNombreCompleto() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nombre completo incorrecto");
        alert.setHeaderText("Nombre completo incorrecto");
        alert.setContentText("El nombre completo es incorrecto o esta vacio");
        alert.showAndWait();
    }
	
	public static void altNoParadas() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("No hay paradas disponibles");
        alert.setHeaderText("No hay paradas disponibles");
        alert.setContentText("No hay paradas disponibles en el sistema para registrarse");
        alert.showAndWait();
    }

}
