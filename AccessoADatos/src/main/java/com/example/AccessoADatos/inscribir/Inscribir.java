package com.example.AccessoADatos.inscribir;

import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.service.CarnetService;
import com.example.AccessoADatos.service.CombateService;
import com.example.AccessoADatos.service.EntrenadorService;
import com.example.AccessoADatos.service.TorneoService;
import java.util.*;


    public class Inscribir {

        CarnetService carnetService;

        CombateService combateService;

        TorneoService torneoService;

        EntrenadorService entrenadorService;


        public Inscribir(CombateService combateService,CarnetService carnetService,TorneoService torneoService,EntrenadorService entrenadorService){
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

            if (listaCombates.get(1).getGanador() != null){
                System.out.println("El torneo ha finalizado no puedes inscribir a nadie.");
                return;
            }

            if (listaEntrenadores.size() < 3){
                System.out.println("El torneo todavia no esta completo");
                System.out.println("--------------------------------");
            }


            if (listaEntrenadores.isEmpty() || listaEntrenadores.size() < 2) {
                System.out.println("No hay entrenadores suficientes en este Torneo.");
                return;
            }

            // Mostrar los entrenadores disponibles
            System.out.println("Los entrenadores disponibles en el torneo son:");
            System.out.println("--------------------------------");
            for (int i = 0; i < listaEntrenadores.size(); i++) {
                System.out.println((i + 1) + ". " + listaEntrenadores.get(i).getNombre());
            }
            System.out.println("--------------------------------");

            // Pedir al usuario que ingrese los nombres de los dos entrenadores
            System.out.print("Ingrese el nombre del primer entrenador: ");
            String nombreEntrenador1 = sc.nextLine();
            System.out.print("Ingrese el nombre del segundo entrenador: ");
            String nombreEntrenador2 = sc.nextLine();
            System.out.println("--------------------------------");

            // Buscar los entrenadores en la lista
            Entrenador entrenador1 = listaEntrenadores.stream()
                    .filter(e -> e.getNombre().equalsIgnoreCase(nombreEntrenador1))
                    .findFirst()
                    .orElse(null);

            Entrenador entrenador2 = listaEntrenadores.stream()
                    .filter(e -> e.getNombre().equalsIgnoreCase(nombreEntrenador2))
                    .findFirst()
                    .orElse(null);

            // Verificar si los entrenadores existen en la lista
            if (entrenador1 == null || entrenador2 == null) {
                System.out.println("Uno o ambos entrenadores no están en la lista del torneo.");
                return;
            }

            // Verificar que los entrenadores no sean el mismo
            if (entrenador1.getId().equals(entrenador2.getId())) {
                System.out.println("No se puede asignar el mismo entrenador a ambos lados del combate.");
                return;
            }

            // Verificar si los entrenadores ya están en algún combate juntos (incluso invertidos)
            boolean combateRepetido = listaCombates.stream()
                    .anyMatch(combate -> {
                        Long id1 = combate.getEntrenador1() != null ? combate.getEntrenador1().getId() : null;
                        Long id2 = combate.getEntrenador2() != null ? combate.getEntrenador2().getId() : null;

                        return (id1 != null && id2 != null) &&
                                ((id1.equals(entrenador1.getId()) && id2.equals(entrenador2.getId())) ||
                                        (id1.equals(entrenador2.getId()) && id2.equals(entrenador1.getId())));
                    });

            if (combateRepetido) {
                System.out.println("Estos entrenadores ya están asignados a un combate juntos.");
                return;
            }

            // Buscar un combate donde idEntrenador1 sea null
            Optional<Combate> combateDisponible = listaCombates.stream()
                    .filter(combate -> combate.getEntrenador1() == null )
                    .findFirst();

            if (combateDisponible.isPresent()) {
                Combate combate = combateDisponible.get();
                combate.setEntrenador1(entrenador1);
                combate.setEntrenador2(entrenador2);
                combateService.guardarCombate(combate);
                System.out.println("Entrenadores asignados a un combate existente.");
            }






        }
    }
