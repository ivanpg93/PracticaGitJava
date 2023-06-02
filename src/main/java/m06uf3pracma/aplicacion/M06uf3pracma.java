/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package m06uf3pracma.aplicacion;

import java.util.logging.Level;
import java.util.logging.Logger;
import m06uf3pracma.Utils.CliManager;
import m06uf3pracma.datos.CliDataLayer;
import org.apache.commons.cli.Options;
import m06uf3pracma.presentacion.CliPresentationLayer;

/**
 * Clase Principal de la aplicación
 *
 * @author Cole
 */
public class M06uf3pracma {

    /**
     * Funció que se ejecuta al ejecutar la aplicación
     *
     * @param args argumentos del usuario al ejecutar la aplicación
     */
    public static void main(String[] args) {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        mongoLogger.setLevel(Level.WARNING);

        mongoLogger = Logger.getLogger("");
        mongoLogger.setLevel(Level.SEVERE);

        CliManager manager = new CliManager();
        CliPresentationLayer presentation = new CliPresentationLayer();
        if (args.length < 1) {
            presentation.printMainHelper();
            System.exit(0);
        }

        Options options = null;
        //Comprobar el primer argumento --> report, export, import, decrypt o encrypt
        switch (args[0].toLowerCase()) {
            case "create" ->
                options = manager.getCreateOptions();
            case "drop" ->
                options = manager.getDropOptions();
            case "push" ->
                options = manager.getPushOptions();
            case "pull" ->
                options = manager.getPullOptions();
            case "compare" ->
                options = manager.getCompareOptions();
            case "clone" ->
                options = manager.getCloneOptions();
            default ->
                presentation.printMainHelper();
        }

        presentation = new CliPresentationLayer(options, args[0]);
        CliDataLayer data = new CliDataLayer(args, options);
        CliLogic logic = new CliLogic(presentation, data);
        logic.processData();
    }
}
