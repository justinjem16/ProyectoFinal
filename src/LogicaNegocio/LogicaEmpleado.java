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

// ================================================================================
// CLASE LogicaEmpleado
// ================================================================================

/**
 * Lógica de negocio para la gestión de empleados.
 * 
 * <p>Esta clase extiende {@link LogicaBase} y proporciona operaciones CRUD
 * (Crear, Leer, Actualizar, Eliminar) para la entidad Empleado. Gestiona
 * la persistencia de datos en archivos CSV y el control automático de IDs.</p>
 * 
 * <p><b>Formato de registro CSV:</b></p>
 * <p>Los empleados se almacenan con el siguiente formato (11 campos):</p>
 * <pre>
 * id,cedula,nombre,apellido1,apellido2,email,telefono,salarioBruto,tipoPlanilla,puesto,fechaIngreso
 * </pre>
 * 
 * <p><b>Estado inicial del objeto:</b></p>
 * <ul>
 *   <li>accesoDatos: Configurado con el archivo de empleados (heredado de LogicaBase)</li>
 *   <li>idControl: null (se inicializa al agregar empleados)</li>
 *   <li>DATE_FORMATTER: Formato de fecha "dd/MM/yyyy" para parseo y serialización</li>
 * </ul>
 * 
 * <p><b>Compatibilidad de formato:</b></p>
 * <p>La clase soporta tanto el formato antiguo (10 campos sin fecha de ingreso)
 * como el nuevo formato (11 campos con fecha de ingreso), garantizando
 * retrocompatibilidad al listar registros existentes.</p>
 * 
 * <p><b>Uso típico:</b> Crear una instancia de LogicaEmpleado, luego utilizar
 * los métodos CRUD según sea necesario. Para listar empleados, primero crear
 * un objeto Empleado vacío que recibirá la lista completa mediante
 * {@link #listarEmpleados(Empleado)}.</p>
 * 
 * @author Justin Espinoza
 * @see LogicaBase
 * @see Entidades.Empleado
 * @see AccesoDatos.IdControl
 */
public class LogicaEmpleado extends LogicaBase {
    
    // ================================================================================
    // ATRIBUTOS
    // ================================================================================
    
    /**
     * Controlador de IDs autoincrementables para empleados.
     * <p>Se inicializa al agregar un nuevo empleado para obtener el siguiente ID disponible.</p>
     */
    private IdControl idControl;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Formateador de fechas para serialización y deserialización.
     * <p>Utiliza el patrón "dd/MM/yyyy" para las fechas de ingreso de empleados.</p>
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    // ================================================================================
    // CONSTRUCTOR
    // ================================================================================
    
    /**
     * Crea una nueva instancia de LogicaEmpleado con configuración inicial.
     * 
     * <p>Configura el objeto {@link AccesoDatos.AccesoDatos} heredado de
     * {@link LogicaBase} con el nombre del archivo de empleados obtenido de
     * {@link Utilidades.NombresArchivos#EMPLEADOS}.</p>
     * 
     * <p><b>Estado inicial del objeto:</b></p>
     * <ul>
     *   <li>accesoDatos.nombreArchivo: Configurado con el archivo de empleados</li>
     *   <li>idControl: null (se inicializa al agregar empleados)</li>
     * </ul>
     * 
     * <p><b>Uso típico:</b> Después de crear la instancia, utilizar los métodos
     * CRUD para gestionar empleados en el sistema.</p>
     * 
     * @see LogicaBase
     * @see Utilidades.NombresArchivos#EMPLEADOS
     */
    public LogicaEmpleado() {
        accesoDatos.setNombreArchivo(NombresArchivos.EMPLEADOS.getNombreArchivo());
    }
    
    // ================================================================================
    // MÉTODOS PÚBLICOS - OPERACIONES CRUD
    // ================================================================================
    
    /**
     * Agrega un nuevo empleado al sistema.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ol>
     *   <li>Inicializa el controlador de IDs mediante {@link AccesoDatos.IdControl}</li>
     *   <li>Asigna automáticamente el siguiente ID disponible al empleado</li>
     *   <li>Serializa todos los datos del empleado en formato CSV (11 campos)</li>
     *   <li>Persiste el registro en el archivo mediante {@link AccesoDatos.AccesoDatos#agregarRegistro()}</li>
     * </ol>
     * 
     * <p><b>Formato del registro generado:</b></p>
     * <pre>
     * id,cedula,nombre,apellido1,apellido2,email,telefono,salarioBruto,tipoPlanilla,puesto,fechaIngreso
     * </pre>
     * 
     * <p><b>Manejo de fecha de ingreso:</b> Si la fecha de ingreso es null,
     * se guarda como cadena vacía para mantener la integridad del formato CSV.</p>
     * 
     * @param empleado el objeto Empleado con todos los datos a persistir. Después de
     *                 la ejecución, el empleado tendrá su ID asignado automáticamente
     * @throws IOException si ocurre un error al leer el archivo de control de IDs
     *                     o al escribir el registro en el archivo de empleados
     * 
     * @see AccesoDatos.IdControl#getNextId(String)
     * @see AccesoDatos.AccesoDatos#agregarRegistro()
     */
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
    
