/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import Entidades.Nomina;
import Utilidades.Constantes;

/**
 * Calculadora de nómina según legislación de Costa Rica.
 * 
 * @author Rachell Mora Reyes
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
