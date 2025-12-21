

package Entidades;

/**
 * Clase ArrayList del paquete java.util que implementa una lista dinámica.
 * 
 * <p>Se utiliza para mantener una colección de usuarios del sistema,
 * permitiendo agregar, eliminar o buscar usuarios de manera flexible
 * sin necesidad de definir un tamaño fijo de antemano.
 */
import java.util.ArrayList;

/**
 * Clase que representa un usuario del sistema de nómina.
 * 
 * <p>Esta clase encapsula la información de autenticación y los datos
 * personales de un usuario que tiene acceso al sistema de gestión de nóminas.
 * Cada usuario cuenta con credenciales únicas (nombre de usuario y contraseña)
 * que le permiten autenticarse y acceder a las funcionalidades del sistema
 * según sus permisos asignados.
 * 
 * <p>Los usuarios pueden ser administradores, empleados del departamento de
 * recursos humanos, o cualquier otro rol que requiera acceso al sistema
 * para gestionar empleados, generar nóminas, consultar reportes o realizar
 * otras operaciones administrativas.
 * 
 * <p>Cada usuario tiene un identificador único asignado por el sistema,
 * información personal de contacto, y credenciales de acceso que se
 * utilizan durante el proceso de inicio de sesión.
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * Usuario usuario = new Usuario(1, "María", "González", "Pérez",
 *                               "maria.gonzalez@empresa.com",
 *                               "mgonzalez", "password123");
 * String nombreCompleto = usuario.getNombre() + " " + 
 *                         usuario.getApellido1() + " " + 
 *                         usuario.getApellido2();
 * </pre>
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 */
public class Usuario {
    
    // ========================================================================
    // ATRIBUTOS PRIVADOS
    // ========================================================================
    
    /**
     * Identificador único del usuario en el sistema.
     * 
     * <p>Este ID es generado automáticamente por el sistema y se utiliza
     * como clave primaria para identificar de manera única a cada usuario
     * registrado en el sistema de nómina.
     */
    private int id;
    
    /**
     * Primer nombre del usuario.
     * 
     * <p>Nombre de pila del usuario, utilizado junto con los apellidos
     * para conformar su identificación completa en el sistema.
     */
    private String nombre;
    
    /**
     * Primer apellido del usuario.
     * 
     * <p>Apellido paterno del usuario, parte esencial de su identificación
     * completa en el sistema.
     */
    private String apellido1;
    
    /**
     * Segundo apellido del usuario.
     * 
     * <p>Apellido materno del usuario, complementa la identificación
     * completa del usuario en el sistema.
     */
    private String apellido2;
    
    /**
     * Dirección de correo electrónico del usuario.
     * 
     * <p>Utilizada para el envío de notificaciones del sistema,
     * recuperación de contraseña y comunicaciones relacionadas con
     * la administración del sistema de nómina.
     */
    private String email;
    
    /**
     * Nombre de usuario para inicio de sesión.
     * 
     * <p>Identificador único de acceso que el usuario utiliza para
     * autenticarse en el sistema. Este nombre debe ser único en el
     * sistema y se utiliza en conjunto con la contraseña para
     * verificar la identidad del usuario.
     */
    private String usuario;
    
    /**
     * Contraseña del usuario para autenticación.
     * 
     * <p>Clave secreta que se utiliza junto con el nombre de usuario
     * para autenticar al usuario en el sistema. La contraseña debe
     * almacenarse de forma segura y cumplir con las políticas de
     * seguridad establecidas.
     */
    private String password;
    
    /**
     * Lista de usuarios del sistema.
     * 
     * <p>Colección dinámica que almacena referencias a múltiples usuarios,
     * utilizada para gestión administrativa y operaciones que requieren
     * manipular conjuntos de usuarios registrados en el sistema.
     */
    private ArrayList<Usuario> listaUsuarios;
    
    // ========================================================================
    // CONSTRUCTORES
    // ========================================================================
    
    /**
     * Constructor parametrizado que inicializa un usuario con todos sus datos.
     * 
     * <p>Este constructor crea una instancia completa de Usuario con toda
     * la información personal y las credenciales de acceso necesarias para
     * su registro en el sistema de nómina.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Usuario admin = new Usuario(1, "Carlos", "Ramírez", "López",
     *                             "carlos.ramirez@empresa.com",
     *                             "cramirez", "securePass456");
     * System.out.println("Usuario creado: " + admin.getUsuario());
     * </pre>
     * 
     * @param id identificador único del usuario en el sistema
     * @param nombre primer nombre del usuario
     * @param apellido1 primer apellido del usuario
     * @param apellido2 segundo apellido del usuario
     * @param email dirección de correo electrónico del usuario
     * @param usuario nombre de usuario para inicio de sesión
     * @param password contraseña del usuario para autenticación
     */
    public Usuario(int id, String nombre, String apellido1, String apellido2, 
                   String email, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
    }
    
