/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.presentacion;

import java.util.List;
import m06uf3pracma.Utils.CliManager;
import m06uf3pracma.Utils.ErrorMessagesHelpers;
import m06uf3pracma.model.Documento;
import m06uf3pracma.presentacion.PresentationLayer;

/**
 * Clase que se encarga de imprimir informacion de la operacion Pull
 *
 * @author Alex
 */
public class PullPresentation extends PresentationLayer {

    public PullPresentation() {

    }

    /**
     * Imprime la informacion del comando
     */
    public static void impHelp() {
        String prefix = "Pull usage: ";
        CliManager manager = new CliManager();

        String usage = "Pull dir_base fichero [-f]:\r\n dir_base: Requerido: Ruta del directorio local.\r\n fichero Opcional: Especificar ruta relativa del fichero.";
        ErrorMessagesHelpers.printCommandHelp(manager.getPushOptions(), usage, prefix);
    }

    /**
     * imprime el error por argumento no valido
     */
    public static void impArgumentError() {

        System.out.println("El comando 'pull' no tiene argumentos validos.");
        System.out.println("Utiliza la opcion -h para información sobre el comando.");
    }

    /**
     * Imprime el resultado de la operacion
     *
     * @param ok resultado de la operacion
     */
    public static void impPullSucces(int ok) {

        switch (ok) {
            case 0:
                System.out.println("Ha habido un error al relizar la operación pull. El archivo no existe");
                break;
            case 1:
                System.out.println("El pull se ha realizado sin problemas.");
                break;
            case 2:
                System.out.println("Pull no realizado. El archivo descargado es mas antiguo que la version local");
                break;
            case 3:
                System.out.println("Ha habido un error al relizar la operación pull. El repositorio remoto no existe");
                break;
            case 4:
                System.out.println("Ha habido un error al realizar la operación pull. No existe un directorio local para el repositorio remoto");
                break;
            case 5:
                System.out.println("Ha habido un error al realizar la operacion pull. Error al guardar los archivos en el direcotiro local");
                break;
        }

    }

    /**
     * Muestra las rutas de los documentos que no se an actualizado por
     * timeStamp
     *
     * @param docs lista de documentos
     */
    public static void impNotPullDocs(List<Documento> docs) {
        System.out.println("Hay " + docs.size() + " archivo/s que no se han actualizado por que la version remota es mas antigua que la local");
        docs.forEach(doc -> {
            System.out.println(doc.getRuta());
        });

    }

    /**
     * Muestra la ruta del documento que no se ha guardado por una IOException
     *
     * @param ruta ruta del documento
     */
    public static void impNotDocWrite(String ruta) {
        System.out.println("El siguiente archivo no ha podido ser guardado: " + ruta);
    }

}
