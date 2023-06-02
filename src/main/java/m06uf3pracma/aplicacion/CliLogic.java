/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import m06uf3pracma.presentacion.CreatePresentation;
import m06uf3pracma.datos.DataLayer;
import org.apache.commons.cli.CommandLine;
import m06uf3pracma.presentacion.CliPresentationLayer;
import m06uf3pracma.presentacion.ClonePresentation;
import m06uf3pracma.presentacion.ComparePresentation;
import m06uf3pracma.presentacion.DropPresentation;
import m06uf3pracma.presentacion.PresentationLayer;
import m06uf3pracma.presentacion.PullPresentation;
import m06uf3pracma.presentacion.PushPresentation;

/**
 * Clase encargada de la lógica de los comandos
 *
 * @author Alex
 */
public class CliLogic extends LogicLayer {

    /**
     * Constructor de la logica del CLI
     *
     * @param presentation capa de presentación
     * @param data capa de datos
     */
    public CliLogic(PresentationLayer presentation, DataLayer data) {
        super(presentation, data);
    }

    /**
     * Función encargada de procesar los datos obtenidos del dataLayer.
     * Dependiendo del comando que se ha utilizado, se comprueba su longitud de
     * argumentos y se ejecuta la función encargada de la lógica de ese comando.
     */
    @Override
    public void processData() {
        CliPresentationLayer presentation = (CliPresentationLayer) this.presentation;

        try {
            CommandLine cmd = (CommandLine) data.getData();
            if (cmd.getArgs().length == 0) {
                presentation.printCommandHelp(new IllegalArgumentException("No se han especificado las opciones del comando de forma correcta."));
            }
            //Comprobar el primer argumento
            switch (cmd.getArgs()[0].toLowerCase()) {
                case "create" -> new CreateLogic(new CreatePresentation(), data).processData();
                    
                case "drop" -> new DropLogic(new DropPresentation(), data).processData();
                    
                case "push" -> new PushLogic(new PushPresentation(), data).processData();
                    
                case "pull" -> new PullLogic(new PullPresentation(), data).processData();
                    
                case "compare" -> new CompareLogic(new ComparePresentation(), data).processData();
                    
                case "clone" -> new CloneLogic(new ClonePresentation(), data).processData();
                    
                default ->
                    presentation.printMainHelper();
            }

        } catch (Exception ex) {
            presentation.printCommandHelp(ex);
        }
    }
}
