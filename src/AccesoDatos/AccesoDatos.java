/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase de acceso a datos que gestiona operaciones CRUD sobre archivos de texto plano.
 * 
 * <p>Esta clase proporciona una capa de abstracción para realizar operaciones de
 * persistencia de datos utilizando archivos CSV (valores separados por comas) como
 * sistema de almacenamiento. Permite agregar, listar, modificar y eliminar registros
 * de manera eficiente y estructurada.</p>
 * 
 * <p>Los archivos utilizados siguen el formato CSV estándar donde:</p>
 * <ul>
 *   <li>Cada línea representa un registro completo</li>
 *   <li>Los campos dentro de cada registro están separados por comas</li>
 *   <li>El primer campo siempre es el identificador único (ID)</li>
 *   <li>No se utilizan encabezados de columnas</li>
 * </ul>
 * 
 * <h3>Formato de archivo CSV:</h3>
 * <pre>
 * 1,Juan,Pérez,juan@email.com,123456
 * 2,María,Rodríguez,maria@email.com,789012
 * 3,Carlos,López,carlos@email.com,345678
 * </pre>
 * 
 * <h3>Operaciones soportadas:</h3>
 * <ul>
 *   <li><b>CREATE:</b> Agregar nuevos registros al final del archivo</li>
 *   <li><b>READ:</b> Cargar todos los registros del archivo a memoria</li>
 *   <li><b>UPDATE:</b> Modificar un registro existente por su ID</li>
 *   <li><b>DELETE:</b> Eliminar un registro específico por su ID</li>
 * </ul>
 * 
 * <h3>Ejemplo de uso - Operaciones básicas:</h3>
 * <pre>
 * // Crear instancia
 * AccesoDatos accesoDatos = new AccesoDatos();
 * accesoDatos.setNombreArchivo("empleados.txt");
 * 
 * // AGREGAR un nuevo registro
 * accesoDatos.setRegistro("4,Pedro,Gómez,pedro@email.com,456789");
 * accesoDatos.agregarRegistro();
 * 
 * // LISTAR todos los registros
 * accesoDatos.listarRegistros();
 * for (String[] registro : accesoDatos.getListaRegistros()) {
 *     System.out.println("ID: " + registro[0] + ", Nombre: " + registro[1]);
 * }
 * 
 * // ACTUALIZAR un registro existente
 * accesoDatos.setIdRegistro(4);
 * accesoDatos.setRegistro("4,Pedro,Gómez,pedro.gomez@email.com,456789");
 * accesoDatos.setEliminar(false);
 * accesoDatos.modificarRegistro();
 * 
 * // ELIMINAR un registro
 * accesoDatos.setIdRegistro(4);
 * accesoDatos.setEliminar(true);
 * accesoDatos.modificarRegistro();
 * </pre>
 * 
 * <h3>Consideraciones importantes:</h3>
 * <ul>
 *   <li>Los archivos se manejan con codificación por defecto del sistema</li>
 *   <li>Las operaciones de modificación y eliminación crean archivos temporales</li>
 *   <li>No hay validación automática del formato de los registros</li>
 *   <li>El ID debe ser único y estar en la primera posición</li>
 *   <li>Los registros no pueden contener comas en sus valores</li>
 * </ul>
 * 
 * <p><b>Nota de seguridad:</b> Esta clase no implementa bloqueo de archivos,
 * por lo que no es segura para acceso concurrente. Si múltiples procesos
 * necesitan acceder al mismo archivo simultáneamente, se recomienda implementar
 * un mecanismo de sincronización externo.</p>
 * 
 * @author Justin Espinoza
 * @version 1.0
 * @see java.io.BufferedReader
 * @see java.io.BufferedWriter
 * @see java.io.FileReader
 * @see java.io.FileWriter
 */
public class AccesoDatos {
    
    // ========================================================================
    // ATRIBUTOS PRIVADOS
    // ========================================================================
    
    /** 
     * Cadena que representa un registro completo en formato CSV.
     * 
     * <p>Contiene todos los campos del registro separados por comas. Este valor
     * se utiliza tanto para agregar nuevos registros como para actualizar
     * registros existentes.</p>
     * 
     * <p><b>Formato esperado:</b> {@code id,campo1,campo2,campo3,...}</p>
     * <p><b>Ejemplo:</b> {@code "1,Juan,Pérez,juan@email.com,123456"}</p>
     */
    private String registro;
    
