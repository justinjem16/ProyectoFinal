/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Entidades.Empleado;
import LogicaNegocio.LogicaEmpleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario principal para la gestión y administración de empleados del sistema.
 * 
 * <p>Esta ventana proporciona una interfaz centralizada para visualizar y administrar
 * el catálogo completo de empleados registrados en el sistema de nómina.
 * 
 * <h3>Funcionalidades principales:</h3>
 * <ul>
 *   <li>Visualización de todos los empleados en una tabla ordenada</li>
 *   <li>Acceso rápido para agregar nuevos empleados</li>
 *   <li>Actualización manual del listado de empleados</li>
 *   <li>Información detallada de cada empleado: identificación, contacto, salario y puesto</li>
 * </ul>
 * 
 * <p>La tabla muestra los siguientes datos de cada empleado:
 * <ul>
 *   <li>ID único del sistema</li>
 *   <li>Número de cédula</li>
 *   <li>Nombre completo (nombre y apellidos)</li>
 *   <li>Correo electrónico</li>
 *   <li>Salario bruto mensual</li>
 *   <li>Tipo de planilla (quincenal/mensual)</li>
 *   <li>Puesto o cargo</li>
 * </ul>
 * 
 * <p>Esta ventana actúa como punto de entrada para la gestión de empleados y
 * puede invocar otros formularios como el de registro de nuevos empleados.
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 * @see FrmEmpleado
 * @see LogicaEmpleado
 * @see Empleado
 */
public class FrmGestionEmpleados extends javax.swing.JFrame {
    
    // ========================================================================
    // ATRIBUTOS DE LÓGICA DE NEGOCIO
    // ========================================================================
    
    /**
     * Instancia de la lógica de negocio para realizar operaciones sobre empleados.
     * 
     * <p>Permite consultar, listar y gestionar los datos de empleados
     * almacenados en el sistema.
     */
    private LogicaEmpleado logicaEmpleado;
    
    /**
     * Modelo de datos de la tabla que muestra el listado de empleados.
     * 
     * <p>Este modelo contiene todas las filas y columnas visibles en la tabla,
     * permitiendo agregar, eliminar o modificar los datos mostrados al usuario.
     */
    private DefaultTableModel modeloTabla;
    
    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Crea una nueva instancia del formulario de gestión de empleados.
     * 
     * <p>Este constructor realiza las siguientes inicializaciones:
     * <ol>
     *   <li>Crea todos los componentes visuales del formulario</li>
     *   <li>Inicializa la lógica de negocio de empleados</li>
     *   <li>Configura la estructura de la tabla con sus columnas</li>
     *   <li>Carga los empleados existentes desde la base de datos</li>
     *   <li>Centra la ventana en la pantalla</li>
     * </ol>
     */
    public FrmGestionEmpleados() {
        initComponents();
        logicaEmpleado = new LogicaEmpleado();
        configurarTabla();
        cargarEmpleados();
        setLocationRelativeTo(null); // Centrar ventana en la pantalla
    }
    
    // ========================================================================
    // MÉTODOS DE CONFIGURACIÓN
    // ========================================================================
    
