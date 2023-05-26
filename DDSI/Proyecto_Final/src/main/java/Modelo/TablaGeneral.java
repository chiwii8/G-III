/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaMonitor;
import Vista.VistaSocio;
import Vista.VistaSocioActividad;
import Vista.Vista_Cutoa_socio;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Alejandro
 */
public class TablaGeneral extends JTable {

    private TableRowSorter<DefaultTableModel> sorter;
    public DefaultTableModel modeloTablaGeneral = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public DefaultTableModel getmodeloTablaGeneral() {
        return this.modeloTablaGeneral;
    }

    /**
     * crea un modelo de tabla para los monitores
     *
     * @param vMonitor
     */
    public void DibujaTablaMonitores(VistaMonitor vMonitor) {
        String[] Columnas = {"Código", "Nombre", "Dni", "Teléfono", "Correo", "Fecha Incorporación", "Nick"};
        modeloTablaGeneral.setColumnIdentifiers(Columnas);
        vMonitor.jTableMonitores.getTableHeader().setResizingAllowed(false);
        vMonitor.jTableMonitores.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        vMonitor.jTableMonitores.getColumnModel().getColumn(0).setPreferredWidth(100);
        vMonitor.jTableMonitores.getColumnModel().getColumn(1).setPreferredWidth(500);
        vMonitor.jTableMonitores.getColumnModel().getColumn(2).setPreferredWidth(200);
        vMonitor.jTableMonitores.getColumnModel().getColumn(3).setPreferredWidth(200);
        vMonitor.jTableMonitores.getColumnModel().getColumn(4).setPreferredWidth(400);
        vMonitor.jTableMonitores.getColumnModel().getColumn(5).setPreferredWidth(150);
        vMonitor.jTableMonitores.getColumnModel().getColumn(6).setPreferredWidth(100);
    }

    /**
     * Crea un modelo de tabla para los socios
     *
     * @param vSocio
     */
    public void DibujaTablaSocio(VistaSocio vSocio) {
        String[] Columnas = {"Código", "Nombre", "Dni", "Fecha Nacimiento", "Teléfono", "Correo", "Fecha Incorporación", "Categoria"};
        modeloTablaGeneral.setColumnIdentifiers(Columnas);
        vSocio.jTableSocio.getTableHeader().setResizingAllowed(false);
        vSocio.jTableSocio.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        vSocio.jTableSocio.getColumnModel().getColumn(0).setPreferredWidth(65);
        vSocio.jTableSocio.getColumnModel().getColumn(1).setPreferredWidth(220);
        vSocio.jTableSocio.getColumnModel().getColumn(2).setPreferredWidth(90);
        vSocio.jTableSocio.getColumnModel().getColumn(3).setPreferredWidth(120);
        vSocio.jTableSocio.getColumnModel().getColumn(4).setPreferredWidth(100);
        vSocio.jTableSocio.getColumnModel().getColumn(5).setPreferredWidth(250);
        vSocio.jTableSocio.getColumnModel().getColumn(6).setPreferredWidth(120);
        vSocio.jTableSocio.getColumnModel().getColumn(7).setPreferredWidth(75);

    }

    /**
     * Crea un modelo de tabla para los socios por actividad
     *
     * @param vActividad
     */
    public void DibujaTablaActividadPorSocio(VistaSocioActividad vActividad) {
        String[] Columna = {"Nombre", "Correo"};
        modeloTablaGeneral.setColumnIdentifiers(Columna);
        vActividad.jTableSocioPorActividad.getTableHeader().setResizingAllowed(false);
        vActividad.jTableSocioPorActividad.getColumnModel().getColumn(0).setPreferredWidth(400);
        vActividad.jTableSocioPorActividad.getColumnModel().getColumn(1).setPreferredWidth(400);
    }

    public void DibujaTablaCuotaSocios(Vista_Cutoa_socio vCuota){
        String[] Columna = {"Nombre", "Precio"};
        modeloTablaGeneral.setColumnIdentifiers(Columna);
        vCuota.jTableCuota.getTableHeader().setResizingAllowed(false);
        vCuota.jTableCuota.getColumnModel().getColumn(0).setPreferredWidth(150);
        vCuota.jTableCuota.getColumnModel().getColumn(1).setPreferredWidth(150);
    }
    
