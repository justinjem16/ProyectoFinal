
package LogicaNegocio;
// Importa la clase encargada del acceso a datos (BD, archivos, etc.)
import AccesoDatos.AccesoDatos;

/**
 * Clase base abstracta para todas las clases de la capa
 * de lógica de negocio.
 * 
 * Su objetivo es:
 * - Centralizar el acceso a la capa de datos
 * - Evitar duplicar código en las clases hijas
 * - Facilitar el mantenimiento y escalabilidad del sistema
 */

public class LogicaBase {
  /**
     * Objeto que permite la comunicación con la capa
     * de acceso a datos.
     * 
     * Se declara como protected para que pueda ser
     * utilizado directamente por las clases hijas.
     */
    protected AccesoDatos accesoDatos;
    
    /**
     * Constructor de la clase base.
     * 
     * Inicializa el objeto {@link AccesoDatos} para que
     * todas las clases que hereden de {@code LogicaBase}
     * tengan acceso inmediato a la capa de datos.
     */
    public LogicaBase() {

        // Crea una nueva instancia del acceso a datos
        this.accesoDatos = new AccesoDatos();
    }  
}
