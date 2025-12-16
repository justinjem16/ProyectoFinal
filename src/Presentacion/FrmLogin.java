/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import LogicaNegocio.LogicaUsuario;
import Entidades.Usuario;
import javax.swing.*;

/**
 * Formulario de inicio de sesión para el Sistema de Cálculo de Nómina.
 * 
 * <p>Esta ventana representa el punto de entrada principal al sistema, donde
 * los usuarios deben autenticarse proporcionando sus credenciales (usuario y contraseña)
 * para acceder a las funcionalidades del sistema de nómina.
 * 
 * <h3>Funcionalidades principales:</h3>
 * <ul>
 *   <li>Captura de credenciales de usuario (nombre de usuario y contraseña)</li>
 *   <li>Validación de campos obligatorios antes de intentar autenticar</li>
 *   <li>Autenticación de usuarios contra la base de datos del sistema</li>
 *   <li>Redirección al menú principal tras autenticación exitosa</li>
 *   <li>Manejo de errores de autenticación con mensajes claros</li>
 * </ul>
 * 
 * <h3>Flujo de autenticación:</h3>
 * <ol>
 *   <li>El usuario ingresa su nombre de usuario y contraseña</li>
 *   <li>Se valida que ambos campos contengan información</li>
 *   <li>Se verifica la autenticidad de las credenciales</li>
 *   <li>Si es exitoso, se muestra el menú principal y se cierra el login</li>
 *   <li>Si falla, se muestra un mensaje de error y se limpia la contraseña</li>
 * </ol>
 * 
 * <p>Esta clase también contiene el método {@link #main(String[])} que sirve
 * como punto de entrada de toda la aplicación.
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 * @see LogicaUsuario
 * @see Usuario
 * @see FrmMenuPrincipal
 */
public class FrmLogin extends javax.swing.JFrame {
    
    // ========================================================================
    // ATRIBUTOS DE LÓGICA DE NEGOCIO
    // ========================================================================
    
    /**
     * Instancia de la lógica de negocio para gestionar la autenticación de usuarios.
     * 
     * <p>Esta clase se encarga de validar las credenciales contra la base de datos
     * y retornar el objeto Usuario si la autenticación es exitosa.
     */
    private LogicaUsuario logicaUsuario;
    
    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Crea una nueva instancia del formulario de inicio de sesión.
     * 
     * <p>Este constructor:
     * <ul>
     *   <li>Inicializa todos los componentes visuales del formulario</li>
     *   <li>Crea la instancia de lógica de usuarios para autenticación</li>
     *   <li>Centra la ventana en la pantalla</li>
     * </ul>
     * 
     * <p>La ventana se configura como no redimensionable para mantener
     * un diseño consistente y profesional.
     */
    public FrmLogin() {
        initComponents();
        logicaUsuario = new LogicaUsuario();
        setLocationRelativeTo(null); // Centrar ventana en la pantalla
    }
    
    // ========================================================================
    // INICIALIZACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /**
     * Inicializa todos los componentes visuales del formulario de login.
     * 
     * <p>Este método es generado automáticamente por el editor de formularios
     * de NetBeans y configura:
     * <ul>
     *   <li>El título del sistema</li>
     *   <li>Etiquetas y campos de texto para usuario y contraseña</li>
     *   <li>Botón de ingreso con su evento asociado</li>
     *   <li>Layout completo de la ventana con espaciado apropiado</li>
     * </ul>
     * 
     * <p>La ventana se configura para cerrar la aplicación completa cuando
     * se cierra esta ventana (EXIT_ON_CLOSE), ya que es el punto de entrada.
     * 
     * <p><b>ADVERTENCIA:</b> No modifique este código manualmente. Use el
     * editor visual de NetBeans para realizar cambios en el diseño.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Inicialización de componentes
        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblContrasenia = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();

