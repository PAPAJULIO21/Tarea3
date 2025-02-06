package com.example.AccessoADatos.iniciarPrograma;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.crearEntrenador.CrearEntrenador;
import com.example.AccessoADatos.login.Login;
import com.example.AccessoADatos.repository.CombateRepository;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.example.AccessoADatos.service.TorneoService;

@Service
public class IniciarPrograma {

	 	
	     TorneoService torneoService;

	    
	     EntrenadorService entrenadorService;
	     
	     CombateService combateRepository;
	     
	     CarnetService carnetService;
	     

	    
	    
    
    public IniciarPrograma(TorneoService torneoService, EntrenadorService entrenadorService,CombateService combateRepository,CarnetService carnetService) {
			super();
			this.torneoService = torneoService;
			this.entrenadorService = entrenadorService;
			this.combateRepository = combateRepository;
			this.carnetService = carnetService;
			
		}

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        do {
            System.out.println("--------------------------------");
            System.out.println("Menu Invitado");
            System.out.println("--------------------------------");
            System.out.println(
                    "1- Iniciar Sesion\n" +
                    "2- Crear Entrenador\n" +
                    "3- Salir\n"+
                            "--------------------------------\n"+
                            "Que quieres hacer: Indica un numero");
            System.out.println("--------------------------------");

            if (!sc.hasNextInt()) { // Si no es un número, limpiar entrada y volver a preguntar
                System.out.println("Entrada inválida. Por favor, ingresa un número válido.");
                sc.next(); // Descarta la entrada incorrecta
                continue; // Reinicia el bucle
            }

            int opcion = sc.nextInt();
            System.out.println("--------------------------------");

            switch (opcion) {
                case 1:
                    Login login = new Login(torneoService, combateRepository, entrenadorService, carnetService);
                    try {
                        login.IniciarSesion();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    CrearEntrenador crearEntrenador = new CrearEntrenador(torneoService, entrenadorService, carnetService);
                    try {
                        crearEntrenador.crearEntrenador();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Un abrazo fuerte.");
                    System.out.println("--------------------------------");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (!salir);
    }
}

