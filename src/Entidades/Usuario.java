
package Entidades;
import java.util.ArrayList;

public class Usuario {
   // Identificador único del usuario
    private int id;
    
    // Datos personales del usuario
    private String nombre, apellido1, apellido2;
    
    // Información de acceso y contacto del usuario
    private String email, usuario, password;
    
    // Lista que almacena objetos de tipo Usuario
    private ArrayList<Usuario> listaUsuarios;

    // Constructor con parámetros para inicializar todos los atributos del usuario
    public Usuario(int id, String nombre, String apellido1, String apellido2, 
                   String email, String usuario, String password) {
        // Asigna el id recibido al atributo id
        this.id = id;
        
        // Asigna el nombre recibido al atributo nombre
        this.nombre = nombre;
        
        // Asigna el primer apellido al atributo apellido1
        this.apellido1 = apellido1;
        
        // Asigna el segundo apellido al atributo apellido2
        this.apellido2 = apellido2;
        
        // Asigna el email al atributo email
        this.email = email;
        
        // Asigna el nombre de usuario al atributo usuario
        this.usuario = usuario;
        
        // Asigna la contraseña al atributo password
        this.password = password;
    }

    // Constructor vacío
    public Usuario() {
        // Inicializa la lista de usuarios para evitar errores null
        listaUsuarios = new ArrayList<>();
    }

    // Devuelve el id del usuario
    public int getId() { 
        return id; 
    }

    // Asigna un valor al id del usuario
    public void setId(int id) { 
        this.id = id; 
    }

    // Devuelve el nombre del usuario
    public String getNombre() { 
        return nombre; 
    }

    // Asigna un valor al nombre del usuario
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    // Devuelve el primer apellido del usuario
    public String getApellido1() { 
        return apellido1; 
    }

    // Asigna un valor al primer apellido del usuario
    public void setApellido1(String apellido1) { 
        this.apellido1 = apellido1; 
    }

    // Devuelve el segundo apellido del usuario
    public String getApellido2() { 
        return apellido2; 
    }

    // Asigna un valor al segundo apellido del usuario
    public void setApellido2(String apellido2) { 
        this.apellido2 = apellido2; 
    }

    // Devuelve el email del usuario
    public String getEmail() { 
        return email; 
    }

    // Asigna un valor al email del usuario
    public void setEmail(String email) { 
        this.email = email; 
    }

    // Devuelve el nombre de usuario
    public String getUsuario() { 
        return usuario; 
    }

    // Asigna un valor al nombre de usuario
    public void setUsuario(String usuario) { 
        this.usuario = usuario; 
    }

    // Devuelve la contraseña del usuario
    public String getPassword() { 
        return password; 
    }

    // Asigna un valor a la contraseña del usuario
    public void setPassword(String password) { 
        this.password = password; 
    }

    // Devuelve la lista de usuarios
    public ArrayList<Usuario> getListaUsuarios() { 
        return listaUsuarios; 
    }

    // Asigna una nueva lista de usuarios
    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) { 
        this.listaUsuarios = listaUsuarios; 
    }

    // Agrega un usuario a la lista de usuarios
    public void agregarListaUsuarios(Usuario usuario) {
        this.listaUsuarios.add(usuario);
    } 
}
