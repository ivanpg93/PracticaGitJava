/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Clase que muestra los mensajes de ayuda de los comandos
 */
public abstract class ErrorMessagesHelpers {

    /**
     * Función encargada de mostrar el mensaje de ayuda de un comando.
     *
     * @param options Opciones del comando
     * @param command Comando en cuestión
     */
    public static void printCommandHelp(Options options, String command) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(command, options);
        System.exit(0);
    }

    public static void printCommandHelp(Options options, String command, String prefix) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setSyntaxPrefix(prefix);
        formatter.printHelp(command, options);
        System.exit(0);
    }

    /**
     * Función encargada de mostrar el mensaje de ayuda de un comando.
     *
     * @param options Opciones del comando
     * @param command Comando en cuestión
     */
    public static void printCommandHelp(Options options, String command, Exception e) {
        HelpFormatter formatter = new HelpFormatter();
        System.out.println("El comando se ha utilizado de forma incorrecta.");
        System.out.println(e.getMessage());
        printCommandHelp(options, command);
    }

    public static void printCommandHelp(Options options, String command, String prefix, Exception e) {
        HelpFormatter formatter = new HelpFormatter();
        System.out.println("El comando se ha utilizado de forma incorrecta.");
        System.out.println(e.getMessage());
        formatter.setSyntaxPrefix(prefix);
        formatter.printHelp(command, options);
        System.exit(0);
    }

    /**
     * Función encargada de mostrar la ayuda de los comandos principales.
     */
    public static void printMainHelper() {
        System.out.println("Comandos soportados:");
        System.out.println("\tcreate\t --- Crea un repositorio remoto.");
        System.out.println("\tdrop\t --- Elimina un repositorio remoto.");
        System.out.println("\tpush\t --- Añade un fichero a un repositorio remoto.");
        System.out.println("\tpull\t --- Descarga un fichero del repositorio.");
        System.out.println("\tcompare\t --- Compara contenidos de ficheros.");
        System.out.println("\tclone\t --- Clona un reposiorio remoto de forma local.");
        System.exit(0);
    }
}
