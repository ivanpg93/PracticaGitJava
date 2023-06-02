/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.diagnostics.Loggers;

/**
 * Clase Singleton encargada de manejar la conexión con MongoDB.
 *
 * @author Alex
 */
public class MongoDBConnector {

    private static MongoDBConnector instance;
    private MongoDatabase bbdd;

    /**
     * Constructor de la clase. Crea una conexión con la base de datos y crea la
     * base de datos GETDB en caso de no existir.
     */
    private MongoDBConnector() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        
        bbdd = mongoClient.getDatabase("GETBD");
    }

    /**
     * Obtiene la instancia del conector a la base de datos.
     *
     * @return MongoDBConnector.
     */
    public static MongoDBConnector getInstance() {
        if (instance == null) {
            instance = new MongoDBConnector();
        }
        return instance;
    }

    /**
     * Obtiene la base de datos.
     *
     * @return MongoDatabase
     */
    public MongoDatabase getDB() {
        return bbdd;
    }
}
