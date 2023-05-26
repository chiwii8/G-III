/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import Modelo.TablaGeneral;
import Vista.NewSocioActividad;
import Vista.VistaDialogos;
import Vista.VistaMensaje;
import Vista.VistaPrincipal;
import Vista.VistaSocio;
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
public class ControladorSocio implements ActionListener {

    private SocioDAO socioDAO = null;
    private ActividadDAO actividadDAO = null;
    private VistaPrincipal vPrincipal = null;
    private VistaDialogos vDialogo = null;
    private NewSocioActividad vNewSocio = null;

    private VistaSocio vSocio = null;

    private TablaGeneral sTabla = null;
    private int Operacion = 0;

    private String nombreAct;

    public ControladorSocio(VistaPrincipal vPrincipal, VistaSocio vSocio, TablaGeneral stabla, SocioDAO socioDAO) {
        this.vPrincipal = vPrincipal;
        this.vDialogo = new VistaDialogos();

        this.vSocio = vSocio;
        this.socioDAO = socioDAO;

        this.sTabla = stabla;

        addListeners();

    }

    private void addListeners() {
        vSocio.jButtonNuevoSocio.addActionListener(this);
        vSocio.jButtonBajaSocio.addActionListener(this);
        vSocio.jButtonActualizarSocio.addActionListener(this);
        vSocio.jTextFieldBusqueda.addActionListener(this);

        vSocio.jButtonNewActividad.addActionListener(this);
        vSocio.jButtonDownActividad.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos;
        switch (e.getActionCommand()) {
            case "NuevoSocio":
                Operacion = 0;
                vDialogo = new VistaDialogos().NuevoSocio();
                Activardialogos(vDialogo);
                break;

            case "ActualizarSocio":
                Operacion = 1;
                pos = vSocio.jTableSocio.getSelectedRow();
                if (pos != -1) {
                    vDialogo = new VistaDialogos().ActualizarSocio(getSocio(pos));
                    Activardialogos(vDialogo);
                }

                break;

            case "BajaSocio":
                pos = vSocio.jTableSocio.getSelectedRow();
                if (pos != -1) {
                    this.BajaSocio(pos);
                }

                break;

            case "NewAct":
                if (vSocio.jTableSocio.getSelectedRow() != -1) {
                    new ControladorSocioActividad(socioDAO.getSesion(), vSocio.jTableSocio.getValueAt(vSocio.jTableSocio.getSelectedRow(), 0).toString(), 0);
                }
                break;

            case "DownAct":
                if (vSocio.jTableSocio.getSelectedRow() != -1) {
                    new ControladorSocioActividad(socioDAO.getSesion(), vSocio.jTableSocio.getValueAt(vSocio.jTableSocio.getSelectedRow(), 0).toString(), 1);
                }
                break;

            case "AceptarOperacion":
                switch (Operacion) {
                    case 0:
                        if (this.NuevoSocio(ActualizarDatosSocio())) {
                            vDialogo.dispose();
                        }
                        break;
                    case 1:
                        if (this.ActualizarSocio(DatosNuevoSocio(), vSocio.jTableSocio.getSelectedRow())) {
                            vDialogo.dispose();
                        }
                        break;
                    case 2:
                        try {
                        socioDAO.AddActividad(String.valueOf(vSocio.jTableSocio.getValueAt(vSocio.jTableSocio.getSelectedRow(), 0)), actividadDAO.getIdActividad(vNewSocio.NombreActividades.getSelectedItem().toString()));
                    } catch (Exception ex) {
                        VistaMensaje.Mensaje(vSocio, "error", ex.getMessage());
                    }

                    vNewSocio.dispose();
                    break;
                    case 3:
                        try {
                        socioDAO.RemoveActividad(String.valueOf(vSocio.jTableSocio.getValueAt(vSocio.jTableSocio.getSelectedRow(), 0)), actividadDAO.getIdActividad(vNewSocio.NombreActividades.getSelectedItem().toString()));
                    } catch (Exception ex) {
                        VistaMensaje.Mensaje(vSocio, "error", ex.getMessage());
                    }

                    vNewSocio.dispose();
                    break;
                    default:
                        throw new AssertionError();
                }
                break;

            case "CancelarOperacion":
                vDialogo.dispose();
                break;

            default:
                throw new AssertionError();
        }
    }

    private void Activardialogos(VistaDialogos dialogo) {
        dialogo.jButtonAceptar.addActionListener(this);
        dialogo.jButtonCancelar.addActionListener(this);

        dialogo.setVisible(true);
    }

    /**
     * habilita los ActionComand de los botones del JDialog seleccionado
     *
     * @param dialogo
     */
    private void ActiviarDialogoActividad(NewSocioActividad dialogo) {
        dialogo.jButtonAceptar.addActionListener(this);
        dialogo.jButtonCancelar.addActionListener(this);

        dialogo.setVisible(true);
    }

