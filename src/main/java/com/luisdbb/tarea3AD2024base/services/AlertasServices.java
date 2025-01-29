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
	
	public static void altPeregrinoExiste() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Usuario existente");
        alert.setHeaderText("Usuario existente");
        alert.setContentText("El peregrino introducido ya existe");
        alert.showAndWait();
    }

}
