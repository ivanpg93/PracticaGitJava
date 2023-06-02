/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m06uf3pracma.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ivan
 */
public class Timestamp {
    
    /**
     * Pasar un String a LocalDateTime en formato Europeo
     * @param date
     * @return 
     */
    public static LocalDateTime dateFormatEU(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        return dateTime;
    }
    
    /**
     * Pasar un LocalDateTime a String con formato Europeo
     * @param date
     * @return 
     */
    public static String dateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = date.format(formatter);
        
        return formattedDateTime;
    }

    /**
     * Obtenemos una fecha en formato europeo a apartir de un timestamp
     * @param tmp
     * @return
     */
    public static LocalDateTime tmpToLDT(long tmp) {
        // Transformamos el timestamp en LocalDateTime
        Instant instant = Instant.ofEpochMilli(tmp);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        // Formateamos la fecha en formato europeo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime, formatter);

        return dateTime;
    }

    /**
     * Compara dos fechas para saber cuál es más anterior
     * @param d1
     * @param d2
     * @return result result < 0 d1 es anterior
     * result > 0 d2 es anterior result = 0 d1 y d2 son iguales
     */
    public static int compareDate(LocalDateTime d1, LocalDateTime d2) {
        int result = d1.compareTo(d2);
        return result;
    }
}
