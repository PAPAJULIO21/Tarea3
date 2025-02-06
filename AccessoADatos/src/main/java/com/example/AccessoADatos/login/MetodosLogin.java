package com.example.AccessoADatos.login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MetodosLogin {
	public static boolean buscarNombreYContraseña(String nombreFichero, String nombreBuscado, String contraseñaBuscada) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;

            // Leer línea por línea
            while ((linea = reader.readLine()) != null) {
                // Dividir la línea en partes
                String[] partes = linea.split(" ");
                if (partes.length >= 3) {
                    String nombre = partes[0];
                    String contraseña = partes[1];

                    // Si el nombre y contraseña coinciden, retornar true
                    if (nombre.equalsIgnoreCase(nombreBuscado) && contraseña.equals(contraseñaBuscada)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    public static String buscarNombreYContraseñaYSacarPerfil(String nombreFichero, String nombreBuscado, String contraseñaBuscada) {
        String pepe = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;

            // Leer línea por línea
            while ((linea = reader.readLine()) != null) {
                // Dividir la línea en partes
                String[] partes = linea.split(" ");
                if (partes.length >= 3) {
                    String nombre = partes[0];
                    String contraseña = partes[1];
                    String perfil = partes[2];

                    // Si el nombre y contraseña coinciden, retornar true
                    if (nombre.equalsIgnoreCase(nombreBuscado) && contraseña.equals(contraseñaBuscada)) {
                        pepe = perfil;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return pepe;
    }

    public static String buscarNombreYContraseñaYSacarId(String nombreFichero, String nombreBuscado, String contraseñaBuscada) {
        String pepe = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;

            // Leer línea por línea
            while ((linea = reader.readLine()) != null) {
                // Dividir la línea en partes
                String[] partes = linea.split(" ");
                if (partes.length >= 3) {
                    String nombre = partes[0];
                    String contraseña = partes[1];
                    String perfil = partes[2];
                    String id = partes[3];

                    // Si el nombre y contraseña coinciden, retornar true
                    if (nombre.equalsIgnoreCase(nombreBuscado) && contraseña.equals(contraseñaBuscada)) {
                        pepe = id;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return pepe;
    }
}
