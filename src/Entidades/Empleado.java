package Entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Representa un empleado del sistema de nómina.
 * 
 * @author Rachell Mora Reyes
 */
public class Empleado {
    
    private int id;
    private String cedula;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private String telefono;
    private double salarioBruto;
    private String tipoPlanilla;
    private String puesto;
    private LocalDate fechaIngreso; // NUEVO CAMPO AGREGADO
    private ArrayList<Empleado> listaEmpleados;
    
    public Empleado(int id, String cedula, String nombre, String apellido1, 
                    String apellido2, String email, String telefono, 
                    double salarioBruto, String tipoPlanilla, String puesto, 
                    LocalDate fechaIngreso) { // PARÁMETRO AGREGADO
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.telefono = telefono;
        this.salarioBruto = salarioBruto;
        this.tipoPlanilla = tipoPlanilla;
        this.puesto = puesto;
        this.fechaIngreso = fechaIngreso; // INICIALIZACIÓN AGREGADA
    }
    
    public Empleado() {
        listaEmpleados = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }
    
    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public double getSalarioBruto() { return salarioBruto; }
    public void setSalarioBruto(double salarioBruto) { this.salarioBruto = salarioBruto; }
    
    public String getTipoPlanilla() { return tipoPlanilla; }
    public void setTipoPlanilla(String tipoPlanilla) { this.tipoPlanilla = tipoPlanilla; }
    
    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    
    // GETTER Y SETTER PARA FECHA DE INGRESO - NUEVOS
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public ArrayList<Empleado> getListaEmpleados() { return listaEmpleados; }
    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) { 
        this.listaEmpleados = listaEmpleados; 
    }
    
    public void agregarListaEmpleados(Empleado empleado) {
        this.listaEmpleados.add(empleado);
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido1 + " " + apellido2;
    }
    
    // MÉTODO NUEVO PARA FORMATEAR LA FECHA DE INGRESO
    public String getFechaIngresoFormateada() {
        if (fechaIngreso == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaIngreso.format(formatter);
    }
}
