/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;

/**
 *
 * @author joseb
 */
public class Utils {

    /**
     * Comprueva si existe el repositorio en la base de datos
     *
     * @param nombre_repo nombre del repositorio
     * @return true: existe false: no existe
     */
    public static boolean existeRepo(String nombre_repo) {
        boolean existe;
        MongoDatabase base_de_datos = MongoDBConnector.getInstance().getDB();
        existe = base_de_datos.listCollectionNames().into(new ArrayList<>()).contains(nombre_repo);
        return existe;

    }

    /**
     * Convierte una ruta a un nombre de repositorio.
     * @param ruta String de la ruta.
     * @return Nombre de repositorio.
     */
    public static String rutaToNombre(String ruta) {
        String nombre = ruta.replaceAll("[/\\\\]", "_");
        nombre = nombre.replaceAll("^[A-Z]:_", "");
        
        if(nombre.substring(0,1).equals("_")) nombre = nombre.substring(1);
        
        return nombre;

    }

    public static String obtenerTipoDeSistema() {
        String sistema = System.getProperty("os.name").toLowerCase();
        if (sistema.contains("win")) {
            return "Windows";
        } else if (sistema.contains("nix") || sistema.contains("nux") || sistema.contains("aix")) {
            return "Unix/Linux";
        } else {
            return "Desconocido";
        }
    }
}
