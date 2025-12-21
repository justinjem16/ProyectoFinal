package LogicaNegocio;

/**
 * Clase Nomina del paquete Entidades que representa una planilla de pago.
 * 
 * <p>Se utiliza en esta clase para realizar los cálculos de deducciones,
 * aportes patronales y salario neto, almacenando todos los resultados
 * en el objeto Nomina correspondiente.
 */
import Entidades.Nomina;

/**
 * Clase Constantes del paquete Utilidades con valores fijos del sistema.
 * 
 * <p>Se utiliza para obtener los porcentajes y límites establecidos por
 * ley para el cálculo de deducciones (CCSS, IVM, SEM, Banco Popular,
 * Impuesto sobre la Renta) y aportes patronales (INA, FCL, Asignaciones),
 * garantizando consistencia en los cálculos.
 */
import Utilidades.Constantes;

/**
 * Clase que realiza todos los cálculos relacionados con la nómina de empleados.
 * 
 * <p>Esta clase implementa la lógica de negocio para calcular las deducciones
 * legales obligatorias, los aportes patronales y el salario neto de un empleado
 * según la legislación laboral y tributaria de Costa Rica.
 * 
 * <p>Los cálculos incluyen:
 * <ul>
 *   <li><strong>Deducciones al empleado:</strong> CCSS (IVM + SEM), Banco Popular
 *       e Impuesto sobre la Renta calculado por tramos progresivos</li>
 *   <li><strong>Aportes patronales:</strong> CCSS patronal (IVM + SEM), INA,
 *       FCL y Asignaciones Familiares</li>
 *   <li><strong>Salario neto:</strong> Resultado de restar las deducciones
 *       del salario bruto</li>
 * </ul>
 * 
 * <p>Esta clase utiliza los porcentajes y límites definidos en la clase
 * {@link Utilidades.Constantes} para garantizar que los cálculos se realicen
 * de acuerdo con las normativas vigentes y puedan actualizarse fácilmente
 * cuando cambien las regulaciones.
 * 
 * <h3>Impuesto sobre la Renta:</h3>
 * <p>El impuesto se calcula mediante un sistema de tramos progresivos:
 * <ul>
 *   <li>Base libre: Sin impuesto</li>
 *   <li>Tramo 1: 10% sobre el excedente de la base libre</li>
 *   <li>Tramo 2: 15% sobre el excedente del tramo 1</li>
 *   <li>Tramo 3: 20% sobre el excedente del tramo 2</li>
 *   <li>Tramo 4: 25% sobre el excedente del tramo 3</li>
 * </ul>
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * CalculadoraNomina calculadora = new CalculadoraNomina();
 * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
 * 
 * // Calcular todo de una vez
 * calculadora.calcularNominaCompleta(nomina);
 * 
 * // O calcular por partes
 * calculadora.calcularDeducciones(nomina);
 * calculadora.calcularAportesPatronales(nomina);
 * calculadora.calcularSalarioNeto(nomina);
 * 
 * double salarioNeto = nomina.getSalarioNeto();
 * </pre>
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 */
public class CalculadoraNomina {
    
    /**
     * Calcula todas las deducciones del empleado.
     */
    public void calcularDeducciones(Nomina nomina) {
        double salarioBruto = nomina.getSalarioBruto();
        
        // CCSS
        nomina.setDeduccionIVM(salarioBruto * Constantes.DEDUCCION_IVM);
        nomina.setDeduccionSEM(salarioBruto * Constantes.DEDUCCION_SEM);
        nomina.setDeduccionCCSS(nomina.getDeduccionIVM() + nomina.getDeduccionSEM());
        
        // Banco Popular
        nomina.setDeduccionBancoPop(salarioBruto * Constantes.DEDUCCION_BANCO_POPULAR);
        
        // Impuesto Renta
        nomina.setDeduccionImpuestoRenta(calcularImpuestoRenta(salarioBruto));
    }
    
    /**
     * Calcula impuesto sobre la renta por tramos.
     */
    private double calcularImpuestoRenta(double salarioBruto) {
        if (salarioBruto <= Constantes.RENTA_BASE_LIBRE) {
            return 0;
        }
        
        double impuesto = 0;
         
        // Tramo 1: 10%
        if (salarioBruto > Constantes.RENTA_BASE_LIBRE) {
            double montoTramo1 = Math.min(
                salarioBruto - Constantes.RENTA_BASE_LIBRE,
                Constantes.RENTA_TRAMO1_LIMITE - Constantes.RENTA_BASE_LIBRE
            );
            impuesto += montoTramo1 * Constantes.RENTA_TRAMO1_PORCENTAJE;
        }
        
        // Tramo 2: 15%
        if (salarioBruto > Constantes.RENTA_TRAMO1_LIMITE) {
            double montoTramo2 = Math.min(
                salarioBruto - Constantes.RENTA_TRAMO1_LIMITE,
                Constantes.RENTA_TRAMO2_LIMITE - Constantes.RENTA_TRAMO1_LIMITE
            );
            impuesto += montoTramo2 * Constantes.RENTA_TRAMO2_PORCENTAJE;
        }
        
        // Tramo 3: 20%
        if (salarioBruto > Constantes.RENTA_TRAMO2_LIMITE) {
            double montoTramo3 = Math.min(
                salarioBruto - Constantes.RENTA_TRAMO2_LIMITE,
                Constantes.RENTA_TRAMO3_LIMITE - Constantes.RENTA_TRAMO2_LIMITE
            );
            impuesto += montoTramo3 * Constantes.RENTA_TRAMO3_PORCENTAJE;
        }
        
        // Tramo 4: 25%
        if (salarioBruto > Constantes.RENTA_TRAMO3_LIMITE) {
            double montoTramo4 = salarioBruto - Constantes.RENTA_TRAMO3_LIMITE;
            impuesto += montoTramo4 * Constantes.RENTA_TRAMO4_PORCENTAJE;
        }
        
        return impuesto;
    }
    
    /**
     * Calcula todos los aportes patronales.
     */
    public void calcularAportesPatronales(Nomina nomina) {
        double salarioBruto = nomina.getSalarioBruto();
        
        // CCSS Patronal
        nomina.setAporteIVM(salarioBruto * Constantes.APORTE_IVM);
        nomina.setAporteSEM(salarioBruto * Constantes.APORTE_SEM);
        nomina.setAporteCCSS(nomina.getAporteIVM() + nomina.getAporteSEM());
        
        // Otros aportes
        nomina.setAporteINA(salarioBruto * Constantes.APORTE_INA);
        nomina.setAporteFCL(salarioBruto * Constantes.APORTE_FCL);
        nomina.setAporteAsignaciones(salarioBruto * Constantes.APORTE_ASIGNACIONES);
    }
    
    /**
     * Calcula el salario neto.
     */
    public void calcularSalarioNeto(Nomina nomina) {
        double salarioNeto = nomina.getSalarioBruto() - nomina.getTotalDeducciones();
        nomina.setSalarioNeto(salarioNeto);
    }
    
    /**
     * Calcula la nómina completa.
     */
    public void calcularNominaCompleta(Nomina nomina) {
        calcularDeducciones(nomina);
        calcularAportesPatronales(nomina);
        calcularSalarioNeto(nomina);
    }
}
