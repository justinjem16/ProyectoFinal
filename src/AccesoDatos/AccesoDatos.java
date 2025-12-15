/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase que maneja el acceso a datos mediante archivos de texto.
 * Permite realizar operaciones básicas como agregar, listar y modificar registros
 * almacenados en archivos con formato CSV.
 * 
 * @author Justin
 */
public class AccesoDatos {
    /** Cadena que representa un registro en formato CSV */
    private String registro;
    
    /** Nombre del archivo donde se almacenan los datos */
    private String nombreArchivo;
    
    /** Lista que contiene todos los registros leídos del archivo */
    private ArrayList<String[]> listaRegistros;
    
    /** Identificador del registro a modificar o eliminar */
    private int idRegistro;
    
    /** Indica si se debe eliminar el registro durante una modificación */
    private boolean eliminar;

    /**
     * Crea una nueva instancia de AccesoDatos.
     * Inicializa la lista de registros vacía.
     */
    public AccesoDatos() {
        listaRegistros = new ArrayList<>();
    }

    /**
     * Obtiene el registro actual.
     * 
     * @return el registro en formato String
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * Establece el registro a trabajar.
     * 
     * @param registro el registro en formato CSV
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    /**
     * Obtiene el nombre del archivo actual.
     * 
     * @return el nombre del archivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Establece el nombre del archivo a utilizar.
     * 
     * @param nombreArchivo el nombre del archivo
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Obtiene la lista de registros cargados.
     * 
     * @return lista de registros donde cada elemento es un array de Strings
     */
    public ArrayList<String[]> getListaRegistros() {
        return listaRegistros;
    }

    /**
     * Establece la lista de registros.
     * 
     * @param listaRegistros lista de registros a establecer
     */
    public void setListaRegistros(ArrayList<String[]> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    /**
     * Obtiene el ID del registro a modificar.
     * 
     * @return el identificador del registro
     */
    public int getIdRegistro() {
        return idRegistro;
    }

    /**
     * Establece el ID del registro a modificar o eliminar.
     * 
     * @param idRegistro el identificador del registro
     */
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    /**
     * Verifica si el registro debe ser eliminado.
     * 
     * @return true si se debe eliminar, false en caso contrario
     */
    public boolean isEliminar() {
        return eliminar;
    }
 
    /**
     * Establece si se debe eliminar el registro.
     * 
     * @param eliminar true para eliminar, false para actualizar
     */
    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    /**
     * Agrega un nuevo registro al final del archivo.
     * El registro se añade en una nueva línea.
     * 
     * @throws IOException si ocurre un error al escribir en el archivo
     */
    public void agregarRegistro() throws IOException {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(this.nombreArchivo, true))) {
            bW.append(this.registro);
            bW.newLine();
        }
    }

    /**
     * Lee todos los registros del archivo y los carga en la lista.
     * Cada línea del archivo se divide por comas y se almacena como un array.
     * Si el archivo no existe, la lista quedará vacía.
     * 
     * @throws IOException si ocurre un error al leer el archivo
     */
    public void listarRegistros() throws IOException {
        this.listaRegistros = new ArrayList<>(); // Limpiar lista antes de cargar
        try (BufferedReader bR = new BufferedReader(new FileReader(this.nombreArchivo))) {
            String linea;
            while ((linea = bR.readLine()) != null) {
                String[] datos = linea.split(",");
                this.listaRegistros.add(datos);
            }
        } catch (FileNotFoundException e) {
            // Si no existe el archivo, simplemente retorna lista vacía
        }
    }

    /**
     * Modifica o elimina un registro del archivo según el ID especificado.
     * Si eliminar es true, el registro se borra. Si es false, se actualiza con el nuevo contenido.
     * El método crea un archivo temporal, realiza los cambios y luego reemplaza el original.
     * 
     * @throws IOException si ocurre un error al leer/escribir o al reemplazar el archivo
     */
    public void modificarRegistro() throws IOException {
        File archivoOriginal = new File(this.nombreArchivo);
        File archivoTemp = new File("temp_" + this.nombreArchivo);
        try (BufferedReader bR = new BufferedReader(new FileReader(archivoOriginal));
             BufferedWriter bW = new BufferedWriter(new FileWriter(archivoTemp))) {
            String linea;
            while ((linea = bR.readLine()) != null) {
                String[] datos = linea.split(",");
                if (this.idRegistro == Integer.parseInt(datos[0])) {
                    if (this.eliminar) {
                        continue; // Elimina saltando esta línea
                    } else {
                        bW.append(this.registro);
                        bW.newLine();
                    }
                } else {
                    bW.append(linea);
                    bW.newLine();
                }
            }
        }
        if (!archivoOriginal.delete()) {
            throw new IOException("No se puede eliminar el archivo original");
        }
        if (!archivoTemp.renameTo(archivoOriginal)) {
            throw new IOException("No se puede renombrar el archivo temporal");
        }
    }
}
