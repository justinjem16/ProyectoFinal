/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import AccesoDatos.IdControl;
import Entidades.Usuario;
import Utilidades.NombresArchivos;
import java.io.IOException;

/**
 *Clase que maneja toda la lógica de negocio relacionada con los usuarios del sistema.
 * Proporciona métodos para agregar, actualizar, eliminar, listar y autenticar usuarios,
 * gestionando la persistencia de datos a través de archivos.
 * @author Justin
 */
public class LogicaUsuario extends LogicaBase {
    
    /** Controlador de IDs únicos para los usuarios */
    private IdControl idControl;
    
    /**
     * Crea una nueva instancia de LogicaUsuario.
     * Configura el archivo de datos de usuarios para las operaciones de acceso.
     */
    public LogicaUsuario() {
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
    }
    
    /**
     * Agrega un nuevo usuario al sistema.
     * Le asigna automáticamente un ID único y guarda toda su información en el archivo.
     * El usuario se almacena en formato CSV con sus datos personales y credenciales.
     * 
     * @param usuario objeto con la información del usuario a agregar
     * @throws IOException si ocurre un error al escribir en el archivo
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
     * Actualiza la información de un usuario existente.
     * Busca el usuario por su ID y reemplaza todos sus datos con la nueva información.
     * El ID del usuario no puede ser modificado.
     * 
     * @param usuario objeto con la información actualizada del usuario
     * @throws IOException si ocurre un error al modificar el archivo
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
     * Elimina un usuario del sistema.
     * Busca el usuario por su ID y lo elimina permanentemente del archivo.
     * Esta operación no puede deshacerse.
     * 
     * @param usuario objeto con el ID del usuario a eliminar
     * @throws IOException si ocurre un error al modificar el archivo
     */
    public void eliminarUsuario(Usuario usuario) throws IOException {
        accesoDatos.setIdRegistro(usuario.getId());
        accesoDatos.setEliminar(true);
        accesoDatos.modificarRegistro();
    }
    
    /**
     * Carga todos los usuarios del archivo y los agrega a la lista del objeto usuario.
     * Lee cada registro del archivo, crea objetos Usuario y los añade a la lista.
     * Solo procesa registros completos con los 7 campos esperados.
     * 
     * @param usuario objeto donde se almacenará la lista de usuarios cargados
     * @throws IOException si ocurre un error al leer el archivo
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
    
    /**
     * Autentica un usuario verificando sus credenciales.
     * Busca en el archivo un usuario que coincida con el nombre de usuario y contraseña proporcionados.
     * Si encuentra una coincidencia, retorna el objeto Usuario completo; si no, retorna null.
     * 
     * @param username nombre de usuario a verificar
     * @param password contraseña a verificar
     * @return objeto Usuario si las credenciales son correctas, null si no se encontró o son incorrectas
     * @throws IOException si ocurre un error al leer el archivo
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
