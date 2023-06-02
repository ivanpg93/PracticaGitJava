/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import m06uf3pracma.dao.RepositorioDAO;
import m06uf3pracma.datos.DataLayer;
import m06uf3pracma.model.Repositorio;
import m06uf3pracma.presentacion.DropPresentation;
import m06uf3pracma.presentacion.PresentationLayer;
import org.apache.commons.cli.CommandLine;

/**
 * @author Ivan
 */
public class DropLogic extends LogicLayer {

    public DropLogic(PresentationLayer presentation, DataLayer data) {
        super(presentation, data);
    }

    @Override
    public void processData() {

        try {
            CommandLine cmd = (CommandLine) data.getData();
            if (cmd.hasOption("h")) {
                ((DropPresentation) presentation).printHelp();
            }
            
            // Comprobamos que el usuario haya escrito algo. Si es así, intentamos obtener el nombre del repositorio
            if (cmd.getArgList().size() > 1) {
                List<String> argList = cmd.getArgList();
                String nombre = argList.get(1);
                RepositorioDAO repoDAO = new RepositorioDAO(nombre);
                Repositorio repo = repoDAO.getByNom(nombre);
                
                // Si existe el repositorio, lo borramos
                if (repo != null) {
                    repoDAO.delete(repo);
                    System.out.println("Se ha borrado el repositorio correctamente.");
                } else {
                    System.out.println("No se ha encontrado ningún repositorio con ese nombre.");
                }
            } else {
                System.out.println("Debes un comando válido.");
                ((DropPresentation) presentation).printHelp();
            }

        } catch (Exception ex) {
            Logger.getLogger(DropLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
