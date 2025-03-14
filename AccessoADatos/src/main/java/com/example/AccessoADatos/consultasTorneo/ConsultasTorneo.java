package com.example.AccessoADatos.consultasTorneo;

import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class ConsultasTorneo {

    @Autowired
    EntrenadorService entrenadorService;
    @Autowired
    CarnetService carnetService;

    public ConsultasTorneo(EntrenadorService entrenadorService,CarnetService carnetService){
        this.entrenadorService = entrenadorService;
        this.carnetService = carnetService;
    }

    public void  consultas(){
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("Escogue una opcion pon un numero");
            System.out.println("--------------------------------");
            System.out.println("1- Dar un nombre de un Torneo y saco informacion\n" +
                    "2- Dar un nombre de un Torneo y te digo que entrenador lo ha ganado\n" +
                    "3- Listar los 2 entrenadores que han ganado más torneos y cuantos gano cada uno\n" +
                    "4- Listar todos los entrenadores y cuantos puntos tiene cada uno de ellos\n" +
                    "5- Dar un nombre de un Entrenador y te digo cuantos puntos tiene\n" +
                    "6- Me das una region y te digo los Torneos que han disputado  \n" +
                    "7- Salir");
            System.out.println("--------------------------------");
            System.out.println("Dime un número");
            System.out.println("--------------------------------");
            int num = sc.nextInt();
            System.out.println("--------------------------------");
            Consultas consultas = new Consultas(entrenadorService,carnetService);


                switch (num){

                    case 1:
                        consultas.datosTorneo();
                        break;
                    case 2:
                        consultas.ganador();
                        break;

                    case 4:
                        consultas.entrenadoresPuntos();
                        break;
                    case 5:
                        consultas.entrenadorPuntos();
                        break;
                    case 6:
                        consultas.region();
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Este numero no tiene opcion.");
                        break;
                }
            break;
        }

    }
}
