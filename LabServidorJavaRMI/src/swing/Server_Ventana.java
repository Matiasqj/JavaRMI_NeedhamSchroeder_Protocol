/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import interfaz_implementaciones.ImplementacionServidor;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import mysql.ConexionMySQL;
import servidorRMI.Cliente;
import servidorRMI.ConexionRmi;

/**
 * Server_Ventana: clase que tiene la ventana principal del servidor
 *
 * @author Matias Quinteros
 */
public class Server_Ventana extends javax.swing.JFrame {

    ConexionRmi conexion = new ConexionRmi();//Variable para objeto de ConexionRmi
    private static Server_Ventana vistaserver;//Variable de mi vista del servidor
    public static ArrayList<Cliente> Online;//Arraylist que contiene objetos de Clientes, corresponde a los usuarios online
    public static ArrayList<Integer> Ck;//mantiene los ck asociados a los clientes online
    DefaultTableModel tabla;//modelo para cargar tabla de usuarios en la ventana

    /**
     * *
     * Constructor
     */
    public Server_Ventana() {
        //Se incializan objetos de la clase
        Online = new ArrayList<Cliente>();
        Ck = new ArrayList<Integer>();
        initComponents();//inicia componentes
        vistaserver = this;
        buttondetener.setEnabled(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        CargarUsuarios();//Se cargan los usuarios

    }

    /**
     * *
     * Busca los usuarios dentro de la lista Online
     *
     * @param usuario: usuario a buscar
     * @return ubicacion (entero) del usuario que se busca
     */
    public int Busca_Usuario_posicion(String usuario) {
        for (int i = 0; i < Online.size(); i++) //para todos los usuarios
        {
            if (Online.get(i).getNombreCliente().equals(usuario))//si se encuentra online 
            {
                return i;//retorno posicion
            }
        }
        return -1;//sino no se encontro
    }

    /**
     * *
     * Server_Ventana getServer_Ventana: retorna valor estatico de mi variable
     * de la ventana del servidor
     *
     * @return
     */
    public static Server_Ventana getServer_Ventana() {
        return vistaserver;
    }

    /**
     * Actualizar_Log_Usuario_ErrorSQL: actualiza Logserver si algun error con
     * Mysql
     *
     * @param ex
     */
    public void Actualizar_Log_Usuario_ErrorSQL(String ex) {
        LogServer.append("" + getTimestamp() + " " + ex + "\n");
    }

    /**
     * Actualizar_Log_Usuario_Conexion: actualiza Logserver si se conecta un
     * nuevo usuario
     *
     * @param nombre
     */
    public void Actualizar_Log_Usuario_Conexion(String nombre) {
        LogServer.append("" + getTimestamp() + " Se conectó el usuario: " + nombre + " \n");
    }

    /**
     * actualiza Actualizar_Log: con un mensaje de entrada , actualiza la vista
     * del LogServer(textarea)
     *
     * @param mensaje
     */
    public void Actualizar_Log(String mensaje) {
        LogServer.append("" + getTimestamp() + " " + mensaje + "\n");
    }

    /**
     * server_mysql_online: verifica si hay conexion con el servidor mysql
     *
     * @return nulo si esta abajo, sino retorna boolean false
     */
    public boolean server_mysql_online() {
        ConexionMySQL mysql = new ConexionMySQL();
        Connection on = mysql.Conectar();
        if (on == null) {//si hay error actualiza el log
            LogServer.append("" + getTimestamp() + " " + "ERROR: " + "No se pudo conectar con Mysql" + "\n");
        }
        return on != null;

    }

    /**
     * getTimestamp: retorna un string con el timestamp calculado
     *
     * @return
     */
    public String getTimestamp() {
        //se calcula de acuerdo al formato
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss").format(new java.util.Date());

        return timeStamp;
    }

    /**
     * *
     * CargarUsuarios: actualiza tabla de usuarios
     */
    public void CargarUsuarios() {
        String[] titulo = {"Id", "Nombre Usuario", "Contraseña"};//titulo de columnas
        tabla = new DefaultTableModel(null, titulo);//se genera el modelo
        String query = "SELECT * FROM usuario";
        try {//Prueba la carga con mysql
            ConexionMySQL mysql = new ConexionMySQL();
            Connection on = mysql.Conectar();
            if (on == null) {//si hay error de conexion con mysql
                LogServer.append("" + getTimestamp() + " " + "ERROR: " + "No se pudo conectar con Mysql" + "\n");
            }

            Statement st = on.createStatement();//se crfea el statement
            ResultSet rs = st.executeQuery(query);//aplica la query 
            while (rs.next()) {//por cada valor de la query
                //capturo una tupla
                String[] fila;
                fila = new String[3];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre_usuario");
                fila[2] = rs.getString("password");
                //la actualiza en la tabla
                tabla.addRow(fila);

            }
            //actualiza ahora todos los valores
            jTable1.setModel(tabla);
            if (tabla.getRowCount() == 0) {//si no hay registros en la bd, la tabla muestra mensaje

                registros.setText("No hay registros");
            } else {//caso contrario se borra ese mensaje
                registros.setText("");
            }
            //cierra conexion
            rs.close();
            on.close();
        } catch (SQLException ex) {//error: actualizo logserver
            LogServer.append("" + getTimestamp() + " " + "ERROR: " + ex + "\n");
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        eliminar = new javax.swing.JMenuItem();
        PanelTab = new javax.swing.JTabbedPane();
        PanelIniciarServer = new javax.swing.JPanel();
        buttoniniciar = new javax.swing.JButton();
        buttondetener = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        LogServer = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        Panel_Usuario = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        buttonguardar = new javax.swing.JButton();
        infoguardar = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        registros = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        Opciones = new javax.swing.JMenu();
        Salir = new javax.swing.JMenuItem();

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(eliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanelTab.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelTabMouseClicked(evt);
            }
        });

        buttoniniciar.setText("Iniciar Servidor");
        buttoniniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttoniniciarActionPerformed(evt);
            }
        });

        buttondetener.setText("Detener Servidor");
        buttondetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttondetenerActionPerformed(evt);
            }
        });

        LogServer.setEditable(false);
        LogServer.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        LogServer.setColumns(20);
        LogServer.setRows(5);
        jScrollPane2.setViewportView(LogServer);

        jLabel4.setText("Log del Servidor:");

        javax.swing.GroupLayout PanelIniciarServerLayout = new javax.swing.GroupLayout(PanelIniciarServer);
        PanelIniciarServer.setLayout(PanelIniciarServerLayout);
        PanelIniciarServerLayout.setHorizontalGroup(
            PanelIniciarServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIniciarServerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PanelIniciarServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                    .addGroup(PanelIniciarServerLayout.createSequentialGroup()
                        .addGroup(PanelIniciarServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttondetener, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(buttoniniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelIniciarServerLayout.setVerticalGroup(
            PanelIniciarServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIniciarServerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttoniniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttondetener)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PanelTab.addTab("Servidor", PanelIniciarServer);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ingresar Usuario"));

        jLabel1.setText("Nombre de Usuario:");

        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });

        jLabel2.setText("Contraseña:");

        buttonguardar.setText("Guardar");
        buttonguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonguardarActionPerformed(evt);
            }
        });

        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonguardar)
                        .addGap(18, 18, 18)
                        .addComponent(infoguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonguardar)
                    .addComponent(infoguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ver Usuarios"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setEditingColumn(1);
        jTable1.setEditingRow(1);
        jTable1.setFocusable(false);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("Seleccionar fila, luego click derecho para modificar/eliminar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registros, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout Panel_UsuarioLayout = new javax.swing.GroupLayout(Panel_Usuario);
        Panel_Usuario.setLayout(Panel_UsuarioLayout);
        Panel_UsuarioLayout.setHorizontalGroup(
            Panel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Panel_UsuarioLayout.setVerticalGroup(
            Panel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_UsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        PanelTab.addTab("Administración de Usuarios", Panel_Usuario);

        Opciones.setText("Opciones");

        Salir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        Opciones.add(Salir);

        jMenuBar1.add(Opciones);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanelTab, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanelTab, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * *
     * eliminarActionPerformed: Accion para eliminar un usuario de la bd
     *
     * @param evt
     */
    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        int id = 0;
        //primero ve si hay conexion
        ConexionMySQL mysql = new ConexionMySQL();
        Connection on = mysql.Conectar();
        if (on == null) {//Sino muestra mensaje
            LogServer.append("" + getTimestamp() + " " + "ERROR: " + "No se pudo conectar con Mysql" + "\n");
        }
        //formo la query
        String query = "delete from usuario where id = ?";
        int fila_seleccionada;
        try {//prueba con mysql
            fila_seleccionada = jTable1.getSelectedRow();//obtiene el valor seleccionado de la fila de la tabla
            if (fila_seleccionada == -1) {//si no selecciono nada, manda mensaje en el panel
                JOptionPane.showMessageDialog(null, "No se selecciono una fila");
            } else {//si selecciono un valor
                DefaultTableModel tabla;
                tabla = (DefaultTableModel) jTable1.getModel();//capturo el modelo de la tabla
                id = Integer.valueOf((String) tabla.getValueAt(fila_seleccionada, 0));//captura el id seleccionado
                String usuario = String.valueOf((String) tabla.getValueAt(fila_seleccionada, 1));//captura el usuario
                //arregla el statement de query id = ?
                PreparedStatement preparedStmt = on.prepareStatement(query);
                preparedStmt.setInt(1, id); //setea la query con el valor de id
                int valor = preparedStmt.executeUpdate(); //ejecuta la query en mysql
                if (valor > 0) {//si fue positiva, limpia la tabla
                    jTable1.removeAll();//borra todo en la tabla
                    CargarUsuarios();//carga denuevo
                    //Manda mensajes:
                    JOptionPane.showMessageDialog(null, "Se eliminó con éxito el usuario");
                    LogServer.append("" + getTimestamp() + " " + "Se eliminó el usuario: " + usuario + "\n");
                    jTextField1.setText("");
                    jTextField2.setText("");
                }
                preparedStmt.close();
            }
            on.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            LogServer.append("" + getTimestamp() + " " + "ERROR: " + ex + "\n");
        }

    }//GEN-LAST:event_eliminarActionPerformed
    /**
     * *
     * Limpia inforguardar
     *
     * @param evt
     */
    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        infoguardar.setText("");
    }//GEN-LAST:event_jTextField2MouseClicked
    /**
     * Accion para guardar un nuevo usuario
     *
     * @param evt
     */
    private void buttonguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonguardarActionPerformed
        String usuario = jTextField1.getText();//capturo nombre del usuario
        String password = jTextField2.getText();//capturo texto
        //prueba la conexion con mysql
        ConexionMySQL mysql = new ConexionMySQL();
        Connection on = mysql.Conectar();
        if (on == null) {//Si no se conecta manda error
            LogServer.append("" + getTimestamp() + " " + "ERROR: " + "No se pudo conectar con Mysql");
        }
        //query para guardar
        String query = "INSERT INTO usuario(nombre_usuario,password)" + "VALUES(?,?)";
        try {//prueba guardar en mysql
            //prepara el statement
            PreparedStatement pst = on.prepareStatement(query);
            //actualiza el statement con los valores ingresados
            pst.setString(1, usuario);
            pst.setString(2, password);
            int valor = pst.executeUpdate();//executa la query
            if (valor > 0) {//si fue un exito
                //borra los valores de la tabla
                jTable1.removeAll();
                //actualiza mensajes:
                infoguardar.setText("Se ha guardado con éxito");
                LogServer.append("" + getTimestamp() + " " + "Nuevo usuario creado : " + usuario + "\n");
                CargarUsuarios();
            }
            on.close();
            pst.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            LogServer.append("" + getTimestamp() + " " + "ERROR: " + ex + "\n");
        }

    }//GEN-LAST:event_buttonguardarActionPerformed
    /**
     * Limpia infoguardar
     *
     * @param evt
     */
    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        infoguardar.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked
    /**
     * *
     * Accion que detiene el servidor
     *
     * @param evt
     */
    private void buttondetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttondetenerActionPerformed

        try {
            //deten el servidor
            conexion.detener();
            buttondetener.setEnabled(false);
            buttoniniciar.setEnabled(true);
            LogServer.append("" + getTimestamp() + " " + "Se detuvo el servidor" + "\n");
        } catch (RemoteException ex) {//si no se puede:
            Logger.getLogger(Server_Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttondetenerActionPerformed
    /**
     * buttoniniciarActionPerformed: inicia el servidor
     *
     * @param evt
     */
    private void buttoniniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttoniniciarActionPerformed
        Registry registro;
        try {
            registro = conexion.getRegistry();
            //Se instancia el objeto que implementa la interfaz del Servidor
            //Ahora hay que hacerlo remoto, para ello se registra en el Registry
            //con el método "rebind" que lo registra con un nombre para poder ser visto en ese espacio

            ImplementacionServidor objeto = new ImplementacionServidor();
            //Rebind Lab en el espacio.
            registro.rebind("Lab", objeto);

        } catch (RemoteException ex) {
            Logger.getLogger(Server_Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttoniniciar.setEnabled(false);
        buttondetener.setEnabled(true);
        LogServer.append("" + getTimestamp() + " " + "Se inició el servidor" + "\n");
    }//GEN-LAST:event_buttoniniciarActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    private void PanelTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelTabMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_PanelTabMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea LogServer;
    private javax.swing.JMenu Opciones;
    private javax.swing.JPanel PanelIniciarServer;
    private javax.swing.JTabbedPane PanelTab;
    private javax.swing.JPanel Panel_Usuario;
    private javax.swing.JMenuItem Salir;
    private javax.swing.JButton buttondetener;
    private javax.swing.JButton buttonguardar;
    private javax.swing.JButton buttoniniciar;
    private javax.swing.JMenuItem eliminar;
    private javax.swing.JLabel infoguardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel registros;
    // End of variables declaration//GEN-END:variables
}
