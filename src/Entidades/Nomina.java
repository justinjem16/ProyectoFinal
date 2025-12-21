

package Entidades;

/**
 * Clase LocalDate del paquete java.time que representa una fecha sin zona horaria.
 * 
 * <p>Se utiliza en esta clase para almacenar la fecha de emisión de la nómina,
 * permitiendo realizar cálculos de periodos de pago y gestión temporal de
 * las planillas de forma precisa.
 */
import java.time.LocalDate;

/**
 * Clase DateTimeFormatter del paquete java.time.format para formatear fechas.
 * 
 * <p>Se utiliza para convertir objetos LocalDate a cadenas de texto con
 * formato específico (dd/MM/yyyy), facilitando la presentación de fechas
 * en comprobantes de pago, reportes y la interfaz de usuario.
 */
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una nómina o planilla de pago en el sistema.
 * 
 * <p>Esta clase encapsula toda la información relacionada con el cálculo
 * de la nómina de un empleado, incluyendo el salario bruto, deducciones
 * legales obligatorias, aportes patronales y el salario neto a pagar.
 * 
 * <p>Las nóminas se generan por periodo (quincenal, mensual, semanal) y
 * contienen el detalle completo de todos los conceptos que conforman el
 * pago final al empleado, así como las obligaciones patronales ante las
 * instituciones correspondientes (CCSS, IVM, SEM, Banco Popular, INA, etc.).
 * 
 * <p>Cada nómina tiene un identificador único, está asociada a un empleado
 * específico y registra la fecha de emisión para control y auditoría.
 * 
 * <h3>Deducciones contempladas:</h3>
 * <ul>
 *   <li>CCSS (Caja Costarricense de Seguro Social)</li>
 *   <li>IVM (Invalidez, Vejez y Muerte)</li>
 *   <li>SEM (Seguro de Enfermedad y Maternidad)</li>
 *   <li>Banco Popular</li>
 *   <li>Impuesto sobre la Renta</li>
 * </ul>
 * 
 * <h3>Aportes patronales contemplados:</h3>
 * <ul>
 *   <li>CCSS (Cuota patronal)</li>
 *   <li>IVM (Cuota patronal)</li>
 *   <li>SEM (Cuota patronal)</li>
 *   <li>INA (Instituto Nacional de Aprendizaje)</li>
 *   <li>FCL (Fondo de Capitalización Laboral)</li>
 *   <li>Asignaciones Familiares</li>
 * </ul>
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * Empleado empleado = new Empleado(...);
 * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
 * nomina.setDeduccionCCSS(25500.0);
 * nomina.setDeduccionImpuestoRenta(15000.0);
 * nomina.setSalarioNeto(760000.0);
 * double totalDeducciones = nomina.getTotalDeducciones();
 * </pre>
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 */
public class Nomina {
    
    // ========================================================================
    // ATRIBUTOS PRIVADOS
    // ========================================================================
    
    /**
     * Identificador único de la nómina en el sistema.
     * 
     * <p>Este ID es generado automáticamente por el sistema y se utiliza
     * como clave primaria para identificar de manera única cada registro
     * de nómina procesado.
     */
    private int id;
    
    /**
     * Empleado asociado a esta nómina.
     * 
     * <p>Referencia al objeto Empleado que contiene toda la información
     * personal y laboral del trabajador al que corresponde esta planilla
     * de pago.
     */
    private Empleado empleado;
    
    /**
     * Fecha de emisión de la nómina.
     * 
     * <p>Fecha en la que se generó y procesó esta planilla de pago.
     * Se establece automáticamente al momento de crear la nómina con
     * la fecha actual del sistema.
     */
    private LocalDate fechaEmision;
    
    /**
     * Descripción del periodo de pago de la nómina.
     * 
     * <p>Texto descriptivo que identifica el periodo al que corresponde
     * esta nómina, por ejemplo: "Primera Quincena Diciembre 2024",
     * "Mes de Noviembre 2024", "Semana del 1 al 7 de Diciembre".
     */
    private String periodo;
    
    /**
     * Salario bruto del empleado antes de deducciones.
     * 
     * <p>Monto total del salario base antes de aplicar cualquier deducción
     * legal u otra retención. Este valor se obtiene del salario bruto
     * configurado en el registro del empleado.
     */
    private double salarioBruto;
    
