package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
public class ModificarDatosTest {
	
	@InjectMocks
    private ModificarDatosController modificarDatosController;
	
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
    
    private TextField nombreField = new TextField();
    private TextField correo = new TextField();
    private ComboBox<String> coBoxNacionalidad = new ComboBox<String>();
    
    @BeforeAll
    public static void init() {
    	Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
    	modificarDatosController = new ModificarDatosController();
    	credencialeService = new CredencialesService();
    	
    	modificarDatosController.fieldNombreCompleto = nombreField;
    	modificarDatosController.fieldCorreo = correo;
    	modificarDatosController.CBoxNacionalidad = coBoxNacionalidad;
    	
    }
    
	@Test
	void testCrearParadaUsuarioExiste() {

		Platform.runLater(() -> {
			try (MockedStatic<ValidacionesService> validacionesMock = mockStatic(ValidacionesService.class)) {

				nombreField.setText("David Rodrigez");
				correo.setText("david@gmail.com");
				coBoxNacionalidad.setValue("España");

				validacionesMock
						.when(() -> ValidacionesService.comprobarCredenciales("a", "a", nombreField.getText(), "a"))
						.thenReturn(true);
				validacionesMock.when(() -> ValidacionesService.validacionCorreo(correo.getText())).thenReturn(true);

				modificarDatosController.pulsaEditar();

				verify(peregrinoService, times(1)).update(any(Peregrino.class));

			}
		});

	}
	
	

}
