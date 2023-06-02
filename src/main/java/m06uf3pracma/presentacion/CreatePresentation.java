/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.presentacion;

import m06uf3pracma.Utils.CliManager;
import m06uf3pracma.Utils.ErrorMessagesHelpers;

/**
 * @author Ivan
 */
public class CreatePresentation extends PresentationLayer {

    public CreatePresentation() {
        
    }
    
    public void printHelp() {
        String prefix = "Create usage: ";
        CliManager manager = new CliManager();
        
        String usage = "CREATE dir_base:\r\n dir_base: Requerido: Ruta del Repositorio Base. Info: Si ya existe, se informará al usuario y no se creará de nuevo.";
        ErrorMessagesHelpers.printCommandHelp(manager.getCreateOptions(), usage, prefix);
        
    }
}