    /**
     * Salario neto a pagar al empleado.
     * 
     * <p>Monto final que recibirá el empleado después de aplicar todas
     * las deducciones correspondientes. Se calcula como:
     * salarioNeto = salarioBruto - totalDeducciones
     */
    private double salarioNeto;
    
    // ========================================================================
    // DEDUCCIONES
    // ========================================================================
    
    /**
     * Deducción por concepto de CCSS (Caja Costarricense de Seguro Social).
     * 
     * <p>Monto que se deduce del salario bruto del empleado como aporte
     * obrero a la Caja Costarricense de Seguro Social para cubrir
     * servicios de salud y prestaciones sociales.
     */
    private double deduccionCCSS;
    
    /**
     * Deducción por concepto de IVM (Invalidez, Vejez y Muerte).
     * 
     * <p>Monto que se deduce del salario bruto del empleado como aporte
     * al régimen de Invalidez, Vejez y Muerte, parte del sistema de
     * pensiones de Costa Rica.
     */
    private double deduccionIVM;
    
    /**
     * Deducción por concepto de SEM (Seguro de Enfermedad y Maternidad).
     * 
     * <p>Monto que se deduce del salario bruto del empleado como aporte
     * al Seguro de Enfermedad y Maternidad, que cubre atención médica
     * y prestaciones por maternidad.
     */
    private double deduccionSEM;
    
    /**
     * Deducción por aporte al Banco Popular.
     * 
     * <p>Monto que se deduce del salario bruto del empleado como aporte
     * obligatorio al Banco Popular y de Desarrollo Comunal, establecido
     * por ley en Costa Rica.
     */
    private double deduccionBancoPop;
    
    /**
     * Deducción por concepto de Impuesto sobre la Renta.
     * 
     * <p>Monto que se retiene del salario bruto del empleado en concepto
     * de impuesto sobre la renta, calculado según los tramos establecidos
     * por la legislación tributaria vigente.
     */
    private double deduccionImpuestoRenta;
    
    // ========================================================================
    // APORTES PATRONALES
    // ========================================================================
    
    /**
     * Aporte patronal a la CCSS (Caja Costarricense de Seguro Social).
     * 
     * <p>Monto que el patrono debe pagar a la CCSS como contribución
     * sobre el salario del empleado. Este es un costo para la empresa
     * adicional al salario bruto del trabajador.
     */
    private double aporteCCSS;
    
    /**
     * Aporte patronal al IVM (Invalidez, Vejez y Muerte).
     * 
     * <p>Monto que el patrono debe pagar al régimen de IVM como contribución
     * sobre el salario del empleado para el sistema de pensiones.
     */
    private double aporteIVM;
    
    /**
     * Aporte patronal al SEM (Seguro de Enfermedad y Maternidad).
     * 
     * <p>Monto que el patrono debe pagar al SEM como contribución sobre
     * el salario del empleado para cubrir servicios de salud.
     */
    private double aporteSEM;
    
    /**
     * Aporte patronal al INA (Instituto Nacional de Aprendizaje).
     * 
     * <p>Monto que el patrono debe pagar al INA como contribución sobre
     * el salario del empleado, destinado a financiar programas de
     * capacitación y formación técnica.
     */
    private double aporteINA;
    
    /**
     * Aporte patronal al FCL (Fondo de Capitalización Laboral).
     * 
     * <p>Monto que el patrono debe pagar al Fondo de Capitalización Laboral
     * como contribución sobre el salario del empleado, que se acumula
     * como prestación laboral.
     */
    private double aporteFCL;
    
    /**
     * Aporte patronal a Asignaciones Familiares.
     * 
     * <p>Monto que el patrono debe pagar al Fondo de Desarrollo Social y
     * Asignaciones Familiares (FODESAF) como contribución sobre el salario
     * del empleado, destinado a programas de ayuda social.
     */
    private double aporteAsignaciones;
    
    // ========================================================================
    // CONSTRUCTORES
    // ========================================================================
    
