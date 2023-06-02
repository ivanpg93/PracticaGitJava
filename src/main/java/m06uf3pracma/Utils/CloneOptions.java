/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.Options;

/**
 * @author Ivan
 */
class CloneOptions implements OptionsInterface{

    /**
     * Carga las opciones del comando Clone
     * @return Opciones del comando Clone
     */
    @Override
    public Options loadOptions() {
        Options cloneOptions = new Options();
        cloneOptions.addOption("t", "timestamp", false, "Indica el timestamp para clonar los ficheros que sean igual o más antiguos a este. Si se omite, se utilizará el momento actual.");
        cloneOptions.addOption("h", "help", false, "Mostrar Ayuda");
        return cloneOptions;
    }
}
