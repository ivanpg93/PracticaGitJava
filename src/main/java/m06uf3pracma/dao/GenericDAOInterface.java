/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.dao;

import java.util.List;
import m06uf3pracma.model.IdProvider;
import org.bson.types.ObjectId;

/**
 * Interfaz para el DAO Generico.
 *
 * @author Alex
 */
public interface GenericDAOInterface<Entidad extends IdProvider> {

    /**
     * Obtiene todos los objetos de la base de datos.
     *
     * @return Objetos del tipo especficicado.
     */
    public List<Entidad> getAll();

    /**
     * Obtiene una Entidad según su id.
     *
     * @param id ID del documento.
     * @return Entidad.
     */
    public Entidad getById(ObjectId id);

    /**
     * Actualiza una Entidad
     *
     * @param entidad entidad a actualizar.
     */
    public void update(Entidad entidad);

    /**
     * Guarda una Entidad
     *
     * @param entidad Entidad a guardar.
     */
    public void save(Entidad entidad);

    /**
     * Elimina una Entidad.
     *
     * @param entidad Entidad a eliminar.
     */
    public void delete(Entidad entidad);

}
