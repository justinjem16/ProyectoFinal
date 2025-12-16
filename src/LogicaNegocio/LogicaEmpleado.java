/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import AccesoDatos.IdControl;
import Entidades.Empleado;
import Utilidades.NombresArchivos;
import java.io.IOException;

/**
 * Clase que maneja toda la lógica de negocio relacionada con los empleados.
 * Proporciona métodos para agregar, actualizar, eliminar y listar empleados,
 * gestionando la persistencia de datos a través de archivos.
 * 
 * @author Justin
 */
public class LogicaEmpleado extends LogicaBase {
    
    /** Controlador de IDs únicos para los empleados */
    private IdControl idControl;
    
    /**
     * Crea una nueva instancia de LogicaEmpleado.
     * Configura el archivo de datos de empleados para las operaciones de acceso.
     */
    public LogicaEmpleado() {
        accesoDatos.setNombreArchivo(NombresArchivos.EMPLEADOS.getNombreArchivo());
    }
    
    /**
     * Agrega un nuevo empleado al sistema.
     * Le asigna automáticamente un ID único y guarda toda su información en el archivo.
     * El empleado se almacena en formato CSV con todos sus datos.
     * 
     * @param empleado objeto con la información del empleado a agregar
     * @throws IOException si ocurre un error al escribir en el archivo
     */
    public void agregarEmpleado(Empleado empleado) throws IOException {
        idControl = new IdControl();
        empleado.setId(idControl.getNextId(NombresArchivos.EMPLEADOS.getNombreArchivo()));
        
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
            empleado.getPuesto()
        );
        
        accesoDatos.agregarRegistro();
    }
    
    /**
     * Actualiza la información de un empleado existente.
     * Busca el empleado por su ID y reemplaza todos sus datos con la nueva información.
     * El ID del empleado no puede ser modificado.
     * 
     * @param empleado objeto con la información actualizada del empleado
     * @throws IOException si ocurre un error al modificar el archivo
     */
    public void actualizarEmpleado(Empleado empleado) throws IOException {
        accesoDatos.setIdRegistro(empleado.getId());
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
            empleado.getPuesto()
        );
        accesoDatos.setEliminar(false);
        accesoDatos.modificarRegistro();
    }
    
    /**
     * Elimina un empleado del sistema.
     * Busca el empleado por su ID y lo elimina permanentemente del archivo.
     * Esta operación no puede deshacerse.
     * 
     * @param empleado objeto con el ID del empleado a eliminar
     * @throws IOException si ocurre un error al modificar el archivo
     */
    public void eliminarEmpleado(Empleado empleado) throws IOException {
        accesoDatos.setIdRegistro(empleado.getId());
        accesoDatos.setEliminar(true);
        accesoDatos.modificarRegistro();
    }
    
    /**
     * Carga todos los empleados del archivo y los agrega a la lista del objeto empleado.
     * Lee cada registro del archivo, crea objetos Empleado y los añade a la lista.
     * Solo procesa registros completos con los 10 campos esperados.
     * 
     * @param empleado objeto donde se almacenará la lista de empleados cargados
     * @throws IOException si ocurre un error al leer el archivo
     */
    public void listarEmpleados(Empleado empleado) throws IOException {
        accesoDatos.setNombreArchivo(NombresArchivos.EMPLEADOS.getNombreArchivo());
        accesoDatos.listarRegistros();
        
        for (String[] datos : accesoDatos.getListaRegistros()) {
            if (datos.length == 10) {
                Empleado emp = new Empleado(
                    Integer.parseInt(datos[0]),
                    datos[1], datos[2], datos[3], datos[4],
                    datos[5], datos[6],
                    Double.parseDouble(datos[7]),
                    datos[8], datos[9]
                );
                empleado.agregarListaEmpleados(emp);
            }
        }
    }
}
