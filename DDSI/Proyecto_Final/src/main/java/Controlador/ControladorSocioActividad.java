/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.NewSocioActividad;
import Vista.VistaMensaje;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.hibernate.Session;

/**
 *
 * @author Alejandro
 */
public class ControladorSocioActividad implements ActionListener {

    private ActividadDAO actividadDAO = null;
    private SocioDAO socioDAO = null;
    private String iDSocio = null;
    private NewSocioActividad vNewSocio = null;
    private ArrayList<String> nombreActividades = null;
    private int Operacion;

    public ControladorSocioActividad(Session sesion, String iDSocio, int Operacion) {
        this.actividadDAO = new ActividadDAO(sesion);
        this.socioDAO = new SocioDAO(sesion);

        this.iDSocio = iDSocio;
        this.Operacion = Operacion;

        this.vNewSocio = new NewSocioActividad();

        this.addListener();

        this.InicializarSocioActividad();
    }

    private void addListener() {
        vNewSocio.jButtonAceptar.addActionListener(this);
        vNewSocio.jButtonCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "AceptarOperacion":
                switch (Operacion) {
                    case 0:
                    try {
                        socioDAO.AddActividad(iDSocio, actividadDAO.getIdActividad(vNewSocio.NombreActividades.getSelectedItem().toString()));
                    } catch (Exception ex) {
                        VistaMensaje.Mensaje(vNewSocio, "error", ex.getMessage());
                    }
                    vNewSocio.dispose();
                    break;
                    case 1:

                    try {
                        socioDAO.RemoveActividad(iDSocio, actividadDAO.getIdActividad(vNewSocio.NombreActividades.getSelectedItem().toString()));
                    } catch (Exception ex) {
                        VistaMensaje.Mensaje(vNewSocio, "error", ex.getMessage());
                    }
                    vNewSocio.dispose();
                    break;

                    default:
                        throw new AssertionError();
                }
                break;

            case "CancelarOperacion":
                vNewSocio.dispose();
                break;
            default:
                System.out.println("Errorrrrrrr");
                throw new AssertionError();

        }
    }

    /**
     * Inicializa el Jdialog dependiendo de la operación que se va ha realizar
     */
    private void InicializarSocioActividad() {
        switch (Operacion) {
            case 0:
                this.AnadirSocioActividad();
                break;
            case 1:
                this.EliminarSocioActividad();
                break;
            default:
                throw new AssertionError();
        }
        vNewSocio.setVisible(true);

    }

    /**
     * Añade a la BD la actividad del socio seleccionado previamente en la BD
     */
    private void AnadirSocioActividad() {
        Socio s = socioDAO.getSocio(iDSocio);
        vNewSocio.jTextFieldNewActNombreSocio.setText(s.getNombre());
        try {
            ArrayList<String> NombreAct = socioDAO.listaActividadPorSocio(s.getNumeroSocio());
            for (String nombre : NombreAct) {
                vNewSocio.NombreActividades.addItem(nombre);
            }
        } catch (Exception ex) {
            VistaMensaje.Mensaje(vNewSocio, "error", ex.getMessage());
            System.out.println("Se produce en anadir socio");
        }
    }

    /**
     * Elimina de la actividad del socio seleccionado en la BD
     */
    private void EliminarSocioActividad() {
        Socio s = socioDAO.getSocio(iDSocio);
        vNewSocio.jTextFieldNewActNombreSocio.setText(s.getNombre());
        try {
            ArrayList<String> NombreAct = socioDAO.listaNoActividadPorSocio(s.getNumeroSocio());
            for (String nombre : NombreAct) {
                vNewSocio.NombreActividades.addItem(nombre);
            }
        } catch (Exception ex) {
            socioDAO.CloseTransaction();
            VistaMensaje.Mensaje(vNewSocio, "error", ex.getMessage());
        }
    }

}
