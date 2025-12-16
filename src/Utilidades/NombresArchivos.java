/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

/**
 * Enumeración que centraliza los nombres de todos los archivos de datos
 * utilizados por el sistema de nómina.
 * 
 * <p>Este enum proporciona una forma segura y consistente de referenciar
 * los archivos de persistencia del sistema, evitando errores por nombres
 * hardcodeados dispersos en el código.
 * 
 * <p>Cada constante del enum representa un archivo específico con su
 * nombre de archivo correspondiente, que se puede obtener mediante el
 * método {@link #getNombreArchivo()}.
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * String archivoEmpleados = NombresArchivos.EMPLEADOS.getNombreArchivo();
 * // Retorna: "empleados.txt"
 * </pre>
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 */
public enum NombresArchivos {
    
    /**
     * Archivo que almacena la información de todos los empleados del sistema.
     * 
     * <p>Este archivo contiene los datos personales, laborales y salariales
     * de cada empleado registrado en el sistema de nómina.
     * 
     * <p>Nombre del archivo: {@code empleados.txt}
     */
    EMPLEADOS("empleados.txt"),
    
    /**
     * Archivo que almacena el historial de nóminas generadas.
     * 
     * <p>Contiene los registros de todas las planillas de pago procesadas,
     * incluyendo cálculos de salarios, deducciones y pagos realizados.
     * 
     * <p>Nombre del archivo: {@code nominas.txt}
     */
    NOMINAS("nominas.txt"),
    
    /**
     * Archivo que almacena las credenciales y perfiles de los usuarios del sistema.
     * 
     * <p>Contiene la información de autenticación (usuarios y contraseñas)
     * así como los roles y permisos de acceso de cada usuario.
     * 
     * <p>Nombre del archivo: {@code usuarios.txt}
     */
    USUARIOS("usuarios.txt"),
    
    /**
     * Archivo de control que almacena los contadores de identificadores únicos.
     * 
     * <p>Este archivo mantiene un registro de los últimos IDs generados para
     * empleados, nóminas u otros elementos del sistema, garantizando que cada
     * nuevo registro tenga un identificador único e incremental.
     * 
     * <p>Nombre del archivo: {@code idControl.txt}
     */
    ID_CONTROL("idControl.txt");
    
    // ========================================================================
    // ATRIBUTO PRIVADO
    // ========================================================================
    
    /**
     * Nombre del archivo asociado a esta constante del enum.
     * 
     * <p>Este valor se establece en el constructor y no puede modificarse
     * posteriormente, garantizando la inmutabilidad de los nombres de archivo.
     */
    private final String nombreArchivo;
    
    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Constructor privado del enum que inicializa cada constante con su
     * nombre de archivo correspondiente.
     * 
     * <p>Este constructor es llamado automáticamente al definir cada
     * constante del enum y no puede ser invocado directamente desde
     * fuera de la clase.
     * 
     * @param nombreArchivo el nombre del archivo (incluyendo extensión)
     *                      asociado a esta constante
     */
    NombresArchivos(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    // ========================================================================
    // MÉTODO PÚBLICO
    // ========================================================================
    
    /**
     * Obtiene el nombre del archivo asociado a esta constante del enum.
     * 
     * <p>Este método permite acceder al nombre completo del archivo
     * (incluyendo su extensión) de forma segura y consistente.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * String archivo = NombresArchivos.EMPLEADOS.getNombreArchivo();
     * System.out.println(archivo); // Imprime: empleados.txt
     * </pre>
     * 
     * @return el nombre del archivo como una cadena de texto (String),
     *         incluyendo la extensión del archivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }
}