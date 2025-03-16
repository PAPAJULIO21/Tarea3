package com.example.AccessoADatos.inscribir;

import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.example.AccessoADatos.service.TorneoService;
import java.util.*;
import java.util.stream.Collectors;

public class Inscribir {

    CarnetService carnetService;
    CombateService combateService;
    TorneoService torneoService;
    EntrenadorService entrenadorService;

    public Inscribir(CombateService combateService, CarnetService carnetService, TorneoService torneoService, EntrenadorService entrenadorService) {
        this.carnetService = carnetService;
        this.combateService = combateService;
        this.torneoService = torneoService;
        this.entrenadorService = entrenadorService;
    }

    public void inscribir(String id) {
        Scanner sc = new Scanner(System.in);
        long id_Tor = Long.parseLong(id);
        Torneo torneo = torneoService.buscarTorneoPorId(id_Tor);
        System.out.println("La información básica del torneo es la siguiente:");
        System.out.println("--------------------------------");
        System.out.println(torneo.toString());
        System.out.println("--------------------------------");

        Set<Combate> combates = torneo.getCombates();
        List<Combate> listaCombates = new ArrayList<>(combates);
        Set<Entrenador> entrenadorSet = torneo.getEntrenadores();
        List<Entrenador> listaEntrenadores = new ArrayList<>(entrenadorSet);

        if (listaCombates.get(1).getGanador() != null) {
            System.out.println("El torneo ha finalizado, no puedes inscribir a nadie.");
            return;
        }

        while (listaEntrenadores.size() < 3) {
            System.out.println("No hay entrenadores suficientes en este Torneo.");
            System.out.println("Los entrenadores que NO están en este torneo y pueden ser inscritos son:");
            System.out.println("--------------------------------");

            // Obtener todos los entrenadores disponibles
            List<Entrenador> todosLosEntrenadores = entrenadorService.obtenerTodosLosEntrenadores();

            // Filtrar los entrenadores que NO están en el torneo actual
            List<Entrenador> entrenadoresNoInscritos = todosLosEntrenadores.stream()
                    .filter(e -> !listaEntrenadores.contains(e))
                    .collect(Collectors.toList());

            if (entrenadoresNoInscritos.isEmpty()) {
                System.out.println("No hay entrenadores disponibles para inscribir.");
                return;
            }

            for (int i = 0; i < entrenadoresNoInscritos.size(); i++) {
                System.out.println((i + 1) + ". " + entrenadoresNoInscritos.get(i).getNombre());
            }
            System.out.println("--------------------------------");

            // Preguntar si quiere inscribir un nuevo entrenador
            System.out.print("¿Deseas inscribir a un nuevo entrenador? (si/no): ");
            String respuesta = sc.nextLine().trim().toLowerCase();

            if (respuesta.equals("si") || respuesta.equals("s")) {
                System.out.print("Ingrese el nombre del entrenador que desea inscribir: ");
                String nombreNuevoEntrenador = sc.nextLine();

                // Buscar al entrenador en la lista de no inscritos
                Entrenador nuevoEntrenador = entrenadoresNoInscritos.stream()
                        .filter(e -> e.getNombre().equalsIgnoreCase(nombreNuevoEntrenador))
                        .findFirst()
                        .orElse(null);

                if (nuevoEntrenador != null) {
                    listaEntrenadores.add(nuevoEntrenador);
                    torneo.getEntrenadores().add(nuevoEntrenador);
                    torneoService.guardarTorneo(torneo);
                    entrenadorService.guardarEntrenador(nuevoEntrenador);
                    System.out.println("Entrenador " + nuevoEntrenador.getNombre() + " inscrito exitosamente.");
                } else {
                    System.out.println("El entrenador ingresado no está en la lista de disponibles.");
                }
            } else {
                return;
            }
        }

        // Verificar si hay al menos 3 entrenadores para asignar combates
        if (listaEntrenadores.size() < 3) {
            System.out.println("No hay suficientes entrenadores para organizar combates.");
            return;
        }

        // Ordenar los combates para asignación
        listaCombates.sort(Comparator.comparingLong(Combate::getId));

        // Obtener los primeros 3 combates disponibles
        if (listaCombates.size() < 3) {
            System.out.println("No hay suficientes combates para asignar.");
            return;
        }

        Combate combate1 = listaCombates.get(0);
        Combate combate2 = listaCombates.get(1);
        Combate combate3 = listaCombates.get(2);

        // Asignar entrenadores según la lógica definida
        Entrenador entrenador1 = listaEntrenadores.get(0);
        Entrenador entrenador2 = listaEntrenadores.get(1);
        Entrenador entrenador3 = listaEntrenadores.get(2);

        combate1.setEntrenador1(entrenador1);
        combate1.setEntrenador2(entrenador2);

        combate2.setEntrenador1(entrenador1);
        combate2.setEntrenador2(entrenador3);

        combate3.setEntrenador1(entrenador2);
        combate3.setEntrenador2(entrenador3);

        // Guardar los combates actualizados
        combateService.guardarCombate(combate1);
        combateService.guardarCombate(combate2);
        combateService.guardarCombate(combate3);

        System.out.println("Entrenadores asignados a combates exitosamente.");
    }
}