    /**
     * Configura la estructura y propiedades de la tabla de empleados.
     * 
     * <p>Este método:
     * <ul>
     *   <li>Define las columnas de la tabla con sus encabezados</li>
     *   <li>Crea un modelo de tabla personalizado</li>
     *   <li>Hace que la tabla sea no editable para proteger los datos</li>
     *   <li>Asigna el modelo configurado a la tabla visual</li>
     * </ul>
     * 
     * <p>Las columnas configuradas son:
     * <ol>
     *   <li>ID - Identificador único del sistema</li>
     *   <li>Cédula - Número de identificación nacional</li>
     *   <li>Nombre Completo - Nombre y apellidos del empleado</li>
     *   <li>Email - Correo electrónico de contacto</li>
     *   <li>Salario - Salario bruto mensual</li>
     *   <li>Tipo Planilla - Frecuencia de pago (mensual/quincenal)</li>
     *   <li>Puesto - Cargo o posición del empleado</li>
     * </ol>
     */
    private void configurarTabla() {
        // Definir los encabezados de las columnas
        String[] columnas = {"ID", "Cédula", "Nombre Completo", "Email", 
                           "Salario", "Tipo Planilla", "Puesto"};
        
        // Crear modelo personalizado que hace la tabla no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda es editable
            }
        };
        
        // Asignar el modelo a la tabla
        tblEmpleados.setModel(modeloTabla);
    }
    
    // ========================================================================
    // MÉTODOS DE CARGA DE DATOS
    // ========================================================================
    
    /**
     * Carga todos los empleados desde la base de datos y los muestra en la tabla.
     * 
     * <p>Este método realiza el siguiente proceso:
     * <ol>
     *   <li>Limpia todos los datos actuales de la tabla</li>
     *   <li>Consulta la lista completa de empleados activos</li>
     *   <li>Itera sobre cada empleado obtenido</li>
     *   <li>Formatea los datos de cada empleado (especialmente el salario)</li>
     *   <li>Agrega cada empleado como una fila en la tabla</li>
     * </ol>
     * 
     * <p>El salario se formatea en colones costarricenses (₡) con dos decimales
     * para una mejor presentación visual.
     * 
     * <p>Si ocurre algún error durante la carga (por ejemplo, problema de conexión
     * o error de lectura), se muestra un mensaje de error al usuario.
     */
    private void cargarEmpleados() {
        modeloTabla.setRowCount(0); // Limpiar tabla antes de cargar
        Empleado empleado = new Empleado();
        
        try {
            // Obtener lista de empleados desde la lógica de negocio
            logicaEmpleado.listarEmpleados(empleado);
            
            // Iterar sobre cada empleado y agregarlo a la tabla
            for (Empleado emp : empleado.getListaEmpleados()) {
                Object[] fila = {
                    emp.getId(),
                    emp.getCedula(),
                    emp.getNombreCompleto(),
                    emp.getEmail(),
                    String.format("₡%.2f", emp.getSalarioBruto()), // Formatear salario
                    emp.getTipoPlanilla(),
                    emp.getPuesto()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            // Mostrar mensaje de error si falla la carga
            JOptionPane.showMessageDialog(this,
                "Error al cargar empleados: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ========================================================================
    // INICIALIZACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /**
     * Inicializa todos los componentes visuales del formulario.
     * 
     * <p>Este método es generado automáticamente por el editor de formularios
     * de NetBeans y configura:
     * <ul>
     *   <li>La tabla de empleados con su panel de scroll</li>
     *   <li>Los botones de acción (Agregar, Actualizar, Cerrar)</li>
     *   <li>El título del formulario</li>
     *   <li>El layout completo de la ventana con espaciado y alineación</li>
     * </ul>
     * 
     * <p><b>ADVERTENCIA:</b> No modifique este código manualmente. Utilice el
     * editor visual de NetBeans para realizar cambios en el diseño del formulario.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Inicialización de componentes
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();

        // Configuración de la ventana principal
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Empleados");
        setResizable(false); // Ventana de tamaño fijo

        // Configuración de la tabla de empleados
        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Cédula", "Nombre", "Email", "Salario", "Planilla", "Puesto"}
        ));
        jScrollPane1.setViewportView(tblEmpleados);

        // Configuración de botones con sus eventos
        btnAgregar.setText("Agregar Empleado");
        btnAgregar.addActionListener(evt -> btnAgregarActionPerformed(evt));

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(evt -> btnActualizarActionPerformed(evt));

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(evt -> btnCerrarActionPerformed(evt));

        // Configuración del título
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Gestión de Empleados");

        // Configuración del layout (diseño visual)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(10, 10, 10)
                        .addComponent(btnActualizar)
                        .addGap(10, 10, 10)
                        .addComponent(btnCerrar)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnActualizar)
                    .addComponent(btnCerrar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack(); // Ajustar tamaño de la ventana al contenido
    }
    
    // ========================================================================
    // MANEJADORES DE EVENTOS
    // ========================================================================
    
    /**
     * Maneja el evento de clic en el botón "Agregar Empleado".
     * 
     * <p>Este método:
     * <ol>
     *   <li>Crea una nueva instancia del formulario de registro de empleado</li>
     *   <li>Pasa una referencia de esta ventana como padre</li>
     *   <li>Muestra el formulario de registro</li>
     * </ol>
     * 
     * <p>Al pasar esta ventana como padre, el formulario de empleado podrá
     * actualizar automáticamente la tabla cuando se guarde un nuevo empleado,
     * manteniendo así la sincronización de datos.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     * @see FrmEmpleado
     */
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        // Crear formulario de empleado pasando esta ventana como padre
        FrmEmpleado frm = new FrmEmpleado(this);
        frm.setVisible(true);
    }
    
    /**
     * Maneja el evento de clic en el botón "Actualizar".
     * 
     * <p>Este método recarga la lista completa de empleados desde la base de datos,
     * refrescando así la información mostrada en la tabla.
     * 
     * <p>Es útil cuando:
     * <ul>
     *   <li>Otros usuarios han agregado o modificado empleados</li>
     *   <li>Se desea verificar cambios recientes en los datos</li>
     *   <li>La tabla podría estar desactualizada</li>
     * </ul>
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarEmpleados(); // Recargar todos los empleados
    }
    
    /**
     * Maneja el evento de clic en el botón "Cerrar".
     * 
     * <p>Este método cierra la ventana actual, liberando los recursos asociados
     * y terminando la sesión de gestión de empleados.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Cerrar la ventana
    }
    
    // ========================================================================
    // MÉTODOS PÚBLICOS
    // ========================================================================
    
    /**
     * Actualiza la tabla de empleados recargando los datos desde la base de datos.
     * 
     * <p>Este método público permite que otros formularios (como el de registro
     * de empleados) soliciten una actualización de la tabla cuando se realicen
     * cambios en los datos.
     * 
     * <p>Es invocado típicamente desde {@link FrmEmpleado} después de guardar
     * exitosamente un nuevo empleado, asegurando que la lista se mantenga
     * actualizada sin intervención manual del usuario.
     */
    public void actualizarTabla() {
        cargarEmpleados(); // Recargar la lista de empleados
    }
    
    // ========================================================================
    // DECLARACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /** Botón para refrescar manualmente la lista de empleados */
    private javax.swing.JButton btnActualizar;
    
    /** Botón para abrir el formulario de registro de nuevos empleados */
    private javax.swing.JButton btnAgregar;
    
    /** Botón para cerrar la ventana de gestión */
    private javax.swing.JButton btnCerrar;
    
    /** Etiqueta con el título principal del formulario */
    private javax.swing.JLabel lblTitulo;
    
    /** Panel con scroll que contiene la tabla de empleados */
    private javax.swing.JScrollPane jScrollPane1;
    
    /** Tabla que muestra el listado completo de empleados con sus datos */
    private javax.swing.JTable tblEmpleados;
}
