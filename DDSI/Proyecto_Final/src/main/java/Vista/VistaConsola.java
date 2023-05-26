/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 *
 * @author Alejandro
 */
public class VistaConsola {
    public void mensajeConsola(String texto){
        System.out.println(texto);
    }
    
    public void mensajeConsola(String texto,String error){
        System.out.println(texto);
        System.out.println(error);
    }
    
    public void MensajeMetadatos(DatabaseMetaData dbmd) throws SQLException{
        StringBuilder s = new StringBuilder("Nombre de la base de datos: ");
        s.append(dbmd.getDatabaseProductName()).append("\n").append("Versión de la base de datos: ").append(dbmd.getDatabaseProductVersion()).append("\n")
        .append("URL: ").append(dbmd.getURL()).append("\n").append("Nombre del driver: ").append(dbmd.getDriverName()).append("\n")
        .append("Version del driver: ").append(dbmd.getDriverVersion()).append("\n").append("Nombre del usuario: ").append(dbmd.getUserName()).append("\n");
        System.out.println(s.toString());
    }
}
