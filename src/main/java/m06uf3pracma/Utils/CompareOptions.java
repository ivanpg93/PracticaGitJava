/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Clase encargada de las las opciones del comando Compare.
 * @author Alex
 */
class CompareOptions implements OptionsInterface {

    /**
     * Función encargada de cargar las opciones del comando Compare.
     * @return Carga las opciones del comando Compare.
     */
    @Override
    public Options loadOptions() {
        Options compareOptions = new Options();
        compareOptions.addOption("d", "detail", false, "Parametro para permite comparar archivos linea a linea de local a remoto y viceversa.");
        compareOptions.addOption("h", "help", false, "Mostrar Ayuda");
        return compareOptions;
    }
}
