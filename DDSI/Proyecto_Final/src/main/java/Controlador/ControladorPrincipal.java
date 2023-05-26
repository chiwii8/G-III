/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import Modelo.TablaGeneral;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaPrincipal;
import Vista.VistaSocio;
import Vista.Vista_Cutoa_socio;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.hibernate.Session;

/**
 * Controlador central del que dependen el resto de controladores
 *
 * @author Alejandro
 */
public class ControladorPrincipal implements ActionListener {

    private Session sesion = null;
    private final Dimension d = new Dimension(1200, 500);
    private VistaPrincipal vPrincipal = null;
    private VistaMonitor vMonitor = null;
    private VistaSocio vSocio = null;

    private Vista_Cutoa_socio vCuota = null;    /////////////////////////

    private final JPanel PanelVacio = new JPanel();

    private MonitorDAO monitorDAO = null;
    private SocioDAO socioDAO = null;
    private ActividadDAO actividadDAO = null;

    private TablaGeneral mTabla = null;
    private TablaGeneral sTabla = null;
    private TablaGeneral cTabla = null;

    private ControladorMonitor cMonitor = null;
    private ControladorSocio cSocio = null;

    public ControladorPrincipal(Session sesion) {
        this.sesion = sesion;

        this.vMonitor = new VistaMonitor(d);
        this.vPrincipal = new VistaPrincipal(d);
        this.monitorDAO = new MonitorDAO(sesion);
        this.socioDAO = new SocioDAO(sesion);
        this.actividadDAO = new ActividadDAO(sesion);

        sTabla = new TablaGeneral();
        mTabla = new TablaGeneral();
        cTabla = new TablaGeneral();

        this.vSocio = new VistaSocio(d, sTabla);

        vPrincipal.setSize(1200, 500);
        vMonitor.setSize(1200, 500);
        vSocio.setSize(1200, 500);

        addListeners();

        vPrincipal.getContentPane().setLayout(new CardLayout());
        vPrincipal.add(PanelVacio);
        vPrincipal.add(vMonitor);
        vPrincipal.add(vSocio);

        mTabla.InicializarTablaMonitores(vMonitor);
        sTabla.InicializarTablaSocios(vSocio);
        this.cMonitor = new ControladorMonitor(vPrincipal, vMonitor, mTabla, monitorDAO);
        this.cSocio = new ControladorSocio(vPrincipal, vSocio, sTabla, socioDAO);

        vPrincipal.setLocationRelativeTo(null);
        vPrincipal.setVisible(true);

        OcultarPanel();
    }

    private void addListeners() {
        vPrincipal.GestionMonitor.addActionListener(this);
        vPrincipal.GestionSocio.addActionListener(this);
        vPrincipal.SociosPorActividad.addActionListener(this);
        vPrincipal.SalirApliciación.addActionListener(this);
        vPrincipal.jMenuItemCuota.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "SalirAplicacion":
                sesion.close();
                vPrincipal.dispose();
                System.exit(0);
                break;

            case "GestionMonitores":
                InicializarMonitores();
                MostrarPanel(vMonitor);
                break;

            case "GestionSocios":
                InicializarSocio();
                MostrarPanel(vSocio);
                break;
            case "SociosporActividad":
                new ControladorActividad(new ActividadDAO(sesion));
                break;

            case "CuotadeSocio":
                vCuota = new Vista_Cutoa_socio();

                cTabla.InicializarTablaCuota(vCuota);
                cTabla.DibujaTablaCuotaSocios(vCuota);
                addActionListenerCuota(vCuota);

                vCuota.setVisible(true);
                break;

            case "VerCuotas":
                this.ModificarVistaCuota(vCuota.jTextFieldNombreSocioCuota.getText());
                break;
        }
    }

    /**
     * Realizado durante el examen
     * Activa el boton del JDialog
     *
     * @param VCuota
     */
    private void addActionListenerCuota(Vista_Cutoa_socio VCuota) {
        VCuota.jButton1.addActionListener(this);
    }

    /**
     * Apartado de Examen 
     * Modifica los parámetros del Jdialog vCuota para que se adecuen a la búsqueda realizada
     * @param id valor recogido del JTextField
     */
    private void ModificarVistaCuota(String id) {

        try {
            Socio socio = socioDAO.getSocio(id);
            ArrayList<Actividad> Actividades = socioDAO.listaActividadesSocio(id);
            int suma = 0, contador = 0;
            double descuento = 0;
            for (Actividad A : Actividades) {
                Object[] obj = {A.getNombre(), A.getPrecioBaseMes()};
                cTabla.InsertarTabla(obj);
                suma += A.getPrecioBaseMes();
                contador++;
            }
            char n = socio.getCategoria();

            vCuota.jLabelNActividades.setText(String.valueOf(contador));
            vCuota.jLabelCuotaMensual.setText(String.valueOf(suma) + "€");
            vCuota.jLabelCategoria.setText(socio.getCategoria().toString());
            switch (n) {
                case 'B':
                    descuento = 0.1;
                    break;
                case 'C':
                    descuento = 0.2;
                    break;
                case 'D':
                    descuento = 0.3;
                    break;
                case 'E':
                    descuento = 0.4;
                    break;
            }

            vCuota.jLabelTotal.setText(String.valueOf(suma - descuento * suma) + "€");

        } catch (Exception ex) {
            System.out.println("el numero de socio no es Válido");
        }

    }

    /**
     * Pone un panel vacío
     */
    private void OcultarPanel() {
        PanelVacio.setVisible(true);
    }

    /**
     * Muestra el panel pasado por parámetro
     *
     * @param jp Panel seleccionado para mostrar
     */
    private void MostrarPanel(JPanel jp) {
        vMonitor.setVisible(false);
        vSocio.setVisible(false);
        PanelVacio.setVisible(false);
        jp.setVisible(true);
    }

    /**
     * Metodo que solicita y recoge los socios de la base de datos, En caso de
     * error solo recoje Exception
     */
    private void pideSocio() {
        try {
            ArrayList<Socio> lSocio = socioDAO.listaSocios();
            sTabla.vaciarTabla();
            sTabla.RellenaTablaSocio(lSocio);
        } catch (Exception ex) {
            socioDAO.CloseTransaction();
            VistaMensaje.Mensaje("error", "Se ha producido un error al cargar los datos de los Socios");
        }
    }

    /**
     * Metodo que solicita y recoge los Monitores de la base de datos en el
     * Jtable, en caso de error solo recoje Exception
     */
    private void pideMonitores() {
        try {
            ArrayList<Monitor> lMonitores = monitorDAO.listaMonitores();
            mTabla.vaciarTabla();
            mTabla.RellenaTablaMonitores(lMonitores);
        } catch (Exception ex) {
            monitorDAO.CloseTransaction();
            VistaMensaje.Mensaje("error", "Se ha producido un error al cargar los datos de los Monitores");
        }
    }

    /**
     * Inicializa los Jtable con los modelos de tabla y realiza una llamada a
     * pideMonitores.
     */
    private void InicializarMonitores() {
        mTabla.DibujaTablaMonitores(vMonitor);
        try {
            pideMonitores();
        } catch (Exception ex) {
            VistaMensaje.Mensaje("error", "Error en la peticion\n" + ex.getMessage());
        }

    }

    /**
     * Inicializa los Jtable con los modelos de tabla y realiza una llamada a
     * pideSocio.
     */
    private void InicializarSocio() {
        sTabla.DibujaTablaSocio(vSocio);
        try {
            pideSocio();
        } catch (Exception ex) {
            VistaMensaje.Mensaje("error", "Error en la peticion\n" + ex.getMessage());
        }

    }

}
