/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Modelo.TablaGeneral;
import Vista.VistaMensaje;
import Vista.VistaSocioActividad;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class ControladorActividad implements ActionListener {

    private TablaGeneral aTabla = null;
    private VistaSocioActividad vAct = null;
    private ActividadDAO actividadDAO = null;

    public ControladorActividad(ActividadDAO actividadDAO) {
        this.actividadDAO = actividadDAO;
        aTabla = new TablaGeneral();
        vAct = new VistaSocioActividad();
        vAct.setLocationRelativeTo(null);
        vAct.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        this.addListeners();

        aTabla.InicializarTablaActividad(vAct);
        inicializarcombobox();
        vAct.setVisible(true);

    }

    private void addListeners() {
        vAct.jComboBoxActividades.addActionListener(this);
        vAct.jButtonSalirJDialog.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "CambiaActividad":
                this.RellenarTablaSocios();
                break;
            case "SalirActividad":
                vAct.setVisible(false);
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Inicializa el Combobox con el nombre de las actividades que se han
     * recogido de la base de datos
     */
    private void inicializarcombobox() {
        aTabla.DibujaTablaActividadPorSocio(vAct);
        try {
            ArrayList<String> nombreAct = actividadDAO.listaNombreActividad();
            for (String item : nombreAct) {
                vAct.jComboBoxActividades.addItem(item);
            }
            //vAct.jComboBoxActividades.setSelectedIndex(0);  ///por defecto el primer elemento 
        } catch (Exception ex) {
            System.out.println("Salta Error:" + ex.getMessage());
        }
    }

    /**
     * Rellena la tabla con los nombres y los pedidos de la actividad que esta
     * seleccionada en el Combobox
     */
    private void RellenarTablaSocios() {
        aTabla.vaciarTabla();
        try {

            aTabla.RellenaTablaSociosporActividad(actividadDAO.SociosPorActividad(actividadDAO.getIdActividad(vAct.jComboBoxActividades.getSelectedItem().toString())));
        } catch (Exception ex) {
            VistaMensaje.Mensaje("error", ex.getMessage());
        }
    }

}
