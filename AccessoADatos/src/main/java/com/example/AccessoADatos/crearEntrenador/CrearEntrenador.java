package com.example.AccessoADatos.crearEntrenador;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.example.AccessoADatos.clases.*;
import com.example.AccessoADatos.crearEntrenador.MetodosCrearEntrenador;
import com.example.AccessoADatos.service.*;


public class CrearEntrenador {
	
	EntrenadorService entrenadorService;
	

	
	TorneoService torneoService;
	
	CarnetService carnetService;
	
	public CrearEntrenador(TorneoService torneoService,EntrenadorService entrenadorService,CarnetService carnetService) {
		this.torneoService = torneoService;
		this.entrenadorService =  entrenadorService;
	
		this.carnetService = carnetService;
	}

	public void crearEntrenador() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean pepe = false;
        String nombre =null;
        String nacionalidad = null;
        String contra = null;
        
        do {
            System.out.print("Dame nombre como nuevo Entrenador: ");
            nombre = sc.next();
            System.out.println("--------------------------------");
            System.out.print("Dame una contraseña para el nuevo Entrenador: ");
            contra = sc.next();
            System.out.println("--------------------------------");
            System.out.println("Para las nacionalidaes tienes las siguientes: ");
            System.out.println("--------------------------------");
            List<String> listaPaises = MetodosCrearEntrenador.cargarNombresDePaises("/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/crearEntrenador/paises.xml");
            System.out.println(listaPaises);
            System.out.println("--------------------------------");
            System.out.print("Dame una nacionalidad: ");
            nacionalidad = sc.next();
            boolean meme = false;
            do {
                if (MetodosCrearEntrenador.verificarPais(nacionalidad,listaPaises)){
                    meme= true;
                }else {
                    System.out.println("--------------------------------");
                    System.out.println("La nacionalidad que has introducido esta mal. Vuelva a introducir.");
                    System.out.println("--------------------------------");
                    System.out.println(listaPaises);
                    System.out.println("--------------------------------");
                    System.out.print("Dame una nacionalidad: ");
                    nacionalidad = sc.next();
                }
            }while (!meme);

            System.out.println("--------------------------------");
            System.out.println("El usuario es: "+nombre+"\nLa contraseña es: "+contra+
                    "\nLa nacinalidad es: "+nacionalidad);
            System.out.println("--------------------------------");
            System.out.println("Si o No");
            System.out.println("--------------------------------");
            String validar = sc.next();


            if (validar.equalsIgnoreCase("si")||validar.equalsIgnoreCase("s")){
                pepe = true;
            }
        }while (!pepe);
        System.out.println("--------------------------------");

       
            MetodosCrearEntrenador entrenador = new MetodosCrearEntrenador();
            long id = entrenador.obtenerUltimoIdEntrenador("/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt","ET");
            if(id == 0){
                id = 1000;
            }
            id += 1;

            if (!MetodosCrearEntrenador.verificarCredenciales("/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt",nombre,contra,"ET")){

                System.out.print("En que torneo te encuentras entre estos: ");
                System.out.println(MetodosCrearEntrenador.obtenerNombresDesdeArchivo("/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/ficheros/torneo.dat"));
                String torneo = sc.next();

                if (MetodosCrearEntrenador.buscarNombreTorneo("/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/ficheros/torneo.dat",torneo)){

                    System.out.println("--------------------------------");
                    System.out.println("Existe el torneo");
                    System.out.println("--------------------------------");
                    System.out.println("Vamos a crearte como entrenador.");
                    System.out.println("--------------------------------");



                    long a = entrenador.sacarIdTorneo("/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/ficheros/torneo.dat",torneo);
                    
                    Torneo torneoBD = torneoService.buscarTorneoPorId(a);
                    Set<Entrenador> entrenadorSet = torneoBD.getEntrenadores();

                    int numEntre = entrenadorSet.size();
                    if (numEntre < 3){
                        Entrenador entrenador1 = new Entrenador();
                        entrenador1.setNombre(nombre);
                        entrenador1.setNacionalidad(nacionalidad);
                        entrenador1.agregarTorneo(torneoBD);
                        Carnet carnet = new Carnet();
                        carnet.setEntrenador(entrenador1);
                        carnet.setIdEntrenador(entrenador1.getId());
                        carnet.setFechaExpedicion(LocalDate.now());
                        carnet.setNumVictorias(0);
                        carnet.setNumVictorias(0);
                        entrenador1.setCarnet(carnet);
                        carnetService.guardarCarnet(carnet);

                        MetodosCrearEntrenador metodosCrearEntrenador = new MetodosCrearEntrenador();
                        metodosCrearEntrenador.escribirAlFinalDelTxtEntrenador("/Users/padrejulian/Documents/TrabajoFicheros/AccessoADatos/src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt",entrenador1,contra);


                        System.out.println("Se crea todo en la base de datos y en credenciales.txt");

                    }else {
                        System.out.println("No se puede crear mas entrenadores para este Torneo");

                    }

                    

                }else{
                    System.out.println("--------------------------------");
                    System.out.println("No existe el torneo.");
                    System.out.println("--------------------------------");
                    System.out.println("El entrenador no se ha creado.");
                }
            }else {
                System.out.println("El nombre del nuevo usuario como Entrenador ya esta en uso.");
                System.out.println("--------------------------------");
                System.out.println("El entrenador no se ha creado.");
            }
        }
	
}
