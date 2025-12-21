/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Entidades.Empleado;
import Entidades.Nomina;
import Entidades.Correo;
import LogicaNegocio.LogicaEmpleado;
import LogicaNegocio.CalculadoraNomina;
import LogicaNegocio.GeneradorPDF;
import LogicaNegocio.LogicaCorreo;
import Utilidades.Constantes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.io.File; 
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Formulario principal para la generación y gestión de nóminas de empleados.
 * 
 * <p>Esta ventana integra todo el proceso de cálculo de nómina, que incluye:
 * <ul>
 *   <li>Selección del empleado desde una tabla</li>
 *   <li>Configuración del período y tipo de planilla</li>
 *   <li>Cálculo automático de salarios, deducciones y aportes patronales</li>
 *   <li>Visualización detallada de los resultados</li>
 *   <li>Generación de reportes PDF para patrono y empleado</li>
 *   <li>Envío automático del comprobante por correo electrónico</li>
 * </ul>
 * 
 * <h3>Flujo de trabajo:</h3>
 * <ol>
 *   <li>El usuario selecciona un empleado de la tabla</li>
 *   <li>Configura la fecha y tipo de planilla (mensual/quincenal)</li>
 *   <li>Presiona "Calcular Nómina" para procesar los cálculos</li>
 *   <li>Revisa los resultados en la tabla de resumen</li>
 *   <li>Genera PDFs para el patrono y/o empleado</li>
 *   <li>Opcionalmente envía el comprobante por correo</li>
 * </ol>
 * 
 * <p>Los cálculos se realizan automáticamente según la legislación costarricense,
 * aplicando las deducciones de ley y aportes patronales correspondientes.
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 * @see CalculadoraNomina
 * @see GeneradorPDF
 * @see Nomina
 * @see Empleado
 */
public class FrmGenerarNomina extends JFrame {

    // ========================================================================
    // ATRIBUTOS DE LÓGICA DE NEGOCIO
    // ========================================================================
    
    /**
     * Gestor de operaciones relacionadas con empleados (consultas, listados).
     */
    private LogicaEmpleado logicaEmpleado;
    
    /**
     * Motor de cálculo que procesa salarios, deducciones y aportes patronales.
     */
    private CalculadoraNomina calculadora;
    
    /**
     * Generador de documentos PDF para reportes de nómina.
     */
    private GeneradorPDF generadorPDF;
    
    /**
     * Gestor de envío de correos electrónicos con comprobantes adjuntos.
     */
    private LogicaCorreo logicaCorreo;
    
    // ========================================================================
    // ATRIBUTOS DE MODELO DE DATOS
    // ========================================================================
    
    /**
     * Modelo de datos para la tabla de empleados disponibles.
     * Contiene columnas: ID, Cédula, Nombre Completo, Salario Bruto, Tipo Planilla.
     */
    private DefaultTableModel modeloTablaEmpleados;
    
    /**
     * Modelo de datos para la tabla de resultados del cálculo de nómina.
     * Muestra conceptos (deducciones, aportes) y sus montos correspondientes.
     */
    private DefaultTableModel modeloTablaResultado;
    
    /**
     * Referencia al empleado seleccionado actualmente en la tabla.
     * Se utiliza para realizar los cálculos de nómina.
     */
    private Empleado empleadoSeleccionado;
    
    /**
     * Objeto que contiene el resultado completo del cálculo de nómina.
     * Incluye todos los montos calculados: deducciones, aportes y salario neto.
     */
    private Nomina nominaCalculada;
    
    // ========================================================================
    // COMPONENTES VISUALES
    // ========================================================================
    
    /** Etiqueta del título principal del formulario */
    private JLabel lblGenerarNomina;
    
    /** Panel con scroll que contiene la tabla de empleados */
    private JScrollPane jscrollEmpleado;
    
    /** Tabla que muestra el listado de empleados disponibles */
    private JTable tblEmpleados;
    
    /** Etiqueta descriptiva para el campo de fecha */
    private JLabel jLabel1;
    
    /** Selector de fecha para el período de la nómina */
    private JDateChooser dateChooserPeriodo;
    
    /** Etiqueta descriptiva para el campo de tipo de planilla */
    private JLabel jLabel2;
    
    /** Combo box para seleccionar el tipo de planilla (mensual/quincenal) */
    private JComboBox<String> cmbTipoPlanilla;
    
