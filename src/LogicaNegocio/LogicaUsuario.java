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
 * Lógica de negocio para usuarios.
 * 
 * @author Rachell Mora Reyes
 */
public class LogicaUsuario extends LogicaBase {

    private IdControl idControl;
    
    public LogicaUsuario() {
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
    }

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

    public void eliminarUsuario(Usuario usuario) throws IOException {
        accesoDatos.setIdRegistro(usuario.getId());
        accesoDatos.setEliminar(true);
        accesoDatos.modificarRegistro();
    }

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
     * Autentica un usuario.
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
