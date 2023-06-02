/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.dao;

import m06uf3pracma.Utils.MongoDBConnector;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import m06uf3pracma.model.IdProvider;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Clase Generica de DAO para interactuar con MongoDB.
 * @author Alex 
 * @param <Entidad> Entidad que usará el DAO.
 */
public class GenericDAO<Entidad extends IdProvider> implements GenericDAOInterface<Entidad> {

    private final MongoCollection<Entidad> collection;
    //private final Gson gson;
    private final Class<Entidad> clazz;
    
    /**
     * Constructor del DAO Generico. Obtiene la colección referente al string.
     * @param clazz Clase 
     * @param collection String que referencia a la colección.
     */
    public GenericDAO(Class<Entidad> clazz, String collection) {
        this.collection = MongoDBConnector.getInstance().getDB().getCollection(collection).withDocumentClass(clazz);
        this.clazz = clazz;
    }

    /**
     * Obtiene todos los objetos de la base de datos.
     * @return Objetos del tipo especficicado.
     */
    @Override
    public List<Entidad> getAll() {
        return collection.find().into(new ArrayList<>());
    }

    /**
     * Obtiene una Entidad según su id.
     * @param id ID del documento.
     * @return Entidad.
     */
    @Override
    public Entidad getById(ObjectId id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.find(query).first();
    }
    
    /**
     * Obtiene la primera Entidad con un nombre concreto.
     * @param nombre Nombre de la Entidad.
     * @return Entidad.
     */
    public Entidad getByNom(String nombre) {
        BasicDBObject query = new BasicDBObject();
        query.put("nombre", nombre);
        return collection.find(query).first();
    }
    
    /**
     * Obtiene una Entidad por la ruta.
     * @param ruta String que representa la ruta.
     * @return Entidad.
     */
    public Entidad getByRuta(String ruta) {
        BasicDBObject query = new BasicDBObject();
        query.put("ruta", ruta);
        return collection.find(query).first();
    }

    /**
     * Actualiza una Entidad
     * @param entidad entidad a actualizar.
     */
    @Override
    public void update(Entidad entidad) {
        delete(entidad);
        save(entidad);
    }

    /**
     * Elimina una Entidad.
     * @param entidad Entidad a eliminar.
     */
    @Override
    public void delete(Entidad entidad) {
        Document query = new Document("_id", entidad.getId());
        collection.deleteOne(query);
    }

    /**
     * Guarda una Entidad
     * @param entidad Entidad a guardar.
     */
    @Override
    public void save(Entidad entidad) {
        collection.insertOne(entidad);
    }

}
