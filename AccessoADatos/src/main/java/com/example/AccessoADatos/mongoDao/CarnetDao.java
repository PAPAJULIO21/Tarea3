package com.example.AccessoADatos.mongoDao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.example.AccessoADatos.clases.Carnet;

public class CarnetDao {
    private MongoCollection<Document> carnets;

    public CarnetDao(MongoDatabase database) {
        this.carnets = database.getCollection("carnets");
    }

    public void guardarCarnet(Carnet carnet) {
        Document doc = new Document("idEntrenador", carnet.getIdEntrenador())
                .append("fechaExpedicion", carnet.getFechaExpedicion())
                .append("puntos", carnet.getPuntos())
                .append("numVictorias", carnet.getNumVictorias());
        carnets.insertOne(doc);
    }

    public Carnet buscarCarnetPorIdEntrenador(Long idEntrenador) {
        Document doc = carnets.find(Filters.eq("idEntrenador", idEntrenador)).first();
        if (doc != null) {
        	Date fechaExpedicionDate = doc.getDate("fechaExpedicion");
            LocalDate fechaExpedicion = fechaExpedicionDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            return new Carnet(
                    doc.getLong("idEntrenador"),
                    fechaExpedicion,
                    doc.getInteger("puntos"),
                    doc.getInteger("numVictorias"),
                    null // El entrenador se debe cargar aparte
            );
        }
        return null;
    }

    public void eliminarCarnet(Long idEntrenador) {
        carnets.deleteOne(Filters.eq("idEntrenador", idEntrenador));
    }
}
