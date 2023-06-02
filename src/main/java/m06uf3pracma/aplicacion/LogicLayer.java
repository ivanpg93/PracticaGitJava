/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.aplicacion;

import m06uf3pracma.datos.DataLayer;
import m06uf3pracma.presentacion.PresentationLayer;

/**
 *
 * Clase Padre de la capa lógica
 */
public abstract class LogicLayer{
    //Capa de Presentación
    protected PresentationLayer presentation;
    //Capa de datos
    protected DataLayer data;

    /**
     * Constructor que inicializa la capa de datos y la capa de presentación
     * @param presentation Capa de presentación
     * @param data Capa de datos
     */
    public LogicLayer(PresentationLayer presentation, DataLayer data) {
        this.presentation = presentation;
        this.data = data;
    }
    
    /**
     * Función utilizada para procesar los datos
     */
    public abstract void processData();
    
}
