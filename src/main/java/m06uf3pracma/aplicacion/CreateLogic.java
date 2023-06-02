/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import m06uf3pracma.Utils.Utils;
import m06uf3pracma.dao.RepositorioDAO;
import m06uf3pracma.datos.DataLayer;
import m06uf3pracma.model.Repositorio;
import m06uf3pracma.presentacion.CreatePresentation;
import m06uf3pracma.presentacion.PresentationLayer;
import org.apache.commons.cli.CommandLine;

/**
 * @author Ivan
 */
public class CreateLogic extends LogicLayer {

    public CreateLogic(PresentationLayer presentation, DataLayer data) {
        super(presentation, data);
    }

    @Override
    public void processData() {

        try {
            CommandLine cmd = (CommandLine) data.getData();
            if (cmd.hasOption("h")) {
                ((CreatePresentation) presentation).printHelp();
            }
            
            // Comprobamos que el usuario haya escrito algo. Si es así, intentamos obtener el nombre del repositorio
            if (cmd.getArgList().size() > 1) {
                Repositorio repo = new Repositorio();
                List<String> argList = cmd.getArgList();
                String ruta = argList.get(1);
                File dir = new File(ruta);
                
                // Comprobamos si el directorio existe en local
                if (dir.isDirectory()) {
                    String nombre = Utils.rutaToNombre(argList.get(1));
                    
                    // Comprobamos si el repositorio existe
                    if (Utils.existeRepo(nombre)) {
                        System.out.println("El repositorio " + nombre + " ya existe.");
                        // Si no existe, lo creamos
                    } else {
                        RepositorioDAO repoDAO = new RepositorioDAO(nombre);
                        repo.setNombre(nombre);
                        repo.setRaiz(argList.get(1));
                        repoDAO.save(repo);
                        System.out.println("Se ha creado el repositorio correctamente.");
                    }
                    
                } else {
                    System.out.println("El repositorio local no existe.");
                    ((CreatePresentation) presentation).printHelp();
                }
                
            } else {
                System.out.println("Debes introducir un comando válido.");
                ((CreatePresentation) presentation).printHelp();
            }

        } catch (Exception ex) {
            Logger.getLogger(CreateLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
