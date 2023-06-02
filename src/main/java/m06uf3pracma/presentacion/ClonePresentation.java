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
public class ClonePresentation extends PresentationLayer {

    public ClonePresentation() {
        
    }
    
    public void printHelp() {
        String prefix = "Clone usage: ";
        CliManager manager = new CliManager();
        
        String usage = "CLONE nombre timestamp [-t / --timestamp]:\r\n nombre: Requerido: Nombre del repositorio que deseas clonar.\r\n timestamp Opcional: Marca de tiempo que indica qué ficheros serán clonados.";
        ErrorMessagesHelpers.printCommandHelp(manager.getCloneOptions(), usage, prefix);
        
    }
}
