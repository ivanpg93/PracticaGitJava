/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import m06uf3pracma.Utils.RecorrerDirectorio;
import m06uf3pracma.Utils.Utils;
import m06uf3pracma.dao.DocumentoDAO;
import m06uf3pracma.dao.RepositorioDAO;
import m06uf3pracma.datos.DataLayer;
import m06uf3pracma.model.Documento;
import m06uf3pracma.model.Repositorio;
import m06uf3pracma.presentacion.ComparePresentation;
import org.apache.commons.cli.CommandLine;

/**
 * Esta clase representa la lógica de la funcionalidad de comparación. Extiende
 * la clase LogicLayer para heredar los métodos y atributos necesarios.
 *
 * @author Alex
 */
public class CompareLogic extends LogicLayer {

    /**
     * Objeto de la capa de presentación que representa la vista de comparación.
     */
    ComparePresentation presentacion;

    /**
     *
     * Constructor de la clase que inicializa la capa de presentación y la capa
     * de datos.
     *
     * @param presentation objeto de la capa de presentación que representa la
     * vista de comparación
     *
     * @param data objeto de la capa de datos que representa el modelo de datos
     */
    public CompareLogic(ComparePresentation presentation, DataLayer data) {
        super(presentation, data);

        presentacion = (ComparePresentation) presentation;
    }

    /**
     *
     * Procesa los datos necesarios para realizar la comparación entre los
     * documentos del sistema y los almacenados en la base de datos.
     *
     * Obtiene los argumentos de la línea de comandos, verifica su validez y
     * realiza la comparación.
     */
    @Override
    public void processData() {
        RepositorioDAO repoDAO;
        DocumentoDAO docDAO;

        try {
            CommandLine cmd = (CommandLine) data.getData();
            List<String> argList = cmd.getArgList();
            argList.remove(0);
            if (cmd.hasOption("h")) {
                presentacion.printHelp();
            }

            if (argList.size() > 2 || argList.size() <= 0) {
                presentacion.argumentosIncorrectos();
            }

            boolean detail = false;
            if (cmd.hasOption("d")) {
                detail = true;
            }

            String dir_base = argList.get(0);

            if (!Files.exists(Paths.get(dir_base))) {
                presentacion.rutaNoExiste();
            }

            String nombreRepositorio = Utils.rutaToNombre(dir_base);

            repoDAO = new RepositorioDAO(nombreRepositorio);
            docDAO = new DocumentoDAO(nombreRepositorio);

            //Comprobar que el repositorio existe
            /*Repositorio repo = repoDAO.getByNom(nombreRepositorio);

            if (repo == null) {
                presentacion.repositorioNoExiste();
            }*/

            if(!Utils.existeRepo(nombreRepositorio)) presentacion.repositorioNoExiste();
            
            String fichero = null;
            if (argList.size() == 2) {
                fichero = argList.get(1);
            }

            List<Documento> documentosSistema = new ArrayList<>();
            List<Documento> documentosDB = docDAO.getAll();

            if (fichero == null) {
                RecorrerDirectorio.listarArchivos(new File(dir_base), documentosSistema);
                comparar(documentosSistema, documentosDB, detail);
            } else {
                Path pathBase = Paths.get(dir_base);
                Path pathRelativo = Paths.get(fichero);

                Path pathResuelto = pathBase.resolve(pathRelativo).normalize();

                if (!Files.exists(pathResuelto)) {
                    presentacion.ficheroNoExiste();
                }

                comparar(RecorrerDirectorio.obtenerArchivotoLocal(pathResuelto.toString()), documentosDB, detail);
            }

        } catch (Exception ex) {
            presentacion.error(ex);
        }

    }

