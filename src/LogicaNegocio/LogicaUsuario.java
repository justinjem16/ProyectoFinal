/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import AccesoDatos.IdControl;
import Entidades.Usuario;
import Utilidades.NombresArchivos;
import java.io.IOException;

// ================================================================================
// CLASE LogicaUsuario
// ================================================================================

/**
 * Lógica de negocio para la gestión y autenticación de usuarios del sistema.
 * 
 * <p>Esta clase extiende {@link LogicaBase} y proporciona operaciones CRUD
 * (Crear, Leer, Actualizar, Eliminar) para la entidad Usuario, además de
 * funcionalidades de autenticación. Gestiona la persistencia de datos en
 * archivos CSV y el control automático de IDs.</p>
 * 
 * <p><b>Formato de registro CSV:</b></p>
 * <p>Los usuarios se almacenan con el siguiente formato (7 campos):</p>
 * <pre>
 * id,nombre,apellido1,apellido2,email,usuario,password
 * </pre>
 * 
 * <p><b>Estado inicial del objeto:</b></p>
 * <ul>
 *   <li>accesoDatos: Configurado con el archivo de usuarios (heredado de LogicaBase)</li>
 *   <li>idControl: null (se inicializa al agregar usuarios)</li>
 * </ul>
 * 
 * <p><b>Seguridad:</b></p>
 * <p>Esta implementación almacena contraseñas en texto plano. Para ambientes
 * de producción, se recomienda implementar hashing de contraseñas utilizando
 * algoritmos como BCrypt, PBKDF2 o Argon2.</p>
 * 
 * <p><b>Uso típico:</b> Crear una instancia de LogicaUsuario, luego utilizar
 * los métodos CRUD según sea necesario. Para autenticación, usar
 * {@link #autenticar(String, String)} con las credenciales del usuario.
 * Para listar usuarios, primero crear un objeto Usuario vacío que recibirá
 * la lista completa mediante {@link #listarUsuario(Usuario)}.</p>
 * 
 * @author Justin Espinoza
 * @see LogicaBase
 * @see Entidades.Usuario
 * @see AccesoDatos.IdControl
 */
public class LogicaUsuario extends LogicaBase {
    
    // ================================================================================
    // ATRIBUTOS
    // ================================================================================
    
    /**
     * Controlador de IDs autoincrementables para usuarios.
     * <p>Se inicializa al agregar un nuevo usuario para obtener el siguiente ID disponible.</p>
     */
    private IdControl idControl;
    
    // ================================================================================
    // CONSTRUCTOR
    // ================================================================================
    
    /**
     * Crea una nueva instancia de LogicaUsuario con configuración inicial.
     * 
     * <p>Configura el objeto {@link AccesoDatos.AccesoDatos} heredado de
     * {@link LogicaBase} con el nombre del archivo de usuarios obtenido de
     * {@link Utilidades.NombresArchivos#USUARIOS}.</p>
     * 
     * <p><b>Estado inicial del objeto:</b></p>
     * <ul>
     *   <li>accesoDatos.nombreArchivo: Configurado con el archivo de usuarios</li>
     *   <li>idControl: null (se inicializa al agregar usuarios)</li>
     * </ul>
     * 
     * <p><b>Uso típico:</b> Después de crear la instancia, utilizar los métodos
     * CRUD para gestionar usuarios o {@link #autenticar(String, String)} para
     * validar credenciales de acceso al sistema.</p>
     * 
     * @see LogicaBase
     * @see Utilidades.NombresArchivos#USUARIOS
     */
    public LogicaUsuario() {
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
    }
    
    // ================================================================================
    // MÉTODOS PÚBLICOS - OPERACIONES CRUD
    // ================================================================================
    
    /**
     * Agrega un nuevo usuario al sistema.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ol>
     *   <li>Inicializa el controlador de IDs mediante {@link AccesoDatos.IdControl}</li>
     *   <li>Asigna automáticamente el siguiente ID disponible al usuario</li>
     *   <li>Serializa todos los datos del usuario en formato CSV (7 campos)</li>
     *   <li>Persiste el registro en el archivo mediante {@link AccesoDatos.AccesoDatos#agregarRegistro()}</li>
     * </ol>
     * 
     * <p><b>Formato del registro generado:</b></p>
     * <pre>
     * id,nombre,apellido1,apellido2,email,usuario,password
     * </pre>
     * 
     * <p><b>Nota de seguridad:</b> La contraseña se almacena en texto plano.
     * Para ambientes de producción, considerar implementar hashing antes de
     * llamar a este método.</p>
     * 
     * @param usuario el objeto Usuario con todos los datos a persistir. Después de
     *                la ejecución, el usuario tendrá su ID asignado automáticamente
     * @throws IOException si ocurre un error al leer el archivo de control de IDs
     *                     o al escribir el registro en el archivo de usuarios
     * 
     * @see AccesoDatos.IdControl#getNextId(String)
     * @see AccesoDatos.AccesoDatos#agregarRegistro()
     */
    public void agregarUsuario(Usuario usuario) throws IOException {
        idControl = new IdControl();
        usuario.setId(idControl.getNextId(NombresArchivos.USUARIOS.getNombreArchivo()));
        accesoDatos.setRegistro(String.valueOf(usuario.getId()) + ","
                + usuario.getNombre() + ","
                + usuario.getApellido1() + ","
                + usuario.getApellido2() + ","
                + usuario.getEmail() + ","
                + usuario.getUsuario() + ","
                + usuario.getPassword());
        accesoDatos.agregarRegistro();
    }
    
