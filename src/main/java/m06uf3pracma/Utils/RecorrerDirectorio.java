/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import m06uf3pracma.model.Documento;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Clase encargada de manejar el directorio.
 * @author Alex
 */
public class RecorrerDirectorio {

    /**
     * Lista un directorio y devuelve un objeto directorio recursivo con todos
     * su contenido.
     *
     * @param directorio Directorio del sistema
     * @param documentosSalida lista de los documentos.
     */
    public static void listarArchivos(File directorio, List<Documento> documentosSalida) {
        File[] listaArchivos = directorio.listFiles();

        List<Documento> documentos = new ArrayList<>();

        for (File archivo : listaArchivos) {
            if (archivo.isDirectory()) {
                listarArchivos(archivo, documentosSalida);
            } else {
                Documento documento = new Documento();
                documento.setRuta(archivo.getAbsolutePath());
                documento.setNombre(archivo.getName());
                documento.setData(bytesArchivo(archivo));
                documento.setHash(hashArchivo(archivo));
                documento.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(archivo.lastModified()), ZoneId.systemDefault()));
                documentos.add(documento);
            }
        }

        documentosSalida.addAll(documentos);
    }

    /**
     * Obtiene los bytes de un archivo.
     * @param archivo Archivo sobre el cual obtener los bytes.
     * @return array de bytes del archivo.
     */
    private static byte[] bytesArchivo(File archivo) {
        try {
            FileInputStream fileInputStream = new FileInputStream(archivo);
            byte[] bytes = new byte[(int) archivo.length()];
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene el hash de un archivo
     * @param archivo Archivo del cual obtener el hash.
     * @return 
     */
    private static String hashArchivo(File archivo) {
        String checksum = "";
        try {
            InputStream is = Files.newInputStream(Paths.get(archivo.getAbsolutePath()));
            checksum = DigestUtils.md5Hex(is);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return checksum;
    }
    
    /**
     * Obtiene un archivo especifico del equipo y lo transforma en documento
     * @param ruta ruta del archivo
     * @return El archivo transformado en Documento
     */
    public static Documento obtenerArchivotoLocal(String ruta) {

        File archivo = new File(ruta);

        Documento documento = new Documento();
        documento.setRuta(archivo.getAbsolutePath());
        documento.setNombre(archivo.getName());
        documento.setData(bytesArchivo(archivo));
        documento.setHash(hashArchivo(archivo));
        documento.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(archivo.lastModified()), ZoneId.systemDefault()));

        return documento;
    }

}
