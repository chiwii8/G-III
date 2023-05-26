/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import com.toedter.calendar.JDateChooser;
import java.awt.Dialog;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author Alejandro
 */
public class VistaDialogos extends JDialog{
    private final int DEFAULT_WIDTH=645;
    private final int DEFAULT_HEIGHT=215;
    
    private final String[] NombreLabel={"Codigo","Nombre","DNI","Telefono","Correo","Fecha Entrada","Nick","Categoria", "Fecha Nacimiento"};

    private JLabel jLabelCodigo;
    private JLabel jLabelNombre;
    private JLabel jLabelDNI;
    private JLabel jLabelTelefono;
    private JLabel jLabelCorreo;
    private JLabel jLabelFechaEntrada;
    private JLabel jLabelNick_Categoria;
    private JLabel jLabelFechaNac;

    public JTextField jTextFieldCodigo;
    public JTextField jTextFieldNombre;
    public JTextField jTextFieldDni;
    public JTextField jTextFieldTelefono;
    public JTextField jTextFieldCorreo;
    public JTextField jTextFieldNick_Categoria;

    public JDateChooser jFechaEntrada;
    public JDateChooser jFechaNAC;
    
    public JButton jButtonAceptar;
    public JButton jButtonCancelar;
    
    
    public VistaDialogos() {
        this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        initComponents(1);
        this.setLocationRelativeTo(null);
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    
    }
    //Formato 0 Monitos 1 Socio
    private void initComponents(int formato){    
        
        jLabelCodigo = new JLabel(NombreLabel[0]);
        jLabelNombre = new JLabel(NombreLabel[1]);
        jLabelDNI = new JLabel(NombreLabel[2]); 
        jLabelTelefono = new JLabel(NombreLabel[3]);        
        jLabelCorreo = new JLabel(NombreLabel[4]);
        jLabelFechaEntrada = new JLabel(NombreLabel[5]);
        if(formato == 0)
        jLabelNick_Categoria = new JLabel(NombreLabel[6]);
        else jLabelNick_Categoria = new JLabel(NombreLabel[7]); 
        jLabelFechaNac= new JLabel(NombreLabel[8]);    
        
        jTextFieldCodigo = new JTextField();
        jTextFieldCodigo.setEditable(false);
        jTextFieldCodigo.enableInputMethods(true);
        
        jTextFieldNombre = new JTextField();
        jTextFieldDni = new JTextField(); 
        jTextFieldTelefono = new JTextField();
        jTextFieldCorreo = new JTextField();
        jTextFieldNick_Categoria = new JTextField();
        
        jFechaEntrada = new JDateChooser();
        jFechaNAC = new JDateChooser();
        
        jButtonAceptar = new JButton("Aceptar");    
        jButtonCancelar = new JButton("Cancelar");
       
        jButtonAceptar.setActionCommand("AceptarOperacion");
        jButtonCancelar.setActionCommand("CancelarOperacion");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDni, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                                .addComponent(jLabelCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(316, 316, 316))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(46, 46, 46)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelFechaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelNick_Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(35, 35, 35)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jFechaNAC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jFechaEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jTextFieldNick_Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCodigo)
                    .addComponent(jLabelCorreo)
                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelNombre)
                        .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelFechaEntrada))
                    .addComponent(jFechaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDNI)
                            .addComponent(jLabelNick_Categoria)
                            .addComponent(jTextFieldNick_Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelTelefono)
                                .addComponent(jLabelFechaNac)
                                .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jFechaNAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextFieldDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAceptar)
                    .addComponent(jButtonCancelar))
                .addGap(14, 14, 14))
        );
        this.pack();
    }
    
    public VistaDialogos(int formato){
        this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        initComponents(formato);
        this.setLocationRelativeTo(null);
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }
    
    /**
     * Crea un JDialog para un nuevo monitor
     * @return 
     */
    public VistaDialogos NuevoMonitor(){
        this.jFechaNAC.setVisible(false);
        this.jLabelFechaNac.setVisible(false);
        this.jTextFieldCodigo.setText(MonitorDAO.getCodLibre());    ///Asignamos el Codigo libre
        return this;
    }
    
    /**
     * crea un JDialog con los parámetros establecidos 
     * @param m
     * @return 
     */
    public VistaDialogos ActualizarMonitor(Monitor m){
        this.jTextFieldCodigo.setText(m.getCodMonitor());
        this.jTextFieldNombre.setText(m.getNombre());
        this.jTextFieldDni.setText(m.getDni());
        this.jTextFieldTelefono.setText(m.getTelefono());
        this.jTextFieldCorreo.setText(m.getCorreo());
        String Fecha[] = m.getFechaEntrada().split("/");
        this.jFechaEntrada.setDate(new Date(Integer.parseInt(Fecha[2])-1900,Integer.parseInt(Fecha[1]),Integer.parseInt(Fecha[0])));
        this.jTextFieldNick_Categoria.setText(m.getNick());
        this.jFechaNAC.setVisible(false);
        this.jLabelFechaNac.setVisible(false);
        return this;
    }        
    
    /**
     * Crea un JDialog para un nuevo socio
     * @return 
     */
    public VistaDialogos NuevoSocio(){
        this.jTextFieldCodigo.setText(SocioDAO.getCodLibre());
         return this;
    }
    
    /**
     * crea un JDialog con los parámetros establecidos 
     * @param m
     * @return 
     */
    public VistaDialogos ActualizarSocio(Socio s){
         this.jTextFieldCodigo.setText(s.getNumeroSocio());
        this.jTextFieldNombre.setText(s.getNombre());
        this.jTextFieldDni.setText(s.getDni());
        this.jTextFieldTelefono.setText(s.getTelefono());
        this.jTextFieldCorreo.setText(s.getCorreo());
        String Fecha[] = s.getFechaEntrada().split("/");
        this.jFechaEntrada.setDate(new Date(Integer.parseInt(Fecha[2])-1900,Integer.parseInt(Fecha[1]),Integer.parseInt(Fecha[0])));
        if(s.getFechaNacimiento()!=null || !s.getFechaNacimiento().isBlank()){
        Fecha = s.getFechaEntrada().split("/");
        this.jFechaNAC.setDate(new Date(Integer.parseInt(Fecha[2])-1900,Integer.parseInt(Fecha[1]),Integer.parseInt(Fecha[0])));
        }
        this.jTextFieldNick_Categoria.setText(s.getCategoria().toString());
         return this;
    }
    
    
}
