package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

@ExtendWith(MockitoExtension.class)
public class SellarPeregrinoTest {
	
	@InjectMocks
    private SellarCarnetController sellarCarnetController;
	
    @Mock
    private PeregrinoService peregrinoService;

    @Mock
    private ParadaService paradaService;

    @Mock
    private ValidacionesService validacionesService;

    @Mock
    private AlertasServices alertasServices;
    
	private ComboBox<String> coBoxPeregrino = new ComboBox<String>();
	private CheckBox chBoxEstancia = new CheckBox();
	private CheckBox chBoxVip = new CheckBox();
	
	/*
	 * NOTA: COMO TENGO EL PROYECTO JUNTO A LA ULTIMA ACTUALIZACION
	 * DE LA TAREA DE ACCESO A DATOS CAUSA UNA INTERFERENCIA CON LA BASE DE 
	 * OBJECTDB Y NO ME DEJA EJECUTAR CORRECTAMENTE LOS TEST
	 * */
	
	@BeforeAll
    public static void init() {
    	Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
    	sellarCarnetController = new SellarCarnetController();
    	
    	sellarCarnetController.coBoxPeregrino = coBoxPeregrino;
    	sellarCarnetController.chBoxEstancia = chBoxEstancia; 
    	sellarCarnetController.chBoxVip = chBoxVip;
    }
    
	@Test
	void testSellarValido() {
		Platform.runLater(() -> {
			coBoxPeregrino.setValue("Alberto");
			chBoxEstancia.setSelected(false);
			chBoxVip.setSelected(false);

			sellarCarnetController.pulsaSellar();

			verify(alertasServices, times(1)).altSelladoCorrecto();
		});

	}

}
