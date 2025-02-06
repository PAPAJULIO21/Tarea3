package com.example.AccessoADatos.crearEntrenador;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.AccessoADatos.clases.*;



public class MetodosCrearEntrenador {

	    public int obtenerUltimoIdEntrenador(String nombreFichero,String perfil) {
	        int ultimoId = 0;
	        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
	            String linea;
	            // Leer línea por línea y extraer el ID (se espera que el ID esté al principio de cada línea)
	            while ((linea = reader.readLine()) != null) {
	                String[] partes = linea.split(" ");
	                if (partes.length == 4 && partes[2].toUpperCase().equals(perfil)){
	                    ultimoId = Integer.parseInt(partes[3]);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error al leer el fichero: " + e.getMessage());
	        }
	        return ultimoId;
	    }

	    public static boolean buscarNombreTorneo(String nombreFichero, String nombreBuscado) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
	            String linea;

	            // Leer línea por línea
	            while ((linea = reader.readLine()) != null) {
	                // Dividir la línea en partes
	                String[] partes = linea.split(" ");
	                if (partes.length == 5) {
	                    String nombre = partes[1];

	                    // Si el nombre y contraseña coinciden, retornar true
	                    if (nombre.equals(nombreBuscado)) {
	                        return true;
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error al leer el fichero: " + e.getMessage());
	        }

	        return false;
	    }

	    public static List<String> obtenerNombresDesdeArchivo(String rutaArchivo) {
	        List<String> nombres = new ArrayList<>(); // Lista para almacenar los nombres

	        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
	            String linea;
	            while ((linea = br.readLine()) != null) { // Leer línea por línea
	                String[] partes = linea.split(" "); // Dividir la línea por espacios
	                if (partes.length >= 4) { // Asegurar que la línea tiene el formato esperado
	                    String nombre = partes[1]; // Extraer el nombre (segunda posición)
	                    nombres.add(nombre); // Agregar el nombre a la lista
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error al leer el archivo: " + e.getMessage());
	        }

	        return nombres; // Retornar la lista de nombres
	    }
	    
	    public static boolean verificarPais(String pais, List<String> listaPaises) {
	        // Convertir el país ingresado a minúsculas para la comparación
	        String paisNormalizado = pais.toLowerCase();

	        // Verificar si la lista contiene el país ignorando mayúsculas/minúsculas
	        for (String nombrePais : listaPaises) {
	            if (nombrePais.toLowerCase().equals(paisNormalizado)) {
	                return true;
	            }
	        }
	        return false;
	    }

	    public int  sacarIdTorneo(String nombreFichero,String nombre) {
	        int a= 0;
	        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
	            String linea;
	            // Leer cada línea del archivo y verificar si ya existe
	            while ((linea = reader.readLine()) != null) {
	                // Si la línea contiene el nombre y contraseña del administrador, retorna true
	                String[] partes = linea.split(" ");
	                if (linea.contains(nombre)) {
	                    a = Integer.parseInt(partes[0]);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error al leer el fichero: " + e.getMessage());
	        }
	        return a;
	    }
	public void escribirAlFinalDelTxtEntrenador(String nombreFichero, Entrenador entrenador,String contraseña) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
			writer.write(entrenador.getNombre() + " " + contraseña + " ET " + entrenador.getId());
			writer.newLine();  // Agregar una nueva línea después de escribir los datos
		} catch (IOException e) {
			System.out.println("Error al escribir en el fichero: " + e.getMessage());
		}
	}

	    public static boolean verificarCredenciales(String rutaArchivo, String usuario, String contrasena, String et) {
	        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
	            String linea;
	            while ((linea = br.readLine()) != null) {
	                // Dividir la línea en partes basándose en los espacios
	                String[] partes = linea.split(" ");
	                if (partes.length >= 3) { // Verificamos que al menos hay usuario, contraseña y ET
	                    String usuarioArchivo = partes[0];
	                    String contrasenaArchivo = partes[1];
	                    String etArchivo = partes[2];

	                    // Comparar con los valores proporcionados
	                    if (usuarioArchivo.equalsIgnoreCase(usuario) && contrasenaArchivo.equals(contrasena) && etArchivo.equals(et)) {
	                        return true; // Si hay coincidencia, devolver true
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Error al leer el archivo: " + e.getMessage());
	        }
	        return false; // Devolver false si no se encuentra coincidencia
	    }
	    
	    public int obtenerIdDesdeArchivoTorneo(String rutaArchivo, String nombre) {
	        int numIdTorneo = 0;

	        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
	            String linea;
	            while ((linea = br.readLine()) != null) { // Leer línea por línea
	                String[] partes = linea.split(" "); // Dividir la línea por espacios
	                if (partes.length >= 2 && partes[1].equals(nombre)) { // Asegurar que hay al menos 2 partes
	                    try {
	                        numIdTorneo = Integer.parseInt(partes[0]); // Convertir correctamente el ID a int
	                    } catch (NumberFormatException e) {
	                        System.out.println("Error al convertir ID a número en la línea: " + linea);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error al leer el archivo: " + e.getMessage());
	        }

	        return numIdTorneo; 
	    }
	    public static List<String> cargarNombresDePaises(String nombreFichero) {
	        List<String> nombresPaises = new ArrayList<>();
	        try {
	            File archivo = new File(nombreFichero);
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document documento = builder.parse(archivo);

	            documento.getDocumentElement().normalize();

	            NodeList listaPaises = documento.getElementsByTagName("pais");

	            for (int i = 0; i < listaPaises.getLength(); i++) {
	                Element elementoPais = (Element) listaPaises.item(i);
	                String nombrePais = elementoPais.getElementsByTagName("nombre").item(0).getTextContent();
	                nombresPaises.add(nombrePais);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return nombresPaises;
	    }

	    public void escribirAlFinalDelTxt(String nombreFichero, Entrenador entrenador,String contraseña) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
	            writer.write(entrenador.getNombre() + " " + contraseña + " ET " + entrenador.getId());
	            writer.newLine();  // Agregar una nueva línea después de escribir los datos
	        } catch (IOException e) {
	            System.out.println("Error al escribir en el fichero: " + e.getMessage());
	        }
	    }
	    
	    public void escribirAlFinalDelTxtTorneo1(String nombreFichero, Torneo torneo,String entrenador1) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
	            writer.write("Los datos del torneo es: "+torneo.getId() + " id " + torneo.getNombre() + " nombre " + torneo.getCodRegion()+ " codRegion "+ torneo.getPuntosVictoria()+" puntosVictoria.");
	            writer.newLine();
	            writer.write("El nombre del primer entrenador es: "+entrenador1);
	            writer.newLine();
	            writer.write("--------------------------------");
	            writer.newLine(); // Agregar una nueva línea después de escribir los datos
	        } catch (IOException e) {
	            System.out.println("Error al escribir en el fichero: " + e.getMessage());
	        }
	    }
	    public void escribirAlFinalDelTxtTorneo2(String nombreFichero, Torneo torneo,String entrenador1,String entrenador2) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
	        	writer.write("Los datos del torneo es: "+torneo.getId() + " id " + torneo.getNombre() + " nombre " + torneo.getCodRegion()+ " codRegion "+ torneo.getPuntosVictoria()+" puntosVictoria.");
	            writer.newLine();
	            writer.write("El nombre del primer entrenador es: "+entrenador1);
	            writer.newLine();
	            writer.write("El nombre del segundo entrenador es: "+entrenador2);
	            writer.newLine();
	            writer.write("--------------------------------");
	            writer.newLine(); // Agregar una nueva línea después de escribir los datos
	        } catch (IOException e) {
	            System.out.println("Error al escribir en el fichero: " + e.getMessage());
	        }
	    }
	    
	    public void escribirAlFinalDelTxtTorneo3(String nombreFichero, Torneo torneo,String entrenador1,String entrenador2,String entrenador3) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
	        	writer.write("Los datos del torneo es: "+torneo.getId() + " id " + torneo.getNombre() + " nombre " + torneo.getCodRegion()+ " codRegion "+ torneo.getPuntosVictoria()+" puntosVictoria.");
	            writer.newLine();
	            writer.write("El nombre del primer entrenador es: "+entrenador1);
	            writer.newLine();
	            writer.write("El nombre del segundo entrenador es: "+entrenador2);
	            writer.newLine();
	            writer.write("El nombre del tercer entrenador es: "+entrenador3);
	         
	            writer.newLine();
	            writer.write("--------------------------------");
	            writer.newLine();// Agregar una nueva línea después de escribir los datos
	        } catch (IOException e) {
	            System.out.println("Error al escribir en el fichero: " + e.getMessage());
	        }
	    

	}


}
