package com.luisdbb.tarea3AD2024base.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
class LoginTest {

    @Mock
    private CredencialesService credencialesService;

    @Mock
    private StageManager stageManager;

    @InjectMocks
    private LoginNuevoController loginController;
    
    @BeforeAll
    public static void init() {
    	Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        loginController.usufield = new TextField();
        loginController.contrafield = new TextField();
        loginController.lblIncorrecto1 = new Label();
        loginController.lblIncorrecto2 = new Label();
    }

    @Test
    void testLoginAdmin() throws IOException {
        loginController.usufield.setText("admin");
        loginController.contrafield.setText("admin");

        loginController.login(mock(ActionEvent.class));

        assertEquals("admin", LoginNuevoController.sesion.getNombre());
        assertEquals("administrador", LoginNuevoController.sesion.getPerfil());
        verify(stageManager).switchScene(FxmlView.MenuAdministrador);
    }

    @Test
    void testLoginPeregrino() throws IOException {
        loginController.usufield.setText("peregrinoUser");
        loginController.contrafield.setText("password123");

        when(credencialesService.authenticate("peregrinoUser", "password123")).thenReturn(true);
        when(credencialesService.findByNombre("peregrinoUser")).thenReturn(new Credenciales("peregrinoUser", "peregrino", "peregrino"));

        loginController.login(mock(ActionEvent.class));

        assertEquals("peregrinoUser", LoginNuevoController.sesion.getNombre());
        assertEquals("peregrino", LoginNuevoController.sesion.getPerfil());
        verify(stageManager).switchScene(FxmlView.MenuPeregrino);
    }

    @Test
    void testLoginParada() throws IOException {
        loginController.usufield.setText("paradaUser");
        loginController.contrafield.setText("securePass");

        when(credencialesService.authenticate("paradaUser", "securePass")).thenReturn(true);
        when(credencialesService.findByNombre("paradaUser")).thenReturn(new Credenciales("paradaUser", "parada", "parada"));

        loginController.login(mock(ActionEvent.class));

        assertEquals("paradaUser", LoginNuevoController.sesion.getNombre());
        assertEquals("parada", LoginNuevoController.sesion.getPerfil());
        verify(stageManager).switchScene(FxmlView.MenuResponsable);
    }

    @Test
    void testLoginIncorrecto() throws IOException {
        loginController.usufield.setText("usuarioInvalido");
        loginController.contrafield.setText("claveErronea");

        when(credencialesService.authenticate("usuarioInvalido", "claveErronea")).thenReturn(false);

        loginController.login(mock(ActionEvent.class));

        assertTrue(loginController.lblIncorrecto1.isVisible());
        assertTrue(loginController.lblIncorrecto2.isVisible());
    }
}