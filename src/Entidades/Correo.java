

package Entidades;

/**
 * Clase File del paquete java.io que representa rutas de archivos y directorios.
 * 
 * <p>Se utiliza en esta clase para referenciar archivos adjuntos que serán
 * enviados junto con los correos electrónicos, como comprobantes de nómina
 * en formato PDF u otros documentos del sistema.
 */
import java.io.File;

/**
 * Clase ArrayList del paquete java.util que implementa una lista dinámica.
 * 
 * <p>Se utiliza para almacenar la colección de archivos adjuntos del correo,
 * permitiendo agregar o eliminar archivos de manera flexible sin necesidad
 * de definir un tamaño fijo de antemano.
 */
import java.util.ArrayList;

/**
 * Clase que representa un correo electrónico en el sistema de nómina.
 * 
 * <p>Esta clase extiende de {@link Usuario} y encapsula la información
 * necesaria para el envío de correos electrónicos, incluyendo el mensaje,
 * asunto y archivos adjuntos.
 * 
 * <p>Los correos pueden contener múltiples archivos adjuntos que se
 * gestionan mediante una lista dinámica. Esta clase es utilizada para
 * notificar a los empleados sobre sus nóminas y otra información relevante
 * del sistema.
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * Correo correo = new Correo();
 * correo.setAsunto("Nómina del mes de Diciembre");
 * correo.setMensaje("Adjunto encontrará su comprobante de pago");
 * correo.agregarArchivoAdjunto(new File("nomina_diciembre.pdf"));
 * </pre>
 */
public class Correo extends Usuario {
    
    // ========================================================================
    // ATRIBUTOS PRIVADOS
    // ========================================================================
    
    /**
     * Contenido del mensaje del correo electrónico.
     * 
     * <p>Este campo almacena el cuerpo principal del correo que será
     * enviado al destinatario.
     */
    private String mensaje;
    
    /**
     * Asunto o título del correo electrónico.
     * 
     * <p>Este campo contiene una breve descripción del contenido del correo,
     * que aparecerá como título en la bandeja de entrada del destinatario.
     */
    private String asunto;
    
    /**
     * Lista de archivos adjuntos al correo electrónico.
     * 
     * <p>Almacena referencias a los archivos que se enviarán junto con
     * el correo, tales como comprobantes de nómina en formato PDF u
     * otros documentos relevantes.
     */
    private ArrayList<File> archivosAdjuntos;
    
    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Constructor por defecto que inicializa un nuevo correo electrónico.
     * 
     * <p>Crea una nueva instancia de Correo con la lista de archivos
     * adjuntos inicializada como una lista vacía, lista para recibir
     * archivos mediante el método {@link #agregarArchivoAdjunto(File)}.
     */
    public Correo() {
        this.archivosAdjuntos = new ArrayList<>();
    }
    
    // ========================================================================
    // MÉTODOS GETTER Y SETTER
    // ========================================================================
    
    /**
     * Obtiene el contenido del mensaje del correo electrónico.
     * 
     * @return el mensaje del correo como una cadena de texto (String)
     */
    public String getMensaje() { 
        return mensaje; 
    }
    
    /**
     * Establece el contenido del mensaje del correo electrónico.
     * 
     * @param mensaje el texto del mensaje que se enviará en el correo
     */
    public void setMensaje(String mensaje) { 
        this.mensaje = mensaje; 
    }
    
    /**
     * Obtiene el asunto del correo electrónico.
     * 
     * @return el asunto del correo como una cadena de texto (String)
     */
    public String getAsunto() { 
        return asunto; 
    }
    
    /**
     * Establece el asunto del correo electrónico.
     * 
     * @param asunto el título o asunto que se mostrará en el correo
     */
    public void setAsunto(String asunto) { 
        this.asunto = asunto; 
    }
    
    /**
     * Obtiene la lista de archivos adjuntos al correo.
     * 
     * @return una lista (ArrayList) con los archivos adjuntos al correo
     */
    public ArrayList<File> getArchivosAdjuntos() { 
        return archivosAdjuntos; 
    }
    
    /**
     * Establece la lista completa de archivos adjuntos del correo.
     * 
     * <p>Este método reemplaza toda la lista existente de archivos
     * adjuntos por una nueva lista proporcionada.
     * 
     * @param archivosAdjuntos la nueva lista de archivos a adjuntar al correo
     */
    public void setArchivosAdjuntos(ArrayList<File> archivosAdjuntos) { 
        this.archivosAdjuntos = archivosAdjuntos; 
    }
    
    // ========================================================================
    // MÉTODOS PÚBLICOS
    // ========================================================================
    
    /**
     * Agrega un archivo adjunto a la lista de archivos del correo.
     * 
     * <p>Este método permite añadir archivos individuales a la lista de
     * adjuntos sin reemplazar los archivos existentes.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Correo correo = new Correo();
     * File comprobante = new File("nomina.pdf");
     * correo.agregarArchivoAdjunto(comprobante);
     * </pre>
     * 
     * @param archivoAdjunto el archivo (File) que se agregará a la lista
     *                       de adjuntos del correo
     */
    public void agregarArchivoAdjunto(File archivoAdjunto) {
        this.archivosAdjuntos.add(archivoAdjunto);
    }
}
