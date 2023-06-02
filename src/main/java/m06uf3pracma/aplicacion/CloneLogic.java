/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import m06uf3pracma.Utils.Timestamp;
import m06uf3pracma.dao.DocumentoDAO;
import m06uf3pracma.dao.RepositorioDAO;
import m06uf3pracma.datos.DataLayer;
import m06uf3pracma.model.Documento;
import m06uf3pracma.model.Repositorio;
import m06uf3pracma.presentacion.ClonePresentation;
import m06uf3pracma.presentacion.PresentationLayer;
import org.apache.commons.cli.CommandLine;

/**
 * @author Ivan
 */
public class CloneLogic extends LogicLayer {

    public CloneLogic(PresentationLayer presentation, DataLayer data) {
        super(presentation, data);
    }

    @Override
    public void processData() {

        try {
            CommandLine cmd = (CommandLine) data.getData();
            if (cmd.hasOption("h")) {
                ((ClonePresentation) presentation).printHelp();
            }

            // Obtenemos el nombre del repositorio
            if (cmd.getArgList().size() > 1) {
                List<String> argList = cmd.getArgList();
                String nombre = argList.get(1);

                // Inicializamos la variable timestamp en el día de hoy, a la espera de comprobar si el usuario ha indicado alguna
                LocalDateTime dateTmp = LocalDateTime.now();
                String today = Timestamp.dateToString(dateTmp);
                dateTmp = Timestamp.dateFormatEU(today);

                // Si lo ha hecho, la sobreescribimos
                if (cmd.getArgList().size() > 2) {
                    String timeStamp = cmd.getArgList().get(2);
                    dateTmp = Timestamp.dateFormatEU(timeStamp);
                }

                // Inicializamos el repositorio que queremos clonar
                RepositorioDAO repoDAO = new RepositorioDAO(nombre);
                Repositorio repo = repoDAO.getByNom(nombre);
                DocumentoDAO docDAO = new DocumentoDAO(nombre);

                // Si el repositorio que queremos clonar existe, copiamos las propiedades de este en el nuevo repositorio
                if (repo != null) {
                    File dir = new File(repo.getRaiz());
                    if (dir.exists()) {
                        System.out.println("ERROR: El directorio " + repo.getRaiz() + " ya existe.");
                    } else {
                        List<Documento> docs = docDAO.getAll();
                        List<Documento> docsClonados = new ArrayList();

                        // Recorremos todos los documentos del repositorio
                        for (Documento doc : docs) {
                            // Obtenemos el timestamp del documento y lo formateamos
                            LocalDateTime dateDoc = doc.getTimestamp();
                            String dateDocFormatted = Timestamp.dateToString(dateDoc);
                            dateDoc = Timestamp.dateFormatEU(dateDocFormatted);

                            // Añadimos todos los documentos en una nueva lista comprobando primero el timestamp
                            if (dateDoc != null) {
                                if (Timestamp.compareDate(dateDoc, dateTmp) <= 0) {
                                    docsClonados.add(doc);

                                    // Creamos el directorio ya que previamente hemos comprobado que no existe
                                    dir = new File(repo.getRaiz());
                                    dir.mkdirs();

                                    // Una vez creado el directorio, clonamos el documento
                                    File file = new File(doc.getRuta());
                                    //Obtenemos su ruta padre
                                    String ruta_dir = file.getParent();

                                    //Creamos los directorios necesarios en caso de que no existan
                                    Files.createDirectories(Path.of(ruta_dir));
                                    FileWriter writer = new FileWriter(file);
                                    writer.write(new String(doc.getData()));
                                    writer.close();
                                }
                            }
                        }

                        // Si no hay ningún documento añadido, avisamos al usuario.
                        if (docsClonados.isEmpty()) {
                            System.out.println("No hay ningún documento que clonar.");

                        } else {
                            System.out.println("Los documentos del repositorio se han clonado correctamente.");
                        }
                    }

                } else {
                    System.out.println("El repositorio que deseas clonar no existe.");
                }

            } else {
                System.out.println("Debes introducir un comando válido.");
                ((ClonePresentation) presentation).printHelp();
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
}