    /** Botón para ejecutar el cálculo de la nómina */
    private JButton btnCalcular;
    
    /** Etiqueta del título de la sección de resultados */
    private JLabel lblResultado;
    
    /** Panel con scroll que contiene la tabla de resultados */
    private JScrollPane jscrollResultado;
    
    /** Tabla que muestra el detalle del cálculo de nómina */
    private JTable tblResultado;
    
    /** Botón para generar el PDF con vista para el patrono */
    private JButton btnPDFPatrono;
    
    /** Botón para generar el PDF con vista para el empleado */
    private JButton btnPDFEmpleado;
    
    /** Botón para cerrar el formulario */
    private JButton btnCerrar;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Crea una nueva instancia del formulario de generación de nóminas.
     * 
     * <p>Este constructor inicializa:
     * <ul>
     *   <li>Todas las instancias de lógica de negocio necesarias</li>
     *   <li>Los componentes visuales del formulario</li>
     *   <li>Las tablas de empleados y resultados</li>
     *   <li>El selector de período con la fecha actual</li>
     *   <li>Carga inicial de empleados desde la base de datos</li>
     * </ul>
     * 
     * <p>La ventana se centra automáticamente en la pantalla al mostrarse.
     */
    public FrmGenerarNomina() {
        // Inicializar componentes de lógica de negocio
        logicaEmpleado = new LogicaEmpleado();
        calculadora = new CalculadoraNomina();
        generadorPDF = new GeneradorPDF();
        logicaCorreo = new LogicaCorreo();
        
        // Inicializar y configurar la interfaz gráfica
        initComponents();
        configurarTablas();
        configurarPeriodo();
        cargarEmpleados();
        setLocationRelativeTo(null); // Centrar ventana
    }

    // ========================================================================
    // INICIALIZACIÓN DE COMPONENTES VISUALES
    // ========================================================================
    