    /**
     * Constructor parametrizado que inicializa una nómina con datos básicos.
     * 
     * <p>Este constructor crea una nueva instancia de Nomina asociada a un
     * empleado específico y un periodo de pago. La fecha de emisión se
     * establece automáticamente con la fecha actual del sistema, y el
     * salario bruto se obtiene del registro del empleado.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Empleado empleado = new Empleado(1, "118520345", "Juan", "Pérez", 
     *                                  "González", "juan@email.com", 
     *                                  "88887777", 850000.0, "Quincenal", 
     *                                  "Desarrollador", LocalDate.of(2024, 1, 15));
     * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
     * </pre>
     * 
     * @param id identificador único de la nómina en el sistema
     * @param empleado objeto Empleado al que corresponde esta nómina
     * @param periodo descripción del periodo de pago (ej: "Primera Quincena Diciembre 2024")
     */
    public Nomina(int id, Empleado empleado, String periodo) {
        this.id = id;
        this.empleado = empleado;
        this.periodo = periodo;
        this.fechaEmision = LocalDate.now();
        this.salarioBruto = empleado.getSalarioBruto();
    }
    
    /**
     * Constructor por defecto que inicializa una nómina vacía.
     * 
     * <p>Crea una nueva instancia de Nomina sin inicializar ningún atributo.
     * Los valores deben ser establecidos posteriormente mediante los métodos
     * setter correspondientes.
     */
    public Nomina() {}
    
    // ========================================================================
    // MÉTODOS GETTER Y SETTER
    // ========================================================================
    
    /**
     * Obtiene el identificador único de la nómina.
     * 
     * @return el ID de la nómina como número entero (int)
     */
    public int getId() { 
        return id; 
    }
    
    /**
     * Establece el identificador único de la nómina.
     * 
     * @param id el identificador único a asignar a la nómina
     */
    public void setId(int id) { 
        this.id = id; 
    }
    
    /**
     * Obtiene el empleado asociado a esta nómina.
     * 
     * @return el objeto Empleado vinculado a esta planilla de pago
     */
    public Empleado getEmpleado() { 
        return empleado; 
    }
    
    /**
     * Establece el empleado asociado a esta nómina.
     * 
     * @param empleado el objeto Empleado a vincular con esta planilla
     */
    public void setEmpleado(Empleado empleado) { 
        this.empleado = empleado; 
    }
    
    /**
     * Obtiene la fecha de emisión de la nómina.
     * 
     * @return la fecha de emisión como objeto LocalDate
     */
    public LocalDate getFechaEmision() { 
        return fechaEmision; 
    }
    
    /**
     * Establece la fecha de emisión de la nómina.
     * 
     * @param fechaEmision la fecha en que se emitió la nómina
     */
    public void setFechaEmision(LocalDate fechaEmision) { 
        this.fechaEmision = fechaEmision; 
    }
    
    /**
     * Obtiene la descripción del periodo de pago.
     * 
     * @return el periodo de pago como cadena de texto (String)
     */
    public String getPeriodo() { 
        return periodo; 
    }
    
    /**
     * Establece la descripción del periodo de pago.
     * 
     * @param periodo la descripción del periodo a asignar
     */
    public void setPeriodo(String periodo) { 
        this.periodo = periodo; 
    }
    
    /**
     * Obtiene el salario bruto antes de deducciones.
     * 
     * @return el salario bruto como número decimal (double)
     */
    public double getSalarioBruto() { 
        return salarioBruto; 
    }
    
    /**
     * Establece el salario bruto antes de deducciones.
     * 
     * @param salarioBruto el salario bruto a asignar
     */
    public void setSalarioBruto(double salarioBruto) { 
        this.salarioBruto = salarioBruto; 
    }
    
    /**
     * Obtiene el salario neto a pagar al empleado.
     * 
     * @return el salario neto como número decimal (double)
     */
    public double getSalarioNeto() { 
        return salarioNeto; 
    }
    
    /**
     * Establece el salario neto a pagar al empleado.
     * 
     * @param salarioNeto el salario neto final a asignar
     */
    public void setSalarioNeto(double salarioNeto) { 
        this.salarioNeto = salarioNeto; 
    }
    
    /**
     * Obtiene la deducción por concepto de CCSS.
     * 
     * @return el monto de deducción CCSS como número decimal (double)
     */
    public double getDeduccionCCSS() { 
        return deduccionCCSS; 
    }
    
