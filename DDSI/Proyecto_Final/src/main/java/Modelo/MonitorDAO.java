/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Alejandro
 */
public class MonitorDAO {

    private Session sesion = null;
    private Transaction t;
    private static int nCod = 0;

    public MonitorDAO(Session sesion) {
        this.sesion = sesion;
    }

    /**
     * realiza una consulta en la BD
     *
     * @return Devuelve los monitores ordenados por CodMonitor
     * @throws Exception
     */
    public ArrayList<Monitor> listaMonitores() throws Exception {
        t = sesion.beginTransaction();
        Query consulta = sesion.createQuery("SELECT m FROM Monitor m ORDER BY m.codMonitor", Monitor.class);
        //Query consulta = sesion.createNamedQuery("Monitor.findAll", Monitor.class);
        ArrayList<Monitor> monitores = (ArrayList<Monitor>) consulta.list();
        t.commit();

        if (!monitores.isEmpty()) {
            nCod = Integer.parseInt(monitores.get(monitores.size() - 1).getCodMonitor().split("M")[1]) + 1;
        } else {
            nCod = 0;
        }

        return monitores;
    }

    public static void incCod() {
        nCod++;
    }

    public static String getCodLibre() {
        return "M" + (100 > nCod ? "0" : (10 > nCod ? "00" : "")) + nCod;
    }

    /**
     * Inserta un nuevo monitor en la BD
     *
     * @param m
     * @throws Exception
     */
    public void NuevoMonitor(Monitor m) throws Exception {
        t = sesion.beginTransaction();
        sesion.save(m);
        t.commit();
    }

    /**
     * Actualiza los datos de un monitor almacenado en la BD
     *
     * @param m
     * @throws Exception
     */
    public void ActualizarMonitor(Monitor m) throws Exception {
        t = sesion.beginTransaction();
        sesion.save(m);
        t.commit();
    }

    /**
     * Elimina un monitor de la BD
     *
     * @param CodMonitor PK del monitor
     * @throws Exception
     */
    public void BajaMonitor(String CodMonitor) throws Exception {
        t = sesion.beginTransaction();
        Monitor m = sesion.get(Monitor.class, CodMonitor);
        sesion.delete(m);
        t.commit();
    }

    /**
     * realiza una consulta de un Monitor
     *
     * @param CodMonitor
     * @return devuelve el monitor
     */
    public Monitor getMonitor(String CodMonitor) {
        t = sesion.beginTransaction();
        Monitor m = sesion.get(Monitor.class, CodMonitor);
        t.commit();
        return m;
    }

    /**
     * Metodo para cerrar transaciones en caso de que se produzca una excepción
     */
    public void CloseTransaction() {
        t.commit();
    }

}
