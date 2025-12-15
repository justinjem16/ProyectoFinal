/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;

import Utilidades.NombresArchivos;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *Clase que gestiona la generación y control de IDs únicos para diferentes archivos del sistema.
 * Mantiene un registro persistente de los últimos IDs generados para cada tipo de archivo,
 * asegurando que no se repitan identificadores.
 * 
 * @author Justin
 */
public class IdControl {
    /** Ruta del archivo donde se almacenan los IDs */
    private String archivoControl;
    
    /** Mapa que asocia cada nombre de archivo con su último ID generado */
    private Map<String, Integer> idMap;

    /**
     * Crea una nueva instancia de IdControl.
     * Inicializa el archivo de control y carga los IDs existentes.
     * Si el archivo no existe, lo crea automáticamente.
     * 
     * @throws IOException si ocurre un error al leer o crear el archivo de control
     */
    public IdControl() throws IOException {
        this.archivoControl = NombresArchivos.ID_CONTROL.getNombreArchivo();
        this.idMap = new HashMap<>();
        loadIds();
    }

    /**
     * Carga los IDs desde el archivo de control hacia el mapa en memoria.
     * Si el archivo no existe, lo crea vacío y retorna sin cargar datos.
     * Cada línea del archivo debe tener el formato: nombreArchivo=id
     * 
     * @throws IOException si ocurre un error al leer el archivo
     */
    private void loadIds() throws IOException {
        File file = new File(archivoControl);
        if (!file.exists()) {
            file.createNewFile();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoControl))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    idMap.put(parts[0], Integer.valueOf(parts[1]));
                }
            }
        }
    }

    /**
     * Guarda todos los IDs del mapa en el archivo de control.
     * Cada entrada se guarda en una línea con el formato: nombreArchivo=id
     * Sobrescribe completamente el contenido anterior del archivo.
     * 
     * @throws IOException si ocurre un error al escribir en el archivo
     */
    private void saveIds() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoControl))) {
            for (Map.Entry<String, Integer> entry : idMap.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }

    /**
     * Obtiene el siguiente ID disponible para un archivo específico.
     * El método es sincronizado para evitar conflictos en accesos concurrentes.
     * Si es la primera vez que se solicita un ID para ese archivo, retorna 1.
     * Después de obtener el ID, automáticamente incrementa el contador y guarda los cambios.
     * 
     * @param fileName nombre del archivo para el cual se solicita el ID
     * @return el siguiente ID disponible para ese archivo
     * @throws IOException si ocurre un error al leer o guardar el archivo de control
     */
    public synchronized int getNextId(String fileName) throws IOException {
        this.archivoControl = NombresArchivos.ID_CONTROL.getNombreArchivo();
        this.idMap = new HashMap<>();
        loadIds();
        
        int nextId = idMap.getOrDefault(fileName, 1);
        idMap.put(fileName, nextId + 1);
        saveIds();
        return nextId;
    }
}
