/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.Options;

/**
 * @author Ivan
 */
class DropOptions implements OptionsInterface {

    /**
     * Función encargada de cargar las opciones del comando Drop.
     *
     * @return Carga las opciones del comando Drop.
     */
    @Override
    public Options loadOptions() {
        Options dropOptions = new Options();
        dropOptions.addOption("h", "help", false, "Mostrar Ayuda");
        return dropOptions;
    }
}
