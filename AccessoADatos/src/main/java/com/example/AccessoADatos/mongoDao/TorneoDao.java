package com.example.AccessoADatos.mongoDao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.example.AccessoADatos.clases.Torneo;

public class TorneoDao {
    private MongoCollection<Document> torneos;

    public TorneoDao(MongoDatabase database) {
        this.torneos = database.getCollection("torneos");
    }

    public void guardarTorneo(Torneo torneo) {
        Document doc = new Document("nombre", torneo.getNombre())
                .append("codRegion", torneo.getCodRegion())
                .append("puntosVictoria", torneo.getPuntosVictoria())
                .append("ganadorTorneo", torneo.getGanadorTorneo());
        torneos.insertOne(doc);
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
