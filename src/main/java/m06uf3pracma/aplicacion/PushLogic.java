/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import java.io.File;
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
import m06uf3pracma.presentacion.PushPresentation;
import org.apache.commons.cli.CommandLine;

/**
 * Logica que se encarga de la funcion Push
 *
 * @author Alex
 */
public class PushLogic extends LogicLayer {

    public PushLogic(PresentationLayer presentation, DataLayer data) {
        super(presentation, data);
    }

    /**
     * Funcion que procesa el comando introducido por el usuario
     */
    @Override
    public void processData() {
        try {

            CommandLine cmd = (CommandLine) data.getData();

            if (cmd.hasOption("h")) {
                PushPresentation.impHelp();
            } else {

                List<String> args = cmd.getArgList();

                if (args.size() == 3) {//Push de un archivo especifico
                    PushPresentation.impPushSucces(pushFichero(cmd));
                } else if (args.size() == 2) {//Push de todo el repositorio
                    PushPresentation.impPushSucces(pushRecursivo(cmd));
                } else {
                    PushPresentation.impArgumentError();
                }
            }

        } catch (Exception e) {
            Logger.getLogger(CreateLogic.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Funcion que sube un archivo al repositorio remoto PUSH
     *
     * @param cmd comando introducido por el usuario
     * @return 1: El push se ha completado 0: El archivo no existe 2: El archivo
     * local es mas antiguo que el remoto 3: El repostiorio remoto no existe
     */
    private int pushFichero(CommandLine cmd) {

        //Obtenemos la ruta del repositorio
        Path ruta_repositorio = Paths.get(cmd.getArgList().get(1));

        //Transformamos la ruta del repo a al nombre del repo
        String nombre_repo = Utils.rutaToNombre(ruta_repositorio.toString());

        if (Utils.existeRepo(nombre_repo)) {// comprobamos que existe el repo

            //Obtenemos la ruta del archivo
            Path ruta_archivo = Paths.get(cmd.getArgList().get(2));

            //Obtenemos la ruta entera uniendo  la ruta del repositorio y la del fichero
            Path ruta_entera = ruta_repositorio.resolve(ruta_archivo);

            if (!Files.exists(ruta_entera)) { //Comprovamos que el arhivo existe el repositorio local

                return 0;//El archivo que deseamos subir no existe
            } else {

                //Instanciamos el DAO de Documentos 
                DocumentoDAO documentoDAO = new DocumentoDAO(nombre_repo);

                //Obtenemos el archivo transformado en un documento a traves de la ruta absoluta
                Documento documento = RecorrerDirectorio.obtenerArchivotoLocal(ruta_entera.toString());

                //Obtenemos el documento remoto correspondiente
                Documento documento_remoto = documentoDAO.getByRuta(ruta_entera.toString());

                if (cmd.hasOption("f")) {// Opcion forzar

                    if (documento_remoto != null) {// Si existe el documento remoto 

                        //Eliminamos el documento remoto
                        documentoDAO.delete(documento_remoto);
                    }

                    //Guardamos el documento remoto
                    documentoDAO.save(documento);
                } else {
                    if (documento_remoto != null) {// Si existe el documento remoto 

                        //Obtenemos las fechas de modificacion del archivo local y remoto
                        LocalDateTime f_mod_local = documento.getTimestamp();
                        LocalDateTime f_mod_remoto = documento_remoto.getTimestamp();

                        if (f_mod_local.isAfter(f_mod_remoto)) {// Comprovamos las fechas de modicicacion

                            //Borramos el archivo remoto
                            documentoDAO.delete(documento_remoto);

                            //Guardamos el archivo local en el repositorio
                            documentoDAO.save(documento);
                        } else {
                            return 2; //El archivo local es mas antiguo que el remoto
                        }

                    }

                }
            }

        } else {
            return 3; //Repositorio remoto no existe
        }

        return 1; // El push se realizo correctamente
    }

    /**
     * Push recursivo
     *
     * @param cmd Comando introducido por el usuario
     * @return : El push se ha completado 0: El archivo no existe 4: El
     * directorio no existe 3: El repostiorio remoto no existe
     */
    private int pushRecursivo(CommandLine cmd) {

        //Obtenemos la ruta del repositorio
        Path ruta_repositorio = Paths.get(cmd.getArgList().get(1));

        //Obtener nombre del repo
        String nombre_repo = Utils.rutaToNombre(ruta_repositorio.toString());

        if (Utils.existeRepo(nombre_repo)) {//Comprovar que el repo remoto existe

            //Creamos el dao de documento
            DocumentoDAO documentoDAO = new DocumentoDAO(nombre_repo);

            if (Files.exists(ruta_repositorio)) {// Comprovamos que existe el directorio local

                //Obtenemos el directorio local a partir de la ruta del repo remoto
                File dir_local = ruta_repositorio.toFile();

                //Obetnemos todos los directorios y documentos del directorio local
                ArrayList<Documento> docs_locales = new ArrayList<>();
                RecorrerDirectorio.listarArchivos(dir_local, docs_locales);

                if (cmd.hasOption("f")) {//opcion forzar

                    docs_locales.forEach(doc_local -> { //recorremos la lista de documentos

                        //Obtenemos el documento remoto
                        Documento doc_remoto = documentoDAO.getByRuta(doc_local.getRuta());

                        if (doc_remoto != null) {//Comrpovamos si existe el dococumento remoto

                            //Eliminamos el docuento remoto
                            documentoDAO.delete(doc_remoto);
                        }

                        //Subimos el documento local 
                        documentoDAO.save(doc_local);
                    });

                } else {// sin forzar

                    ArrayList<Documento> docs_no_subidos = new ArrayList<>();
                    for (Documento doc_local : docs_locales) { //recorremos la lista de documentos

                        //Obtenemos el documento remoto
                        Documento doc_remoto = documentoDAO.getByRuta(doc_local.getRuta());

                        if (doc_remoto != null) {//Comrpovamos si existe el dococumento remoto

                            //Obtenemos las fechas de modificacion del los documentos
                            LocalDateTime f_mod_local = doc_local.getTimestamp();
                            LocalDateTime f_mod_remoto = doc_remoto.getTimestamp();

                            if (f_mod_local.isAfter(f_mod_remoto)) {//Comprovamos las fechas

                                //Eliminamos el documento_remoto
                                documentoDAO.delete(doc_remoto);

                                //Subimos el documento local
                                documentoDAO.save(doc_local);
                            } else {
                                //Añadimos el documento a la lista de docs no actualizados
                                docs_no_subidos.add(doc_local);
                            }

                        } else {
                            //Guardar documento 
                            documentoDAO.save(doc_local);

                        }

                    }
                    //Imprimimos los docs no subidos
                    if (docs_no_subidos.size() > 0) {
                        PushPresentation.impNotPushDocs(docs_no_subidos);
                    }
                }

            } else {
                return 4;// El directorio local correspondiente no existe.
            }

        } else {
            return 3; // El repo remoto no existe
        }

        return 1;//Push completado
    }

}
