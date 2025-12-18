/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Entidades.Empleado;
import LogicaNegocio.LogicaEmpleado;
import Utilidades.Constantes;
import com.toedter.calendar.JDateChooser; // NUEVO IMPORT
import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Formulario de interfaz gráfica para registrar nuevos empleados en el sistema de nómina.
 * 
 * <p>Esta ventana permite al usuario ingresar todos los datos necesarios de un empleado:
 * información personal (cédula, nombre, apellidos, contacto) e información laboral
 * (salario, tipo de planilla, puesto, fecha de ingreso).
 * 
 * <p>El formulario incluye validaciones básicas para garantizar que todos los campos
 * obligatorios sean completados antes de guardar el empleado en el sistema.
 * 
 * <h3>Funcionalidades principales:</h3>
 * <ul>
 *   <li>Captura de datos personales y laborales del empleado</li>
 *   <li>Selección de fecha de ingreso mediante calendario (JDateChooser)</li>
 *   <li>Selección del tipo de planilla (quincenal o mensual)</li>
 *   <li>Validación de campos obligatorios</li>
 *   <li>Integración con la lógica de negocio para persistir los datos</li>
 *   <li>Actualización automática de la ventana padre tras guardar</li>
 * </ul>
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 * @see LogicaEmpleado
 * @see Empleado
 * @see FrmGestionEmpleados
 */
public class FrmEmpleado extends javax.swing.JFrame {
    
    // ========================================================================
    // ATRIBUTOS DE CLASE
    // ========================================================================
    
    /**
     * Instancia de la lógica de negocio para gestionar las operaciones relacionadas
     * con empleados (crear, validar, persistir).
     */
    private LogicaEmpleado logicaEmpleado;
    
    /**
     * Referencia a la ventana padre que invocó este formulario.
     * 
     * <p>Esta referencia se utiliza para actualizar la tabla de empleados
     * en la ventana de gestión después de guardar un nuevo empleado.
     */
    private FrmGestionEmpleados ventanaPadre;
    
    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Crea una nueva instancia del formulario de empleado.
     * 
     * <p>Este constructor inicializa todos los componentes visuales del formulario,
     * configura la lógica de negocio, establece la referencia a la ventana padre
     * y centra la ventana en la pantalla.
     * 
     * @param padre referencia a la ventana de gestión de empleados que invoca
     *              este formulario, utilizada para actualizar la tabla tras guardar
     */
    public FrmEmpleado(FrmGestionEmpleados padre) {
        this.ventanaPadre = padre;
        initComponents();
        logicaEmpleado = new LogicaEmpleado();
        configurarFormulario();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }
    
    // ========================================================================
    // MÉTODOS DE CONFIGURACIÓN
    // ========================================================================
    
    /**
     * Configura los componentes del formulario con valores predeterminados.
     * 
     * <p>Este método inicializa el combo box de tipo de planilla con las opciones
     * disponibles definidas en la clase de constantes (QUINCENAL y MENSUAL).
     * También configura el selector de fecha con la fecha actual.
     */
    private void configurarFormulario() {
        // Crear array con los tipos de planilla disponibles
        String[] tiposPlanilla = {
            Constantes.PLANILLA_QUINCENAL,
            Constantes.PLANILLA_MENSUAL
        };
        // Asignar las opciones al combo box
        cmbTipoPlanilla.setModel(new DefaultComboBoxModel<>(tiposPlanilla));
        
        // NUEVO: Configurar el selector de fecha con la fecha actual
        dateChooserFechaIngreso.setDate(new Date());
    }
    
    // ========================================================================
    // INICIALIZACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /**
     * Inicializa todos los componentes visuales del formulario.
     * 
     * <p>Este método es generado automáticamente por el editor de formularios
     * de NetBeans y configura todos los labels, campos de texto, botones y
     * el layout del formulario.
     * 
     * <p><b>ADVERTENCIA:</b> No modifique este código manualmente. Cualquier
     * cambio debe realizarse a través del editor visual de NetBeans para
     * evitar inconsistencias.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Inicialización de etiquetas (labels)
        lblCedula = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellido1 = new javax.swing.JLabel();
        lblApellido2 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblSalario = new javax.swing.JLabel();
        lblPlanilla = new javax.swing.JLabel();
        lblPuesto = new javax.swing.JLabel();
        lblFechaIngreso = new javax.swing.JLabel(); // NUEVO LABEL
        
        // Inicialización de campos de texto
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido1 = new javax.swing.JTextField();
        txtApellido2 = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtSalario = new javax.swing.JTextField();
        txtPuesto = new javax.swing.JTextField();
        