    /**
     *
     * Compara los documentos del sistema local con los de la base de datos del
     * repositorio remoto, y muestra en la presentación la información sobre las
     * diferencias encontradas.
     *
     * @param documentosSistema Lista de Documento que representa los documentos
     * del sistema local.
     * @param documentosDB Lista de Documento que representa los documentos de
     * la base de datos del repositorio remoto.
     * @param detail Booleano que indica si se deben mostrar detalles de las
     * diferencias en caso de encontrarlas.
     * @throws IOException Si ocurre un error al leer los documentos o al
     * comparar línea a línea en caso de encontrar diferencias.
     */
    private void comparar(List<Documento> documentosSistema, List<Documento> documentosDB, boolean detail) throws IOException {
        Map<String, Documento> mapaSistema = new HashMap<>();
        documentosSistema.forEach(documento -> mapaSistema.put(documento.getRuta(), documento));

        for (Documento documentoDB : documentosDB) {
            Documento documentoSistema = mapaSistema.get(documentoDB.getRuta());
            if (documentoSistema == null) {
                presentacion.ficheroNoExisteRepositorioLocal(documentoDB);
            } else if (!documentoDB.getHash().equals(documentoSistema.getHash())) {
                presentacion.ficheroDiferenteContenido(documentoDB);
                if (detail) {
                    compararLineaALinea(documentoDB.bytesToLineas(), documentoSistema.bytesToLineas());
                }
            } else if (!documentoDB.getTimestamp().equals(documentoSistema.getTimestamp())) {
                presentacion.ficheroDiferenteTimestamp(documentoDB, documentoSistema);
                if (detail) {
                    compararLineaALinea(documentoDB.bytesToLineas(), documentoSistema.bytesToLineas());
                }
            } else {
                presentacion.ficheroSinDiferencias(documentoDB);
            }
            documentosSistema.remove(documentoSistema);
        }
        for (Documento documentoSistema : documentosSistema) {
            presentacion.ficheroNoExisteRepositorioRemoto(documentoSistema);
        }
    }

    /**
     *
     * Compara un documento del sistema con una lista de documentos de la base
     * de datos remota y muestra los resultados de la comparación en la
     * presentación.
     *
     * @param documentoSistema el documento del sistema a comparar
     * @param documentosDB la lista de documentos de la base de datos remota
     * @param detail un booleano que indica si se debe mostrar el detalle de la
     * comparación línea a línea
     * @throws IOException si ocurre un error al comparar las líneas del
     * documento
     */
    private void comparar(Documento documentoSistema, List<Documento> documentosDB, boolean detail) throws IOException {
        boolean existeEnRemoto = false;
        for (Documento documentoDB : documentosDB) {
            if (documentoDB.getRuta().equals(documentoSistema.getRuta())) {
                existeEnRemoto = true;
                if (!documentoDB.getHash().equals(documentoSistema.getHash())) {
                    presentacion.ficheroDiferenteContenido(documentoDB);
                    if (detail) {
                        compararLineaALinea(documentoDB.bytesToLineas(), documentoSistema.bytesToLineas());
                    }
                } else if (!documentoDB.getTimestamp().equals(documentoSistema.getTimestamp())) {
                    presentacion.ficheroDiferenteTimestamp(documentoDB, documentoSistema);
                    if (detail) {
                        compararLineaALinea(documentoDB.bytesToLineas(), documentoSistema.bytesToLineas());
                    }
                } else {
                    presentacion.ficheroSinDiferencias(documentoDB);
                }
            }
        }
        if (!existeEnRemoto) {
            presentacion.ficheroNoExisteRepositorioRemoto(documentoSistema);
        }
    }

