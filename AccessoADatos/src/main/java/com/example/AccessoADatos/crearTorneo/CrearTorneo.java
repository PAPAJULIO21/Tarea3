package com.example.AccessoADatos.crearTorneo;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.example.AccessoADatos.db4o.Usuario;
import com.example.AccessoADatos.db4o.UsuariosDb4o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.TorneoService;

@Component
public class CrearTorneo {

	 
	  TorneoService torneoService;
	  CombateService combateService;
	  
	  public CrearTorneo(TorneoService service,CombateService combateService) {
		this.torneoService = service;
		this.combateService = combateService;
	}
	
	public  void crearTorneo() throws SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Si quieres crear un nuevo torneo. Tendrás que seguir los siguientes pasos.");
        System.out.println("--------------------------------");
        System.out.print("Nombre del torneo: ");
        String nombreTor = sc.next();
        System.out.print("Localidad del torneo: ");
        String localidad = sc.next();

        System.out.println("--------------------------------");
        MetodosCrearTorneo metodosCrearTorneo = new MetodosCrearTorneo();

        if (!metodosCrearTorneo.existeTorneo("src/main/java/com/example/AccessoADatos/ficheros/torneo.dat",nombreTor,localidad)){
            System.out.println("Para crear un torneo tienes que darme un Usuario de Administrador de Torneo y una Contraseña.");
            boolean pepe = false;
            String usu_AT = null;
            String contra_AT = null;
            do {
                System.out.println("--------------------------------");
                System.out.print("Usuario: ");
                usu_AT = sc.next();
                System.out.print("Contraseña: ");
                contra_AT = sc.next();
                System.out.println("--------------------------------");
                System.out.println("El usuario es: "+usu_AT+
                        "\nEl contraseña es: "+contra_AT);
                System.out.println("--------------------------------");
                System.out.println("Si o No");
                System.out.println("--------------------------------");
                String validar = sc.next();


                if (validar.equalsIgnoreCase("si")||validar.equalsIgnoreCase("s")){
                    pepe = true;
                }
            }while (!pepe);
            System.out.println("--------------------------------");

            int id_Todo = metodosCrearTorneo.obtenerUltimoIdTorneo("src/main/java/com/example/AccessoADatos/ficheros/torneo.dat");
            
            id_Todo +=1;
            
            if (pepe){

                MetodosCrearTorneo admin = new MetodosCrearTorneo(usu_AT,contra_AT,"AT",id_Todo);

                if (!admin.existeEnFichero("src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt","AT")){
                    
                	Torneo torneo = new Torneo();               
                	torneo.setNombre(nombreTor);
                	torneo.setPuntosVictoria(10000);
                	torneo.setCodRegion(localidad.charAt(0));

                	torneoService.guardarTorneo(torneo);
                	Torneo torneoCreado = torneoService.buscarTorneoPorId(torneo.getId());
                	
                    metodosCrearTorneo.guardarSiNoExisteConDatos("src/main/java/com/example/AccessoADatos/ficheros/torneo.dat",torneoCreado,usu_AT);
                    admin.guardarSiNoExiste("src/main/java/com/example/AccessoADatos/ficheros/credenciales.txt","AT",torneoCreado.getId());
                    System.out.println("El torneo se ha creado perfectamente.");
                    System.out.println("--------------------------------");
                    System.out.println("Vamos a crear los combates");
                    System.out.println("--------------------------------");
                    Usuario usuario = new Usuario(usu_AT,contra_AT,"AT",torneo.getId());
                    UsuariosDb4o usuariosDb4o = new UsuariosDb4o();
                    usuariosDb4o.agregarUsuario(usuario);
                    
                    LocalDate[] fechas = new LocalDate[3];  // Arreglo para almacenar las tres fechas
                    boolean fechaValida;

                    for (int i = 0; i < 3; i++) {  // Se repite tres veces
                        fechaValida = false;

                        while (!fechaValida) {
                            System.out.print("Dime la fecha " + (i + 1) + " en este formato (YYYY-MM-DD): ");
                            String fechaStr = sc.next();
                            System.out.println("--------------------------------");

                            try {
                                // Intentar convertir el String a LocalDate
                                fechas[i] = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
                                fechaValida = true;  // Si no hay excepción, la fecha es válida
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato de fecha incorrecto. Inténtalo de nuevo.");
                            }
                        }
                    }
                    
                    for(int i =0; i<fechas.length;i++) {
                    	
                    	Combate combate = new Combate();
                    	combate.setTorneo(torneoCreado);
                    	combate.setFecha(fechas[i]);
                    	combateService.guardarCombate(combate);
                    	
                    	
                    }
                    System.out.println("Se han guardado perfectamente los combates.");
                  
                   
                    
                }else {
                    System.out.println("Ya existen estos datos de Administrador de Torneo.");
                    System.out.println("--------------------------------");
                    System.out.println("No se ha creado el torneo.");
                    
                    
                }
            }


        }else {
            System.out.println("El Torneo ya existe no se permiten duplicados.");
            
        }



}

}
