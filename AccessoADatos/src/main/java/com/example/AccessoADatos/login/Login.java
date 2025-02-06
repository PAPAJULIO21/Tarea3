package com.example.AccessoADatos.login;

import java.sql.SQLException;
import java.util.Scanner;

import com.example.AccessoADatos.crearTorneo.CrearTorneo;
import com.example.AccessoADatos.exportarCarnetXML.ExportarCarnetXML;
import com.example.AccessoADatos.exportarDatosTorneo.ExportarDatosTorneo;
import com.example.AccessoADatos.inscribir.Inscribir;
import com.example.AccessoADatos.pelear.Pelear;
import com.example.AccessoADatos.repository.CombateRepository;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.example.AccessoADatos.service.TorneoService;


public class Login {

	TorneoService service;

	CombateService combateService;

    EntrenadorService entrenadorService;

    CarnetService carnetService;

	public Login(TorneoService service,CombateService combateService,EntrenadorService entrenadorService,CarnetService carnetService) {
		this.service = service;
		this.combateService = combateService;
        this.entrenadorService = entrenadorService;
        this.carnetService = carnetService;
	}

	public  void IniciarSesion() throws SQLException {

	Scanner sc = new Scanner(System.in);
    System.out.print("Dime tu usuario: ");
    String usu = sc.next();
    System.out.print("Dame tu contraseña: " );
    String contra = sc.next();
    System.out.println("--------------------------------");
    MetodosLogin metodosLogin = new MetodosLogin();
    if (MetodosLogin.buscarNombreYContraseña("src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt",usu,contra)){
        String per = MetodosLogin.buscarNombreYContraseñaYSacarPerfil("src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt",usu,contra);
        while (true) { // Este bucle asegura que el menú se repita hasta que el usuario elija salir
            switch (per) {
                case "AG":
                    System.out.println("--------------------------------");
                    System.out.println("Como Administrador General puedes hacer lo siguiente: ");
                    System.out.println("--------------------------------");
                    System.out.println("1- Crear un nuevo Torneo\n" +
                                       "2- Salir");
                    System.out.println("--------------------------------");
                    int num = sc.nextInt();
                    System.out.println("--------------------------------");
                    switch (num) {
                        case 1:
                        	CrearTorneo crearTorneo = new CrearTorneo(service,combateService);
                            crearTorneo.crearTorneo(); // Ejecuta el método
                            break; // Regresa al menú de "AG"
                        case 2:
                            return; // Sale del programa o del menú principal
                        default:
                            System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                            break;
                    }
                    break; // Sale del caso "AG" para reiniciar desde el menú principal

                case "AT":
                    String i = MetodosLogin.buscarNombreYContraseñaYSacarId("src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt", usu, contra);
                    System.out.println("--------------------------------");
                    System.out.println("Como Administrador de Torneo puedes hacer lo siguiente: ");
                    System.out.println("--------------------------------");
                    System.out.println("1- Exportar Datos de Torneo\n" +
                                       "2- Inscribir\n" +
                                        "3- Pelear\n"+
                                       "4- Salir");
                    System.out.println("--------------------------------");
                    int num1 = sc.nextInt();
                    System.out.println("--------------------------------");
                    switch (num1) {
                        case 1:
                            ExportarDatosTorneo exportarDatosTorneo = new ExportarDatosTorneo(service,entrenadorService,combateService);
                            exportarDatosTorneo.exportarTorneo(i);
                            break; // Regresa al menú de "AT"
                        case 2:
                            Inscribir inscribir = new Inscribir(combateService ,carnetService,service,entrenadorService);
                            inscribir.inscribir(i);
                            break;
                        case 3:
                            Pelear pelear = new Pelear(combateService,service,carnetService,entrenadorService);
                            pelear.pelear(i);
                            break;
                        case 4:
                            return; // Sale del programa o del menú principal
                        default:
                            System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                            break;
                    }
                    break; // Sale del caso "AT" para reiniciar desde el menú principal

                case "ET":
                    String id = MetodosLogin.buscarNombreYContraseñaYSacarId("src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt", usu, contra);
                    System.out.println("--------------------------------");
                    System.out.println("Como Entrenador puedes hacer lo siguiente: ");
                    System.out.println("--------------------------------");
                    System.out.println("1- Exportar tu Carnet en XML\n" +
                                       "2- Salir");
                    System.out.println("--------------------------------");
                    int num2 = sc.nextInt();
                    System.out.println("--------------------------------");
                    switch (num2) {
                        case 1:
                            ExportarCarnetXML exportarCarnetXML = new ExportarCarnetXML(entrenadorService,carnetService,service,combateService);
                            exportarCarnetXML.exportarCarnet(id);
                            break; // Regresa al menú de "ET"
                        case 2:
                            return; // Sale del programa o del menú principal
                        default:
                            System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                            break;
                    }
                    break; // Sale del caso "ET" para reiniciar desde el menú principal

                default:
                    System.out.println("Rol no reconocido. Por favor, verifica.");
                    return;
            }
        }


    }else{
        System.out.println("Mal introducido el usuario o la contraseña.");
    }
	}
}
