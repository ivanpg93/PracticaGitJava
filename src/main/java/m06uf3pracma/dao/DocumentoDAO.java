/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.dao;

import java.util.List;
import m06uf3pracma.model.Documento;

/**
 * Clase encargada del DAO del modelo Documento.
 * @author Alex
 */
public class DocumentoDAO extends GenericDAO<Documento> {

    /**
     * Constructor del Documento DAO.
     * @param repositorio nombre del repositorio donde se realizarán las acciones.
     */
    public DocumentoDAO(String repositorio) {
        super(Documento.class, repositorio);
    }

    /**
     * Obtiene todos los documentos de tipo Documento. Borra el primer documento de la lista.
     * @return lista de Documento.
     */
    @Override
    public List<Documento> getAll() {
        List<Documento> documentosDB = super.getAll();
        documentosDB.remove(0);
        return documentosDB;
    }

    /**
     * Guarda un objeto Documento siempre y cuando tenga las extensiones .java, .txt, .xml, .html
     * @param entidad entidad de tipo Documento.
     */
    @Override
    public void save(Documento entidad) {
        String nombre = entidad.getNombre();
        if (nombre.endsWith(".java") || nombre.endsWith(".txt") || nombre.endsWith(".xml") || nombre.endsWith(".html")) {
            super.save(entidad);
        }
    }

}
