/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.presentacion;

import java.time.format.DateTimeFormatter;
import m06uf3pracma.Utils.CliManager;
import m06uf3pracma.Utils.ErrorMessagesHelpers;
import m06uf3pracma.model.Documento;

/**
 * Esta clase representa la capa de presentación para comparar archivos. Esta
 * clase hereda de la clase PresentationLayer.
 *
 * @author Alex
 */
public class ComparePresentation extends PresentationLayer {

    /**
     *
     * Constructor por defecto de la clase ComparePresentation.
     */
    public ComparePresentation() {

    }

    /**
     *
     * Imprime la ayuda para el comando "compare".
     */
    public void printHelp() {
        String prefix = "\nCompare usage: ";
        CliManager manager = new CliManager();

        String usage = "COMPARE dir_base fichero [-d / --detail]:\r\n dir_base: Requerido: Ruta del directorio Base.\r\n fichero: Opcional: Fichero el cual comparar.";
        ErrorMessagesHelpers.printCommandHelp(manager.getCompareOptions(), usage, prefix);

    }

    /**
     *
     * Imprime un mensaje de error cuando el número de argumentos es incorrecto
     * y muestra la ayuda del comando correspondiente.
     */
    public void argumentosIncorrectos() {
        System.out.println("El número de argumentos es incorrecto.");
        printHelp();
    }

    /**
     *
     * Imprime un mensaje de error cuando la ruta proporcionada no existe y
     * termina la ejecución del programa.
     */
    public void rutaNoExiste() {
        System.out.println("La ruta proporcionada no existe.");
        System.exit(0);
    }

    /**
     *
     * Imprime un mensaje de error cuando no existe un repositorio remoto para
     * la ruta proporcionada y termina la ejecución del programa.
     */
    public void repositorioNoExiste() {
        System.out.println("No existe un repositorio remoto para esta ruta.");
        System.exit(0);
    }

    /**
     *
     * Imprime un mensaje de error cuando el fichero introducido no existe y
     * termina la ejecución del programa.
     */
    public void ficheroNoExiste() {
        System.out.println("El fichero introducido no existe.");
        System.exit(0);
    }

    /**
     *
     * Imprime un mensaje informando que el documento no existe en el
     * repositorio local.
     *
     * @param documento el documento que no existe en el repositorio local.
     */
    public void ficheroNoExisteRepositorioLocal(Documento documento) {
        System.out.println("El documento " + documento.getRuta() + " no existe en el repositorio local.");
    }

    /**
     *
     * Imprime un mensaje informando que el documento no tiene el mismo
     * contenido de forma local y remota.
     *
     * @param documento el documento que tiene diferente contenido local y
     * remoto.
     */
    public void ficheroDiferenteContenido(Documento documento) {
        System.out.println("El documento " + documento.getRuta()
                + " no tiene el mismo contenido de forma local y remota.");
    }

    /**
     *
     * Imprime un mensaje informando que el documento tiene diferentes fechas de
     * modificación de forma local y remota.
     *
     * @param documentoDB el documento en el repositorio remoto.
     * @param documentoSistema el documento en el sistema local.
     */
    public void ficheroDiferenteTimestamp(Documento documentoDB, Documento documentoSistema) {
        System.out.println("El documento " + documentoDB.getRuta()
                + " tiene diferentes fechas de modificación de forma local y remota");
        System.out.println("\tFecha y hora de modificación en el repositorio local: " + documentoSistema.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("\tFecha y hora de modificación en el reposiotorio remoto: " + documentoDB.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /**
     *
     * Imprime un mensaje informando que el documento no tiene diferencias con
     * el repositorio remoto.
     *
     * @param documento el documento que no tiene diferencias con el repositorio
     * remoto.
     */
    public void ficheroSinDiferencias(Documento documento) {
        System.out.println("El documento " + documento.getRuta()
                + " no tiene diferencias con el repositorio remoto.");
    }

    /**
     *
     * Imprime un mensaje informando que el documento no existe en el
     * repositorio remoto.
     *
     * @param documento el documento que no existe en el repositorio remoto.
     */
    public void ficheroNoExisteRepositorioRemoto(Documento documento) {
        System.out.println("El documento " + documento.getRuta() + " no existe en el repositorio remoto.");
    }

    /**
     * Muestra un mensaje de error.
     * @param ex Objeto de error.
     */
    public void error(Exception ex) {
        System.out.println("Ha ocurrido un error: " + ex.getMessage());
    }
}
