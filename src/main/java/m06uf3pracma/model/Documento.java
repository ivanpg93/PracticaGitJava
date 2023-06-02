/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.model;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.bson.types.ObjectId;

/**
 *
 *
 * Clase que representa un documento en el sistema. Implementa la interfaz
 * IdProvider para proveer de un identificador único.
 *
 * @author Alex
 */
public class Documento implements IdProvider {

    private ObjectId _id;
    private String ruta;
    private String nombre;
    private String hash;
    private byte[] data;
    private LocalDateTime timestamp;

    public Documento() {
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * Convierte el contenido del documento, representado como un array de
     * bytes, en un HashMap de líneas numeradas.
     *
     * @return HashMap con las líneas numeradas del contenido del documento
     */
    public HashMap<Integer, String> bytesToLineas() throws IOException {
        HashMap<Integer, String> lines = new HashMap<>();
        // Convertir el array de bytes en un InputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        // Crear un BufferedReader para leer el InputStream línea por línea
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        // Leer cada línea del BufferedReader y agregarla al HashMap
        String line;
        int lineNumber = 1;
        while ((line = reader.readLine()) != null) {
            lines.put(lineNumber, line);
            lineNumber++;
        }
        // Cerrar el BufferedReader y el InputStream
        reader.close();
        inputStream.close();
        return lines;
    }
}
