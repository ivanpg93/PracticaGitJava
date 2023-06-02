/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.Options;

/**
 * Interfaz de las opciones de los comandos.
 * @author Alex
 */
interface OptionsInterface {
    
    /**
     * Función encargada de crear las opciones.
     * @return Opciones del comando.
     */
    public Options loadOptions();
}
