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
public class DropPresentation extends PresentationLayer {

    public DropPresentation() {
        
    }
    
    public void printHelp() {
        String prefix = "Drop usage: ";
        CliManager manager = new CliManager();
        
        String usage = "DROP nombre:\r\n nombre: Requerido: Nombre del Repositorio Base. Si no existe, se informará al usuario y no se realizará la eliminación.";
        ErrorMessagesHelpers.printCommandHelp(manager.getDropOptions(), usage, prefix);
        
    }
}