    /**
     * Actualiza los datos de un empleado existente en el sistema.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ol>
     *   <li>Identifica el empleado a actualizar mediante su ID</li>
     *   <li>Serializa todos los datos actualizados del empleado en formato CSV (11 campos)</li>
     *   <li>Establece la bandera de eliminación en false para indicar modificación</li>
     *   <li>Persiste los cambios mediante {@link AccesoDatos.AccesoDatos#modificarRegistro()}</li>
     * </ol>
     * 
     * <p><b>Formato del registro actualizado:</b></p>
     * <pre>
     * id,cedula,nombre,apellido1,apellido2,email,telefono,salarioBruto,tipoPlanilla,puesto,fechaIngreso
     * </pre>
     * 
     * <p><b>Nota importante:</b> El ID del empleado no cambia durante la actualización.
     * Todos los demás campos pueden ser modificados según los datos proporcionados.</p>
     * 
     * @param empleado el objeto Empleado con los datos actualizados. Debe incluir
     *                 un ID válido que corresponda a un empleado existente
     * @throws IOException si ocurre un error al leer o escribir en el archivo de empleados
     * 
     * @see AccesoDatos.AccesoDatos#modificarRegistro()
     */
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
    
    /**
     * Elimina un empleado del sistema mediante eliminación lógica.
     * 
     * <p>Este método realiza una eliminación lógica (soft delete) del empleado,
     * estableciendo la bandera de eliminación en true. El registro permanece en
     * el archivo pero es marcado como eliminado para futuras operaciones.</p>
     * 
     * <p><b>Proceso de eliminación:</b></p>
     * <ol>
     *   <li>Identifica el empleado mediante su ID</li>
     *   <li>Establece la bandera de eliminación en true</li>
     *   <li>Marca el registro como eliminado mediante {@link AccesoDatos.AccesoDatos#modificarRegistro()}</li>
     * </ol>
     * 
     * <p><b>Nota:</b> Esta es una eliminación lógica, no física. El registro
     * se mantiene en el archivo pero no aparecerá en listados posteriores.</p>
     * 
     * @param empleado el objeto Empleado a eliminar. Solo se requiere que tenga
     *                 un ID válido que corresponda a un empleado existente
     * @throws IOException si ocurre un error al leer o escribir en el archivo de empleados
     * 
     * @see AccesoDatos.AccesoDatos#modificarRegistro()
     */
    public void eliminarEmpleado(Empleado empleado) throws IOException {
        accesoDatos.setIdRegistro(empleado.getId());
        accesoDatos.setEliminar(true);
        accesoDatos.modificarRegistro();
    }
    
    /**
     * Lista todos los empleados activos del sistema.
     * 
     * <p>Este método carga todos los empleados no eliminados del archivo y los
     * agrega a la lista interna del objeto Empleado proporcionado como parámetro.
     * Soporta retrocompatibilidad con dos formatos de registro:</p>
     * 
     * <p><b>Formato antiguo (10 campos - sin fecha de ingreso):</b></p>
     * <pre>
     * id,cedula,nombre,apellido1,apellido2,email,telefono,salarioBruto,tipoPlanilla,puesto
     * </pre>
     * 
     * <p><b>Formato nuevo (11 campos - con fecha de ingreso):</b></p>
     * <pre>
     * id,cedula,nombre,apellido1,apellido2,email,telefono,salarioBruto,tipoPlanilla,puesto,fechaIngreso
     * </pre>
     * 
     * <p><b>Proceso de listado:</b></p>
     * <ol>
     *   <li>Configura el archivo de empleados mediante {@link Utilidades.NombresArchivos#EMPLEADOS}</li>
     *   <li>Carga todos los registros mediante {@link AccesoDatos.AccesoDatos#listarRegistros()}</li>
     *   <li>Itera sobre cada registro y lo deserializa en objetos Empleado</li>
     *   <li>Maneja el parseo de fecha de ingreso con tolerancia a errores</li>
     *   <li>Agrega cada empleado a la lista del objeto parámetro</li>
     * </ol>
     * 
     * <p><b>Manejo de errores en fechas:</b> Si el campo de fecha de ingreso está
     * presente pero no puede parsearse correctamente, se asigna null al empleado
     * en lugar de fallar, garantizando que otros datos se carguen correctamente.</p>
     * 
     * @param empleado objeto Empleado que recibirá la lista completa de empleados
     *                 en su colección interna mediante
     *                 {@link Entidades.Empleado#agregarListaEmpleados(Empleado)}
     * @throws IOException si ocurre un error al leer el archivo de empleados
     * 
     * @see AccesoDatos.AccesoDatos#listarRegistros()
     * @see Entidades.Empleado#agregarListaEmpleados(Empleado)
     */
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
