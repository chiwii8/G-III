/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Vista.*;
import java.awt.event.*;
import Modelo.HibernateUtilMariaDB;
import Modelo.HibernateUtilOracle;

import org.hibernate.Session;

/**
 * Controlador que administra la entrada en las distintas bases de datos Oracle
 * o Maria DB
 *
 * @author Alejandro
 */
public class ControladorLogin implements ActionListener {

    private VistaLogin vLogin = null;
    private final VistaConsola vista = new VistaConsola();
    private Session sesion;
    private int Servidor;

    public ControladorLogin() {
        vLogin = new VistaLogin();

        addListeners();

        vLogin.setLocationRelativeTo(null);
        vLogin.setVisible(true);
    }

    private void addListeners() {
        vLogin.jButton1Conectar.addActionListener(this);
        vLogin.jButton2SalirLogin.addActionListener(this);
    }

    /**
     * Metodo que realiza una conexión con los servidores
     *
     * @return la conexión establecida
     */
    public Session conectar() {
        String server = (String) (vLogin.jComboBoxServidor.getSelectedItem());
        if ("Maria DB".equals(server)) {
            server = "mariadb";
        } else if ("Oracle".equals(server)) {
            server = "oracle";
        }
        if ("oracle".equals(server)) {
            sesion = HibernateUtilOracle.getSessionFactory().openSession();
            ActividadDAO.setServidor(0);
        } else if ("mariadb".equals(server)) {
            sesion = HibernateUtilMariaDB.getSessionFactory().openSession();
            ActividadDAO.setServidor(1);
        }
        return (Session) sesion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ConectarBD":
                sesion = conectar();
                if (sesion != null) {
                    VistaMensaje.Mensaje(null, "info", "Conexión correcta con Hibernate");
                    vLogin.dispose();
                    new ControladorPrincipal(sesion);
                } else {
                    VistaMensaje.Mensaje(null, "error", "Error en la conexión. No se ha podido crear una sesión\n");
                }
                break;
            case "Salir":
                vLogin.dispose();
                System.exit(0);
                break;
        }
    }
}
