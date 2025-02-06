package com.example.AccessoADatos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.example.AccessoADatos.iniciarPrograma.IniciarPrograma;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.example.AccessoADatos.service.TorneoService;

@SpringBootApplication
public class AccessoADatosApplication implements CommandLineRunner {
	@Autowired
	TorneoService service;
	
	@Autowired
	EntrenadorService entrenadorService;
	
	@Autowired
	CombateService combateService;

	
	@Autowired
	CarnetService carnetService;
	
	
    public static void main(String[] args) {
        SpringApplication.run(AccessoADatosApplication.class, args);
    }

    @Override
    public void run(String... args) {    
    	IniciarPrograma iniciarPrograma = new IniciarPrograma(service,entrenadorService,combateService,carnetService);
    	iniciarPrograma.mostrarMenu();

    }

}