    /**
     * Establece la deducción por concepto de CCSS.
     * 
     * @param deduccionCCSS el monto a deducir por CCSS
     */
    public void setDeduccionCCSS(double deduccionCCSS) { 
        this.deduccionCCSS = deduccionCCSS; 
    }
    
    /**
     * Obtiene la deducción por concepto de IVM.
     * 
     * @return el monto de deducción IVM como número decimal (double)
     */
    public double getDeduccionIVM() { 
        return deduccionIVM; 
    }
    
    /**
     * Establece la deducción por concepto de IVM.
     * 
     * @param deduccionIVM el monto a deducir por IVM
     */
    public void setDeduccionIVM(double deduccionIVM) { 
        this.deduccionIVM = deduccionIVM; 
    }
    
    /**
     * Obtiene la deducción por concepto de SEM.
     * 
     * @return el monto de deducción SEM como número decimal (double)
     */
    public double getDeduccionSEM() { 
        return deduccionSEM; 
    }
    
    /**
     * Establece la deducción por concepto de SEM.
     * 
     * @param deduccionSEM el monto a deducir por SEM
     */
    public void setDeduccionSEM(double deduccionSEM) { 
        this.deduccionSEM = deduccionSEM; 
    }
    
    /**
     * Obtiene la deducción por aporte al Banco Popular.
     * 
     * @return el monto de deducción Banco Popular como número decimal (double)
     */
    public double getDeduccionBancoPop() { 
        return deduccionBancoPop; 
    }
    
    /**
     * Establece la deducción por aporte al Banco Popular.
     * 
     * @param deduccionBancoPop el monto a deducir por Banco Popular
     */
    public void setDeduccionBancoPop(double deduccionBancoPop) { 
        this.deduccionBancoPop = deduccionBancoPop; 
    }
    
    /**
     * Obtiene la deducción por Impuesto sobre la Renta.
     * 
     * @return el monto de deducción por impuesto como número decimal (double)
     */
    public double getDeduccionImpuestoRenta() { 
        return deduccionImpuestoRenta; 
    }
    
    /**
     * Establece la deducción por Impuesto sobre la Renta.
     * 
     * @param deduccionImpuestoRenta el monto a deducir por impuesto sobre la renta
     */
    public void setDeduccionImpuestoRenta(double deduccionImpuestoRenta) { 
        this.deduccionImpuestoRenta = deduccionImpuestoRenta; 
    }
    
    /**
     * Obtiene el aporte patronal a la CCSS.
     * 
     * @return el monto del aporte patronal CCSS como número decimal (double)
     */
    public double getAporteCCSS() { 
        return aporteCCSS; 
    }
    
    /**
     * Establece el aporte patronal a la CCSS.
     * 
     * @param aporteCCSS el monto del aporte patronal a CCSS
     */
    public void setAporteCCSS(double aporteCCSS) { 
        this.aporteCCSS = aporteCCSS; 
    }
    
    /**
     * Obtiene el aporte patronal al IVM.
     * 
     * @return el monto del aporte patronal IVM como número decimal (double)
     */
    public double getAporteIVM() { 
        return aporteIVM; 
    }
    
    /**
     * Establece el aporte patronal al IVM.
     * 
     * @param aporteIVM el monto del aporte patronal a IVM
     */
    public void setAporteIVM(double aporteIVM) { 
        this.aporteIVM = aporteIVM; 
    }
    
    /**
     * Obtiene el aporte patronal al SEM.
     * 
     * @return el monto del aporte patronal SEM como número decimal (double)
     */
    public double getAporteSEM() { 
        return aporteSEM; 
    }
    
    /**
     * Establece el aporte patronal al SEM.
     * 
     * @param aporteSEM el monto del aporte patronal a SEM
     */
    public void setAporteSEM(double aporteSEM) { 
        this.aporteSEM = aporteSEM; 
    }
    
    /**
     * Obtiene el aporte patronal al INA.
     * 
     * @return el monto del aporte patronal INA como número decimal (double)
     */
    public double getAporteINA() { 
        return aporteINA; 
    }
    
    /**
     * Establece el aporte patronal al INA.
     * 
     * @param aporteINA el monto del aporte patronal a INA
     */
    public void setAporteINA(double aporteINA) { 
        this.aporteINA = aporteINA; 
    }
    
