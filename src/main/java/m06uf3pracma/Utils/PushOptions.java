/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 *
 * @author Alex
 */
class PushOptions implements OptionsInterface {

    @Override
    public Options loadOptions() {
        Options opciones = new Options();
        opciones.addOption("h", "help", false, "Muestra información sobre el comando");
        opciones.addOption("f", "force", false, "Ignora las fechas de modificacion de los ficheros.");
        return opciones;
    }
}
