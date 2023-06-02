/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import m06uf3pracma.Utils.RecorrerDirectorio;
import m06uf3pracma.Utils.Utils;
import m06uf3pracma.dao.DocumentoDAO;
import m06uf3pracma.datos.DataLayer;
import m06uf3pracma.model.Documento;
import m06uf3pracma.presentacion.PresentationLayer;
import m06uf3pracma.presentacion.PullPresentation;
import m06uf3pracma.presentacion.PushPresentation;
import org.apache.commons.cli.CommandLine;

/**
 * Logica que se encarga de la funcion Pull
 *
 * @author Alex
 */
public class PullLogic extends LogicLayer {

    public PullLogic(PresentationLayer presentation, DataLayer data) {
        super(presentation, data);
    }

    /**
     * Funcion que se encargar de procesar el comando introducido por le
     * usuario.
     */
    @Override
    public void processData() {
        try {

            CommandLine cmd = (CommandLine) data.getData();

            if (cmd.hasOption("h")) {
                PullPresentation.impHelp();
            } else {

                List<String> args = cmd.getArgList();

                if (args.size() == 3) {//Pull de un archivo especifico
                    PullPresentation.impPullSucces(pullFichero(cmd));
                } else if (args.size() == 2) {//Pull de todo el repositorio
                    PullPresentation.impPullSucces(pullRecursivo(cmd));
                } else {
                    PushPresentation.impArgumentError();
                }
            }

        } catch (Exception e) {
            Logger.getLogger(CreateLogic.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Pull de un archivo especifico
     *
     * @param cmd comando
     * @return el resultado de la operacion, segun el numero significa un
     * resultado diferente
     */
    private int pullFichero(CommandLine cmd) {

        //Obtenemos la ruta del repositorio
        Path ruta_repositorio = Paths.get(cmd.getArgList().get(1));

        //Transformamos la ruta del repo a al nombre del repo
        String nombre_repo = Utils.rutaToNombre(ruta_repositorio.toString());

        if (Utils.existeRepo(nombre_repo)) {// comprobamos que existe el repo

            //Obtenemos la ruta del archivo
            Path ruta_archivo = Paths.get(cmd.getArgList().get(2));

            //Obtenemos la ruta entera uniendo  la ruta del repositorio y la del fichero
            Path ruta_entera = ruta_repositorio.resolve(ruta_archivo);
            if (Files.exists(ruta_repositorio)) {
                //Instanciamos el DAO de Documentos 
                DocumentoDAO documentoDAO = new DocumentoDAO(nombre_repo);

                //Obtenemos el documento remoto
                Documento doc_remoto = documentoDAO.getByRuta(ruta_entera.toString());

                if (doc_remoto == null) { //Comprovamos que el arhivo remoto existe 

                    return 0;//El archivo que deseamos descargar no existe
                } else {

                    if (cmd.hasOption("f")) {// Opcion forzar

                        //Guardar archivo
                        guardarArchivo(doc_remoto);

                    } else { // sin forzar

                        if (Files.exists(ruta_entera)) {// si existe el archivo local

                            //Obtenemos el archivo local
                            Documento doc_local = RecorrerDirectorio.obtenerArchivotoLocal(ruta_entera.toString());

                            //Obtenemos las fechas de modificacion del archivo local y remoto
                            LocalDateTime f_mod_remoto = doc_remoto.getTimestamp();
                            LocalDateTime f_mod_local = doc_local.getTimestamp();

                            if (f_mod_remoto.isAfter(f_mod_local)) {// Comprovamos las fechas de modicicacion

                                //Guardamos el archivo en el repositorio local
                                guardarArchivo(doc_remoto);

                            } else {
                                return 2; //El archivo remoto es mas antiguo que el local
                            }
                        } else {
                            //Guardamos el archivo en el repositorio local
                            guardarArchivo(doc_remoto);
                        }

                    }
                }
            } else {
                return 4; // directorio local no existe
            }

        } else {
            return 3; //Repositorio remoto no existe
        }

        return 1; // El push se realizo correctamente
    }

    /**
     * Pull recursivo
     *
     * @param cmd comando
     * @return el resultado de la operacion, segun el numero el resultado es
     * diferente
     */
    private int pullRecursivo(CommandLine cmd) {

        //Obtenemos la ruta del repositorio
        Path ruta_repositorio = Paths.get(cmd.getArgList().get(1));

        //Obtener nombre del repo
        String nombre_repo = Utils.rutaToNombre(ruta_repositorio.toString());

        if (Utils.existeRepo(nombre_repo)) {//Comprovar que el repo remoto existe

            //Creamos el dao de documento
            DocumentoDAO documentoDAO = new DocumentoDAO(nombre_repo);

            if (Files.exists(ruta_repositorio)) {// Comprovamos que existe el directorio local

                //Obtenemos todos los documentos del repositorio remoto
                ArrayList<Documento> docs_remotos = (ArrayList<Documento>) documentoDAO.getAll();

                if (cmd.hasOption("f")) {//opcion forzar

                    docs_remotos.forEach(doc_remoto -> { //recorremos la lista de documentos remotos

                        //Guardamos el archivo y imprimimos el resultado en caso de IO error
                        int ok = guardarArchivo(doc_remoto);
                        if (ok == 5) {
                            PullPresentation.impNotDocWrite(doc_remoto.getRuta());
                        }
                    });

                } else {// sin forzar

                    //Lista de documentos no actualizados
                    ArrayList<Documento> docs_no_bajados = new ArrayList<>();

                    for (Documento doc_remoto : docs_remotos) { //recorremos la lista de documentos remotos

                        //Obtener ruta del archivo remoto/local
                        Path ruta_doc_remoto = Paths.get(doc_remoto.getRuta());

                        if (Files.exists(ruta_doc_remoto)) {//comprovar si existe el archivo local

                            //Obtenemos el archivo local
                            Documento doc_local = RecorrerDirectorio.obtenerArchivotoLocal(ruta_doc_remoto.toString());

                            //Obtenemos las fechas de modificacion del archivo local y remoto
                            LocalDateTime f_mod_remoto = doc_remoto.getTimestamp();
                            LocalDateTime f_mod_local = doc_local.getTimestamp();

                            if (f_mod_remoto.isAfter(f_mod_local)) {// Comprovamos las fechas de modicicacion

                                //Guardamos el archivo en el repositorio local
                                int ok = guardarArchivo(doc_remoto);
                                if (ok == 5) {
                                    PullPresentation.impNotDocWrite(doc_remoto.getRuta());
                                }

                            } else {
                                docs_no_bajados.add(doc_remoto);
                            }

                        } else {
                            int ok = guardarArchivo(doc_remoto);
                            if (ok == 5) {
                                PullPresentation.impNotDocWrite(doc_remoto.getRuta());
                            }
                        }

                    }
                    //Imprimimos los docs no bajados
                    if (docs_no_bajados.size() > 0) {
                        PullPresentation.impNotPullDocs(docs_no_bajados);
                    }
                }

            } else {
                return 4;// El directorio local correspondiente no existe.
            }

        } else {
            return 3; // El repo remoto no existe
        }

        return 1;//Pull completado
    }

    /**
     * Funcion que guarda archivos en el repositorio local
     *
     * @param doc documento que guardamos
     * @return 1: si se guardo correctamente 5: si hubo un IO error
     */
    private int guardarArchivo(Documento doc) {

        try {
            //Creamos el archivo a partir del documento remoto
            File file = new File(doc.getRuta());

            //Obtenemos su ruta padre
            String ruta_dir = file.getParent();

            //Creamos los directorios necesarios en caso de que no existan
            Files.createDirectories(Path.of(ruta_dir));

            //Escribimos en el archivo el data
            FileWriter writer = new FileWriter(file);
            writer.write(new String(doc.getData()));
            writer.close();
            return 1;
        } catch (IOException e) {
            return 5; //  IO error
        }

    }
}
