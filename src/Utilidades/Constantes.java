/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

/**
 * Clase que centraliza todas las constantes del sistema de nómina de acuerdo
 * con la legislación laboral y tributaria de Costa Rica.
 * 
 * <p>Esta clase contiene valores fijos que se utilizan en los cálculos de:
 * <ul>
 *   <li>Deducciones de ley aplicadas al salario del empleado</li>
 *   <li>Aportes patronales que debe pagar el empleador</li>
 *   <li>Tramos de impuesto sobre la renta</li>
 *   <li>Configuración del sistema de correo electrónico</li>
 *   <li>Tipos de planilla disponibles</li>
 * </ul>
 * 
 * <p>Nota: Los valores de deducciones e impuestos están actualizados según
 * la legislación vigente al año 2024.
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 */
public class Constantes {
    
    // ========================================================================
    // DEDUCCIONES DEL EMPLEADO
    // ========================================================================
    
    /**
     * Porcentaje de deducción para la Caja Costarricense de Seguro Social (CCSS).
     * 
     * <p>Esta deducción se aplica sobre el salario bruto del empleado para
     * financiar el seguro de salud y otros beneficios de la seguridad social.
     * 
     * <p>Valor: 10.67% del salario bruto
     */
    public static final double DEDUCCION_CCSS = 0.1067;
    
    /**
     * Porcentaje de deducción para Invalidez, Vejez y Muerte (IVM).
     * 
     * <p>Esta deducción contribuye al fondo de pensiones del régimen de
     * Invalidez, Vejez y Muerte, que garantiza una pensión al empleado
     * cuando se retire o en caso de invalidez.
     * 
     * <p>Valor: 4.17% del salario bruto
     */
    public static final double DEDUCCION_IVM = 0.0417;
    
    /**
     * Porcentaje de deducción para el Seguro de Enfermedad y Maternidad (SEM).
     * 
     * <p>Este aporte cubre los gastos médicos, hospitalarios y subsidios
     * por enfermedad o maternidad del trabajador y sus beneficiarios.
     * 
     * <p>Valor: 6.50% del salario bruto
     */
    public static final double DEDUCCION_SEM = 0.0650;
    
    /**
     * Porcentaje de aporte obligatorio al Banco Popular y de Desarrollo Comunal.
     * 
     * <p>Esta deducción es obligatoria para todos los trabajadores asalariados
     * y se destina a apoyar las actividades del Banco Popular.
     * 
     * <p>Valor: 1.00% del salario bruto
     */
    public static final double DEDUCCION_BANCO_POPULAR = 0.01;
    
    // ========================================================================
    // APORTES PATRONALES
    // ========================================================================
    
    /**
     * Porcentaje de aporte patronal a la Caja Costarricense de Seguro Social (CCSS).
     * 
     * <p>Este es el aporte que debe realizar el empleador sobre el salario
     * bruto del trabajador para financiar el sistema de seguridad social.
     * Este monto NO se deduce del salario del empleado.
     * 
     * <p>Valor: 26.67% del salario bruto
     */
    public static final double APORTE_CCSS = 0.2667;
    
    /**
     * Porcentaje de aporte patronal para Invalidez, Vejez y Muerte (IVM).
     * 
     * <p>Aporte del empleador al régimen de pensiones. Este valor se suma
     * a la deducción que hace el empleado para conformar el fondo total.
     * 
     * <p>Valor: 7.08% del salario bruto
     */
    public static final double APORTE_IVM = 0.0708;
    
    /**
     * Porcentaje de aporte patronal para el Seguro de Enfermedad y Maternidad (SEM).
     * 
     * <p>Contribución del empleador para el seguro de salud del trabajador.
     * 
     * <p>Valor: 10.59% del salario bruto
     */
    public static final double APORTE_SEM = 0.1059;
    
    /**
     * Porcentaje de aporte patronal al Instituto Nacional de Aprendizaje (INA).
     * 
     * <p>Este aporte financia los programas de capacitación técnica y
     * formación profesional que ofrece el INA a la población.
     * 
     * <p>Valor: 1.50% del salario bruto
     */
    public static final double APORTE_INA = 0.015;
    
    /**
     * Porcentaje de aporte patronal al Fondo de Capitalización Laboral (FCL).
     * 
     * <p>Este fondo se acumula a favor del trabajador y le será entregado
     * cuando finalice la relación laboral, ya sea por renuncia o despido.
     * 
     * <p>Valor: 3.00% del salario bruto
     */
    public static final double APORTE_FCL = 0.03;
    
    /**
     * Porcentaje de aporte patronal para Asignaciones Familiares.
     * 
     * <p>Este aporte financia el programa de asignaciones familiares que
     * beneficia a familias de escasos recursos en Costa Rica.
     * 
     * <p>Valor: 5.00% del salario bruto
     */
    public static final double APORTE_ASIGNACIONES = 0.05;
    
    // ========================================================================
    // TRAMOS DE IMPUESTO SOBRE LA RENTA 2024
    // ========================================================================
    