    /**
     * Obtiene el aporte patronal al FCL.
     * 
     * @return el monto del aporte patronal FCL como número decimal (double)
     */
    public double getAporteFCL() { 
        return aporteFCL; 
    }
    
    /**
     * Establece el aporte patronal al FCL.
     * 
     * @param aporteFCL el monto del aporte patronal a FCL
     */
    public void setAporteFCL(double aporteFCL) { 
        this.aporteFCL = aporteFCL; 
    }
    
    /**
     * Obtiene el aporte patronal a Asignaciones Familiares.
     * 
     * @return el monto del aporte a asignaciones como número decimal (double)
     */
    public double getAporteAsignaciones() { 
        return aporteAsignaciones; 
    }
    
    /**
     * Establece el aporte patronal a Asignaciones Familiares.
     * 
     * @param aporteAsignaciones el monto del aporte a asignaciones familiares
     */
    public void setAporteAsignaciones(double aporteAsignaciones) { 
        this.aporteAsignaciones = aporteAsignaciones; 
    }
    
    // ========================================================================
    // MÉTODOS PÚBLICOS
    // ========================================================================
    
    /**
     * Calcula el total de deducciones aplicadas al salario del empleado.
     * 
     * <p>Este método suma todas las deducciones que se restan del salario
     * bruto del empleado para obtener el salario neto. Las deducciones
     * incluidas en el cálculo son:
     * <ul>
     *   <li>Deducción CCSS (Caja Costarricense de Seguro Social)</li>
     *   <li>Deducción Banco Popular</li>
     *   <li>Deducción Impuesto sobre la Renta</li>
     * </ul>
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
     * nomina.setDeduccionCCSS(25500.0);
     * nomina.setDeduccionBancoPop(8500.0);
     * nomina.setDeduccionImpuestoRenta(15000.0);
     * double totalDeducciones = nomina.getTotalDeducciones();
     * // Retorna: 49000.0
     * </pre>
     * 
     * @return el total de deducciones como número decimal (double),
     *         resultado de sumar deduccionCCSS + deduccionBancoPop + 
     *         deduccionImpuestoRenta
     */
    public double getTotalDeducciones() {
        return deduccionCCSS + deduccionBancoPop + deduccionImpuestoRenta;
    }
    
    /**
     * Calcula el total de aportes patronales que la empresa debe pagar.
     * 
     * <p>Este método suma todos los aportes patronales que constituyen
     * obligaciones legales del empleador sobre el salario del empleado.
     * Estos montos son adicionales al salario bruto y representan el costo
     * laboral total para la empresa. Los aportes incluidos en el cálculo son:
     * <ul>
     *   <li>Aporte patronal CCSS (Caja Costarricense de Seguro Social)</li>
     *   <li>Aporte patronal INA (Instituto Nacional de Aprendizaje)</li>
     *   <li>Aporte patronal FCL (Fondo de Capitalización Laboral)</li>
     *   <li>Aporte patronal Asignaciones Familiares</li>
     * </ul>
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
     * nomina.setAporteCCSS(68850.0);
     * nomina.setAporteINA(4250.0);
     * nomina.setAporteFCL(12750.0);
     * nomina.setAporteAsignaciones(21250.0);
     * double totalAportes = nomina.getTotalAportesPatronales();
     * // Retorna: 107100.0
     * </pre>
     * 
     * @return el total de aportes patronales como número decimal (double),
     *         resultado de sumar aporteCCSS + aporteINA + aporteFCL + 
     *         aporteAsignaciones
     */
    public double getTotalAportesPatronales() {
        return aporteCCSS + aporteINA + aporteFCL + aporteAsignaciones;
    }
    
    /**
     * Obtiene la fecha de emisión de la nómina en formato legible.
     * 
     * <p>Convierte la fecha de emisión almacenada como LocalDate a una
     * cadena de texto con formato dd/MM/yyyy (día/mes/año), facilitando
     * su presentación en comprobantes de pago, reportes y la interfaz
     * de usuario.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
     * String fechaFormateada = nomina.getFechaEmisionFormateada();
     * System.out.println(fechaFormateada); // Imprime: 19/12/2024 (si hoy es 19/12/2024)
     * </pre>
     * 
     * @return la fecha de emisión formateada como String en formato
     *         "dd/MM/yyyy"
     */
    public String getFechaEmisionFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaEmision.format(formatter);
    }
}
