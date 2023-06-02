/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.dao;

import m06uf3pracma.Utils.MongoDBConnector;
import m06uf3pracma.model.Repositorio;

/**
 * Clase que representa el DAO para el objeto Repositorio.
 * @author Alex
 */
public class RepositorioDAO extends GenericDAO<Repositorio> {
    
    /**
     * Constructor del DAO.
     * @param nombre Nombre de la colección.
     */
    public RepositorioDAO(String nombre) {
        super(Repositorio.class, nombre);
    }

    /**
     * Borra un Repositorio y elimina la colección.
     * @param entidad Repositorio a eliminar.
     */
    @Override
    public void delete(Repositorio entidad) {
        super.delete(entidad);
        MongoDBConnector.getInstance().getDB().getCollection(entidad.getNombre()).drop();
    }
    
    
}
