/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Alejandro
 */
public class SocioDAO {

    private Session sesion = null;
    private Transaction t;
    private static int nCod = 0;

    public SocioDAO(Session sesion) {
        this.sesion = sesion;
    }

    public Session getSesion() {
        return sesion;
    }

    /**
     * realiza una consulta en la BD
     *
     * @return Devuelve los socios ordenados por numeroSocio
     * @throws Exception
     */
    public ArrayList<Socio> listaSocios() throws Exception {
        t = sesion.beginTransaction();
        Query consulta = sesion.createQuery("SELECT s FROM Socio s ORDER BY s.numeroSocio", Socio.class);
        ArrayList<Socio> Socios = (ArrayList<Socio>) consulta.list();
        t.commit();

        if (!Socios.isEmpty()) {
            nCod = Integer.parseInt(Socios.get(Socios.size() - 1).getNumeroSocio().split("S")[1]) + 1;
        } else {
            nCod = 0;
        }

        return Socios;

    }

    /**
     * realiza un procedimiento para obtener los nombres de la actividades que
     * realiza el socio
     *
     * @param id Primary key del socio
     * @return devuelve el nombre de las actividades
     * @throws Exception
     */
    public ArrayList<String> listaNoActividadPorSocio(String id) throws Exception {
        t = sesion.beginTransaction();
        Query consulta = sesion.createNamedQuery("Actividad.findAll", Actividad.class);
        ArrayList<Actividad> Actividades = (ArrayList<Actividad>) consulta.list();
        Socio s = sesion.get(Socio.class, id);
        t.commit();

        ArrayList<String> nombreAct = new ArrayList<>();
        for (Actividad A : Actividades) {
            if (s.getActividades().contains(A)) {
                nombreAct.add(A.getNombre());
            }
        }

        return nombreAct;
    }

    /**
     * realiza una consulta para obtener los nombres de la actividades que no
     * realiza el socio
     *
     * @param id PK del socio
     * @return devuelve los nombres de las actividades
     * @throws Exception
     */
    public ArrayList<String> listaActividadPorSocio(String id) throws Exception {
        t = sesion.beginTransaction();
        Query consulta = sesion.createNamedQuery("Actividad.findAll", Actividad.class);
        ArrayList<Actividad> Actividades = (ArrayList<Actividad>) consulta.list();
        Socio s = sesion.get(Socio.class, id);
        t.commit();

        ArrayList<String> nombreAct = new ArrayList<>();
        for (Actividad A : Actividades) {
            if (!s.getActividades().contains(A)) {
                nombreAct.add(A.getNombre());
            }
        }

        return nombreAct;
    }
    
    /**
     * Parte realizada en el examen ////////////////////////////
     * Devuelve las actividades del numero de socio pasada
     * @param id numero de socio
     * @return array de las actividades en las que participa
     * @throws Exception 
     */
    public ArrayList<Actividad> listaActividadesSocio(String id) throws Exception{
        t = sesion.beginTransaction();
        Query consulta = sesion.createNamedQuery("Actividad.findAll", Actividad.class);
        ArrayList<Actividad> Actividades = (ArrayList<Actividad>) consulta.list();
        Socio s = sesion.get(Socio.class, id);
        t.commit();

        ArrayList<Actividad> Dev = new ArrayList<>();
        for (Actividad A : Actividades) {
            if (s.getActividades().contains(A)) {
                Dev.add(A);
            }
        }

        return Dev;
    }

    public static void incCod() {
        nCod++;
    }

    public static String getCodLibre() {
        return "S" + (100 > nCod ? "0" : (10 > nCod ? "00" : "")) + nCod;
    }

    /**
     * Inserta un nuevo socio en la BD
     *
     * @param s
     * @throws Exception
     */
    public void NuevoSocio(Socio s) throws Exception {
        t = sesion.beginTransaction();
        sesion.save(s);
        t.commit();
    }

    /**
     * Actualiza un socio de la BD
     *
     * @param s
     * @throws Exception
     */
    public void ActualizarSocio(Socio s) throws Exception {
        t = sesion.beginTransaction();
        sesion.save(s);
        t.commit();
    }

    /**
     * Elimina un socio de la BD
     *
     * @param nSocio
     */
    public void BajaSocio(String nSocio) {
        t = sesion.beginTransaction();
        Socio socio = sesion.get(Socio.class, nSocio);
        sesion.delete(socio);
        t.commit();
    }

    /**
     * Devuelve un socio de la BD
     *
     * @param nSocio
     * @return
     */
    public Socio getSocio(String nSocio) {
        t = sesion.beginTransaction();
        Socio s = sesion.get(Socio.class, nSocio);
        t.commit();
        return s;
    }

    /**
     * Añade una nueva actividad a un socio en la BD
     *
     * @param nSocio
     * @param idAct
     * @throws Exception
     */
    public void AddActividad(String nSocio, String idAct) throws Exception {
        t = sesion.beginTransaction();
        Socio s = sesion.get(Socio.class, nSocio);
        Actividad a = sesion.get(Actividad.class, idAct);

        a.addSocio(s);
        sesion.save(a);

        t.commit();
    }

    /**
     * Elimina a un socio de una actividad en la BD
     *
     * @param nSocio
     * @param idAct
     */
    public void RemoveActividad(String nSocio, String idAct) {
        t = sesion.beginTransaction();
        Socio s = sesion.get(Socio.class, nSocio);
        Actividad a = sesion.get(Actividad.class, idAct);

        a.eliminaSocio(s);
        sesion.save(a);

        t.commit();
    }

    /**
     * Metodo para cerrar transaciones en caso de que se produzca una excepción
     */
    public void CloseTransaction() {
        t.commit();
    }
}
