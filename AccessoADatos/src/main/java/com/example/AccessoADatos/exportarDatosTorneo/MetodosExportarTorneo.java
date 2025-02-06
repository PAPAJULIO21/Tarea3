package com.example.AccessoADatos.exportarDatosTorneo;

import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MetodosExportarTorneo {

    public static void escribirEnArchivo(String nombreArchivo, Torneo torneo, List<Entrenador> entrenadores, List<Combate> combates) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write("Los datos de este Torneo son: " + torneo.toString());
            writer.newLine(); // Agrega un salto de línea

            if (!entrenadores.isEmpty()) {
                writer.write("Los datos de los entrenadores son:");
                writer.newLine();
                for (Entrenador entrenador : entrenadores) {
                    writer.write(entrenador.toString());
                    writer.newLine();
                }
            }

            if (!combates.isEmpty()) {
                writer.write("Los datos de los combates son:");
                writer.newLine();
                for (Combate combate : combates) {
                    writer.write(combate.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

}