    /**
     * Actualiza los datos de un usuario existente en el sistema.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ol>
     *   <li>Identifica el usuario a actualizar mediante su ID</li>
     *   <li>Serializa todos los datos actualizados del usuario en formato CSV (7 campos)</li>
     *   <li>Establece la bandera de eliminación en false para indicar modificación</li>
     *   <li>Persiste los cambios mediante {@link AccesoDatos.AccesoDatos#modificarRegistro()}</li>
     * </ol>
     * 
     * <p><b>Formato del registro actualizado:</b></p>
     * <pre>
     * id,nombre,apellido1,apellido2,email,usuario,password
     * </pre>
     * 
     * <p><b>Nota importante:</b> El ID del usuario no cambia durante la actualización.
     * Todos los demás campos, incluyendo el nombre de usuario y contraseña, pueden
     * ser modificados según los datos proporcionados.</p>
     * 
     * <p><b>Nota de seguridad:</b> Si se actualiza la contraseña, esta se almacena
     * en texto plano. Considerar implementar hashing antes de llamar a este método.</p>
     * 
     * @param usuario el objeto Usuario con los datos actualizados. Debe incluir
     *                un ID válido que corresponda a un usuario existente
     * @throws IOException si ocurre un error al leer o escribir en el archivo de usuarios
     * 
     * @see AccesoDatos.AccesoDatos#modificarRegistro()
     */
    public void actualizarUsuario(Usuario usuario) throws IOException {
        accesoDatos.setIdRegistro(usuario.getId());
        accesoDatos.setRegistro(String.valueOf(usuario.getId()) + ","
                + usuario.getNombre() + ","
                + usuario.getApellido1() + ","
                + usuario.getApellido2() + ","
                + usuario.getEmail() + ","
                + usuario.getUsuario() + ","
                + usuario.getPassword());
        accesoDatos.setEliminar(false);
        accesoDatos.modificarRegistro();
    }
    
    /**
     * Elimina un usuario del sistema mediante eliminación lógica.
     * 
     * <p>Este método realiza una eliminación lógica (soft delete) del usuario,
     * estableciendo la bandera de eliminación en true. El registro permanece en
     * el archivo pero es marcado como eliminado para futuras operaciones.</p>
     * 
     * <p><b>Proceso de eliminación:</b></p>
     * <ol>
     *   <li>Identifica el usuario mediante su ID</li>
     *   <li>Establece la bandera de eliminación en true</li>
     *   <li>Marca el registro como eliminado mediante {@link AccesoDatos.AccesoDatos#modificarRegistro()}</li>
     * </ol>
     * 
     * <p><b>Nota:</b> Esta es una eliminación lógica, no física. El registro
     * se mantiene en el archivo pero no aparecerá en listados ni en procesos
     * de autenticación posteriores.</p>
     * 
     * @param usuario el objeto Usuario a eliminar. Solo se requiere que tenga
     *                un ID válido que corresponda a un usuario existente
     * @throws IOException si ocurre un error al leer o escribir en el archivo de usuarios
     * 
     * @see AccesoDatos.AccesoDatos#modificarRegistro()
     */
    public void eliminarUsuario(Usuario usuario) throws IOException {
        accesoDatos.setIdRegistro(usuario.getId());
        accesoDatos.setEliminar(true);
        accesoDatos.modificarRegistro();
    }
    