    /**
     * rellena la tabla de Monitores
     *
     * @param lMonitor
     */
    public void RellenaTablaMonitores(ArrayList<Monitor> lMonitor) {
        Object[] fila = new Object[7];
        for (Monitor m : lMonitor) {
            fila[0] = m.getCodMonitor();
            fila[1] = m.getNombre();
            fila[2] = m.getDni();
            fila[3] = m.getTelefono();
            fila[4] = m.getCorreo();
            fila[5] = m.getFechaEntrada();
            fila[6] = m.getNick();
            modeloTablaGeneral.addRow(fila);
        }
    }

    /**
     * Rellena la tabla de Socios
     *
     * @param lSocio
     */
    public void RellenaTablaSocio(ArrayList<Socio> lSocio) {
        Object[] fila = new Object[8];
        for (Socio s : lSocio) {
            fila[0] = s.getNumeroSocio();
            fila[1] = s.getNombre();
            fila[2] = s.getDni();
            fila[3] = s.getFechaNacimiento();
            fila[4] = s.getTelefono();
            fila[5] = s.getCorreo();
            fila[6] = s.getFechaEntrada();
            fila[7] = s.getCategoria();
            modeloTablaGeneral.addRow(fila);
        }
    }

    /**
     * rellena la tabla de Actividades con socios
     *
     * @param lSocio
     */
    public void RellenaTablaSociosporActividad(ArrayList<Socio> lSocio) {
        Object[] fila = new Object[2];
        for (Socio s : lSocio) {
            fila[0] = s.getNombre();
            fila[1] = s.getCorreo();
            modeloTablaGeneral.addRow(fila);
        }

    }

    /**
     * Inicializa la tabla de monitores
     *
     * @param vMonitor
     */
    public void InicializarTablaMonitores(VistaMonitor vMonitor) {
        vMonitor.jTableMonitores.setModel(modeloTablaGeneral);
    }

    /**
     * Inicializa la tabla de socios
     *
     * @param vSocio
     */
    public void InicializarTablaSocios(VistaSocio vSocio) {
        vSocio.jTableSocio.setModel(modeloTablaGeneral);
        vSocio.jTableSocio.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(modeloTablaGeneral);
        vSocio.jTableSocio.setRowSorter(sorter);
    }

    /**
     * Inicializa la tabla de socios por actividad
     *
     * @param vActividad
     */
    public void InicializarTablaActividad(VistaSocioActividad vActividad) {
        vActividad.jTableSocioPorActividad.setModel(modeloTablaGeneral);
    }

    public  void InicializarTablaCuota(Vista_Cutoa_socio vCuota){
        vCuota.jTableCuota.setModel(modeloTablaGeneral);
    }
    
    /**
     * Inserta una fila en la tabla
     *
     * @param fila
     */
    public void InsertarTabla(Object[] fila) {
        modeloTablaGeneral.addRow(fila);
    }

    /**
     * Vacia la tabla
     */
    public void vaciarTabla() {
        while (modeloTablaGeneral.getRowCount() > 0) {
            modeloTablaGeneral.removeRow(0);
        }
    }

    /**
     * Elimina una fila de la tabla
     *
     * @param pos posicion a eliminar
     */
    public void RemoverFila(int pos) {
        modeloTablaGeneral.removeRow(pos);
    }

    /**
     * Actualiza una fila de la tabla
     *
     * @param fila valores actualizados
     * @param pos fila a actualizar
     */
    public void ActualizarFila(Object[] fila, int pos) {
        System.out.println(pos);
        for (int i = 0; i < fila.length; i++) {
            modeloTablaGeneral.setValueAt(fila[i], pos, i);
        }
    }

    /**
     * filtrado de búsqueda por DNI Nº columna de DNI : 2
     *
     * @param Texto texto a buscar
     */
    public void filtrar(String Texto) {
        try {
            sorter.setRowFilter(RowFilter.regexFilter(Texto, 2));
        } catch (Exception | AssertionError e) {
        }
    }
}
