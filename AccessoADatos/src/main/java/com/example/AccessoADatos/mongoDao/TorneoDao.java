package com.example.AccessoADatos.mongoDao;

import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.mongoConexion.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.example.AccessoADatos.clases.Torneo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TorneoDao {
    private MongoCollection<Document> torneos;

    public TorneoDao(MongoDatabase database) {
        this.torneos = database.getCollection("datos");
    }

    public void guardarTorneo(Torneo torneo) {

        Document torneoDoc = new Document("nombre", torneo.getNombre())
                .append("codRegion", torneo.getCodRegion())
                .append("puntosVictoria", torneo.getPuntosVictoria())
                .append("ganadorTorneo", torneo.getGanadorTorneo());

        // Guardar entrenadores
        List<Document> entrenadoresDocs = new ArrayList<>();
        for (Entrenador entrenador : torneo.getEntrenadores()) {
            Document entrenadorDoc = new Document("id", entrenador.getId())
                    .append("nombre", entrenador.getNombre());
            entrenadoresDocs.add(entrenadorDoc);
        }
        torneoDoc.append("entrenadores", entrenadoresDocs);

        // Guardar combates
        List<Document> combatesDocs = new ArrayList<>();
        for (Combate combate : torneo.getCombates()) {
            Document combateDoc = new Document("id", combate.getId())
                    .append("resultado", combate.getGanador())
                    .append("entrenador1", combate.getEntrenador1().getId())
                    .append("entrenador2", combate.getEntrenador2().getId());
            combatesDocs.add(combateDoc);
        }
        torneoDoc.append("combates", combatesDocs);

        // Insertar en la colección
        torneos.insertOne(torneoDoc);
    }
    public Set<Entrenador> obtenerTodosLosEntrenadores() {
        Set<Entrenador> entrenadores = new HashSet<>();
        for (Document torneoDoc : torneos.find()) {
            List<Document> entrenadoresDocs = torneoDoc.getList("entrenadores", Document.class);
            if (entrenadoresDocs != null) {
                for (Document doc : entrenadoresDocs) {
                    Entrenador entrenador = new Entrenador();
                    entrenador.setId(doc.getLong("id"));
                    entrenador.setNombre(doc.getString("nombre"));
                    entrenadores.add(entrenador);
                }
            }
        }
        return entrenadores;
    }
    public List<Torneo> obtenerTodosLosTorneos() {
        List<Torneo> listaTorneos = new ArrayList<>();
        for (Document torneoDoc : torneos.find()) {
            Torneo torneo = new Torneo();
            torneo.setNombre(torneoDoc.getString("nombre"));
            torneo.setCodRegion(torneoDoc.getString("codRegion").charAt(0));
            torneo.setPuntosVictoria(torneoDoc.getInteger("puntosVictoria"));
            torneo.setGanadorTorneo(torneoDoc.getLong("ganadorTorneo"));

            // Obtener entrenadores
            List<Document> entrenadoresDocs = torneoDoc.getList("entrenadores", Document.class);
            Set<Entrenador> entrenadores = new HashSet<>();
            if (entrenadoresDocs != null) {
                for (Document doc : entrenadoresDocs) {
                    Entrenador entrenador = new Entrenador();
                    entrenador.setId(doc.getLong("id"));
                    entrenador.setNombre(doc.getString("nombre"));
                    entrenadores.add(entrenador);
                }
            }
            torneo.setEntrenadores(entrenadores);

            // Obtener combates
            List<Document> combatesDocs = torneoDoc.getList("combates", Document.class);
            Set<Combate> combates = new HashSet<>();
            if (combatesDocs != null) {
                for (Document doc : combatesDocs) {
                    Combate combate = new Combate();
                    combate.setId(doc.getLong("id"));
                    combate.setGanador(doc.getLong("resultado"));

                    Entrenador entrenador1 = new Entrenador();
                    entrenador1.setId(doc.getLong("entrenador1"));

                    Entrenador entrenador2 = new Entrenador();
                    entrenador2.setId(doc.getLong("entrenador2"));

                    combate.setEntrenador1(entrenador1);
                    combate.setEntrenador2(entrenador2);

                    combates.add(combate);
                }
            }
            torneo.setCombates(combates);

            listaTorneos.add(torneo);
        }
        return listaTorneos;
    }




    public Torneo obtenerTorneoPorNombre(String nombre) {
        Document torneoDoc = torneos.find(Filters.eq("nombre", nombre)).first();

        if (torneoDoc == null) {
            System.out.println("No se encontró un torneo con el nombre: " + nombre);
            return null;
        }

        // Convertir el documento de MongoDB a un objeto Torneo
        Torneo torneo = new Torneo();
        torneo.setNombre(torneoDoc.getString("nombre"));
        torneo.setCodRegion(torneoDoc.getString("codRegion").charAt(0));
        torneo.setPuntosVictoria(torneoDoc.getInteger("puntosVictoria"));
        torneo.setGanadorTorneo(torneoDoc.getLong("ganadorTorneo"));

        // Obtener entrenadores
        List<Document> entrenadoresDocs = torneoDoc.getList("entrenadores", Document.class);
        Set<Entrenador> entrenadores = new HashSet<>();
        if (entrenadoresDocs != null) {
            for (Document doc : entrenadoresDocs) {
                Entrenador entrenador = new Entrenador();
                entrenador.setId(doc.getLong("id"));
                entrenador.setNombre(doc.getString("nombre"));
                entrenadores.add(entrenador);
            }
        }
        torneo.setEntrenadores(entrenadores);

        // Obtener combates
        List<Document> combatesDocs = torneoDoc.getList("combates", Document.class);
        Set<Combate> combates = new HashSet<>();
        if (combatesDocs != null) {
            for (Document doc : combatesDocs) {
                Combate combate = new Combate();
                combate.setId(doc.getLong("id"));
                combate.setGanador(doc.getLong("resultado"));

                Entrenador entrenador1 = new Entrenador();
                entrenador1.setId(doc.getLong("entrenador1"));

                Entrenador entrenador2 = new Entrenador();
                entrenador2.setId(doc.getLong("entrenador2"));

                combate.setEntrenador1(entrenador1);
                combate.setEntrenador2(entrenador2);

                combates.add(combate);
            }
        }
        torneo.setCombates(combates);

        return torneo;
    }


    public Torneo buscarTorneoPorId(ObjectId id) {
        Document doc = torneos.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return new Torneo(
                    doc.getString("nombre"),
                    doc.getString("codRegion").charAt(0),
                    doc.getInteger("puntosVictoria"),
                    null, // Combates se deben cargar aparte
                    null, // Entrenadores se deben cargar aparte
                    doc.getLong("ganadorTorneo")
            );
        }
        return null;
    }

    public void eliminarTorneo(ObjectId id) {
        torneos.deleteOne(Filters.eq("_id", id));
    }
}
