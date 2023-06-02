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
 * Clase que muestra por terminal informacion sobre la operacion Push
 *
 * @author Alex
 */
public class PushPresentation extends PresentationLayer {

    public PushPresentation() {

    }

    /**
     * Muestra la informacion sobre el comando Push
     */
    public static void impHelp() {
        String prefix = "Push usage: ";
        CliManager manager = new CliManager();
        
        String usage = "Push dir_base fichero [-f]:\r\n dir_base: Requerido: Ruta del repositorio objetivo.\r\n fichero Opcional: Especificar fichero.";
        ErrorMessagesHelpers.printCommandHelp(manager.getPushOptions(), usage, prefix);
    }

    /**
     * Muestra el mensage de error por argumentos no validos.
     */
    public static void impArgumentError() {

        System.out.println("El comando 'push' no tiene argumentos validos.");
        System.out.println("Utiliza la opcion -h para información sobre el comando.");
    }

    /**
     * Muestra el resultado de la operacion Push
     *
     * @param ok resultado de la operacion
     */
    public static void impPushSucces(int ok) {

        switch (ok) {
            case 0:
                System.out.println("Ha habido un error al relizar la operación push. El archivo no existe");
                break;
            case 1:
                System.out.println("El push se ha realizado sin problemas.");
                break;
            case 2:
                System.out.println("Push no realizado. El archivo subido es mas antiguo que su version remota");
                break;
            case 3:
                System.out.println("Ha habido un error al relizar la operación push. El repositorio remoto no existe");
                break;
            case 4:
                System.out.println("Ha habido un error al realizar la operación push. No existe un directorio local para el repositorio remoto");
                break;
        }

    }

    /**
     * Muestra la ruta de los documentos que no se han actualizado por TimeStamp
     *
     * @param docs
     */
    public static void impNotPushDocs(List<Documento> docs) {
        System.out.println("Hay " + docs.size() + " archivo/s que no se han actualizado por que la version local es mas antigua que la remota");
        docs.forEach(doc -> {
            System.out.println(doc.getRuta());
        });

    }
}