    /**
     * Constructor por defecto que inicializa un usuario vacío.
     * 
     * <p>Crea una nueva instancia de Usuario con la lista de usuarios
     * inicializada como una lista vacía, lista para recibir elementos
     * mediante el método {@link #agregarListaUsuarios(Usuario)}.
     * 
     * <p>Este constructor es útil cuando se necesita crear un objeto
     * usuario para posteriormente configurar sus atributos mediante
     * los métodos setter correspondientes, o cuando se requiere
     * gestionar una colección de usuarios.
     */
    public Usuario() {
        listaUsuarios = new ArrayList<>();
    }
    
    // ========================================================================
    // MÉTODOS GETTER Y SETTER
    // ========================================================================
    
    /**
     * Obtiene el identificador único del usuario.
     * 
     * @return el ID del usuario como número entero (int)
     */
    public int getId() { 
        return id; 
    }
    
    /**
     * Establece el identificador único del usuario.
     * 
     * @param id el identificador único a asignar al usuario
     */
    public void setId(int id) { 
        this.id = id; 
    }
    
    /**
     * Obtiene el primer nombre del usuario.
     * 
     * @return el nombre del usuario como cadena de texto (String)
     */
    public String getNombre() { 
        return nombre; 
    }
    
    /**
     * Establece el primer nombre del usuario.
     * 
     * @param nombre el nombre de pila a asignar al usuario
     */
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    /**
     * Obtiene el primer apellido del usuario.
     * 
     * @return el primer apellido como cadena de texto (String)
     */
    public String getApellido1() { 
        return apellido1; 
    }
    
    /**
     * Establece el primer apellido del usuario.
     * 
     * @param apellido1 el apellido paterno a asignar al usuario
     */
    public void setApellido1(String apellido1) { 
        this.apellido1 = apellido1; 
    }
    
    /**
     * Obtiene el segundo apellido del usuario.
     * 
     * @return el segundo apellido como cadena de texto (String)
     */
    public String getApellido2() { 
        return apellido2; 
    }
    
    /**
     * Establece el segundo apellido del usuario.
     * 
     * @param apellido2 el apellido materno a asignar al usuario
     */
    public void setApellido2(String apellido2) { 
        this.apellido2 = apellido2; 
    }
    
    /**
     * Obtiene la dirección de correo electrónico del usuario.
     * 
     * @return el email del usuario como cadena de texto (String)
     */
    public String getEmail() { 
        return email; 
    }
    
    /**
     * Establece la dirección de correo electrónico del usuario.
     * 
     * @param email la dirección de correo electrónico a asignar
     */
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    /**
     * Obtiene el nombre de usuario para inicio de sesión.
     * 
     * @return el nombre de usuario como cadena de texto (String)
     */
    public String getUsuario() { 
        return usuario; 
    }
    
    /**
     * Establece el nombre de usuario para inicio de sesión.
     * 
     * @param usuario el nombre de usuario a asignar
     */
    public void setUsuario(String usuario) { 
        this.usuario = usuario; 
    }
    
    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return la contraseña como cadena de texto (String)
     */
    public String getPassword() { 
        return password; 
    }
    
    /**
     * Establece la contraseña del usuario.
     * 
     * @param password la contraseña a asignar al usuario
     */
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    /**
     * Obtiene la lista de usuarios del sistema.
     * 
     * @return una lista (ArrayList) con los usuarios registrados
     */
    public ArrayList<Usuario> getListaUsuarios() { 
        return listaUsuarios; 
    }
    
    /**
     * Establece la lista completa de usuarios del sistema.
     * 
     * <p>Este método reemplaza toda la lista existente de usuarios
     * por una nueva lista proporcionada.
     * 
     * @param listaUsuarios la nueva lista de usuarios a asignar
     */
    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) { 
        this.listaUsuarios = listaUsuarios; 
    }
    
    // ========================================================================
    // MÉTODOS PÚBLICOS
    // ========================================================================
    
    /**
     * Agrega un usuario a la lista de usuarios del sistema.
     * 
     * <p>Este método permite añadir usuarios individuales a la lista
     * sin reemplazar los usuarios existentes. Es útil para la gestión
     * administrativa de múltiples usuarios en el sistema.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Usuario administrador = new Usuario();
     * Usuario nuevoUsuario = new Usuario(2, "Ana", "Martínez", "Rojas",
     *                                    "ana.martinez@empresa.com",
     *                                    "amartinez", "pass789");
     * administrador.agregarListaUsuarios(nuevoUsuario);
     * </pre>
     * 
     * @param usuario el objeto Usuario que se agregará a la lista
     */
    public void agregarListaUsuarios(Usuario usuario) {
        this.listaUsuarios.add(usuario);
    }
}
