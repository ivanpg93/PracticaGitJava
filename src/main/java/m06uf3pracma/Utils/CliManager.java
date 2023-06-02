/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Clase Singleton encargada de manejar las opciones de la línea de comandos.
 *
 * @author Cole
 */
public class CliManager {

    //Opciones de los comandos
    private Options createOptions;
    private Options dropOptions;
    private Options pushOptions;
    private Options pullOptions;
    private Options compareOptions;
    private Options cloneOptions;

    public Options getCreateOptions() {
        return createOptions;
    }

    public Options getDropOptions() {
        return dropOptions;
    }

    public Options getPushOptions() {
        return pushOptions;
    }

    public Options getPullOptions() {
        return pullOptions;
    }

    public Options getCompareOptions() {
        return compareOptions;
    }

    public Options getCloneOptions() {
        return cloneOptions;
    }

    public CommandLine getCmd() {
        return cmd;
    }

    //Línea de comandos con las opciones
    private CommandLine cmd;

    /**
     * Constructor que instancia las opciones de los comandos.
     */
    public CliManager() {
        cmd = null;
        addAllOptions();
    }

    
    /**
     * Funcición encargada de cargar todos los comandos
     */
    private void addAllOptions() {
        createOptions = new CreateOptions().loadOptions();
        dropOptions = new DropOptions().loadOptions();
        pushOptions = new PushOptions().loadOptions();
        pullOptions = new PullOptions().loadOptions();
        compareOptions = new CompareOptions().loadOptions();
        cloneOptions = new CloneOptions().loadOptions();

    }

    /**
     * Ejemplos de como añadir comandos
     */

    /*
    private void addAllOptions() {
        exportOptions = new Options();        
        //Exportar
        exportOptions.addOption("f", "format", true, "Formato de salida (CSV, XML o CSVXML)");
        exportOptions.addOption("o", "output", true, "Directorio del archivo de salida");
        exportOptions.addOption("c", "column", true, "Elemento sobre el cual ordenar la exportación");
        exportOptions.addOption("s","sortingOrder",true,"[Opcional] Orden a la hora de ordenar los datos (ascending, descending)");
        exportOptions.addOption("h","help",false,"Ayuda sobre el comando");
        
        // Reporte
        reportOptions = new Options();  
        reportOptions.addOption("h","help",false,"Ayuda sobre el comando");
        reportOptions.addOption("e","directorio_exportar", true, "Opción de exportación");
        reportOptions.addOption("t","tipo_informe", true, "Tipo de informe [0,1,2]");
        
        //Encriptar
        encryptOptions = new Options();
        encryptOptions.addOption("i", "input", true, "Directorio del archivo de entrada");
        encryptOptions.addOption("o", "output", true, "Directorio del archivo de salida");
        encryptOptions.addOption("k", "key", true, "Clave de Encriptación/Desencriptación");
        encryptOptions.addOption("h","help",false,"Ayuda sobre el comando");
        
        //Desencriptar
        decryptOptions = new Options();
        decryptOptions.addOption("i", "input", true, "Directorio del archivo de entrada");
        decryptOptions.addOption("o", "output", true, "Directorio del archivo de salida");
        decryptOptions.addOption("k", "key", true, "Clave de Encriptación/Desencriptación");
        decryptOptions.addOption("h","help",false,"Ayuda sobre el comando");
        
        // Buscar
        searchOptions = new Options();
        searchOptions.addOption("h","help",false,"Ayuda sobre el comando");
        searchOptions.addOption("e","elemento", true, "Elemento a buscar");
        searchOptions.addOption("n","nombre", true, "Nombre del elemento a buscar");
    }
     */
    /**
     * Función encargada de parsear la linea de comandos con las opciones dadas
     *
     * @param options Las opciones que están disponibles
     * @param args Argumentos de la linea de comandos
     * @return Linea de comandos parseada
     */
    public CommandLine parseOptions(Options options, String[] args) throws ParseException {
        CliManager manager = new CliManager();
        cmd = manager.parseOptions(options, args);
        return cmd;
    }
}
