package com.example.AccessoADatos.crearTorneo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.AccessoADatos.clases.Torneo;

public class MetodosCrearTorneo {

    private String nombre;
    private String contraseña;
    private String perfil;
    private int id;


    // Constructor
    public MetodosCrearTorneo(String nombre, String contraseña, String perfil, int id) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.perfil = perfil;
        this.id = id;
    }
    public  MetodosCrearTorneo(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int obtenerUltimoIdTorneo(String nombreFichero) {
        int ultimoId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            // Leer línea por línea y extraer el ID (se espera que el ID esté al principio de cada línea)
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length == 5){
                    ultimoId = Integer.parseInt(partes[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return ultimoId;
    }

    public boolean existeTorneo(String nombreFichero,String nombre,String cod) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            // Leer cada línea del archivo y verificar si ya existe
            while ((linea = reader.readLine()) != null) {
                // Si la línea contiene el nombre y contraseña del administrador, retorna true
                if (linea.contains(nombre) && linea.contains(cod)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    public boolean existeEnFichero(String nombreFichero,String perfil) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            // Leer cada línea del archivo y verificar si ya existe
            while ((linea = reader.readLine()) != null) {
                // Si la línea contiene el nombre y contraseña del administrador, retorna true
                if (linea.contains(this.nombre) && linea.contains(perfil)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    public void guardarSiNoExiste(String nombreFichero,String perfil,long id) {

        if (!existeEnFichero(nombreFichero,perfil)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
                writer.write(this.nombre + " "+this.contraseña+" "+this.perfil+" "+id);
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error al escribir en el fichero: " + e.getMessage());
            }
        }else{
            System.out.println("El administrador ya esta creado no puede duplicase.");
        }

    }

    public boolean existeEnFicheroTorneo(String nombreFichero,Torneo torneo) {
        String cadena = String.valueOf(torneo.getCodRegion());
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            // Leer cada línea del archivo y verificar si ya existe
            while ((linea = reader.readLine()) != null) {
                // Si la línea contiene el nombre y contraseña del administrador, retorna true
                if (linea.contains(torneo.getNombre()) && linea.contains(cadena)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    public void guardarSiNoExisteConDatos(String nombreFichero, Torneo torneo, String nombre) {
        if (!existeEnFicheroTorneo(nombreFichero,torneo)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
                writer.write(torneo.getId()+" "+ torneo.getNombre()+" "+torneo.getCodRegion()+" "+torneo.getPuntosVictoria()+" "+nombre);
                writer.newLine();

            } catch (IOException e) {
                System.out.println("Error al escribir en el fichero: " + e.getMessage());
            }
        }
    }

    
  
   


}
