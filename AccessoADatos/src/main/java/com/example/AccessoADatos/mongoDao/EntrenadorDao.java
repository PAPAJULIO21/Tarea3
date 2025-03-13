package com.example.AccessoADatos.mongoDao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.example.AccessoADatos.clases.Entrenador;

public class EntrenadorDao {
    private MongoCollection<Document> entrenadores;

    public EntrenadorDao(MongoDatabase database) {
        this.entrenadores = database.getCollection("entrenadores");
    }

    public void guardarEntrenador(Entrenador entrenador) {
        Document doc = new Document("nombre", entrenador.getNombre())
                .append("nacionalidad", entrenador.getNacionalidad())
                .append("carnet", entrenador.getCarnet().getIdEntrenador());
        entrenadores.insertOne(doc);
    }

    public Entrenador buscarEntrenadorPorId(ObjectId id) {
        Document doc = entrenadores.find(Filters.eq("_id", id)).first();
        if (doc != null) {
        	return new Entrenador(
        		    doc.getLong("_id"),  
        		    doc.getString("nombre"),
        		    doc.getString("nacionalidad"),
        		    null, // Carnet se debe cargar aparte
        		    null  // Torneos se deben cargar aparte
        		);
        }
        return null;
    }

    public void eliminarEntrenador(ObjectId id) {
        entrenadores.deleteOne(Filters.eq("_id", id));
    }
}