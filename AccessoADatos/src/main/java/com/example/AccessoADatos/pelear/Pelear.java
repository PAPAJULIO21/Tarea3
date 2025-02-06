package com.example.AccessoADatos.pelear;

import com.example.AccessoADatos.clases.Carnet;
import com.example.AccessoADatos.clases.Combate;
import java.util.Random;

import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.example.AccessoADatos.service.TorneoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Pelear {

    CombateService combateService;
    TorneoService torneoService;
    CarnetService carnetService;
    EntrenadorService entrenadorService;

    public Pelear(CombateService combateService,TorneoService torneoService,CarnetService carnetService,EntrenadorService entrenadorService){
        this.combateService = combateService;
        this.torneoService = torneoService;
        this.entrenadorService = entrenadorService;
        this.carnetService = carnetService;
    }

    public void pelear(String id) {
        Scanner sc = new Scanner(System.in);
        long id_Tor = Long.parseLong(id);
        Torneo torneo = torneoService.buscarTorneoPorId(id_Tor);
        System.out.println("Los datos del Torneo son los siguientes");
        System.out.println("--------------------------------");
        System.out.println(torneo.toString());
        System.out.println("--------------------------------");

        Set<Combate> combates = torneo.getCombates();
        List<Combate> listaCombates = new ArrayList<>(combates);

        if (listaCombates.get(0).getGanador()!= null){
            System.out.println("Ya han peleado en este Torneo.");
            return;
        }

        // Verificar si todos los combates tienen ambos entrenadores
        if (todosCombatesConEntrenadores(listaCombates)) {
            System.out.println("Todos los combates tienen ambos entrenadores asignados.");
            System.out.println("Determinando ganadores de forma aleatoria...");

            // Recorrer la lista de combates y asignar un ganador aleatorio
            Random random = new Random();
            for (Combate combate : listaCombates) {
                // Generar un número aleatorio (0 o 1)
                int ganadorAleatorio = random.nextInt(2); // 0 para entrenador1, 1 para entrenador2

                Entrenador ganador;
                if (ganadorAleatorio == 0) {
                    ganador = combate.getEntrenador1(); // Ganador es entrenador1
                    combate.setGanador(ganador.getId());
                    System.out.println("En el combate " + combate.getId() + " gana el entrenador: " + ganador.getNombre());
                } else {
                    ganador = combate.getEntrenador2(); // Ganador es entrenador2
                    combate.setGanador(ganador.getId());
                    System.out.println("En el combate " + combate.getId() + " gana el entrenador: " + ganador.getNombre());
                }

                // Obtener el carnet del ganador
                Carnet carnetGanador = carnetService.buscarCarnetPorId(ganador.getId());

                // Sumar los puntos del torneo al carnet del ganador
                int puntosTorneo = torneo.getPuntosVictoria(); // Asumiendo que Torneo tiene un método getPuntos()
                carnetGanador.setPuntos(carnetGanador.getPuntos() + puntosTorneo);

                // Incrementar el número de victorias del ganador
                carnetGanador.setNumVictorias(carnetGanador.getNumVictorias() + 1);

                // Guardar el carnet actualizado en la base de datos
                carnetService.guardarCarnet(carnetGanador);

                // Guardar el combate actualizado en la base de datos
                combateService.guardarCombate(combate);
            }
        } else {
            System.out.println("Algunos combates no tienen ambos entrenadores asignados.");
            // Aquí puedes manejar el caso en que falten entrenadores
        }
    }

    public boolean todosCombatesConEntrenadores(List<Combate> combates) {
        for (Combate combate : combates) {
            if (combate.getEntrenador1() == null || combate.getEntrenador2() == null) {
                return false; // Si algún combate no tiene ambos entrenadores, retorna false
            }
        }
        return true; // Si todos los combates tienen ambos entrenadores, retorna true
    }
}
