package LogicaNegocio;

import AccesoDatos.AccesoDatos;

/**
 * Clase abstracta LogicaBase que sirve como base para la capa de lógica de negocio.
 * 
 * <p>Esta clase centraliza el acceso a la capa de datos mediante la clase
 * {@link AccesoDatos.AccesoDatos}, permitiendo que las clases hijas reutilicen
 * dicha funcionalidad sin necesidad de crear múltiples instancias.
 * 
 * <p>Forma parte del patrón de arquitectura en capas del sistema, donde:
 * <ul>
 *   <li><strong>Capa de Lógica de Negocio:</strong> Contiene las reglas y procesos
 *       principales del sistema.</li>
 *   <li><strong>Capa de Acceso a Datos:</strong> Gestiona la comunicación con la
 *       base de datos.</li>
 * </ul>
 * 
 * <p>Las clases que hereden de {@code LogicaBase} podrán utilizar el objeto
 * {@code accesoDatos} para realizar operaciones de persistencia sin acoplarse
 * directamente a la implementación de acceso a datos.
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * public class LogicaEmpleado extends LogicaBase {
 *     
 *     public void guardarEmpleado(Empleado empleado) {
 *         accesoDatos.insertarEmpleado(empleado);
 *     }
 * }
 * </pre>
 * 
 * @author Alejandro Moran Reyes
 * @version 1.0
 */
public abstract class LogicaBase {
    
    /**
     * Objeto que permite el acceso a la capa de datos.
     * 
     * <p>Es protegido para que pueda ser utilizado directamente por las
     * clases que hereden de {@code LogicaBase}.
     */
    protected AccesoDatos accesoDatos;
    
    /**
     * Constructor de la clase LogicaBase.
     * 
     * <p>Inicializa el objeto {@link AccesoDatos.AccesoDatos} que será utilizado
     * por las clases hijas para interactuar con la base de datos.
     */
    public LogicaBase() {
        this.accesoDatos = new AccesoDatos();
    }
}