        // Inicialización del combo box y date chooser
        cmbTipoPlanilla = new javax.swing.JComboBox<>();
        dateChooserFechaIngreso = new JDateChooser(); // NUEVO COMPONENTE
        
        // Inicialización de botones
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        // Configuración de la ventana principal
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Empleado");
        setResizable(false);

        // Configuración del texto de las etiquetas
        lblCedula.setText("Cédula (X-XXXX-XXXX):");
        lblNombre.setText("Nombre:");
        lblApellido1.setText("Primer Apellido:");
        lblApellido2.setText("Segundo Apellido:");
        lblEmail.setText("Email:");
        lblTelefono.setText("Teléfono:");
        lblSalario.setText("Salario Bruto:");
        lblPlanilla.setText("Tipo Planilla:");
        lblPuesto.setText("Puesto:");
        lblFechaIngreso.setText("Fecha de Ingreso:"); // NUEVO TEXTO

        // Configuración de botones con sus eventos
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed(evt));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));

        // Configuración del layout (diseño visual del formulario)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCedula)
                    .addComponent(lblNombre)
                    .addComponent(lblApellido1)
                    .addComponent(lblApellido2)
                    .addComponent(lblEmail)
                    .addComponent(lblTelefono)
                    .addComponent(lblSalario)
                    .addComponent(lblPlanilla)
                    .addComponent(lblPuesto)
                    .addComponent(lblFechaIngreso)) // NUEVO COMPONENTE EN LAYOUT
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCedula)
                    .addComponent(txtNombre)
                    .addComponent(txtApellido1)
                    .addComponent(txtApellido2)
                    .addComponent(txtEmail)
                    .addComponent(txtTelefono)
                    .addComponent(txtSalario)
                    .addComponent(cmbTipoPlanilla, 0, 250, Short.MAX_VALUE)
                    .addComponent(txtPuesto)
                    .addComponent(dateChooserFechaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // NUEVO COMPONENTE
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellido1)
                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellido2)
                    .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSalario)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlanilla)
                    .addComponent(cmbTipoPlanilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPuesto)
                    .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                // NUEVO GRUPO PARA FECHA DE INGRESO
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaIngreso)
                    .addComponent(dateChooserFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack(); // Ajustar el tamaño de la ventana según su contenido
    }
    
    // ========================================================================
    // MANEJADORES DE EVENTOS
    // ========================================================================
    
    /**
     * Maneja el evento de clic en el botón "Guardar".
     * 
     * <p>Este método realiza las siguientes acciones:
     * <ol>
     *   <li>Valida que todos los campos obligatorios estén completos</li>
     *   <li>Crea un objeto Empleado con los datos ingresados</li>
     *   <li>Invoca la lógica de negocio para guardar el empleado</li>
     *   <li>Muestra mensaje de éxito o error según el resultado</li>
     *   <li>Actualiza la tabla en la ventana padre</li>
     *   <li>Cierra el formulario si todo fue exitoso</li>
     * </ol>
     * 
     * <p>El ID del empleado se inicializa en 0 porque será asignado automáticamente
     * por el sistema al momento de persistir el registro.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Validar que todos los campos obligatorios estén completos
            if (!validarCampos()) {
                return; // Si la validación falla, no continuar
            }
            
            // NUEVO: Convertir la fecha del JDateChooser a LocalDate
            LocalDate fechaIngreso = null;
            if (dateChooserFechaIngreso.getDate() != null) {
                fechaIngreso = dateChooserFechaIngreso.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            }
            
            // Crear objeto Empleado con los datos del formulario
            // El ID se inicializa en 0 porque será generado automáticamente
            Empleado empleado = new Empleado(
                0, // ID temporal, será asignado por el sistema
                txtCedula.getText().trim(),
                txtNombre.getText().trim(),
                txtApellido1.getText().trim(),
                txtApellido2.getText().trim(),
                txtEmail.getText().trim(),
                txtTelefono.getText().trim(),
                Double.parseDouble(txtSalario.getText().trim()),
                (String) cmbTipoPlanilla.getSelectedItem(),
                txtPuesto.getText().trim(),
                fechaIngreso // NUEVO PARÁMETRO
            );
            
            // Guardar el empleado a través de la lógica de negocio
            logicaEmpleado.agregarEmpleado(empleado);
            
            // Mostrar mensaje de éxito al usuario
            JOptionPane.showMessageDialog(this,
                "Empleado guardado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar la tabla en la ventana de gestión de empleados
            ventanaPadre.actualizarTabla();
            
            // Cerrar este formulario
            this.dispose();
            
        } catch (Exception e) {
            // Capturar cualquier error y mostrarlo al usuario
            JOptionPane.showMessageDialog(this,
                "Error al guardar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Maneja el evento de clic en el botón "Cancelar".
     * 
     * <p>Este método simplemente cierra el formulario sin guardar ningún dato,
     * descartando cualquier información ingresada por el usuario.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Cerrar la ventana
    }
    
    // ========================================================================
    // MÉTODOS DE VALIDACIÓN
    // ========================================================================
    
    /**
     * Valida que todos los campos obligatorios del formulario estén completos.
     * 
     * <p>Los campos obligatorios son:
     * <ul>
     *   <li>Cédula</li>
     *   <li>Nombre</li>
     *   <li>Primer apellido</li>
     *   <li>Email</li>
     *   <li>Salario</li>
     *   <li>Puesto</li>
     *   <li>Fecha de Ingreso</li>
     * </ul>
     * 
     * <p>Nota: El segundo apellido y el teléfono son opcionales.
     * 
     * @return {@code true} si todos los campos obligatorios están completos,
     *         {@code false} en caso contrario (mostrando un mensaje de advertencia)
     */
    private boolean validarCampos() {
        // Verificar que los campos obligatorios no estén vacíos
        if (txtCedula.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtApellido1.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            txtSalario.getText().trim().isEmpty() ||
            txtPuesto.getText().trim().isEmpty() ||
            dateChooserFechaIngreso.getDate() == null) { // NUEVA VALIDACIÓN
            
            // Mostrar mensaje de advertencia al usuario
            JOptionPane.showMessageDialog(this,
                "Por favor complete todos los campos obligatorios",
                "Campos vacíos",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    // ========================================================================
    // DECLARACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /** Botón para cancelar la operación y cerrar el formulario */
    private javax.swing.JButton btnCancelar;
    
    /** Botón para guardar el nuevo empleado en el sistema */
    private javax.swing.JButton btnGuardar;
    
    /** Combo box para seleccionar el tipo de planilla (quincenal o mensual) */
    private javax.swing.JComboBox<String> cmbTipoPlanilla;
    
    /** Selector de fecha para la fecha de ingreso del empleado - NUEVO */
    private JDateChooser dateChooserFechaIngreso;
    
    /** Etiqueta descriptiva para el campo de cédula */
    private javax.swing.JLabel lblCedula;
    
    /** Etiqueta descriptiva para el campo de nombre */
    private javax.swing.JLabel lblNombre;
    
    /** Etiqueta descriptiva para el campo de primer apellido */
    private javax.swing.JLabel lblApellido1;
    
    /** Etiqueta descriptiva para el campo de segundo apellido */
    private javax.swing.JLabel lblApellido2;
    
    /** Etiqueta descriptiva para el campo de email */
    private javax.swing.JLabel lblEmail;
    
    /** Etiqueta descriptiva para el campo de teléfono */
    private javax.swing.JLabel lblTelefono;
    
    /** Etiqueta descriptiva para el campo de salario bruto */
    private javax.swing.JLabel lblSalario;
    
    /** Etiqueta descriptiva para el campo de tipo de planilla */
    private javax.swing.JLabel lblPlanilla;
    
    /** Etiqueta descriptiva para el campo de puesto */
    private javax.swing.JLabel lblPuesto;
    
    /** Etiqueta descriptiva para el campo de fecha de ingreso - NUEVA */
    private javax.swing.JLabel lblFechaIngreso;
    
    /** Campo de texto para ingresar el primer apellido del empleado */
    private javax.swing.JTextField txtApellido1;
    
    /** Campo de texto para ingresar el segundo apellido del empleado (opcional) */
    private javax.swing.JTextField txtApellido2;
    
    /** Campo de texto para ingresar la cédula del empleado en formato X-XXXX-XXXX */
    private javax.swing.JTextField txtCedula;
    
    /** Campo de texto para ingresar el correo electrónico del empleado */
    private javax.swing.JTextField txtEmail;
    
    /** Campo de texto para ingresar el nombre del empleado */
    private javax.swing.JTextField txtNombre;
    
    /** Campo de texto para ingresar el puesto o cargo del empleado */
    private javax.swing.JTextField txtPuesto;
    
    /** Campo de texto para ingresar el salario bruto mensual del empleado */
    private javax.swing.JTextField txtSalario;
    
    /** Campo de texto para ingresar el número de teléfono del empleado (opcional) */
    private javax.swing.JTextField txtTelefono;
}