    /**
     *
     * Compara dos HashMaps que representan el contenido de un documento en la
     * base de datos y en el sistema local línea por línea. Si una línea se
     * encuentra en el sistema local pero no en la base de datos, se muestra
     * como nueva. Si una línea se encuentra en la base de datos pero no en el
     * sistema local, se muestra como eliminada. Si una línea se encuentra en
     * ambas, se compara su contenido y se muestra como modificada si es
     * diferente.
     *
     * @param lineasDocumentoDB Un HashMap que representa el contenido del
     * documento en la base de datos.
     * @param lineasDocumentoSistema Un HashMap que representa el contenido del
     * documento en el sistema local.
     * @return void.
     */
    private void compararLineaALinea(HashMap<Integer, String> lineasDocumentoDB, HashMap<Integer, String> lineasDocumentoSistema) {

        //IDEA: Borrar de la lista una vez se repite.
        //Lo de abajo está mal creo
        //Comparar las lineas
        //Primero fichero remoto --> 
        //  Para cada linea, buscar esa linea en el fichero remoto. Si no se encuentra, mostrar la linea como modificada o eliminada y añadir el número de linea.
        // Comparar archivo local con archivo remoto
        System.out.println("\n\tDETALLES:");
        System.out.println("\t#-------------------------------------------------------------------------------------#");

        List<String> mensajes = new ArrayList<>();

        HashMap<Integer, String> lineasDocumentoDBTemp = new HashMap<>();
        HashMap<Integer, String> lineasDocumentoSistemaTemp = new HashMap<>();

        lineasDocumentoDBTemp.putAll(lineasDocumentoDB);
        lineasDocumentoSistemaTemp.putAll(lineasDocumentoSistema);

        //comparacion local a remoto
        for (Map.Entry<Integer, String> entrada : lineasDocumentoSistema.entrySet()) {
            Integer numeroLinea = entrada.getKey();
            String linea = entrada.getValue();

            if (numeroLinea == 16) {
                var a = 1;
            }
            if (!lineasDocumentoDBTemp.containsValue(linea)) {
                if (!lineasDocumentoDBTemp.containsKey(numeroLinea)) {
                    if (lineasDocumentoDB.containsKey(numeroLinea)) {
                        String lineaModificada = "\tLa Linea " + numeroLinea + " ha sido modificada\n" + "\t\tContenido Local: " + lineasDocumentoSistema.get(numeroLinea) + "\n\t\tContenido Remoto: " + lineasDocumentoDB.get(numeroLinea);
                        if (!mensajes.contains(lineaModificada)) {
                            mensajes.add(lineaModificada);
                        }
                    } else {
                        String nuevaLinea = "\tNueva Linea Local: " + numeroLinea + "\n\t\tContenido Local: " + lineasDocumentoSistema.get(numeroLinea);
                        if (!mensajes.contains(nuevaLinea)) {
                            mensajes.add(nuevaLinea);
                        }
                    }

                } else {
                    lineasDocumentoDBTemp.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().equals(linea))
                            .findFirst()
                            .ifPresent(entry -> lineasDocumentoDBTemp.remove(entry.getKey()));
                    String lineaModificada = "\tLa Linea " + numeroLinea + " ha sido modificada\n" + "\t\tContenido Local: " + lineasDocumentoSistema.get(numeroLinea) + "\n\t\tContenido Remoto: " + lineasDocumentoDB.get(numeroLinea);
                    if (!mensajes.contains(lineaModificada)) {
                        mensajes.add(lineaModificada);
                    }
                }
            } else {
                lineasDocumentoDBTemp.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().equals(linea))
                        .findFirst()
                        .ifPresent(entry -> lineasDocumentoDBTemp.remove(entry.getKey()));
            }
        }

        for (Map.Entry<Integer, String> entrada : lineasDocumentoDB.entrySet()) {
            Integer numeroLinea = entrada.getKey();
            String linea = entrada.getValue();

            if (!lineasDocumentoSistemaTemp.containsValue(linea)) {
                if (!lineasDocumentoSistemaTemp.containsKey(numeroLinea)) {
                    String lineaEliminada = "\tLa Linea: " + numeroLinea + " ha sido eliminada localmente.\n" + "\t\tContenido Remoto: " + lineasDocumentoDB.get(numeroLinea);
                    if (!mensajes.contains(lineaEliminada)) {
                        mensajes.add(lineaEliminada);
                    }
                } else {
                    lineasDocumentoSistemaTemp.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().equals(linea))
                            .findFirst()
                            .ifPresent(entry -> lineasDocumentoSistemaTemp.remove(entry.getKey()));
                    String lineaModificada = "\tLa Linea " + numeroLinea + " ha sido modificada\n" + "\t\tContenido Local: " + lineasDocumentoSistema.get(numeroLinea) + "\n\t\tContenido Remoto: " + lineasDocumentoDB.get(numeroLinea);
                    if (!mensajes.contains(lineaModificada)) {
                        mensajes.add(lineaModificada);
                    }
                }
            }

        }
        if (mensajes.isEmpty()) {
            System.out.println("\n\tNo se han encontrado cambios.");
        } else {
            mensajes.forEach(mensaje -> System.err.println("\n" + mensaje));
        }
        System.out.println("\n\t#-------------------------------------------------------------------------------------#\n");
    }

}
