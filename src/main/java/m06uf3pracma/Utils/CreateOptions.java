/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.Options;

/**
 * @author Ivan
 */
class CreateOptions implements OptionsInterface{

     /**
     * Función encargada de cargar las opciones del comando Create.
     * @return Carga las opciones del comando Options.
     */
    @Override
    public Options loadOptions() {
        Options createOptions = new Options();
        createOptions.addOption("h", "help", false, "Mostrar Ayuda");
        return createOptions;
    }
}
