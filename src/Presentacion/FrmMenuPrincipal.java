/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Entidades.Usuario;
import javax.swing.*;

/**
 * Menú principal del Sistema de Cálculo de Nómina.
 * 
 * <p>Esta ventana representa el hub central de navegación del sistema, desde donde
 * los usuarios autenticados pueden acceder a todas las funcionalidades principales
 * de la aplicación de nómina.
 * 
 * <h3>Funcionalidades disponibles:</h3>
 * <ul>
 *   <li><b>Gestión de Empleados:</b> Permite administrar el catálogo de empleados
 *       (agregar, visualizar, actualizar información)</li>
 *   <li><b>Generación de Nómina:</b> Permite calcular y generar nóminas para empleados,
 *       incluyendo PDFs y envío por correo</li>
 *   <li><b>Cerrar Sesión:</b> Permite al usuario salir del sistema de forma segura</li>
 * </ul>
 * 
 * <h3>Características de seguridad:</h3>
 * <ul>
 *   <li>Muestra información del usuario autenticado (nombre y apellido)</li>
 *   <li>Mantiene la sesión del usuario actual durante toda la navegación</li>
 *   <li>Solicita confirmación antes de cerrar sesión</li>
 *   <li>Retorna al login al cerrar sesión de forma segura</li>
 * </ul>
 * 
 * <p>Esta ventana se crea después de una autenticación exitosa y permanece abierta
 * mientras el usuario navega por las diferentes secciones del sistema. Cerrar esta
 * ventana cierra toda la aplicación.
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 * @see FrmLogin
 * @see FrmGestionEmpleados
 * @see FrmGenerarNomina
 * @see Usuario
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {
    
    // ========================================================================
    // ATRIBUTOS DE SESIÓN
    // ========================================================================
    
    /**
     * Objeto que representa al usuario actualmente autenticado en el sistema.
     * 
     * <p>Este objeto contiene toda la información del usuario que inició sesión,
     * incluyendo su nombre, apellidos, rol y permisos. Se utiliza para:
     * <ul>
     *   <li>Mostrar el mensaje de bienvenida personalizado</li>
     *   <li>Mantener el contexto de la sesión activa</li>
     *   <li>Potencialmente aplicar permisos según el rol del usuario</li>
     * </ul>
     */
    private Usuario usuarioActual;
    
    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Crea una nueva instancia del menú principal para el usuario especificado.
     * 
     * <p>Este constructor:
     * <ol>
     *   <li>Recibe y almacena la información del usuario autenticado</li>
     *   <li>Inicializa todos los componentes visuales del menú</li>
     *   <li>Personaliza el mensaje de bienvenida con el nombre del usuario</li>
     *   <li>Centra la ventana en la pantalla</li>
     * </ol>
     * 
     * <p>El usuario proporcionado debe haber sido previamente autenticado por
     * el formulario de login {@link FrmLogin}.
     * 
     * @param usuario el objeto Usuario que contiene la información del usuario
     *                autenticado que accede al sistema
     * @throws NullPointerException si el usuario proporcionado es null
     */
    public FrmMenuPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
        
        // Personalizar mensaje de bienvenida con nombre completo del usuario
        lblBienvenida.setText("Bienvenido: " + usuario.getNombre() + " " + usuario.getApellido1());
        
        setLocationRelativeTo(null); // Centrar ventana en la pantalla
    }
    
    // ========================================================================
    // INICIALIZACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /**
     * Inicializa todos los componentes visuales del menú principal.
     * 
     * <p>Este método es generado automáticamente por el editor de formularios
     * de NetBeans y configura:
     * <ul>
     *   <li>Etiqueta de bienvenida con el nombre del usuario</li>
     *   <li>Separador visual para organización</li>
     *   <li>Botones de navegación a las diferentes secciones:
     *       <ul>
     *         <li>Gestión de Empleados</li>
     *         <li>Generación de Nómina</li>
     *       </ul>
     *   </li>
     *   <li>Botón de cierre de sesión</li>
     *   <li>Layout completo con espaciado y dimensiones apropiadas</li>
     * </ul>
     * 
     * <p>La ventana se configura con EXIT_ON_CLOSE porque cerrar el menú principal
     * debe cerrar toda la aplicación (a menos que se cierre sesión explícitamente).
     * 
     * <p><b>ADVERTENCIA:</b> No modifique este código manualmente. Use el
     * editor visual de NetBeans para realizar cambios en el diseño.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Inicialización de componentes
        lblBienvenida = new javax.swing.JLabel();
        btnGestionEmpleados = new javax.swing.JButton();
        btnGenerarNomina = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        // Configuración de la ventana principal
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Nómina - Menú Principal");
        setResizable(false); // Ventana de tamaño fijo

        // Configuración de la etiqueta de bienvenida
        lblBienvenida.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblBienvenida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBienvenida.setText("Bienvenido Usuario"); // Será personalizado en el constructor

        // Configuración de botones de navegación
        btnGestionEmpleados.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnGestionEmpleados.setText("Gestión de Empleados");
        btnGestionEmpleados.addActionListener(evt -> btnGestionEmpleadosActionPerformed(evt));

        btnGenerarNomina.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnGenerarNomina.setText("Generar Nómina");
        btnGenerarNomina.addActionListener(evt -> btnGenerarNominaActionPerformed(evt));

        // Configuración del botón de cierre de sesión
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.addActionListener(evt -> btnCerrarSesionActionPerformed(evt));

        // Configuración del layout (diseño visual)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblBienvenida, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(btnGestionEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGenerarNomina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblBienvenida)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGestionEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGenerarNomina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack(); // Ajustar tamaño de ventana al contenido
    }
    
    // ========================================================================
    // MANEJADORES DE EVENTOS
    // ========================================================================
    
    /**
     * Maneja el evento de clic en el botón "Gestión de Empleados".
     * 
     * <p>Este método:
     * <ol>
     *   <li>Crea una nueva instancia del formulario de gestión de empleados</li>
     *   <li>Muestra la ventana al usuario</li>
     * </ol>
     * 
     * <p>La ventana de gestión de empleados permite:
     * <ul>
     *   <li>Visualizar todos los empleados registrados</li>
     *   <li>Agregar nuevos empleados al sistema</li>
     *   <li>Actualizar la lista de empleados</li>
     * </ul>
     * 
     * <p>El menú principal permanece abierto mientras el usuario trabaja
     * en la gestión de empleados, permitiendo navegar entre módulos.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     * @see FrmGestionEmpleados
     */
    private void btnGestionEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {
        // Abrir formulario de gestión de empleados
        FrmGestionEmpleados frm = new FrmGestionEmpleados();
        frm.setVisible(true);
    }
    
    /**
     * Maneja el evento de clic en el botón "Generar Nómina".
     * 
     * <p>Este método:
     * <ol>
     *   <li>Crea una nueva instancia del formulario de generación de nómina</li>
     *   <li>Muestra la ventana al usuario</li>
     * </ol>
     * 
     * <p>La ventana de generación de nómina permite:
     * <ul>
     *   <li>Seleccionar un empleado de la lista</li>
     *   <li>Configurar período y tipo de planilla</li>
     *   <li>Calcular automáticamente deducciones y aportes</li>
     *   <li>Generar reportes PDF para patrono y empleado</li>
     *   <li>Enviar comprobantes por correo electrónico</li>
     * </ul>
     * 
     * <p>El menú principal permanece abierto para facilitar la navegación
     * entre diferentes módulos del sistema.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     * @see FrmGenerarNomina
     */
    private void btnGenerarNominaActionPerformed(java.awt.event.ActionEvent evt) {
        // Abrir formulario de generación de nómina
        FrmGenerarNomina frm = new FrmGenerarNomina();
        frm.setVisible(true);
    }
    
    /**
     * Maneja el evento de clic en el botón "Cerrar Sesión".
     * 
     * <p>Este método implementa el proceso seguro de cierre de sesión:
     * <ol>
     *   <li>Muestra un cuadro de diálogo de confirmación al usuario</li>
     *   <li>Si el usuario confirma:
     *       <ul>
     *         <li>Crea una nueva instancia del formulario de login</li>
     *         <li>Muestra la ventana de login</li>
     *         <li>Cierra y libera los recursos del menú principal</li>
     *       </ul>
     *   </li>
     *   <li>Si el usuario cancela, no realiza ninguna acción</li>
     * </ol>
     * 
     * <p><b>Seguridad:</b> Este proceso garantiza que:
     * <ul>
     *   <li>Los cierres de sesión sean intencionales (requiere confirmación)</li>
     *   <li>La sesión del usuario se finalice apropiadamente</li>
     *   <li>Se retorne al estado de autenticación inicial</li>
     *   <li>No queden ventanas huérfanas abiertas</li>
     * </ul>
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     * @see FrmLogin
     */
    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {
        // Solicitar confirmación antes de cerrar sesión
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        // Si el usuario confirma el cierre de sesión
        if (respuesta == JOptionPane.YES_OPTION) {
            // Crear y mostrar nueva ventana de login
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
            
            // Cerrar menú principal y liberar recursos
            this.dispose();
        }
        // Si el usuario cancela, no hacer nada (permanecer en el menú)
    }
    
    // ========================================================================
    // DECLARACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /**
     * Botón que permite cerrar la sesión del usuario actual.
     * 
     * <p>Al hacer clic, solicita confirmación y retorna al login si el
     * usuario confirma la acción.
     */
    private javax.swing.JButton btnCerrarSesion;
    
    /**
     * Botón de navegación al módulo de generación de nómina.
     * 
     * <p>Abre la ventana donde se pueden calcular nóminas, generar PDFs
     * y enviar comprobantes de pago por correo.
     */
    private javax.swing.JButton btnGenerarNomina;
    
    /**
     * Botón de navegación al módulo de gestión de empleados.
     * 
     * <p>Abre la ventana donde se puede administrar el catálogo completo
     * de empleados del sistema.
     */
    private javax.swing.JButton btnGestionEmpleados;
    
    /**
     * Separador visual horizontal.
     * 
     * <p>Divide visualmente el mensaje de bienvenida de las opciones del menú
     * para mejorar la organización y legibilidad.
     */
    private javax.swing.JSeparator jSeparator1;
    
    /**
     * Etiqueta que muestra el mensaje de bienvenida personalizado.
     * 
     * <p>Incluye el nombre y primer apellido del usuario autenticado,
     * proporcionando una experiencia personalizada y confirmando visualmente
     * quién está usando el sistema.
     */
    private javax.swing.JLabel lblBienvenida;
}