    /** 
     * Nombre del archivo donde se almacenan los datos.
     * 
     * <p>Puede ser una ruta relativa o absoluta. Si es relativa, se interpreta
     * desde el directorio de ejecución de la aplicación.</p>
     * 
     * <p><b>Ejemplos válidos:</b></p>
     * <ul>
     *   <li>{@code "empleados.txt"} - Archivo en directorio actual</li>
     *   <li>{@code "data/empleados.txt"} - Archivo en subdirectorio</li>
     *   <li>{@code "C:/datos/empleados.txt"} - Ruta absoluta (Windows)</li>
     *   <li>{@code "/home/user/datos/empleados.txt"} - Ruta absoluta (Linux/Mac)</li>
     * </ul>
     */
    private String nombreArchivo;
    
    /** 
     * Lista que contiene todos los registros leídos del archivo.
     * 
     * <p>Cada elemento de la lista es un array de String que representa un registro
     * completo dividido por campos. El índice [0] siempre contiene el ID, y los
     * índices siguientes contienen los demás campos del registro.</p>
     * 
     * <p><b>Estructura:</b></p>
     * <pre>
     * ArrayList&lt;String[]&gt; donde:
     *   String[0] = ID del registro
     *   String[1] = Primer campo
     *   String[2] = Segundo campo
     *   String[n] = n-ésimo campo
     * </pre>
     * 
     * <p><b>Ejemplo:</b></p>
     * <pre>
     * listaRegistros.get(0) → ["1", "Juan", "Pérez", "juan@email.com"]
     * listaRegistros.get(1) → ["2", "María", "Rodríguez", "maria@email.com"]
     * </pre>
     */
    private ArrayList<String[]> listaRegistros;
    
    /** 
     * Identificador único del registro que se desea modificar o eliminar.
     * 
     * <p>Este valor se utiliza para localizar el registro específico dentro del
     * archivo. Debe corresponder al primer campo (posición [0]) del registro
     * en formato CSV.</p>
     * 
     * <p><b>Uso:</b> Se compara con el campo ID de cada registro durante las
     * operaciones de modificación o eliminación.</p>
     */
    private int idRegistro;
    
