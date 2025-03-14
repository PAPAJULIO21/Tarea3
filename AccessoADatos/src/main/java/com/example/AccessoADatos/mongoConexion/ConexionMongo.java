package com.example.AccessoADatos.mongoConexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {
	private static final String URI = "mongodb://localhost:27017"; // Cambia esto si usas una URI diferente
    private static final String DATABASE_NAME = "mongo"; // Cambia esto por el nombre de tu BD

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Método para obtener la conexión
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
            database = mongoClient.getDatabase(DATABASE_NAME);

        }
        return database;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}