    /**
     * Lista todos los usuarios activos del sistema.
     * 
     * <p>Este método carga todos los usuarios no eliminados del archivo y los
     * agrega a la lista interna del objeto Usuario proporcionado como parámetro.</p>
     * 
     * <p><b>Formato esperado del registro (7 campos):</b></p>
     * <pre>
     * id,nombre,apellido1,apellido2,email,usuario,password
     * </pre>
     * 
     * <p><b>Proceso de listado:</b></p>
     * <ol>
     *   <li>Configura el archivo de usuarios mediante {@link Utilidades.NombresArchivos#USUARIOS}</li>
     *   <li>Carga todos los registros mediante {@link AccesoDatos.AccesoDatos#listarRegistros()}</li>
     *   <li>Valida que cada registro tenga exactamente 7 campos</li>
     *   <li>Deserializa cada registro válido en objetos Usuario</li>
     *   <li>Agrega cada usuario a la lista del objeto parámetro</li>
     * </ol>
     * 
     * <p><b>Validación de formato:</b> Solo se procesan registros con exactamente
     * 7 campos. Registros con formato incorrecto son ignorados silenciosamente,
     * garantizando que la lista contenga solo usuarios válidos.</p>
     * 
     * @param usuario objeto Usuario que recibirá la lista completa de usuarios
     *                en su colección interna mediante
     *                {@link Entidades.Usuario#agregarListaUsuarios(Usuario)}
     * @throws IOException si ocurre un error al leer el archivo de usuarios
     * 
     * @see AccesoDatos.AccesoDatos#listarRegistros()
     * @see Entidades.Usuario#agregarListaUsuarios(Usuario)
     */
    public void listarUsuario(Usuario usuario) throws IOException {
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
        accesoDatos.listarRegistros();
        for (String[] datos : accesoDatos.getListaRegistros()) {
            if (datos.length == 7) {
                Usuario usr = new Usuario(
                    Integer.parseInt(datos[0]), 
                    datos[1], datos[2], datos[3], 
                    datos[4], datos[5], datos[6]
                );
                usuario.agregarListaUsuarios(usr);
            }
        }
    }
    
    // ================================================================================
    // MÉTODOS PÚBLICOS - AUTENTICACIÓN
    // ================================================================================
    
    /**
     * Autentica a un usuario mediante sus credenciales de acceso.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ol>
     *   <li>Carga todos los usuarios activos del sistema</li>
     *   <li>Busca secuencialmente un usuario que coincida con las credenciales proporcionadas</li>
     *   <li>Compara el nombre de usuario y contraseña de forma sensible a mayúsculas</li>
     *   <li>Retorna el objeto Usuario completo si la autenticación es exitosa</li>
     *   <li>Retorna null si las credenciales son inválidas o el usuario no existe</li>
     * </ol>
     * 
     * <p><b>Formato de validación:</b></p>
     * <ul>
     *   <li>Campo usuario (índice 5): Debe coincidir exactamente con el parámetro username</li>
     *   <li>Campo password (índice 6): Debe coincidir exactamente con el parámetro password</li>
     *   <li>Ambas comparaciones son case-sensitive (sensibles a mayúsculas)</li>
     * </ul>
     * 
     * <p><b>Nota de seguridad:</b> Este método compara contraseñas en texto plano.
     * Para ambientes de producción, se recomienda:</p>
     * <ul>
     *   <li>Implementar hashing de contraseñas (BCrypt, PBKDF2, Argon2)</li>
     *   <li>Agregar límite de intentos de autenticación</li>
     *   <li>Implementar logging de intentos de acceso</li>
     *   <li>Agregar tiempo de espera entre intentos fallidos</li>
     * </ul>
     * 
     * <p><b>Uso típico:</b></p>
     * <pre>
     * LogicaUsuario logica = new LogicaUsuario();
     * Usuario usuarioAutenticado = logica.autenticar("admin", "password123");
     * if (usuarioAutenticado != null) {
     *     // Autenticación exitosa, proceder con el login
     * } else {
     *     // Credenciales inválidas, mostrar error
     * }
     * </pre>
     * 
     * @param username el nombre de usuario para autenticar (case-sensitive)
     * @param password la contraseña del usuario (case-sensitive)
     * @return el objeto Usuario completo si la autenticación es exitosa,
     *         o null si las credenciales son inválidas o el usuario no existe
     * @throws IOException si ocurre un error al leer el archivo de usuarios
     * 
     * @see AccesoDatos.AccesoDatos#listarRegistros()
     * @see #listarUsuario(Usuario)
     */
    public Usuario autenticar(String username, String password) throws IOException {
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
        accesoDatos.listarRegistros();
        
        for (String[] datos : accesoDatos.getListaRegistros()) {
            if (datos.length == 7) {
                if (datos[5].equals(username) && datos[6].equals(password)) {
                    return new Usuario(
                        Integer.parseInt(datos[0]),
                        datos[1], datos[2], datos[3],
                        datos[4], datos[5], datos[6]
                    );
                }
            }
        }
        return null;
    }
}