    /**
     * Inicializa todos los componentes visuales del formulario.
     * 
     * <p>Este método es generado automáticamente por el editor de formularios
     * de NetBeans y configura el layout completo de la ventana, incluyendo:
     * <ul>
     *   <li>Tablas de empleados y resultados</li>
     *   <li>Controles de fecha y tipo de planilla</li>
     *   <li>Botones de acción con sus eventos asociados</li>
     *   <li>Diseño y espaciado de todos los componentes</li>
     * </ul>
     * 
     * <p><b>ADVERTENCIA:</b> No modifique este código manualmente. Use el
     * editor visual de NetBeans para realizar cambios en el diseño.
     */
    private void initComponents() {
        // Inicializar componentes
        lblGenerarNomina = new JLabel();
        jscrollEmpleado = new JScrollPane();
        tblEmpleados = new JTable();
        jLabel1 = new JLabel();
        dateChooserPeriodo = new JDateChooser();
        jLabel2 = new JLabel();
        cmbTipoPlanilla = new JComboBox<>();
        btnCalcular = new JButton();
        lblResultado = new JLabel();
        jscrollResultado = new JScrollPane();
        tblResultado = new JTable();
        btnPDFPatrono = new JButton();
        btnPDFEmpleado = new JButton();
        btnCerrar = new JButton();

        // Configuración de la ventana
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generar Nómina");
        setResizable(false); // Ventana de tamaño fijo

        // Título principal
        lblGenerarNomina.setFont(new Font("Segoe UI", 0, 24));
        lblGenerarNomina.setText("Generación de Nomina");

        // Configuración de la tabla de empleados
        tblEmpleados.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Cédula", "Nombre", "Salario", "Tipo"}
        ));
        jscrollEmpleado.setViewportView(tblEmpleados);

        // Etiquetas y controles de período
        jLabel1.setText("Fecha:");
        
        dateChooserPeriodo.setDateFormatString("yyyy-MM-dd");
        dateChooserPeriodo.setPreferredSize(new Dimension(150, 25));

        jLabel2.setText("Tipo Planilla:");

        cmbTipoPlanilla.setModel(new DefaultComboBoxModel<>(new String[] { "Mensual", "Quincenal" }));

        // Botón de cálculo
        btnCalcular.setText("Calcular Nómina");
        btnCalcular.addActionListener(evt -> btnCalcularActionPerformed(evt));

        // Sección de resultados
        lblResultado.setText("Resultado del Cálculo");

        tblResultado.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"Concepto", "Monto"}
        ));
        jscrollResultado.setViewportView(tblResultado);

        // Botones de generación de PDF (inicialmente deshabilitados)
        btnPDFPatrono.setText("Generar PDF Patrono");
        btnPDFPatrono.setEnabled(false);
        btnPDFPatrono.addActionListener(evt -> btnPDFPatronoActionPerformed(evt));

        btnPDFEmpleado.setText("Generar PDF Empleado");
        btnPDFEmpleado.setEnabled(false);
        btnPDFEmpleado.addActionListener(evt -> btnPDFEmpleadoActionPerformed(evt));

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(evt -> btnCerrarActionPerformed(evt));

        // Configuración del layout (diseño visual)
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblGenerarNomina)
                    .addComponent(jscrollEmpleado, GroupLayout.PREFERRED_SIZE, 808, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooserPeriodo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoPlanilla, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCalcular))
                    .addComponent(lblResultado)
                    .addComponent(jscrollResultado, GroupLayout.PREFERRED_SIZE, 808, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPDFPatrono)
                        .addGap(36, 36, 36)
                        .addComponent(btnPDFEmpleado)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrar)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblGenerarNomina)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jscrollEmpleado, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(dateChooserPeriodo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbTipoPlanilla, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalcular))
                .addGap(18, 18, 18)
                .addComponent(lblResultado)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jscrollResultado, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPDFPatrono)
                    .addComponent(btnPDFEmpleado)
                    .addComponent(btnCerrar))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack(); // Ajustar tamaño de ventana al contenido
    }

    // ========================================================================
    // MÉTODOS DE CONFIGURACIÓN
    // ========================================================================
    
    /**
     * Configura las tablas del formulario con sus modelos y propiedades.
     * 
     * <p>Este método inicializa:
     * <ul>
     *   <li><b>Tabla de empleados:</b> Muestra ID, cédula, nombre completo, 
     *       salario bruto y tipo de planilla</li>
     *   <li><b>Tabla de resultados:</b> Muestra conceptos y montos del cálculo</li>
     * </ul>
     * 
     * <p>Ambas tablas se configuran como no editables para evitar modificaciones
     * accidentales por parte del usuario.
     */
    private void configurarTablas() {
        // Configurar tabla de empleados
        String[] columnasEmpleados = {"ID", "Cédula", "Nombre Completo", "Salario Bruto", "Tipo Planilla"};
        modeloTablaEmpleados = new DefaultTableModel(columnasEmpleados, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla no editable
            }
        };
        tblEmpleados.setModel(modeloTablaEmpleados);
        
        // Configurar tabla de resultados
        String[] columnasResultado = {"Concepto", "Monto"};
        modeloTablaResultado = new DefaultTableModel(columnasResultado, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla no editable
            }
        };
        tblResultado.setModel(modeloTablaResultado);
    }

    /**
     * Carga la lista de empleados desde la base de datos y la muestra en la tabla.
     * 
     * <p>Este método:
     * <ol>
     *   <li>Limpia cualquier dato previo en la tabla</li>
     *   <li>Consulta todos los empleados activos del sistema</li>
     *   <li>Popula la tabla con la información de cada empleado</li>
     *   <li>Formatea el salario en colones costarricenses</li>
     * </ol>
     * 
     * <p>Si ocurre un error durante la carga, se muestra un mensaje al usuario.
     */
    private void cargarEmpleados() {
        modeloTablaEmpleados.setRowCount(0); // Limpiar tabla
        Empleado empleado = new Empleado();
        
        try {
            // Obtener lista de empleados desde la lógica de negocio
            logicaEmpleado.listarEmpleados(empleado);
            
            // Agregar cada empleado a la tabla
            for (Empleado emp : empleado.getListaEmpleados()) {
                Object[] fila = {
                    emp.getId(),
                    emp.getCedula(),
                    emp.getNombreCompleto(),
                    String.format("₡%.2f", emp.getSalarioBruto()), // Formatear en colones
                    emp.getTipoPlanilla()
                };
                modeloTablaEmpleados.addRow(fila);
            }
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this,
                "Error al cargar empleados: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configura los controles de período y tipo de planilla con valores iniciales.
     * 
     * <p>Este método:
     * <ul>
     *   <li>Carga las opciones de tipo de planilla desde las constantes del sistema</li>
     *   <li>Establece la fecha actual como valor predeterminado en el selector</li>
     * </ul>
     */
    private void configurarPeriodo() {
        // Configurar combo box con tipos de planilla
        String[] tiposPlanilla = {Constantes.PLANILLA_MENSUAL, Constantes.PLANILLA_QUINCENAL};
        cmbTipoPlanilla.setModel(new DefaultComboBoxModel<>(tiposPlanilla));
        
        // Establecer fecha actual por defecto
        dateChooserPeriodo.setDate(new java.util.Date());
    }
    
    // ========================================================================
    // MÉTODOS DE UTILIDAD
    // ========================================================================
    
    /**
     * Genera el código del período de nómina según la fecha y tipo de planilla seleccionados.
     * 
     * <p>El formato del período varía según el tipo de planilla:
     * <ul>
     *   <li><b>Mensual:</b> YYYY-MM-M (ejemplo: 2024-12-M)</li>
     *   <li><b>Quincenal:</b> YYYY-MM-Q1 o YYYY-MM-Q2 según la fecha:
     *       <ul>
     *         <li>Q1: del día 1 al 15</li>
     *         <li>Q2: del día 16 al final del mes</li>
     *       </ul>
     *   </li>
     * </ul>
     * 
     * @return una cadena con el código del período formateado
     */
    private String obtenerPeriodoFormateado() {
        // Formatear fecha base como YYYY-MM
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String fechaBase = sdf.format(dateChooserPeriodo.getDate());
        
        String tipoPlanilla = (String) cmbTipoPlanilla.getSelectedItem();
        
        if (tipoPlanilla.equals(Constantes.PLANILLA_MENSUAL)) {
            return fechaBase + "-M";
        } else {
            // Para quincenal, determinar si es Q1 o Q2 según el día
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateChooserPeriodo.getDate());
            int dia = cal.get(Calendar.DAY_OF_MONTH);
            
            // Q1 va del 1 al 15, Q2 del 16 al final del mes
            if (dia <= 15) {
                return fechaBase + "-Q1";
            } else {
                return fechaBase + "-Q2";
            }
        }
    }

    // ========================================================================
    // MANEJADORES DE EVENTOS
    // ========================================================================
    
    /**
     * Maneja el evento de clic en el botón "Calcular Nómina".
     * 
     * <p>Este método ejecuta el proceso completo de cálculo de nómina:
     * <ol>
     *   <li>Valida que se haya seleccionado un empleado y una fecha</li>
     *   <li>Verifica que el tipo de planilla coincida con el del empleado</li>
     *   <li>Obtiene los datos completos del empleado seleccionado</li>
     *   <li>Genera el código del período según la fecha y tipo de planilla</li>
     *   <li>Invoca la calculadora para procesar todos los cálculos</li>
     *   <li>Muestra los resultados en la tabla de resumen</li>
     *   <li>Habilita los botones de generación de PDF</li>
     * </ol>
     * 
     * <p>El método valida que el tipo de planilla seleccionado coincida con el
     * configurado para el empleado, evitando así errores en el cálculo.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {
        int filaSeleccionada = tblEmpleados.getSelectedRow();
        
        // Validar que se haya seleccionado un empleado
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione un empleado",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar que se haya seleccionado una fecha
        if (dateChooserPeriodo.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione una fecha",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Obtener ID del empleado seleccionado
            int idEmpleado = (Integer) tblEmpleados.getValueAt(filaSeleccionada, 0);
            Empleado empleado = new Empleado();
            logicaEmpleado.listarEmpleados(empleado);
            
            // Buscar el empleado completo por su ID
            for (Empleado emp : empleado.getListaEmpleados()) {
                if (emp.getId() == idEmpleado) {
                    empleadoSeleccionado = emp;
                    break;
                }
            }
            
            if (empleadoSeleccionado == null) {
                throw new Exception("No se encontró el empleado");
            }
            
            // ===== NUEVA VALIDACIÓN: VERIFICAR FECHA DE INGRESO =====
            if (empleadoSeleccionado.getFechaIngreso() != null) {
                // Convertir la fecha del periodo a LocalDate
                java.time.LocalDate fechaPeriodo = dateChooserPeriodo.getDate()
                    .toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
                
                // Verificar si la fecha del periodo es anterior a la fecha de ingreso
                if (fechaPeriodo.isBefore(empleadoSeleccionado.getFechaIngreso())) {
                    JOptionPane.showMessageDialog(this,
                        "No se puede generar nómina para una fecha anterior a la fecha de ingreso del empleado.\n\n" +
                        "Fecha de ingreso: " + empleadoSeleccionado.getFechaIngresoFormateada() + "\n" +
                        "Fecha del periodo seleccionado: " + fechaPeriodo.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        "Error: Fecha inválida",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // ===== FIN DE NUEVA VALIDACIÓN =====
            
            // Validar que el tipo de planilla coincida con el del empleado
            String tipoPlanillaSeleccionada = (String) cmbTipoPlanilla.getSelectedItem();
            if (!empleadoSeleccionado.getTipoPlanilla().equals(tipoPlanillaSeleccionada)) {
                JOptionPane.showMessageDialog(this,
                    "El tipo de planilla seleccionada (" + tipoPlanillaSeleccionada + ") " +
                    "no coincide con el tipo de planilla del empleado (" + 
                    empleadoSeleccionado.getTipoPlanilla() + ")",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Generar el período formateado
            String periodo = obtenerPeriodoFormateado();
            
            // Crear objeto de nómina y calcular todos los valores
            nominaCalculada = new Nomina(0, empleadoSeleccionado, periodo);
            calculadora.calcularNominaCompleta(nominaCalculada);
            
            // Mostrar resultados en la tabla
            mostrarResultadosCalculo();
            
            // Habilitar botones de PDF ahora que hay una nómina calculada
            btnPDFEmpleado.setEnabled(true);
            btnPDFPatrono.setEnabled(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al calcular nómina: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Maneja el evento de clic en el botón "Generar PDF Patrono".
     * 
     * <p>Este método genera un PDF con el reporte detallado de la nómina para el patrono,
     * que incluye información completa de deducciones y aportes patronales.
     * 
     * <p>El PDF se genera solo si existe una nómina calculada previamente.
     * Al finalizar, muestra la ruta donde se guardó el archivo.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnPDFPatronoActionPerformed(java.awt.event.ActionEvent evt) {
        // Validar que se haya seleccionado un periodo
        if (dateChooserPeriodo.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione un periodo",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Obtener el periodo seleccionado
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
            String periodo = sdf.format(dateChooserPeriodo.getDate());
            
            // Cargar TODOS los empleados del sistema
            Empleado empleadoTemp = new Empleado();
            logicaEmpleado.listarEmpleados(empleadoTemp);
            
            if (empleadoTemp.getListaEmpleados().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No hay empleados registrados en el sistema",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Convertir la fecha del periodo a LocalDate para validación
            java.time.LocalDate fechaPeriodo = dateChooserPeriodo.getDate()
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
            
            // Crear lista para almacenar todas las nóminas calculadas
            java.util.List<Nomina> listaNominas = new java.util.ArrayList<>();
            
            // Variable para contar empleados procesados y excluidos
            int empleadosProcesados = 0;
            int empleadosExcluidos = 0;
            
            // Calcular la nómina de cada empleado
            for (Empleado emp : empleadoTemp.getListaEmpleados()) {
                // VALIDAR FECHA DE INGRESO: Solo incluir empleados que ya trabajaban en ese periodo
                if (emp.getFechaIngreso() != null) {
                    if (fechaPeriodo.isBefore(emp.getFechaIngreso())) {
                        // Este empleado aún no había ingresado en este periodo, omitirlo
                        empleadosExcluidos++;
                        continue;
                    }
                }
                
                // Crear objeto nómina para este empleado
                Nomina nomina = new Nomina(0, emp, periodo);
                
                // Calcular la nómina completa
                calculadora.calcularNominaCompleta(nomina);
                
                // Agregar a la lista
                listaNominas.add(nomina);
                empleadosProcesados++;
            }
            
            // Verificar que haya al menos un empleado para procesar
            if (listaNominas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No hay empleados que hayan ingresado antes del periodo seleccionado.\n" +
                    "Total de empleados excluidos: " + empleadosExcluidos,
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Generar el PDF consolidado
            String archivo = generadorPDF.generarReportePatronoMensual(listaNominas, periodo);
            
            // Construir mensaje informativo
            String mensaje = "PDF patronal mensual generado exitosamente:\n" + archivo +
                            "\n\nTotal de empleados procesados: " + empleadosProcesados;
            
            if (empleadosExcluidos > 0) {
                mensaje += "\nEmpleados excluidos (no habían ingresado): " + empleadosExcluidos;
            }
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this,
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al generar PDF patronal mensual: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    /**
     * Maneja el evento de clic en el botón "Generar PDF Empleado".
     * 
     * <p>Este método:
     * <ol>
     *   <li>Genera un PDF con el comprobante de pago para el empleado</li>
     *   <li>Muestra la ruta donde se guardó el archivo</li>
     *   <li>Pregunta al usuario si desea enviar el comprobante por correo</li>
     *   <li>Si el usuario acepta, envía el PDF adjunto al email del empleado</li>
     * </ol>
     * 
     * <p>El PDF para empleado es más simplificado que el del patrono, mostrando
     * principalmente el salario bruto, las deducciones y el salario neto.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnPDFEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {
        // Validar que exista una nómina calculada
        if (nominaCalculada == null) {
            JOptionPane.showMessageDialog(this,
                "Primero debe calcular la nómina",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Generar el PDF para el empleado
            String archivo = generadorPDF.generarReporteEmpleado(nominaCalculada);
            
            // Preguntar si desea enviar por correo
            int respuesta = JOptionPane.showConfirmDialog(this,
                "PDF generado exitosamente:\n" + archivo + "\n\n¿Desea enviarlo por correo al empleado?",
                "Éxito",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
            
            // Si el usuario acepta, enviar el correo con el PDF adjunto
            if (respuesta == JOptionPane.YES_OPTION) {
                enviarCorreo(archivo);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al generar PDF: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Maneja el evento de clic en el botón "Cerrar".
     * 
     * <p>Simplemente cierra la ventana actual, liberando los recursos asociados.
     * 
     * @param evt el evento de acción generado al hacer clic en el botón
     */
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Cerrar la ventana
    }

    // ========================================================================
    // MÉTODOS DE PRESENTACIÓN DE DATOS
    // ========================================================================
    
    /**
     * Muestra los resultados del cálculo de nómina en la tabla de resumen.
     * 
     * <p>Este método popula la tabla de resultados con:
     * <ul>
     *   <li><b>Información del empleado:</b> Nombre, cédula y período</li>
     *   <li><b>Salario bruto:</b> Monto base antes de deducciones</li>
     *   <li><b>Deducciones:</b> Detalle de cada deducción de ley:
     *       <ul>
     *         <li>CCSS - IVM (4.17%)</li>
     *         <li>CCSS - SEM (6.50%)</li>
     *         <li>Banco Popular (1%)</li>
     *         <li>Impuesto sobre la renta (progresivo)</li>
     *       </ul>
     *   </li>
     *   <li><b>Aportes patronales:</b> Contribuciones del empleador:
     *       <ul>
     *         <li>CCSS - IVM (7.08%)</li>
     *         <li>CCSS - SEM (10.59%)</li>
     *         <li>INA (1.5%)</li>
     *         <li>FCL (3%)</li>
     *         <li>Asignaciones Familiares (5%)</li>
     *       </ul>
     *   </li>
     *   <li><b>Salario neto:</b> Monto final que recibe el empleado</li>
     * </ul>
     * 
     * <p>Todos los montos se formatean en colones costarricenses (₡) con dos decimales.
     */
    private void mostrarResultadosCalculo() {
        modeloTablaResultado.setRowCount(0); // Limpiar tabla
        
        // ===== INFORMACIÓN DEL EMPLEADO =====
        modeloTablaResultado.addRow(new Object[]{"Empleado", empleadoSeleccionado.getNombreCompleto()});
        modeloTablaResultado.addRow(new Object[]{"Cédula", empleadoSeleccionado.getCedula()});
        modeloTablaResultado.addRow(new Object[]{"Periodo", nominaCalculada.getPeriodo()});
        modeloTablaResultado.addRow(new Object[]{"", ""}); // Línea en blanco
        
        // ===== SALARIO BRUTO =====
        modeloTablaResultado.addRow(new Object[]{"SALARIO BRUTO", String.format("₡%.2f", nominaCalculada.getSalarioBruto())});
        modeloTablaResultado.addRow(new Object[]{"", ""});
        
        // ===== DEDUCCIONES DEL EMPLEADO =====
        modeloTablaResultado.addRow(new Object[]{"DEDUCCIONES:", ""});
        modeloTablaResultado.addRow(new Object[]{"  CCSS - IVM (4.17%)", String.format("₡%.2f", nominaCalculada.getDeduccionIVM())});
        modeloTablaResultado.addRow(new Object[]{"  CCSS - SEM (6.50%)", String.format("₡%.2f", nominaCalculada.getDeduccionSEM())});
        modeloTablaResultado.addRow(new Object[]{"  Banco Popular (1%)", String.format("₡%.2f", nominaCalculada.getDeduccionBancoPop())});
        modeloTablaResultado.addRow(new Object[]{"  Impuesto Renta", String.format("₡%.2f", nominaCalculada.getDeduccionImpuestoRenta())});
        modeloTablaResultado.addRow(new Object[]{"TOTAL DEDUCCIONES", String.format("₡%.2f", nominaCalculada.getTotalDeducciones())});
        modeloTablaResultado.addRow(new Object[]{"", ""});
        
        // ===== APORTES PATRONALES =====
        modeloTablaResultado.addRow(new Object[]{"APORTES PATRONALES:", ""});
        modeloTablaResultado.addRow(new Object[]{"  CCSS - IVM (7.08%)", String.format("₡%.2f", nominaCalculada.getAporteIVM())});
        modeloTablaResultado.addRow(new Object[]{"  CCSS - SEM (10.59%)", String.format("₡%.2f", nominaCalculada.getAporteSEM())});
        modeloTablaResultado.addRow(new Object[]{"  INA (1.5%)", String.format("₡%.2f", nominaCalculada.getAporteINA())});
        modeloTablaResultado.addRow(new Object[]{"  FCL (3%)", String.format("₡%.2f", nominaCalculada.getAporteFCL())});
        modeloTablaResultado.addRow(new Object[]{"  Asignaciones (5%)", String.format("₡%.2f", nominaCalculada.getAporteAsignaciones())});
        modeloTablaResultado.addRow(new Object[]{"TOTAL APORTES", String.format("₡%.2f", nominaCalculada.getTotalAportesPatronales())});
        modeloTablaResultado.addRow(new Object[]{"", ""});
        
        // ===== SALARIO NETO =====
        modeloTablaResultado.addRow(new Object[]{"SALARIO NETO", String.format("₡%.2f", nominaCalculada.getSalarioNeto())});
    }

    /**
     * Envía el comprobante de pago por correo electrónico al empleado.
     * 
     * <p>Este método:
     * <ol>
     *   <li>Crea un objeto de correo con los datos del empleado</li>
     *   <li>Configura el asunto y mensaje del correo de forma personalizada</li>
     *   <li>Adjunta el archivo PDF del comprobante</li>
     *   <li>Envía el correo en un hilo separado para no bloquear la interfaz</li>
     * </ol>
     * 
     * <p>El correo incluye información resumida del período y salario neto,
     * junto con una firma formal del departamento de recursos humanos.
     * 
     * @param pdfEmpleado ruta completa del archivo PDF a adjuntar al correo
     */
    private void enviarCorreo(String pdfEmpleado) {
        try {
            // Crear y configurar el objeto de correo
            Correo correo = new Correo();
            correo.setEmail(empleadoSeleccionado.getEmail());
            correo.setAsunto("Comprobante de Pago - " + nominaCalculada.getPeriodo());
            
            // Configurar mensaje personalizado con datos de la nómina
            correo.setMensaje(
                "Estimado(a) " + empleadoSeleccionado.getNombreCompleto() + ",\n\n" +
                "Adjunto encontrará su comprobante de pago correspondiente al periodo " +
                nominaCalculada.getPeriodo() + ".\n\n" +
                "Salario Neto: ₡" + String.format("%.2f", nominaCalculada.getSalarioNeto()) + "\n\n" +
                "Saludos cordiales,\n" +
                "Departamento de Recursos Humanos"
            );
            
            // Adjuntar el PDF del comprobante
            correo.agregarArchivoAdjunto(new File(pdfEmpleado));

            // Enviar el correo en un hilo separado (asíncrono)
            logicaCorreo.enviarCorreoThread(correo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al enviar el correo: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}