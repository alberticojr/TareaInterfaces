package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public class RegistroParadaTest {

    @InjectMocks
    private RegistroParadaController registroParadaController;

    @Mock
    private ParadaService paradaService;

    @Mock
    private CredencialesService credencialeService;

    @Mock
    private AlertasServices alertasServices;

    @Mock
    private ValidacionesService validacionesService;

    @Mock
    private StageManager stageManager;
    
    @BeforeAll
    public static void init() {
    	Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
    	registroParadaController = new RegistroParadaController();
    	credencialeService = new CredencialesService();
    	
    }


	@Test
	void testCrearParadaUsuarioExiste() {
		Platform.runLater(() -> {
			try (MockedStatic<ValidacionesService> validacionesMock = mockStatic(ValidacionesService.class)) {
				// Simulando entrada de datos válidos

				String nombreParada = "ParadaTest";
				String region = "A";
				String nombreResponsable = "Responsable";
				String contraResponsable = "password123";

				validacionesMock.when(() -> ValidacionesService.comprobarCredenciales(nombreResponsable,
						contraResponsable, nombreParada, region)).thenReturn(true);

				when(credencialeService.credencialExiste(nombreResponsable)).thenReturn(true);

				// Llamada al método
				try {
					registroParadaController.pulsaCrearParada(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Verificar que se muestra la alerta de usuario ya existe
				verify(alertasServices, times(1)).altUsuarioExiste();
				verify(paradaService, never()).save(any());
			}
		});
	}
	
	@Test
	void testCrearParadaExistente() {
		Platform.runLater(() -> {
			try (MockedStatic<ValidacionesService> validacionesMock = mockStatic(ValidacionesService.class)) {
				// Simulando entrada de datos válidos

				String nombreParada = "ParadaTest";
				String region = "A";

				when(paradaService.existeParada(nombreParada, region.charAt(0))).thenReturn(false);

				// Llamada al método
				try {
					registroParadaController.pulsaCrearParada(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Verificar que se muestra la alerta de usuario ya existe
				verify(alertasServices, times(1)).altParadaExiste();
				verify(paradaService, never()).save(any());
			}
		});
	}





}