    /**
     * Crea un Socio con los datos anadidos en el JDialog
     *
     * @return Devuelve un nuevo Socio
     */
    private Socio DatosNuevoSocio() {
        Socio s = socioDAO.getSocio(String.valueOf(vSocio.jTableSocio.getValueAt(vSocio.jTableSocio.getSelectedRow(), 0)));
        s.setNombre(vDialogo.jTextFieldNombre.getText());
        s.setDni(vDialogo.jTextFieldDni.getText());
        s.setTelefono(vDialogo.jTextFieldTelefono.getText());
        s.setCorreo(vDialogo.jTextFieldCorreo.getText());
        String Fecha = null;
        if (vDialogo.jFechaEntrada.getDate() != null) {
            String Aux[] = vDialogo.jFechaEntrada.getDate().toString().toLowerCase().split(" ");
            if (Aux.length == 1) {
                Fecha = new Date(Integer.parseInt(Aux[2]) - 1900, Integer.parseInt(Aux[1]), Integer.parseInt(Aux[0])).toString();
            } else {
                Fecha = ConvertirFecha(Aux);
            }
        }
        s.setFechaEntrada(Fecha);
        String FechaNac = "";
        if (vDialogo.jFechaEntrada.getDate() != null) {
            FechaNac = ConvertirFecha(vDialogo.jFechaNAC.getDate().toString().toLowerCase().split(" "));
            String Aux[] = vDialogo.jFechaNAC.getDate().toString().toLowerCase().split(" ");
            if (Aux.length == 1) {
                FechaNac = new Date(Integer.parseInt(Aux[2]) - 1900, Integer.parseInt(Aux[1]), Integer.parseInt(Aux[0])).toString();
            } else {
                FechaNac = ConvertirFecha(Aux);
            }
        }
        s.setFechaNacimiento(FechaNac);
        s.setCategoria(vDialogo.jTextFieldNick_Categoria.getText().charAt(0));

        return s;
    }

    /**
     * Actualiza los datos de un socio
     *
     * @return
     */
    private Socio ActualizarDatosSocio() {
        String cod = vDialogo.jTextFieldCodigo.getText();
        String Nombre = vDialogo.jTextFieldNombre.getText();
        String Dni = vDialogo.jTextFieldDni.getText();
        String telefono = vDialogo.jTextFieldTelefono.getText();
        String Correo = vDialogo.jTextFieldCorreo.getText();

        String FechaEnt = null;
        if (vDialogo.jFechaEntrada.getDate() != null) {
            String Aux[] = vDialogo.jFechaEntrada.getDate().toString().toLowerCase().split(" ");
            if (Aux.length == 1) {
                FechaEnt = new Date(Integer.parseInt(Aux[2]) - 1900, Integer.parseInt(Aux[1]), Integer.parseInt(Aux[0])).toString();
            } else {
                FechaEnt = ConvertirFecha(Aux);
            }
        }
        String FechaNac = "";
        if (vDialogo.jFechaEntrada.getDate() != null) {
            FechaNac = ConvertirFecha(vDialogo.jFechaNAC.getDate().toString().toLowerCase().split(" "));
            String Aux[] = vDialogo.jFechaNAC.getDate().toString().toLowerCase().split(" ");
            if (Aux.length == 1) {
                FechaNac = new Date(Integer.parseInt(Aux[2]) - 1900, Integer.parseInt(Aux[1]), Integer.parseInt(Aux[0])).toString();
            } else {
                FechaNac = ConvertirFecha(Aux);
            }
        }
        char Categoria = vDialogo.jTextFieldNick_Categoria.getText().toCharArray()[0];

        return new Socio(cod, Nombre, Dni, FechaNac, telefono, Correo, FechaEnt, Categoria);
    }

