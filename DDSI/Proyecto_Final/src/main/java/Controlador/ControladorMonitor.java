/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.TablaGeneral;
import Vista.VistaDialogos;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Alejandro
 */
public class ControladorMonitor implements ActionListener {

    private MonitorDAO monitorDAO = null;
    private VistaMonitor vMonitor = null;
    private VistaDialogos vDialogo = null;
    private VistaPrincipal vPrincipal = null;
    private TablaGeneral mTabla = null;
    private Monitor MonitorAct;
    private int Operacion = 0;

    public ControladorMonitor(VistaPrincipal vPrincipal, VistaMonitor vMonitor, TablaGeneral mtabla, MonitorDAO monitorDAO) {
        this.monitorDAO = monitorDAO;
        this.vPrincipal = vPrincipal;
        this.vMonitor = vMonitor;
        this.mTabla = mtabla;
        addListeners();

    }

    private void addListeners() {
        vMonitor.jButtonNuevoMonitor.addActionListener(this);
        vMonitor.jButtonBajaMonitor.addActionListener(this);
        vMonitor.jButtonActualizarMonitor.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos;
        switch (e.getActionCommand()) {
            case "NuevoMonitor":
                Operacion = 0;
                vDialogo = new VistaDialogos(0).NuevoMonitor();
                Activardialogos(vDialogo);
                break;

            case "ActualizarMonitor":
                Operacion = 1;
                pos = vMonitor.jTableMonitores.getSelectedRow();
                if (pos != -1) {
                    //mirar
                    vDialogo = new VistaDialogos(0).ActualizarMonitor(getMonitor(pos));
                    Activardialogos(vDialogo);
                }
                break;

            case "BajaMonitor":
                pos = vMonitor.jTableMonitores.getSelectedRow();
                if (pos != -1) {
                    this.BajaMonitor(pos);
                }
                break;
            case "AceptarOperacion":
                switch (Operacion) {
                    case 0:
                        if (this.NuevoMonitor(DatosMonitor())) {
                            vDialogo.dispose();
                        }
                        break;
                    case 1:
                        if (this.ActualizarMonitor(DatosMonitorBD(), vMonitor.jTableMonitores.getSelectedRow())) {
                            vDialogo.dispose();
                        }
                        break;

                }
                break;

            case "CancelarOperacion":
                vDialogo.dispose();
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Activa los actionComand de los JDialog que se crean para cada una de las
     * llamacas
     *
     * @param dialogo nombre del Jdialog a Habilitar
     */
    private void Activardialogos(VistaDialogos dialogo) {
        dialogo.jButtonAceptar.addActionListener(this);
        dialogo.jButtonCancelar.addActionListener(this);
        dialogo.setVisible(true);
    }

    /**
     * Recoge los datos de la Jtable en la posicion seleccionada en un nuevo
     * monitor
     *
     * @param pos posicion en la Jtable
     * @return devuelve un monitor
     */
    private Monitor getMonitor(int pos) {
        return new Monitor(String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 0)), String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 1)),
                String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 2)), String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 3)),
                String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 4)), String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 5)),
                String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 6)));
    }

    /**
     * metodo que realiza una llamada a la BD para obtener el monitor de la BD y
     * modificar sus atributos con los parámetros insertados en el JDialog
     *
     * @return devuelve los Datos modificados de un monitor
     */
    private Monitor DatosMonitorBD() {
        Monitor m = monitorDAO.getMonitor(String.valueOf(vMonitor.jTableMonitores.getValueAt(vMonitor.jTableMonitores.getSelectedRow(), 0)));
        m.setNombre(vDialogo.jTextFieldNombre.getText());
        m.setDni(vDialogo.jTextFieldDni.getText());
        m.setTelefono(vDialogo.jTextFieldTelefono.getText());
        m.setCorreo(vDialogo.jTextFieldCorreo.getText());
        String Fecha = null;
        if (vDialogo.jFechaEntrada.getDate() != null) {
            String Aux[] = vDialogo.jFechaEntrada.getDate().toString().toLowerCase().split(" ");
            if (Aux.length == 1) {
                Fecha = new Date(Integer.parseInt(Aux[2]) - 1900, Integer.parseInt(Aux[1]), Integer.parseInt(Aux[0])).toString();
            } else {
                Fecha = ConvertirFecha(Aux);
            }
        }
        m.setFechaEntrada(Fecha);
        m.setNick(vDialogo.jTextFieldNick_Categoria.getText());

        return m;
    }

    /**
     * Crea un monitor con los datos anadidos en el JDialog
     *
     * @return Devuelve un nuevo monitor
     */
    private Monitor DatosMonitor() {
        String cod = vDialogo.jTextFieldCodigo.getText();
        String Nombre = vDialogo.jTextFieldNombre.getText();
        String Dni = vDialogo.jTextFieldDni.getText();
        String telefono = vDialogo.jTextFieldTelefono.getText();
        String Correo = vDialogo.jTextFieldCorreo.getText();

        String Fecha = null;
        if (vDialogo.jFechaEntrada.getDate() != null) {
            String Aux[] = vDialogo.jFechaEntrada.getDate().toString().toLowerCase().split(" ");
            if (Aux.length == 1) {
                Fecha = new Date(Integer.parseInt(Aux[2]) - 1900, Integer.parseInt(Aux[1]), Integer.parseInt(Aux[0])).toString();
            } else {
                Fecha = ConvertirFecha(Aux);
            }
        }
        String nick = vDialogo.jTextFieldNick_Categoria.getText();

        return new Monitor(cod, Nombre, Dni, telefono, Correo, Fecha, nick);
    }

    /**
     * Metodo que insertar un nuevo monitor en la BD
     *
     * @param m Monitor a insertar en la BD
     * @return true si se ha creado con éxito o false si se ha generado una
     * excepción
     */
    private boolean NuevoMonitor(Monitor m) {
        try {
            this.VerficarMonitor(m);
            monitorDAO.NuevoMonitor(m);
            MonitorDAO.incCod();
            Object[] fila = {m.getCodMonitor(), m.getNombre(), m.getDni(), m.getTelefono(), m.getCorreo(), m.getFechaEntrada(), m.getNick()};
            mTabla.InsertarTabla(fila);
        } catch (Exception ex) {
            VistaMensaje.Mensaje(vDialogo, "error", ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Método que verifica los atributos pasados en el Jdialog superen los
     * espándares básicos
     *
     * @param m Objeto monitor a revisar
     * @throws Exception En caso de que no se cumplan los estándares
     */
    private void VerficarMonitor(Monitor m) throws Exception {
        Pattern reg = Pattern.compile("^\\d{8}?[A-Z]$");
        if (m.getDni().isBlank()) {
            throw new Exception("El campo dni no puede estar vacio");
        }
        Matcher match = reg.matcher(m.getDni());
        if (!match.find()) {
            throw new Exception("dni no valido");
        }
        if (m.getNombre().isBlank()) {
            throw new Exception("El campo nombre no puede estar vacio");
        }
        if (m.getTelefono() != null) {
            reg = Pattern.compile("^\\d{3}?\\s*\\d{2}?\\s*\\d{2}?\\s*\\d{2}?$");
            match = reg.matcher(m.getTelefono());
            if (!match.find()) {
                throw new Exception("Telefono no valido");
            }
        }
        if (m.getCorreo() != null) {
            reg = Pattern.compile("^\\w+@\\D+\\.[a-zA-Z]+$");
            match = reg.matcher(m.getCorreo());
            if (!match.find()) {
                throw new Exception("Correo no valido");
            }
        }
        if (m.getFechaEntrada() == null || m.getFechaEntrada().isBlank()) {
            throw new Exception("El campo fecha es obligatorio");
        } else {
            String Aux[] = m.getFechaEntrada().split("/");
            Date d = new Date();
            boolean Mayor = false;
            if (m.getFechaEntrada().isBlank() || Integer.parseInt(Aux[2]) > d.getYear() + 1900) {
                Mayor = true;
            } else if (Integer.parseInt(Aux[2]) == d.getYear() + 1900 && Integer.parseInt(Aux[1]) > d.getMonth()) {
                Mayor = true;
            } else if (Integer.parseInt(Aux[2]) == d.getYear() + 1900 && Integer.parseInt(Aux[1]) == d.getMonth() && Integer.parseInt(Aux[0]) > d.getDate()) {
                Mayor = true;
            }
            if (Mayor) {
                throw new Exception("Fecha no valida.");
            }
        }

    }

    /**
     * Metodo que actualiza el Monitor
     *
     * @param m Objeto monitor con los valores ya modificados
     * @param pos posición del Jtable a Actualizar
     * @return true si se actualiza con éxito el monitor en la BD o false si se
     * ha generado una excepción
     */
    private boolean ActualizarMonitor(Monitor m, int pos) {
        try {
            monitorDAO.ActualizarMonitor(m);
            Object[] fila = {m.getCodMonitor(), m.getNombre(), m.getDni(), m.getTelefono(), m.getCorreo(), m.getFechaEntrada(), m.getNick()};
            mTabla.ActualizarFila(fila, pos);
        } catch (Exception ex) {
            monitorDAO.CloseTransaction();
            VistaMensaje.Mensaje(vDialogo, "error", "Error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Metodo que pregunta si dar de baja al monitor seleccionado
     *
     * @param pos posicion del monitor a dar de baja
     */
    private void BajaMonitor(int pos) {
        int confirmacion = JOptionPane.showConfirmDialog(vPrincipal, "¿Deseas eliminar a " + String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 1)) + " de la base de datos?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (JOptionPane.OK_OPTION == confirmacion) {
            try {
                monitorDAO.BajaMonitor(String.valueOf(vMonitor.jTableMonitores.getValueAt(pos, 0)));
                mTabla.RemoverFila(pos);
            } catch (Exception ex) {
                monitorDAO.CloseTransaction();
                VistaMensaje.Mensaje("error", "Se ha producido un error al eliminar el Monitor " + ex.getMessage());
            }
        } else {
            System.out.println("Se Cancela la operacion");
        }
    }

    /**
     * Transforma la fecha en un formato válido para
     *
     * @param Fecha
     * @return devuelve la fecha en el formato /dd/mm/yy
     */
    private String ConvertirFecha(String Fecha[]) {
        String Meses[] = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sept", "oct", "nov", "dec"};
        int i = 0;
        while (!Meses[i].equals(Fecha[1])) {
            i++;
        }

        return Fecha[2] + "/" + (i < 10 ? "0" : "") + i + "/" + Fecha[5];
    }
}