    /**
     * Monto base libre de impuesto sobre la renta mensual.
     * 
     * <p>Los salarios mensuales hasta este monto NO pagan impuesto sobre la renta.
     * Este es el primer tramo de la tabla de impuestos.
     * 
     * <p>Valor: ₡941,000 mensuales
     */
    public static final double RENTA_BASE_LIBRE = 941000;
    
    /**
     * Límite superior del primer tramo de impuesto sobre la renta.
     * 
     * <p>Los ingresos entre la base libre (₡941,000) y este límite
     * pagan el porcentaje del tramo 1.
     * 
     * <p>Valor: ₡1,405,000 mensuales
     */
    public static final double RENTA_TRAMO1_LIMITE = 1405000;
    
    /**
     * Porcentaje de impuesto aplicable al primer tramo de renta.
     * 
     * <p>Se aplica sobre el excedente de ₡941,000 hasta ₡1,405,000.
     * 
     * <p>Valor: 10%
     */
    public static final double RENTA_TRAMO1_PORCENTAJE = 0.10;
    
    /**
     * Límite superior del segundo tramo de impuesto sobre la renta.
     * 
     * <p>Los ingresos entre ₡1,405,000 y este límite pagan el porcentaje del tramo 2.
     * 
     * <p>Valor: ₡2,108,000 mensuales
     */
    public static final double RENTA_TRAMO2_LIMITE = 2108000;
    
    /**
     * Porcentaje de impuesto aplicable al segundo tramo de renta.
     * 
     * <p>Se aplica sobre el excedente de ₡1,405,000 hasta ₡2,108,000.
     * 
     * <p>Valor: 15%
     */
    public static final double RENTA_TRAMO2_PORCENTAJE = 0.15;
    
    /**
     * Límite superior del tercer tramo de impuesto sobre la renta.
     * 
     * <p>Los ingresos entre ₡2,108,000 y este límite pagan el porcentaje del tramo 3.
     * 
     * <p>Valor: ₡4,215,000 mensuales
     */
    public static final double RENTA_TRAMO3_LIMITE = 4215000;
    
    /**
     * Porcentaje de impuesto aplicable al tercer tramo de renta.
     * 
     * <p>Se aplica sobre el excedente de ₡2,108,000 hasta ₡4,215,000.
     * 
     * <p>Valor: 20%
     */
    public static final double RENTA_TRAMO3_PORCENTAJE = 0.20;
    
    /**
     * Porcentaje de impuesto aplicable al cuarto tramo de renta.
     * 
     * <p>Se aplica sobre cualquier ingreso que exceda los ₡4,215,000 mensuales.
     * Este es el tramo más alto de la tabla de impuestos.
     * 
     * <p>Valor: 25%
     */
    public static final double RENTA_TRAMO4_PORCENTAJE = 0.25;
    
    // ========================================================================
    // CONFIGURACIÓN DE CORREO ELECTRÓNICO
    // ========================================================================
    
    /**
     * Servidor SMTP para el envío de correos electrónicos.
     * 
     * <p>Este es el servidor de correo que se utiliza para enviar
     * notificaciones, comprobantes de pago y otros documentos del sistema.
     */
    public static final String SMTP_HOST = "securemail.comredcr.com";
    
    /**
     * Puerto del servidor SMTP.
     * 
     * <p>Puerto 465 corresponde a conexión SSL/TLS segura.
     */
    public static final String SMTP_PORT = "465";
    
    /**
     * Dirección de correo electrónico remitente del sistema.
     * 
     * <p>Todos los correos del sistema se enviarán desde esta dirección.
     */
    public static final String EMAIL_FROM = "curso_progra2@comredcr.com";
    
    /**
     * Contraseña de la cuenta de correo del sistema.
     * 
     * <p><b>NOTA DE SEGURIDAD:</b> En un entorno de producción, esta contraseña
     * debería almacenarse en un archivo de configuración externo o en variables
     * de entorno, nunca directamente en el código fuente.
     */
    public static final String EMAIL_PASSWORD = "u6X1h1p9@";
    
    // ========================================================================
    // TIPOS DE PLANILLA
    // ========================================================================
    
    /**
     * Identificador para planillas de pago quincenal.
     * 
     * <p>Indica que los empleados reciben su salario cada quince días.
     */
    public static final String PLANILLA_QUINCENAL = "QUINCENAL";
    
    /**
     * Identificador para planillas de pago mensual.
     * 
     * <p>Indica que los empleados reciben su salario una vez al mes.
     */
    public static final String PLANILLA_MENSUAL = "MENSUAL";
    
    /**
     * Constructor privado para prevenir la instanciación de esta clase.
     * 
     * <p>Esta clase solo contiene constantes estáticas y no debe ser instanciada.
     * El constructor privado garantiza que nadie pueda crear objetos de esta clase.
     * 
     * @throws UnsupportedOperationException si se intenta instanciar mediante reflexión
     */
    private Constantes() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no debe ser instanciada");
    }
}