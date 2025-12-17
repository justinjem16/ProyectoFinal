
package LogicaNegocio;
// Importa la clase Nomina que contiene los datos del empleado y los resultados
import Entidades.Nomina;

// Importa las constantes de porcentajes y límites salariales
import Utilidades.Constantes;
/**
 * Clase encargada de realizar todos los cálculos relacionados
 * con la nómina de un empleado.
 * 
 * Incluye:
 * - Deducciones del trabajador
 * - Aportes patronales
 * - Cálculo del salario neto
 */
public class CalculadoraNomina {
  /**
     * Calcula todas las deducciones que se aplican al salario bruto
     * del empleado y las guarda dentro del objeto {@link Nomina}.
     * 
     * @param nomina Objeto que contiene el salario bruto y donde se
     *               almacenan las deducciones calculadas.
     */
    public void calcularDeducciones(Nomina nomina) {

        // Obtiene el salario bruto del empleado
        double salarioBruto = nomina.getSalarioBruto();
        
        /* =======================
         * Deducciones CCSS
         * ======================= */
        
        // Calcula la deducción por IVM (Invalidez, Vejez y Muerte)
        nomina.setDeduccionIVM(salarioBruto * Constantes.DEDUCCION_IVM);

        // Calcula la deducción por SEM (Seguro de Enfermedad y Maternidad)
        nomina.setDeduccionSEM(salarioBruto * Constantes.DEDUCCION_SEM);

        // Suma ambas deducciones para obtener el total de CCSS
        nomina.setDeduccionCCSS(
            nomina.getDeduccionIVM() + nomina.getDeduccionSEM()
        );
        
        /* =======================
         * Deducción Banco Popular
         * ======================= */
        
        // Calcula la deducción obligatoria para el Banco Popular
        nomina.setDeduccionBancoPop(
            salarioBruto * Constantes.DEDUCCION_BANCO_POPULAR
        );
        
        /* =======================
         * Impuesto sobre la Renta
         * ======================= */
        
        // Calcula el impuesto sobre la renta por tramos salariales
        nomina.setDeduccionImpuestoRenta(
            calcularImpuestoRenta(salarioBruto)
        );
    }
    
    /**
     * Calcula el impuesto sobre la renta aplicando los tramos
     * establecidos por ley.
     * 
     * Cada tramo grava únicamente el monto que cae dentro de su rango.
     * 
     * @param salarioBruto Salario total del empleado antes de deducciones.
     * @return Monto total del impuesto sobre la renta.
     */
    private double calcularImpuestoRenta(double salarioBruto) {

        // Si el salario está dentro del rango exento, no paga impuesto
        if (salarioBruto <= Constantes.RENTA_BASE_LIBRE) {
            return 0;
        }
        
        // Variable acumuladora del impuesto total
        double impuesto = 0;
         
        /* =======================
         * Tramo 1 (10%)
         * ======================= */
        
        if (salarioBruto > Constantes.RENTA_BASE_LIBRE) {

            // Calcula cuánto del salario cae en este tramo
            double montoTramo1 = Math.min(
                salarioBruto - Constantes.RENTA_BASE_LIBRE,
                Constantes.RENTA_TRAMO1_LIMITE - Constantes.RENTA_BASE_LIBRE
            );

            // Aplica el porcentaje correspondiente
            impuesto += montoTramo1 * Constantes.RENTA_TRAMO1_PORCENTAJE;
        }
        
        /* =======================
         * Tramo 2 (15%)
         * ======================= */
        
        if (salarioBruto > Constantes.RENTA_TRAMO1_LIMITE) {

            double montoTramo2 = Math.min(
                salarioBruto - Constantes.RENTA_TRAMO1_LIMITE,
                Constantes.RENTA_TRAMO2_LIMITE - Constantes.RENTA_TRAMO1_LIMITE
            );

            impuesto += montoTramo2 * Constantes.RENTA_TRAMO2_PORCENTAJE;
        }
        
        /* =======================
         * Tramo 3 (20%)
         * ======================= */
        
        if (salarioBruto > Constantes.RENTA_TRAMO2_LIMITE) {

            double montoTramo3 = Math.min(
                salarioBruto - Constantes.RENTA_TRAMO2_LIMITE,
                Constantes.RENTA_TRAMO3_LIMITE - Constantes.RENTA_TRAMO2_LIMITE
            );

            impuesto += montoTramo3 * Constantes.RENTA_TRAMO3_PORCENTAJE;
        }
        
        /* =======================
         * Tramo 4 (25%)
         * ======================= */
        
        if (salarioBruto > Constantes.RENTA_TRAMO3_LIMITE) {

            // Todo lo que exceda el último tramo se grava al 25%
            double montoTramo4 = salarioBruto - Constantes.RENTA_TRAMO3_LIMITE;

            impuesto += montoTramo4 * Constantes.RENTA_TRAMO4_PORCENTAJE;
        }
        
        // Retorna el impuesto total calculado
        return impuesto;
    }
    
    /**
     * Calcula los aportes patronales que debe pagar la empresa
     * por el empleado.
     * 
     * @param nomina Objeto donde se guardan los aportes calculados.
     */
    public void calcularAportesPatronales(Nomina nomina) {

        // Obtiene el salario bruto del empleado
        double salarioBruto = nomina.getSalarioBruto();
        
        /* =======================
         * Aportes CCSS Patronales
         * ======================= */
        
        // Aporte patronal al IVM
        nomina.setAporteIVM(salarioBruto * Constantes.APORTE_IVM);

        // Aporte patronal al SEM
        nomina.setAporteSEM(salarioBruto * Constantes.APORTE_SEM);

        // Total de aportes patronales a la CCSS
        nomina.setAporteCCSS(
            nomina.getAporteIVM() + nomina.getAporteSEM()
        );
        
        /* =======================
         * Otros aportes patronales
         * ======================= */
        
        // Aporte al INA
        nomina.setAporteINA(salarioBruto * Constantes.APORTE_INA);

        // Aporte al Fondo de Capitalización Laboral
        nomina.setAporteFCL(salarioBruto * Constantes.APORTE_FCL);

        // Aporte a Asignaciones Familiares
        nomina.setAporteAsignaciones(
            salarioBruto * Constantes.APORTE_ASIGNACIONES
        );
    }
    
    /**
     * Calcula el salario neto del empleado restando
     * todas las deducciones al salario bruto.
     * 
     * @param nomina Objeto que contiene los datos salariales.
     */
    public void calcularSalarioNeto(Nomina nomina) {

        // Salario neto = salario bruto - total de deducciones
        double salarioNeto = 
            nomina.getSalarioBruto() - nomina.getTotalDeducciones();

        // Guarda el salario neto en la nómina
        nomina.setSalarioNeto(salarioNeto);
    }
    
    /**
     * Realiza el cálculo completo de la nómina:
     * 1. Deducciones del empleado
     * 2. Aportes patronales
     * 3. Salario neto
     * 
     * @param nomina Objeto nómina a procesar.
     */
    public void calcularNominaCompleta(Nomina nomina) {

        // Calcula deducciones
        calcularDeducciones(nomina);

        // Calcula aportes patronales
        calcularAportesPatronales(nomina);

        // Calcula salario neto
        calcularSalarioNeto(nomina);
    }  
}
