
package Entidades;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Nomina {
// Identificador único de la nómina
    private int id;
    
    // Empleado al que pertenece la nómina
    private Empleado empleado;
    
    // Fecha en la que se emite la nómina
    private LocalDate fechaEmision;
    
    // Periodo de pago de la nómina (ej: Enero 2025)
    private String periodo;
    
    // Salario bruto del empleado
    private double salarioBruto;
    
    // Salario neto después de deducciones
    private double salarioNeto;
    
    // ================= DEDUCCIONES =================
    
    // Deducción por CCSS
    private double deduccionCCSS;
    
    // Deducción por IVM
    private double deduccionIVM;
    
    // Deducción por Seguro de Enfermedad y Maternidad
    private double deduccionSEM;
    
    // Deducción al Banco Popular
    private double deduccionBancoPop;
    
    // Deducción por impuesto sobre la renta
    private double deduccionImpuestoRenta;
    
    // ============== APORTES PATRONALES ==============
    
    // Aporte patronal a la CCSS
    private double aporteCCSS;
    
    // Aporte patronal al IVM
    private double aporteIVM;
    
    // Aporte patronal al SEM
    private double aporteSEM;
    
    // Aporte patronal al INA
    private double aporteINA;
    
    // Aporte patronal al Fondo de Capitalización Laboral
    private double aporteFCL;
    
    // Aporte patronal a asignaciones familiares
    private double aporteAsignaciones;
    
    // Constructor con parámetros
    public Nomina(int id, Empleado empleado, String periodo) {
        // Asigna el id de la nómina
        this.id = id;
        
        // Asigna el empleado al que pertenece la nómina
        this.empleado = empleado;
        
        // Asigna el periodo de pago
        this.periodo = periodo;
        
        // Asigna la fecha actual como fecha de emisión
        this.fechaEmision = LocalDate.now();
        
        // Obtiene el salario bruto desde el empleado
        this.salarioBruto = empleado.getSalarioBruto();
    }
    
    // Constructor vacío
    public Nomina() {}
    
    // ================= GETTERS Y SETTERS =================
    
    // Devuelve el id de la nómina
    public int getId() { 
        return id; 
    }
    
    // Asigna un valor al id de la nómina
    public void setId(int id) { 
        this.id = id; 
    }
    
    // Devuelve el empleado asociado a la nómina
    public Empleado getEmpleado() { 
        return empleado; 
    }
    
    // Asigna un empleado a la nómina
    public void setEmpleado(Empleado empleado) { 
        this.empleado = empleado; 
    }
    
    // Devuelve la fecha de emisión
    public LocalDate getFechaEmision() { 
        return fechaEmision; 
    }
    
    // Asigna una nueva fecha de emisión
    public void setFechaEmision(LocalDate fechaEmision) { 
        this.fechaEmision = fechaEmision; 
    }
    
    // Devuelve el periodo de la nómina
    public String getPeriodo() { 
        return periodo; 
    }
    
    // Asigna un valor al periodo
    public void setPeriodo(String periodo) { 
        this.periodo = periodo; 
    }
    
    // Devuelve el salario bruto
    public double getSalarioBruto() { 
        return salarioBruto; 
    }
    
    // Asigna el salario bruto
    public void setSalarioBruto(double salarioBruto) { 
        this.salarioBruto = salarioBruto; 
    }
    
    // Devuelve el salario neto
    public double getSalarioNeto() { 
        return salarioNeto; 
    }
    
    // Asigna el salario neto
    public void setSalarioNeto(double salarioNeto) { 
        this.salarioNeto = salarioNeto; 
    }
    
    // Devuelve la deducción CCSS
    public double getDeduccionCCSS() { 
        return deduccionCCSS; 
    }
    
    // Asigna la deducción CCSS
    public void setDeduccionCCSS(double deduccionCCSS) { 
        this.deduccionCCSS = deduccionCCSS; 
    }
    
    // Devuelve la deducción IVM
    public double getDeduccionIVM() { 
        return deduccionIVM; 
    }
    
    // Asigna la deducción IVM
    public void setDeduccionIVM(double deduccionIVM) { 
        this.deduccionIVM = deduccionIVM; 
    }
    
    // Devuelve la deducción SEM
    public double getDeduccionSEM() { 
        return deduccionSEM; 
    }
    
    // Asigna la deducción SEM
    public void setDeduccionSEM(double deduccionSEM) { 
        this.deduccionSEM = deduccionSEM; 
    }
    
    // Devuelve la deducción Banco Popular
    public double getDeduccionBancoPop() { 
        return deduccionBancoPop; 
    }
    
    // Asigna la deducción Banco Popular
    public void setDeduccionBancoPop(double deduccionBancoPop) { 
        this.deduccionBancoPop = deduccionBancoPop; 
    }
    
    // Devuelve la deducción de impuesto sobre la renta
    public double getDeduccionImpuestoRenta() { 
        return deduccionImpuestoRenta; 
    }
    
    // Asigna la deducción de impuesto sobre la renta
    public void setDeduccionImpuestoRenta(double deduccionImpuestoRenta) { 
        this.deduccionImpuestoRenta = deduccionImpuestoRenta; 
    }
    
    // Devuelve el aporte CCSS
    public double getAporteCCSS() { 
        return aporteCCSS; 
    }
    
    // Asigna el aporte CCSS
    public void setAporteCCSS(double aporteCCSS) { 
        this.aporteCCSS = aporteCCSS; 
    }
    
    // Devuelve el aporte IVM
    public double getAporteIVM() { 
        return aporteIVM; 
    }
    
    // Asigna el aporte IVM
    public void setAporteIVM(double aporteIVM) { 
        this.aporteIVM = aporteIVM; 
    }
    
    // Devuelve el aporte SEM
    public double getAporteSEM() { 
        return aporteSEM; 
    }
    
    // Asigna el aporte SEM
    public void setAporteSEM(double aporteSEM) { 
        this.aporteSEM = aporteSEM; 
    }
    
    // Devuelve el aporte INA
    public double getAporteINA() { 
        return aporteINA; 
    }
    
    // Asigna el aporte INA
    public void setAporteINA(double aporteINA) { 
        this.aporteINA = aporteINA; 
    }
    
    // Devuelve el aporte FCL
    public double getAporteFCL() { 
        return aporteFCL; 
    }
    
    // Asigna el aporte FCL
    public void setAporteFCL(double aporteFCL) { 
        this.aporteFCL = aporteFCL; 
    }
    
    // Devuelve el aporte de asignaciones familiares
    public double getAporteAsignaciones() { 
        return aporteAsignaciones; 
    }
    
    // Asigna el aporte de asignaciones familiares
    public void setAporteAsignaciones(double aporteAsignaciones) { 
        this.aporteAsignaciones = aporteAsignaciones; 
    }
    
    // Calcula y devuelve el total de deducciones del empleado
    public double getTotalDeducciones() {
        return deduccionCCSS + deduccionBancoPop + deduccionImpuestoRenta;
    }
    
    // Calcula y devuelve el total de aportes patronales
    public double getTotalAportesPatronales() {
        return aporteCCSS + aporteINA + aporteFCL + aporteAsignaciones;
    }
    
    // Devuelve la fecha de emisión con formato dd/MM/yyyy
    public String getFechaEmisionFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaEmision.format(formatter);
    } 
}