        // Configuración de la ventana principal
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); // Cerrar aplicación al cerrar ventana
        setTitle("Sistema de Nómina - Login");
        setResizable(false); // Ventana de tamaño fijo

        // Configuración del título
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Sistema de Cálculo de Nómina");

        // Configuración de etiquetas
        lblUsuario.setText("Usuario:");
        lblContrasenia.setText("Contraseña:");

        // Configuración del botón de ingreso
        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(evt -> btnIngresarActionPerformed(evt));

        // Configuración del layout (diseño visual)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addComponent(lblContrasenia))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtPassword)))
                    .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContrasenia)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack(); // Ajustar tamaño de ventana al contenido
    }
    
    // ========================================================================
    // MANEJADORES DE EVENTOS
    // ========================================================================
    
    /**
     * Maneja el evento de clic en el botón "Ingresar".
     * 
     * <p>Este método ejecuta el proceso completo de autenticación:
     * <ol>
     *   <li>Captura y limpia los datos ingresados (usuario y contraseña)</li>
     *   <li>Valida que ambos campos contengan información</li>
     *   <li>Invoca la lógica de negocio para autenticar al usuario</li>
     *   <li>Si la autenticación es exitosa:
     *       <ul>
     *         <li>Muestra mensaje de bienvenida personalizado</li>
     *         <li>Abre el menú principal del sistema</li>
     *         <li>Cierra la ventana de login</li>
     *       </ul>
     *   </li>
     *   <li>Si la autenticación falla:
     *       <ul>
     *         <li>Muestra mensaje de error</li>
     *         <li>Limpia el campo de contraseña por seguridad</li>
     *       </ul>
     *   </li>
     * </ol>
     * 
     * <p>El método utiliza {@code trim()} para eliminar espacios en blanco
     * innecesarios del nombre de usuario, y convierte la contraseña de
     * char[] a String de forma segura.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {
        // Capturar y limpiar los datos ingresados
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Validar que ambos campos estén completos
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingrese usuario y contraseña",
                "Campos vacíos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Intentar autenticar al usuario
            Usuario usuario = logicaUsuario.autenticar(username, password);
            
            // Verificar si la autenticación fue exitosa
            if (usuario != null) {
                // Autenticación exitosa
                JOptionPane.showMessageDialog(this,
                    "Bienvenido " + usuario.getNombre(),
                    "Acceso exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Abrir menú principal y pasar el usuario autenticado
                FrmMenuPrincipal menu = new FrmMenuPrincipal(usuario);
                menu.setVisible(true);
                
                // Cerrar ventana de login
                this.dispose();
            } else {
                // Autenticación fallida
                JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE);
                
                // Limpiar campo de contraseña por seguridad
                txtPassword.setText("");
            }
        } catch (Exception e) {
            // Manejar cualquier error durante el proceso de autenticación
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ========================================================================
    // MÉTODO PRINCIPAL
    // ========================================================================
    
    /**
     * Método principal que inicia la aplicación del Sistema de Nómina.
     * 
     * <p>Este método:
     * <ol>
     *   <li>Configura el Look and Feel del sistema operativo para una apariencia nativa</li>
     *   <li>Crea y muestra la ventana de login en el Event Dispatch Thread (EDT)</li>
     * </ol>
     * 
     * <p>El uso de {@code EventQueue.invokeLater()} garantiza que toda la
     * interfaz gráfica se ejecute en el hilo apropiado de Swing, evitando
     * problemas de concurrencia y asegurando un comportamiento correcto.
     * 
     * <p><b>Look and Feel:</b> Se intenta usar el aspecto nativo del sistema operativo
     * (Windows, macOS, Linux) para que la aplicación se sienta familiar al usuario.
     * Si falla, se usa el Look and Feel por defecto de Java.
     * 
     * @param args argumentos de línea de comandos (no utilizados en esta aplicación)
     */
    public static void main(String args[]) {
        // Intentar usar el Look and Feel nativo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, continuar con el Look and Feel por defecto
            e.printStackTrace();
        }
        
        // Iniciar la aplicación en el Event Dispatch Thread
        java.awt.EventQueue.invokeLater(() -> new FrmLogin().setVisible(true));
    }
    
    // ========================================================================
    // DECLARACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /** Botón para ejecutar el proceso de autenticación */
    private javax.swing.JButton btnIngresar;
    
    /** Etiqueta con el título principal del sistema */
    private javax.swing.JLabel lblTitulo;
    
    /** Etiqueta descriptiva para el campo de usuario */
    private javax.swing.JLabel lblUsuario;
    
    /** Etiqueta descriptiva para el campo de contraseña */
    private javax.swing.JLabel lblContrasenia;
    
    /**
     * Campo de contraseña para ingresar la clave del usuario.
     * 
     * <p>Este campo oculta los caracteres ingresados por seguridad,
     * mostrando típicamente asteriscos o puntos en lugar del texto real.
     */
    private javax.swing.JPasswordField txtPassword;
    
    /** Campo de texto para ingresar el nombre de usuario */
    private javax.swing.JTextField txtUsername;
}