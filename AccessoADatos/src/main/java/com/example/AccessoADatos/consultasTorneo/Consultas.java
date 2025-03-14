package com.example.AccessoADatos.consultasTorneo;

import com.example.AccessoADatos.clases.Carnet;
import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.mongoConexion.ConexionMongo;
import com.example.AccessoADatos.mongoDao.TorneoDao;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Consultas {

    EntrenadorService entrenadorService;
    CarnetService carnetService;

    public Consultas(EntrenadorService entrenadorService,CarnetService carnetService){
        this.entrenadorService=entrenadorService;
        this.carnetService = carnetService;
    }

    public void datosTorneo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame un nombre para ver que datos tiene");
        System.out.println("--------------------------------");
        String nombreTor = sc.nextLine();
        System.out.println("--------------------------------");


        ConexionMongo conexionMongo = new ConexionMongo();
        MongoDatabase mongoDatabase = conexionMongo.getDatabase();
        TorneoDao torneoDao = new TorneoDao(mongoDatabase);

        Torneo torneo=torneoDao.obtenerTorneoPorNombre(nombreTor);
        if (torneo != null){
            System.out.println(torneo.toString());
            System.out.println("--------------------------------");
            System.out.println("Quieres más datos del torneo");
            System.out.println("--------------------------------");
            String siNo = sc.nextLine();
            System.out.println("--------------------------------");
            if (siNo.equalsIgnoreCase("si")||siNo.equalsIgnoreCase("s")){
                List<Entrenador> listaEntrenadores = new ArrayList<>(torneo.getEntrenadores());
                System.out.println("Los entrenadores de este Torneo son:");
                System.out.println("--------------------------------");
                for (int i = 0; i < listaEntrenadores.size(); i++) {
                    System.out.println(listaEntrenadores.get(i).getNombre());
                }
                System.out.println("--------------------------------");
                List<Combate> listaCombates = new ArrayList<>(torneo.getCombates());

                System.out.println("Los id de los combates de este Torneo son");
                System.out.println("--------------------------------");
                for (int i = 0; i < listaCombates.size(); i++) {
                    System.out.println( listaCombates.get(i).getId());
                }
                System.out.println("--------------------------------");
            }
        }
    }

    public void ganador(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame un nombre de un torneo para ver quien gano el torneo");
        System.out.println("--------------------------------");
        String nombreTor = sc.nextLine();
        System.out.println("--------------------------------");


        ConexionMongo conexionMongo = new ConexionMongo();
        MongoDatabase mongoDatabase = conexionMongo.getDatabase();
        TorneoDao torneoDao = new TorneoDao(mongoDatabase);


        Torneo torneo = torneoDao.obtenerTorneoPorNombre(nombreTor);

        if (torneo!= null){
            Entrenador entrenador = entrenadorService.buscarEntrenadorPorId(torneo.getGanadorTorneo());
            System.out.println(entrenador.toString());
        }else{
            System.out.println("El torneo no existe");
        }

    }

    public void ganadorDosTorneos(){
        ConexionMongo conexionMongo = new ConexionMongo();
        MongoDatabase mongoDatabase = conexionMongo.getDatabase();
        TorneoDao torneoDao = new TorneoDao(mongoDatabase);

    }

    public  void entrenadoresPuntos(){
        ConexionMongo conexionMongo = new ConexionMongo();
        MongoDatabase mongoDatabase = conexionMongo.getDatabase();
        TorneoDao torneoDao = new TorneoDao(mongoDatabase);

        List<Torneo>torneos=torneoDao.obtenerTodosLosTorneos();

        if (torneos!=null){

            for (int i = 0;i<torneos.size();i++){
                List<Entrenador> listaEntrenadores = new ArrayList<>(torneos.get(i).getEntrenadores());
                System.out.println("Los entrenadores del Torneo "+ torneos.get(i).getNombre()+" y sus puntos son:");
                System.out.println("--------------------------------");
                for (int o = 0; o < listaEntrenadores.size(); o++) {
                    Carnet carnet=carnetService.buscarCarnetPorId(listaEntrenadores.get(o).getId());
                    System.out.println(listaEntrenadores.get(o).getNombre()+" - "+ carnet.getPuntos());
                }
                System.out.println("--------------------------------");
            }
        }else {
            System.out.println("No hay torneos todavia");
        }
    }

    public  void entrenadorPuntos(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame un nombre de un Entrenador para mostrar su puntuacion");
        System.out.println("--------------------------------");
        String nombreTor = sc.nextLine();
        System.out.println("--------------------------------");


        ConexionMongo conexionMongo = new ConexionMongo();
        MongoDatabase mongoDatabase = conexionMongo.getDatabase();
        TorneoDao torneoDao = new TorneoDao(mongoDatabase);

        Set<Entrenador> torneo=torneoDao.obtenerTodosLosEntrenadores();
        List<Entrenador> entrenadoresList = new ArrayList<>(torneo);
        boolean encontrado = false;
        long idEntrenador = 0;

        for (int i = 0;i<entrenadoresList.size();i++) {
            if (entrenadoresList.get(i).getNombre().equalsIgnoreCase(nombreTor)) {
                idEntrenador = entrenadoresList.get(i).getId();
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            Carnet carnet = carnetService.buscarCarnetPorId(idEntrenador);
            System.out.println("El entrenador "+ nombreTor+" tiene "+ carnet.getPuntos()+" puntos.");
        } else {
            System.out.println("El entrenador " + nombreTor + " no existe.");
        }
    }
    public  void region(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame una region recuerda que la region es una sola una letra");
        System.out.println("--------------------------------");
        String nombreTor = sc.nextLine();
        System.out.println("--------------------------------");

        ConexionMongo conexionMongo = new ConexionMongo();
        MongoDatabase mongoDatabase = conexionMongo.getDatabase();
        TorneoDao torneoDao = new TorneoDao(mongoDatabase);

        List<Torneo> torneos = torneoDao.obtenerTodosLosTorneos();

        for (int i = 0; i < torneos.size(); i++) {
            if (String.valueOf(torneos.get(i).getCodRegion()).equalsIgnoreCase(nombreTor.substring(0, 1))) {
                System.out.println("Torneo encontrado: " + torneos.get(i).getNombre());
            }
        }

    }
}
