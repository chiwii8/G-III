/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Alejandro
 */
public class ActividadDAO {

    private Transaction t;
    private Session sesion = null;
    private static int Servidor = 0; //0 Oracle  // 1 MariaDB

    public ActividadDAO(Session sesion) {
        this.sesion = sesion;
    }

    public static void setServidor(int Servidor_O_DB) {
        Servidor = Servidor_O_DB;
    }

    public static int getServidor() {
        return Servidor;
    }

    /**
     * metodo que recoge los nombres de las actividades que no tiene el socio
     *
     * @param id primary key del socio
     * @return
     * @throws Exception en caso de genererse una excepción en la Query o una
     * búsqueda con datos erróneos
     */
    public ArrayList<String> listaNombreActividadPorSocio(String id) throws Exception {
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
     * Recoge los nombres de todas las actividades
     *
     * @return
     * @throws Exception
     */
    public ArrayList<String> listaNombreActividad() throws Exception {
        t = sesion.beginTransaction();
        Query consulta = sesion.createQuery("SELECT a.nombre FROM Actividad a");
        ArrayList<String> nombreAct = (ArrayList<String>) consulta.list();
        t.commit();

        return nombreAct;
    }

    /**
     * Recoge el Id de la Actividad a través de la clave única nombre
     *
     * @param Nombre
     * @return
     */
    public String getIdActividad(String Nombre) {
        t = sesion.beginTransaction();
        Query consulta = sesion.createQuery("SELECT a.idActividad FROM Actividad a WHERE a.nombre= :Nombre");
        consulta.setParameter("Nombre", Nombre);
        ArrayList<String> IdAct = (ArrayList<String>) consulta.list();
        t.commit();
        System.out.println("Se ha obtenido" + IdAct.get(0));
        return IdAct.get(0);
    }

    /**
     * realiza un procedimiento para obtener el nombre y correo de los socios
     * que están apuntados a la actividad
     *
     * @param IdActividad Primary key de la actividad
     * @return
     * @throws Exception
     */
    public ArrayList<Socio> SociosPorActividad(String IdActividad) throws Exception {

        t = sesion.beginTransaction();
        StoredProcedureQuery llamada = null;
        ArrayList<Object[]> resultado = null;

        if (Servidor == 0) {
            llamada = sesion.createStoredProcedureCall("pActividadesSocios")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
                    .setParameter(1, IdActividad);
            llamada.execute();
            resultado = (ArrayList<Object[]>) llamada.getResultList();
        } else if (Servidor == 1) {
            llamada = sesion.createStoredProcedureCall("pActividadesSocios")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .setParameter(1, IdActividad);
            llamada.execute();
            resultado = (ArrayList<Object[]>) llamada.getResultList();
        }

        t.commit();

        ArrayList<Socio> lSocios = new ArrayList<>();
        for (Object[] o : resultado) {
            lSocios.add(new Socio(o[0].toString(), o[1].toString()));
        }

        return lSocios;
    }

    /**
     * Cierra la transacion en caso de que haya surgido una excepción
     */
    public void CloseTransaction() {
        t.commit();
    }
}
