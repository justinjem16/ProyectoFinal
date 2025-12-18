
package Entidades;

import java.io.File;
import java.util.ArrayList;

// Declaración de la clase Correo, que hereda de la clase Usuario
public class Correo extends Usuario {

    // Variable que almacena el contenido del mensaje del correo
    private String mensaje;

    // Variable que almacena el asunto del correo
    private String asunto;

    // Lista que almacena los archivos adjuntos del correo
    private ArrayList<File> archivosAdjuntos;

    // Constructor de la clase Correo
    public Correo() {
        // Inicializa la lista de archivos adjuntos para evitar valores null
        this.archivosAdjuntos = new ArrayList<>();
    }

    // Método getter que devuelve el mensaje del correo
    public String getMensaje() {
        return mensaje;
    }

    // Método setter que asigna un valor al mensaje del correo
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // Método getter que devuelve el asunto del correo
    public String getAsunto() {
        return asunto;
    }

    // Método setter que asigna un valor al asunto del correo
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    // Método getter que devuelve la lista de archivos adjuntos
    public ArrayList<File> getArchivosAdjuntos() {
        return archivosAdjuntos;
    }

    // Método setter que asigna una nueva lista de archivos adjuntos
    public void setArchivosAdjuntos(ArrayList<File> archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }

    // Método que agrega un archivo a la lista de archivos adjuntos
    public void agregarArchivoAdjunto(File archivoAdjunto) {
        this.archivosAdjuntos.add(archivoAdjunto);
    }

}