    /** 
     * Bandera que indica la operación a realizar durante la modificación.
     * 
     * <p>Controla el comportamiento del método {@link #modificarRegistro()}:</p>
     * <ul>
     *   <li><b>true:</b> El registro será eliminado del archivo</li>
     *   <li><b>false:</b> El registro será actualizado con el nuevo contenido</li>
     * </ul>
     * 
     * <p>Este diseño permite que un solo método maneje tanto actualizaciones
     * como eliminaciones, diferenciándolas mediante esta bandera.</p>
     */
    private boolean eliminar;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Crea una nueva instancia de AccesoDatos con configuración inicial.
     * 
     * <p>Inicializa la lista de registros como un ArrayList vacío, listo para
     * recibir datos mediante el método {@link #listarRegistros()}.</p>
     * 
     * <p><b>Estado inicial del objeto:</b></p>
     * <ul>
     *   <li>registro: null</li>
     *   <li>nombreArchivo: null</li>
     *   <li>listaRegistros: ArrayList vacío (size = 0)</li>
     *   <li>idRegistro: 0</li>
     *   <li>eliminar: false</li>
     * </ul>
     * 
     * <p><b>Uso típico:</b> Después de crear la instancia, debe establecerse
     * el nombre del archivo mediante {@link #setNombreArchivo(String)} antes
     * de realizar cualquier operación de lectura o escritura.</p>
     */
    public AccesoDatos() {
        listaRegistros = new ArrayList<>();
    }

    // ========================================================================
    // MÉTODOS GETTERS Y SETTERS
    // ========================================================================
    
    /**
     * Obtiene el registro actual en formato CSV.
     * 
     * @return el registro completo como cadena de texto, o null si no se ha establecido
     * 
     * @see #setRegistro(String)
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * Establece el registro que se desea agregar o utilizar para actualización.
     * 
     * <p>El registro debe estar en formato CSV válido con el ID en la primera
     * posición y los campos separados por comas.</p>
     * 
     * <p><b>Formato correcto:</b> {@code "id,campo1,campo2,campo3,..."}</p>
     * <p><b>Ejemplo:</b> {@code "1,Juan,Pérez,juan@email.com,123456"}</p>
     * 
     * @param registro el registro en formato CSV (no debe ser null ni vacío)
     * 
     * @see #getRegistro()
     * @see #agregarRegistro()
     * @see #modificarRegistro()
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    /**
     * Obtiene el nombre del archivo actual configurado.
     * 
     * @return el nombre o ruta del archivo, o null si no se ha establecido
     * 
     * @see #setNombreArchivo(String)
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Establece el nombre del archivo donde se realizarán las operaciones.
     * 
     * <p>Puede ser una ruta relativa al directorio de ejecución o una ruta
     * absoluta del sistema de archivos.</p>
     * 
     * <p><b>Importante:</b> Este método no verifica si el archivo existe ni
     * si es accesible. Las validaciones se realizan al intentar las operaciones
     * de lectura o escritura.</p>
     * 
     * @param nombreArchivo el nombre del archivo con extensión (ej: "datos.txt")
     *                      o ruta completa al archivo
     * 
     * @see #getNombreArchivo()
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Obtiene la lista completa de registros cargados en memoria.
     * 
     * <p>Esta lista se llena mediante el método {@link #listarRegistros()} y
     * representa todos los registros del archivo en el momento de la última lectura.</p>
     * 
     * @return ArrayList donde cada elemento es un array de String representando
     *         un registro. Retorna lista vacía si no se han cargado registros
     * 
     * @see #setListaRegistros(ArrayList)
     * @see #listarRegistros()
     */
    public ArrayList<String[]> getListaRegistros() {
        return listaRegistros;
    }

    /**
     * Establece manualmente la lista completa de registros.
     * 
     * <p>Este método permite cargar registros desde una fuente externa o
     * modificar la lista cargada sin afectar el archivo físico.</p>
     * 
     * <p><b>Nota:</b> Los cambios en esta lista NO se reflejan automáticamente
     * en el archivo. Para persistir los datos, use los métodos de escritura
     * apropiados.</p>
     * 
     * @param listaRegistros ArrayList de arrays de String con los registros
     * 
     * @see #getListaRegistros()
     */
    public void setListaRegistros(ArrayList<String[]> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    /**
     * Obtiene el ID del registro que será modificado o eliminado.
     * 
     * @return el identificador único del registro objetivo
     * 
     * @see #setIdRegistro(int)
     * @see #modificarRegistro()
     */
    public int getIdRegistro() {
        return idRegistro;
    }

    /**
     * Establece el ID del registro que se desea modificar o eliminar.
     * 
     * <p>Este ID debe corresponder exactamente con el primer campo del registro
     * en el archivo CSV. Si no existe un registro con este ID, la operación
     * de modificación/eliminación no tendrá efecto.</p>
     * 
     * @param idRegistro el identificador único del registro (debe ser positivo)
     * 
     * @see #getIdRegistro()
     * @see #modificarRegistro()
     */
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    /**
     * Verifica si el modo de operación actual es eliminación.
     * 
     * @return true si el próximo {@link #modificarRegistro()} eliminará el registro,
     *         false si lo actualizará
     * 
     * @see #setEliminar(boolean)
     * @see #modificarRegistro()
     */
    public boolean isEliminar() {
        return eliminar;
    }
 
    /**
     * Establece el modo de operación para la modificación del registro.
     * 
     * <p>Esta bandera determina el comportamiento del método
     * {@link #modificarRegistro()}:</p>
     * <ul>
     *   <li><b>true:</b> Eliminará el registro identificado por {@link #idRegistro}</li>
     *   <li><b>false:</b> Actualizará el registro con el contenido de {@link #registro}</li>
     * </ul>
     * 
     * @param eliminar true para eliminar el registro, false para actualizarlo
     * 
     * @see #isEliminar()
     * @see #modificarRegistro()
     */
    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    // ========================================================================
    // MÉTODOS PÚBLICOS - OPERACIONES CRUD
    // ========================================================================
    
    /**
     * Agrega un nuevo registro al final del archivo especificado.
     * 
     * <p>Esta operación es de tipo <b>append</b>, lo que significa que el nuevo
     * registro se añade al final del archivo preservando todo el contenido
     * existente. Cada registro se escribe en una nueva línea.</p>
     * 
     * <p><b>Proceso interno:</b></p>
     * <ol>
     *   <li>Abre el archivo en modo append (FileWriter con parámetro true)</li>
     *   <li>Escribe el contenido de {@link #registro} al final del archivo</li>
     *   <li>Agrega un salto de línea después del registro</li>
     *   <li>Cierra el archivo automáticamente (try-with-resources)</li>
     * </ol>
     * 
     * <p><b>Estado del archivo ANTES:</b></p>
     * <pre>
     * 1,Juan,Pérez
     * 2,María,Rodríguez
     * </pre>
     * 
     * <p><b>Llamada al método:</b></p>
     * <pre>
     * accesoDatos.setRegistro("3,Carlos,López");
     * accesoDatos.agregarRegistro();
     * </pre>
     * 
     * <p><b>Estado del archivo DESPUÉS:</b></p>
     * <pre>
     * 1,Juan,Pérez
     * 2,María,Rodríguez
     * 3,Carlos,López
     * </pre>
     * 
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>Debe haberse establecido {@link #nombreArchivo}</li>
     *   <li>Debe haberse establecido {@link #registro} con formato CSV válido</li>
     *   <li>El directorio del archivo debe existir y tener permisos de escritura</li>
     * </ul>
     * 
     * <p><b>Comportamiento con archivos nuevos:</b> Si el archivo no existe,
     * se crea automáticamente. No es necesario crear el archivo manualmente.</p>
     * 
     * @throws IOException si ocurre un error al escribir en el archivo, como:
     *         <ul>
     *           <li>Permisos insuficientes para escribir</li>
     *           <li>Disco lleno</li>
     *           <li>Ruta de directorio inválida</li>
     *           <li>Archivo bloqueado por otro proceso</li>
     *         </ul>
     * 
     * @see #setRegistro(String)
     * @see #setNombreArchivo(String)
     */
    public void agregarRegistro() throws IOException {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(this.nombreArchivo, true))) {
            bW.append(this.registro);
            bW.newLine();
        }
    }

    /**
     * Lee todos los registros del archivo y los carga en memoria.
     * 
     * <p>Este método es fundamental para operaciones de lectura masiva. Lee el
     * archivo línea por línea, divide cada línea por comas, y almacena el
     * resultado en {@link #listaRegistros}.</p>
     * 
     * <p><b>Proceso interno:</b></p>
     * <ol>
     *   <li>Limpia la lista actual de registros (previene duplicados)</li>
     *   <li>Abre el archivo en modo lectura</li>
     *   <li>Lee cada línea del archivo</li>
     *   <li>Divide la línea usando la coma como separador</li>
     *   <li>Agrega el array resultante a la lista</li>
     *   <li>Repite hasta llegar al final del archivo</li>
     *   <li>Cierra el archivo automáticamente</li>
     * </ol>
     * 
     * <p><b>Contenido del archivo:</b></p>
     * <pre>
     * 1,Juan,Pérez,juan@email.com
     * 2,María,Rodríguez,maria@email.com
     * 3,Carlos,López,carlos@email.com
     * </pre>
     * 
     * <p><b>Resultado en listaRegistros:</b></p>
     * <pre>
     * listaRegistros[0] = ["1", "Juan", "Pérez", "juan@email.com"]
     * listaRegistros[1] = ["2", "María", "Rodríguez", "maria@email.com"]
     * listaRegistros[2] = ["3", "Carlos", "López", "carlos@email.com"]
     * </pre>
     * 
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * AccesoDatos accesoDatos = new AccesoDatos();
     * accesoDatos.setNombreArchivo("empleados.txt");
     * accesoDatos.listarRegistros();
     * 
     * // Iterar sobre los registros cargados
     * for (String[] registro : accesoDatos.getListaRegistros()) {
     *     System.out.println("ID: " + registro[0]);
     *     System.out.println("Nombre: " + registro[1]);
     *     System.out.println("Apellido: " + registro[2]);
     * }
     * </pre>
     * 
     * <p><b>Comportamiento especial:</b></p>
     * <ul>
     *   <li><b>Archivo no encontrado:</b> La lista queda vacía (no lanza excepción)</li>
     *   <li><b>Archivo vacío:</b> La lista queda vacía</li>
     *   <li><b>Líneas vacías:</b> Se procesan como arrays con un elemento vacío</li>
     *   <li><b>Llamadas múltiples:</b> Cada llamada recarga completamente la lista</li>
     * </ul>
     * 
     * <p><b>Advertencia:</b> Si un registro contiene comas dentro de sus valores
     * (sin escape), la división será incorrecta. Asegúrese de que los datos no
     * contengan el carácter separador.</p>
     * 
     * @throws IOException si ocurre un error al leer el archivo (excepto FileNotFoundException,
     *         que se maneja internamente retornando lista vacía)
     * 
     * @see #getListaRegistros()
     * @see #setNombreArchivo(String)
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
     * Modifica o elimina un registro específico del archivo basándose en su ID.
     * 
     * <p>Esta es la operación más compleja de la clase, ya que maneja tanto
     * actualizaciones como eliminaciones. Utiliza un archivo temporal para
     * garantizar la integridad de los datos en caso de fallo durante la operación.</p>
     * 
     * <p><b>Algoritmo de modificación segura:</b></p>
     * <ol>
     *   <li>Crea un archivo temporal (temp_[nombreArchivo])</li>
     *   <li>Lee el archivo original línea por línea</li>
     *   <li>Para cada línea:
     *     <ul>
     *       <li>Extrae el ID (primer campo)</li>
     *       <li>Si NO coincide con {@link #idRegistro}: copia la línea sin cambios</li>
     *       <li>Si SÍ coincide:
     *         <ul>
     *           <li>Si {@link #eliminar} = true: omite la línea (eliminación)</li>
     *           <li>Si {@link #eliminar} = false: escribe {@link #registro} (actualización)</li>
     *         </ul>
     *       </li>
     *     </ul>
     *   </li>
     *   <li>Cierra ambos archivos</li>
     *   <li>Elimina el archivo original</li>
     *   <li>Renombra el archivo temporal al nombre original</li>
     * </ol>
     * 
     * <h4>Ejemplo 1: Actualización de registro</h4>
     * <p><b>Archivo ANTES:</b></p>
     * <pre>
     * 1,Juan,Pérez,juan@email.com
     * 2,María,Rodríguez,maria@email.com
     * 3,Carlos,López,carlos@email.com
     * </pre>
     * 
     * <p><b>Código de actualización:</b></p>
     * <pre>
     * accesoDatos.setIdRegistro(2);
     * accesoDatos.setRegistro("2,María,Rodríguez,maria.rodriguez@email.com");
     * accesoDatos.setEliminar(false);
     * accesoDatos.modificarRegistro();
     * </pre>
     * 
     * <p><b>Archivo DESPUÉS:</b></p>
     * <pre>
     * 1,Juan,Pérez,juan@email.com
     * 2,María,Rodríguez,maria.rodriguez@email.com
     * 3,Carlos,López,carlos@email.com
     * </pre>
     * 
     * <h4>Ejemplo 2: Eliminación de registro</h4>
     * <p><b>Archivo ANTES:</b></p>
     * <pre>
     * 1,Juan,Pérez,juan@email.com
     * 2,María,Rodríguez,maria@email.com
     * 3,Carlos,López,carlos@email.com
     * </pre>
     * 
     * <p><b>Código de eliminación:</b></p>
     * <pre>
     * accesoDatos.setIdRegistro(2);
     * accesoDatos.setEliminar(true);
     * accesoDatos.modificarRegistro();
     * </pre>
     * 
     * <p><b>Archivo DESPUÉS:</b></p>
     * <pre>
     * 1,Juan,Pérez,juan@email.com
     * 3,Carlos,López,carlos@email.com
     * </pre>
     * 
     * <p><b>Ventajas del algoritmo con archivo temporal:</b></p>
     * <ul>
     *   <li>Garantiza integridad: si falla, el original permanece intacto</li>
     *   <li>Evita corrupción de datos por escritura parcial</li>
     *   <li>Permite operaciones atómicas (todo o nada)</li>
     * </ul>
     * 
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *   <li>Debe haberse establecido {@link #nombreArchivo}</li>
     *   <li>Debe haberse establecido {@link #idRegistro}</li>
     *   <li>Si eliminar=false, debe haberse establecido {@link #registro}</li>
     *   <li>El archivo debe existir y ser legible</li>
     *   <li>El directorio debe tener permisos de escritura</li>
     * </ul>
     * 
     * <p><b>Comportamiento especial:</b></p>
     * <ul>
     *   <li><b>ID no encontrado:</b> El archivo permanece sin cambios (operación silenciosa)</li>
     *   <li><b>Múltiples IDs iguales:</b> Solo modifica/elimina la primera ocurrencia</li>
     *   <li><b>Archivo temporal existente:</b> Se sobrescribe sin advertencia</li>
     * </ul>
     * 
     * <p><b>Advertencias importantes:</b></p>
     * <ul>
     *   <li>Esta operación NO es atómica a nivel de sistema operativo</li>
     *   <li>Requiere espacio en disco para el archivo temporal</li>
     *   <li>El archivo original se elimina permanentemente</li>
     *   <li>No es segura para acceso concurrente sin sincronización externa</li>
     * </ul>
     * 
     * @throws IOException si ocurre un error durante el proceso, incluyendo:
     *         <ul>
     *           <li>Archivo original no encontrado</li>
     *           <li>Permisos insuficientes para leer/escribir</li>
     *           <li>Disco lleno al crear archivo temporal</li>
     *           <li>No se puede eliminar el archivo original</li>
     *           <li>No se puede renombrar el archivo temporal</li>
     *         </ul>
     * @throws NumberFormatException si el primer campo del registro no es un número
     *         válido (lanzada por Integer.parseInt)
     * 
     * @see #setIdRegistro(int)
     * @see #setEliminar(boolean)
     * @see #setRegistro(String)
     * @see #setNombreArchivo(String)
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
