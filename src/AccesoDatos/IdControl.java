/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;

import java.io.*;
import java.util.*;
import Utilidades.NombresArchivos;

// ================================================================================

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// CLASE IdControl
// ================================================================================

/**
 * Control de IDs autoincrementables por archivo.
 * <p>Esta clase gestiona la generación automática de identificadores únicos
 * para diferentes archivos, manteniendo un registro persistente de los últimos
 * IDs utilizados.</p>
 * 
 * <p><b>Estado inicial del objeto:</b></p>
 * <ul>
 *   <li>archivoControl: Nombre del archivo de control obtenido de NombresArchivos.ID_CONTROL</li>
 *   <li>idMap: HashMap vacío, listo para cargar los IDs desde el archivo</li>
 * </ul>
 * 
 * <p><b>Uso típico:</b> Después de crear la instancia, se debe llamar al método
 * {@link #getNextId(String)} para obtener el siguiente ID disponible para un archivo específico.</p>
 * 
 * @author Justin Espinoza
 */
public class IdControl {
    
    // ================================================================================
    // ATRIBUTOS
    // ================================================================================
    
    /**
     * Ruta del archivo que almacena el control de IDs.
     */
    private String archivoControl;
    
    /**
     * Mapa que asocia nombres de archivo con su último ID utilizado.
     */
    private Map<String, Integer> idMap;
    
    // ================================================================================
    // CONSTRUCTOR
    // ================================================================================
    
    /**
     * Crea una nueva instancia de IdControl con configuración inicial.
     * 
     * <p>Inicializa el archivo de control y carga los IDs existentes desde el archivo.</p>
     * 
     * <p><b>Estado inicial del objeto:</b></p>
     * <ul>
     *   <li>archivoControl: Nombre del archivo obtenido de NombresArchivos.ID_CONTROL</li>
     *   <li>idMap: HashMap con los IDs cargados desde el archivo, o vacío si el archivo no existe</li>
     * </ul>
     * 
     * <p><b>Uso típico:</b> Después de crear la instancia, utilizar {@link #getNextId(String)}
     * para obtener IDs autoincrementables.</p>
     * 
     * @throws IOException si ocurre un error al leer o crear el archivo de control
     */
    public IdControl() throws IOException {
        this.archivoControl = NombresArchivos.ID_CONTROL.getNombreArchivo();
        this.idMap = new HashMap<>();
        loadIds();
    }
    
    // ================================================================================
    // MÉTODOS PRIVADOS
    // ================================================================================
    
    /**
     * Carga los IDs desde el archivo de control.
     * 
     * <p>Lee el archivo línea por línea y parsea los pares nombreArchivo=id
     * para poblar el mapa de IDs. Si el archivo no existe, lo crea vacío.</p>
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
     * Guarda los IDs actuales en el archivo de control.
     * 
     * <p>Escribe todos los pares nombreArchivo=id del mapa en el archivo,
     * sobrescribiendo el contenido anterior.</p>
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
    
    // ================================================================================
    // MÉTODOS PÚBLICOS
    // ================================================================================
    
    /**
     * Obtiene el siguiente ID disponible para un archivo específico.
     * 
     * <p>Este método es sincronizado para garantizar la generación de IDs únicos
     * en entornos multihilo. Recarga los IDs desde el archivo, obtiene el siguiente
     * ID disponible (comenzando en 1 si es la primera vez), incrementa el contador
     * y guarda los cambios.</p>
     * 
     * @param fileName el nombre del archivo para el cual se requiere un nuevo ID
     * @return el siguiente ID disponible para el archivo especificado
     * @throws IOException si ocurre un error al leer o escribir el archivo de control
     * 
     * @see #loadIds()
     * @see #saveIds()
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