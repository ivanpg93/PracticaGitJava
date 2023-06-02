/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.datos;

import m06uf3pracma.Utils.CliManager;
import m06uf3pracma.Utils.ErrorMessagesHelpers;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 *  Clase encargada de obtener las opciones de la línea de comandos.
 * @author Cole
 */
public class CliDataLayer extends DataLayer {

    // Array de Argumentos introducidos por consola.
    private final String[] args;
    private final Options options;
   
    /**
     * Constructor ecargado de instanciar los argumentos
     * @param args Argumentos
     * @param options Opciones de Comandos
     */
    public CliDataLayer(String[] args, Options options) {
        this.args = args;
        this.options = options;
    }
    
    @Override
    public Object getData() throws ParseException {
        return parseOptions(options, args);
    }
    
    /**
     * Función encargada de parsear la linea de comandos con las opciones dadas
     * @param options Las opciones que están disponibles
     * @param args Argumentos de la linea de comandos
     * @return Linea de comandos parseada
     */
    public CommandLine parseOptions(Options options,String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        cmd = parser.parse(options, args);
        return cmd;
    }
}
