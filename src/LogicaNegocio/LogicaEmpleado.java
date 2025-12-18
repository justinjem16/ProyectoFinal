/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import AccesoDatos.IdControl;
import Entidades.Empleado;
import Utilidades.NombresArchivos;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Lógica de negocio para empleados.
 * 
 * @author Rachell Mora Reyes
 */
public class LogicaEmpleado extends LogicaBase {
    
    private IdControl idControl;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public LogicaEmpleado() {
        accesoDatos.setNombreArchivo(NombresArchivos.EMPLEADOS.getNombreArchivo());
    }
    
    public void agregarEmpleado(Empleado empleado) throws IOException {
        idControl = new IdControl();
        empleado.setId(idControl.getNextId(NombresArchivos.EMPLEADOS.getNombreArchivo()));
        
        // FORMATO MODIFICADO: Ahora incluye la fecha de ingreso al final
        accesoDatos.setRegistro(
            String.valueOf(empleado.getId()) + "," +
            empleado.getCedula() + "," +
            empleado.getNombre() + "," +
            empleado.getApellido1() + "," +
            empleado.getApellido2() + "," +
            empleado.getEmail() + "," +
            empleado.getTelefono() + "," +
            String.valueOf(empleado.getSalarioBruto()) + "," +
            empleado.getTipoPlanilla() + "," +
            empleado.getPuesto() + "," +
            (empleado.getFechaIngreso() != null ? empleado.getFechaIngreso().format(DATE_FORMATTER) : "")
        );
        
        accesoDatos.agregarRegistro();
    }
    
    public void actualizarEmpleado(Empleado empleado) throws IOException {
        accesoDatos.setIdRegistro(empleado.getId());
        
        // FORMATO MODIFICADO: Ahora incluye la fecha de ingreso al final
        accesoDatos.setRegistro(
            String.valueOf(empleado.getId()) + "," +
            empleado.getCedula() + "," +
            empleado.getNombre() + "," +
            empleado.getApellido1() + "," +
            empleado.getApellido2() + "," +
            empleado.getEmail() + "," +
            empleado.getTelefono() + "," +
            String.valueOf(empleado.getSalarioBruto()) + "," +
            empleado.getTipoPlanilla() + "," +
            empleado.getPuesto() + "," +
            (empleado.getFechaIngreso() != null ? empleado.getFechaIngreso().format(DATE_FORMATTER) : "")
        );
        accesoDatos.setEliminar(false);
        accesoDatos.modificarRegistro();
    }
    
    public void eliminarEmpleado(Empleado empleado) throws IOException {
        accesoDatos.setIdRegistro(empleado.getId());
        accesoDatos.setEliminar(true);
        accesoDatos.modificarRegistro();
    }
    
    public void listarEmpleados(Empleado empleado) throws IOException {
        accesoDatos.setNombreArchivo(NombresArchivos.EMPLEADOS.getNombreArchivo());
        accesoDatos.listarRegistros();
        
        for (String[] datos : accesoDatos.getListaRegistros()) {
            // MODIFICADO: Ahora soporta tanto el formato antiguo (10 campos) como el nuevo (11 campos)
            if (datos.length >= 10) {
                LocalDate fechaIngreso = null;
                
                // Si tiene el campo 11 (fecha de ingreso), parsearlo
                if (datos.length == 11 && !datos[10].isEmpty()) {
                    try {
                        fechaIngreso = LocalDate.parse(datos[10], DATE_FORMATTER);
                    } catch (Exception e) {
                        // Si falla el parseo, dejar null
                        fechaIngreso = null;
                    }
                }
                
                Empleado emp = new Empleado(
                    Integer.parseInt(datos[0]),
                    datos[1], datos[2], datos[3], datos[4],
                    datos[5], datos[6],
                    Double.parseDouble(datos[7]),
                    datos[8], datos[9],
                    fechaIngreso // NUEVO PARÁMETRO
                );
                empleado.agregarListaEmpleados(emp);
            }
        }
    }
}