    /**
     * Metodo que insertar un nuevo Socio en la BD
     *
     * @param s Socio a insertar en la BD
     * @return true si se ha creado con éxito o false si se ha generado una
     * excepción
     */
    private boolean NuevoSocio(Socio s) {
        try {
            this.VerficarSocio(s);
            socioDAO.NuevoSocio(s);
            SocioDAO.incCod();
            Object[] fila = {s.getNumeroSocio(), s.getNombre(), s.getDni(), s.getFechaNacimiento(), s.getTelefono(), s.getCorreo(), s.getFechaEntrada(), s.getCategoria()};
            sTabla.InsertarTabla(fila);
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
     * @param s Objeto socio a revisar
     * @throws Exception en caso de que no se cumplan los estándares
     */
    private void VerficarSocio(Socio s) throws Exception {
        Pattern reg = Pattern.compile("^\\d{8}?[A-Z]$");
        if (s.getDni().isBlank()) {
            throw new Exception("El campo dni no puede estar vacio");
        }
        Matcher match = reg.matcher(s.getDni());
        if (!match.find()) {
            throw new Exception("dni no valido");
        }
        if (s.getNombre().isBlank()) {
            throw new Exception("El campo nombre no puede estar vacio");
        }

        if (s.getFechaEntrada() != null || !s.getFechaEntrada().isBlank()) {
            String Aux[] = s.getFechaEntrada().split("/");
            Date d = new Date();
            boolean Mayor = false;
            if (s.getFechaEntrada().isBlank() || Integer.parseInt(Aux[2]) > d.getYear() + 1900) {
                Mayor = true;
            } else if (Integer.parseInt(Aux[2]) == d.getYear() + 1900 && Integer.parseInt(Aux[1]) > d.getMonth()) {
                Mayor = true;
            } else if (Integer.parseInt(Aux[2]) == d.getYear() + 1900 && Integer.parseInt(Aux[1]) == d.getMonth() && Integer.parseInt(Aux[0]) > d.getDate()) {
                Mayor = true;
            }
            if (Mayor) {
                throw new Exception("Fecha de Nacimiento no valida.");
            }
        }

        if (s.getTelefono() != null) {
            reg = Pattern.compile("^\\d{3}?\\s*\\d{2}?\\s*\\d{2}?\\s*\\d{2}?$");
            match = reg.matcher(s.getTelefono());
            if (!match.find()) {
                throw new Exception("Telefono no valido");
            }
        }
        if (s.getCorreo() != null) {
            reg = Pattern.compile("^\\w+@\\D+\\.[a-zA-Z]+$");
            match = reg.matcher(s.getCorreo());
            if (!match.find()) {
                throw new Exception("Correo no valido");
            }
        }
        if (s.getFechaEntrada() == null || s.getFechaEntrada().isBlank()) {
            throw new Exception("El campo fecha es obligatorio");
        } else {
            String Aux[] = s.getFechaEntrada().split("/");
            Date d = new Date();
            boolean Mayor = false;
            if (s.getFechaEntrada().isBlank() || Integer.parseInt(Aux[2]) > d.getYear() + 1900) {
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
        reg = Pattern.compile("A|B|C|D|E");
        match = reg.matcher(s.getCategoria().toString());
        if (!match.find()) {
            throw new Exception("El campo CAtegoria no es válido\n Debe ser A,B,C,D o E");
        }

    }

    /**
     * Recoge los datos de la Jtable en la posicion seleccionada en un nuevo
     * monitor
     *
     * @param pos posicion en la Jtable
     * @return devuelve un monitor
     */
    private Socio getSocio(int pos) {
        return new Socio(String.valueOf(vSocio.jTableSocio.getValueAt(pos, 0)), String.valueOf(vSocio.jTableSocio.getValueAt(pos, 1)),
                String.valueOf(vSocio.jTableSocio.getValueAt(pos, 2)), String.valueOf(vSocio.jTableSocio.getValueAt(pos, 3)),
                String.valueOf(vSocio.jTableSocio.getValueAt(pos, 4)), String.valueOf(vSocio.jTableSocio.getValueAt(pos, 5)),
                String.valueOf(vSocio.jTableSocio.getValueAt(pos, 6)), String.valueOf(vSocio.jTableSocio.getValueAt(pos, 7)).charAt(0));
    }

    /**
     * Metodo que actualiza el Monitor
     *
     * @param s Objeto socio con los valores ya modificados
     * @param pos posición del Jtable a Actualizar
     * @return true si se actualiza con éxito el socio en la BD o false si se ha
     * generado una excepción
     */
    private boolean ActualizarSocio(Socio s, int pos) {
        try {
            socioDAO.ActualizarSocio(s);
            Object[] fila = {s.getNumeroSocio(), s.getNombre(), s.getDni(), s.getFechaNacimiento(), s.getTelefono(), s.getCorreo(), s.getFechaEntrada(), s.getCategoria()};
            sTabla.ActualizarFila(fila, pos);
        } catch (Exception ex) {
            socioDAO.CloseTransaction();
            VistaMensaje.Mensaje(vDialogo, "error", "Error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Metodo que pregunta si dar de baja al socio seleccionado
     *
     * @param pos posicion del socio a dar de baja
     */
    private void BajaSocio(int pos) {
        int confirmacion = JOptionPane.showConfirmDialog(vPrincipal, "¿Deseas eliminar a " + String.valueOf(vSocio.jTableSocio.getValueAt(pos, 1)) + " de la base de datos?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (JOptionPane.OK_OPTION == confirmacion) {
            try {
                socioDAO.BajaSocio(String.valueOf(vSocio.jTableSocio.getValueAt(pos, 0)));
                sTabla.RemoverFila(pos);
            } catch (Exception ex) {
                socioDAO.CloseTransaction();
                VistaMensaje.Mensaje("error", "Se ha producido un error al eliminar el Socio");
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

    /////Inicializa los valores de la ventana
}
