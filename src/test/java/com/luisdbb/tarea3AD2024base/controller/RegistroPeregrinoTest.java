package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
class RegistroPeregrinoTest {

    @InjectMocks
    private RegistroPeregrinoController registroController;

    @Mock
    private PeregrinoService peregrinoService;

    @Mock
    private ParadaService paradaService;

    @Mock
    private CredencialesService credencialeService;

    @Mock
    private ValidacionesService validacionesService;

    @Mock
    private AlertasServices alertasServices;

    // Simulación de los campos de entrada de la interfaz gráfica
    private TextField nombreField = new TextField();
    private TextField usuField = new TextField();
    private TextField contraField = new TextField();
    private TextField conf_Contra = new TextField();
    private TextField correo = new TextField();
    private ChoiceBox<String> chbParada = new ChoiceBox<>();
    private ChoiceBox<String> chbPaises = new ChoiceBox<>();
    
    @BeforeAll
    public static void init() {
    	Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        // Asignar los campos a la instancia del controlador
        registroController.nombreField = nombreField;
        registroController.usuField = usuField;
        registroController.contraField = contraField;
        registroController.conf_Contra = conf_Contra;
        registroController.correo = correo;
        registroController.chbParada = chbParada;
        registroController.chbPaises = chbPaises;
    }

    @Test
    void testCrearPeregrinoExitoso() {
        Platform.runLater(() -> {
            try (MockedStatic<ValidacionesService> validacionesMock = mockStatic(ValidacionesService.class)) {
                
                // Simular datos de entrada válidos
                nombreField.setText("David Rodrigez");
                usuField.setText("david");
                contraField.setText("dav123");
                conf_Contra.setText("dav123");
                correo.setText("david@gmail.com");
                chbParada.setValue("Gijon - G");
                chbPaises.setValue("España");

                // Configurar mocks para métodos estáticos
                validacionesMock.when(() -> ValidacionesService.comprobarCredenciales(usuField.getText(), contraField.getText(), nombreField.getText(), "a"))
                                .thenReturn(true);
                validacionesMock.when(() -> ValidacionesService.validacionCorreo(correo.getText()))
                                .thenReturn(true);

                // Configurar mocks para servicios
                when(peregrinoService.existePeregrino(usuField.getText())).thenReturn(false);
                when(alertasServices.altConfirmacion()).thenReturn(true);
                when(paradaService.findByNameAndRegion("Gijon", 'G')).thenReturn(new Parada("Gijon", 'G', "alberto"));

                // Ejecutar el método
                registroController.PulsaCrearPeregrino();

                // Verificar que se llamaron los métodos esperados
                verify(credencialeService, times(1)).save(any(Credenciales.class));
                verify(peregrinoService, times(3)).save(any(Peregrino.class));
                verify(alertasServices, times(1)).altPeregrinoCreado();
            }
        });
    }

	@Test
	void testCrearPeregrinoNombreUsuarioYaExiste() {
		Platform.runLater(() -> {
			
			usuField.setText("david");

			when(peregrinoService.existePeregrino(usuField.getText())).thenReturn(true);

			registroController.PulsaCrearPeregrino();

			verify(alertasServices, times(1)).altUsuarioExiste();
			verify(peregrinoService, never()).save(any(Peregrino.class));
		});
	}

    @Test
    void testCrearPeregrinoCorreoInvalido() {
    	Platform.runLater(() -> {
        
        correo.setText("david@gmail.com");

        when(validacionesService.validacionCorreo(correo.getText())).thenReturn(false);

        registroController.PulsaCrearPeregrino();

        verify(alertasServices, times(1)).altCorreoIncorrecto();
    	});
    }

    @Test
    void testCrearPeregrinoContrasenaNoCoincide() {
    	Platform.runLater(() -> {
        
        contraField.setText("dav123");
        conf_Contra.setText("dav12345");

        registroController.PulsaCrearPeregrino();

        verify(contraField.equals(conf_Contra));
    	});
    }

}

