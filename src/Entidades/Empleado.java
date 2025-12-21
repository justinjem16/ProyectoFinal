package Entidades;

/**
 * Clase LocalDate del paquete java.time que representa una fecha sin zona horaria.
 * 
 * <p>Se utiliza en esta clase para almacenar la fecha de ingreso del empleado
 * a la empresa, permitiendo realizar cálculos de antigüedad y gestión de
 * periodos laborales de forma precisa.
 */
import java.time.LocalDate;

/**
 * Clase DateTimeFormatter del paquete java.time.format para formatear fechas.
 * 
 * <p>Se utiliza para convertir objetos LocalDate a cadenas de texto con
 * formato específico (dd/MM/yyyy), facilitando la presentación de fechas
 * en la interfaz de usuario y reportes del sistema.
 */
import java.time.format.DateTimeFormatter;

/**
 * Clase ArrayList del paquete java.util que implementa una lista dinámica.
 * 
 * <p>Se utiliza para mantener una colección de empleados del sistema,
 * permitiendo agregar, eliminar o buscar empleados de manera flexible
 * sin necesidad de definir un tamaño fijo de antemano.
 */
import java.util.ArrayList;

/**
 * Clase que representa un empleado en el sistema de nómina.
 * 
 * <p>Esta clase encapsula toda la información personal, laboral y salarial
 * de un empleado registrado en el sistema. Incluye datos de identificación,
 * contacto, información laboral como puesto y salario, así como la gestión
 * de una lista de empleados para funcionalidades administrativas.
 * 
 * <p>Cada empleado tiene un identificador único asignado por el sistema,
 * una cédula de identidad, información de contacto y detalles laborales
 * que son utilizados para el cálculo de nóminas y generación de reportes.
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * LocalDate fechaIngreso = LocalDate.of(2024, 1, 15);
 * Empleado empleado = new Empleado(1, "118520345", "Juan", "Pérez", 
 *                                  "González", "juan.perez@email.com", 
 *                                  "88887777", 850000.0, "Quincenal", 
 *                                  "Desarrollador", fechaIngreso);
 * String nombreCompleto = empleado.getNombreCompleto();
 * // Retorna: "Juan Pérez González"
 * </pre>
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 */
public class Empleado {
    
    // ========================================================================
    // ATRIBUTOS PRIVADOS
    // ========================================================================
    
    /**
     * Identificador único del empleado en el sistema.
     * 
     * <p>Este ID es generado automáticamente por el sistema y se utiliza
     * como clave primaria para identificar de manera única a cada empleado
     * en la base de datos y en las operaciones del sistema de nómina.
     */
    private int id;
    
    /**
     * Número de cédula de identidad del empleado.
     * 
     * <p>Documento de identificación oficial del empleado, utilizado
     * para verificación de identidad y cumplimiento de requisitos legales.
     */
    private String cedula;
    
    /**
     * Primer nombre del empleado.
     * 
     * <p>Nombre de pila del empleado, utilizado junto con los apellidos
     * para conformar el nombre completo.
     */
    private String nombre;
    
    /**
     * Primer apellido del empleado.
     * 
     * <p>Apellido paterno del empleado, parte esencial de su identificación
     * completa en el sistema.
     */
    private String apellido1;
    
    /**
     * Segundo apellido del empleado.
     * 
     * <p>Apellido materno del empleado, complementa la identificación
     * completa del empleado en el sistema.
     */
    private String apellido2;
    
    /**
     * Dirección de correo electrónico del empleado.
     * 
     * <p>Utilizada para el envío de notificaciones, comprobantes de pago
     * y comunicaciones oficiales del sistema de nómina.
     */
    private String email;
    
    /**
     * Número de teléfono de contacto del empleado.
     * 
     * <p>Número telefónico principal para contactar al empleado en caso
     * de emergencias o notificaciones importantes.
     */
    private String telefono;
    
    /**
     * Salario bruto mensual del empleado.
     * 
     * <p>Salario base antes de aplicar deducciones de ley (cargas sociales,
     * impuesto sobre la renta, etc.). Este valor es utilizado como base
     * para el cálculo de la nómina.
     */
    private double salarioBruto;
    
    /**
     * Tipo de planilla a la que pertenece el empleado.
     * 
     * <p>Define la frecuencia de pago del empleado. Valores comunes incluyen:
     * "Quincenal", "Mensual", "Semanal". Este campo determina el periodo
     * de cálculo para la generación de nóminas.
     */
    private String tipoPlanilla;
    
    /**
     * Puesto o cargo que desempeña el empleado en la empresa.
     * 
     * <p>Descripción del rol laboral del empleado, como "Desarrollador",
     * "Contador", "Gerente", etc. Utilizado para clasificación y reportes.
     */
    private String puesto;
    
    /**
     * Fecha de ingreso del empleado a la empresa.
     * 
     * <p>Fecha en la que el empleado comenzó a laborar en la empresa.
     * Este campo es utilizado para calcular la antigüedad laboral,
     * prestaciones y otros beneficios que dependen del tiempo de servicio.
     */
    private LocalDate fechaIngreso;
    
    /**
     * Lista de empleados del sistema.
     * 
     * <p>Colección dinámica que almacena referencias a múltiples empleados,
     * utilizada para gestión administrativa y operaciones que requieren
     * manipular conjuntos de empleados.
     */
    private ArrayList<Empleado> listaEmpleados;
    
    // ========================================================================
    // CONSTRUCTORES
    // ========================================================================
    
    /**
     * Constructor parametrizado que inicializa un empleado con todos sus datos.
     * 
     * <p>Este constructor crea una instancia completa de Empleado con toda
     * la información personal, laboral y salarial necesaria para su registro
     * en el sistema de nómina.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * LocalDate fecha = LocalDate.of(2024, 1, 15);
     * Empleado emp = new Empleado(1, "118520345", "María", "López", 
     *                             "Rodríguez", "maria@email.com", 
     *                             "88889999", 950000.0, "Quincenal", 
     *                             "Analista", fecha);
     * </pre>
     * 
     * @param id identificador único del empleado en el sistema
     * @param cedula número de cédula de identidad del empleado
     * @param nombre primer nombre del empleado
     * @param apellido1 primer apellido del empleado
     * @param apellido2 segundo apellido del empleado
     * @param email dirección de correo electrónico del empleado
     * @param telefono número de teléfono de contacto
     * @param salarioBruto salario bruto mensual antes de deducciones
     * @param tipoPlanilla tipo de planilla (Quincenal, Mensual, etc.)
     * @param puesto cargo o posición laboral del empleado
     * @param fechaIngreso fecha en que el empleado ingresó a la empresa
     */
    public Empleado(int id, String cedula, String nombre, String apellido1, 
                    String apellido2, String email, String telefono, 
                    double salarioBruto, String tipoPlanilla, String puesto, 
                    LocalDate fechaIngreso) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.telefono = telefono;
        this.salarioBruto = salarioBruto;
        this.tipoPlanilla = tipoPlanilla;
        this.puesto = puesto;
        this.fechaIngreso = fechaIngreso;
    }
    
    /**
     * Constructor por defecto que inicializa un empleado vacío.
     * 
     * <p>Crea una nueva instancia de Empleado con la lista de empleados
     * inicializada como una lista vacía, lista para recibir elementos
     * mediante el método {@link #agregarListaEmpleados(Empleado)}.
     * 
     * <p>Este constructor es útil cuando se necesita crear un objeto
     * empleado para posteriormente configurar sus atributos mediante
     * los métodos setter correspondientes.
     */
    public Empleado() {
        listaEmpleados = new ArrayList<>();
    }
    
    // ========================================================================
    // MÉTODOS GETTER Y SETTER
    // ========================================================================
    
    /**
     * Obtiene el identificador único del empleado.
     * 
     * @return el ID del empleado como número entero (int)
     */
    public int getId() { 
        return id; 
    }
    
    /**
     * Establece el identificador único del empleado.
     * 
     * @param id el identificador único a asignar al empleado
     */
    public void setId(int id) { 
        this.id = id; 
    }
    
    /**
     * Obtiene el número de cédula del empleado.
     * 
     * @return la cédula del empleado como cadena de texto (String)
     */
    public String getCedula() { 
        return cedula; 
    }
    
    /**
     * Establece el número de cédula del empleado.
     * 
     * @param cedula el número de cédula de identidad a asignar
     */
    public void setCedula(String cedula) { 
        this.cedula = cedula; 
    }
    
    /**
     * Obtiene el primer nombre del empleado.
     * 
     * @return el nombre del empleado como cadena de texto (String)
     */
    public String getNombre() { 
        return nombre; 
    }
    
    /**
     * Establece el primer nombre del empleado.
     * 
     * @param nombre el nombre de pila a asignar al empleado
     */
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    /**
     * Obtiene el primer apellido del empleado.
     * 
     * @return el primer apellido como cadena de texto (String)
     */
    public String getApellido1() { 
        return apellido1; 
    }
    
    /**
     * Establece el primer apellido del empleado.
     * 
     * @param apellido1 el apellido paterno a asignar al empleado
     */
    public void setApellido1(String apellido1) { 
        this.apellido1 = apellido1; 
    }
    
    /**
     * Obtiene el segundo apellido del empleado.
     * 
     * @return el segundo apellido como cadena de texto (String)
     */
    public String getApellido2() { 
        return apellido2; 
    }
    
    /**
     * Establece el segundo apellido del empleado.
     * 
     * @param apellido2 el apellido materno a asignar al empleado
     */
    public void setApellido2(String apellido2) { 
        this.apellido2 = apellido2; 
    }
    
    /**
     * Obtiene la dirección de correo electrónico del empleado.
     * 
     * @return el email del empleado como cadena de texto (String)
     */
    public String getEmail() { 
        return email; 
    }
    
    /**
     * Establece la dirección de correo electrónico del empleado.
     * 
     * @param email la dirección de correo electrónico a asignar
     */
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    /**
     * Obtiene el número de teléfono del empleado.
     * 
     * @return el teléfono del empleado como cadena de texto (String)
     */
    public String getTelefono() { 
        return telefono; 
    }
    
    /**
     * Establece el número de teléfono del empleado.
     * 
     * @param telefono el número telefónico a asignar al empleado
     */
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }
    
    /**
     * Obtiene el salario bruto mensual del empleado.
     * 
     * @return el salario bruto como número decimal (double)
     */
    public double getSalarioBruto() { 
        return salarioBruto; 
    }
    
    /**
     * Establece el salario bruto mensual del empleado.
     * 
     * @param salarioBruto el salario bruto antes de deducciones a asignar
     */
    public void setSalarioBruto(double salarioBruto) { 
        this.salarioBruto = salarioBruto; 
    }
    
    /**
     * Obtiene el tipo de planilla del empleado.
     * 
     * @return el tipo de planilla como cadena de texto (String),
     *         por ejemplo: "Quincenal", "Mensual", "Semanal"
     */
    public String getTipoPlanilla() { 
        return tipoPlanilla; 
    }
    
    /**
     * Establece el tipo de planilla del empleado.
     * 
     * @param tipoPlanilla el tipo de planilla a asignar (frecuencia de pago)
     */
    public void setTipoPlanilla(String tipoPlanilla) { 
        this.tipoPlanilla = tipoPlanilla; 
    }
    
    /**
     * Obtiene el puesto o cargo del empleado.
     * 
     * @return el puesto laboral como cadena de texto (String)
     */
    public String getPuesto() { 
        return puesto; 
    }
    
    /**
     * Establece el puesto o cargo del empleado.
     * 
     * @param puesto la posición laboral a asignar al empleado
     */
    public void setPuesto(String puesto) { 
        this.puesto = puesto; 
    }
    
    /**
     * Obtiene la fecha de ingreso del empleado a la empresa.
     * 
     * @return la fecha de ingreso como objeto LocalDate
     */
    public LocalDate getFechaIngreso() { 
        return fechaIngreso; 
    }
    
    /**
     * Establece la fecha de ingreso del empleado a la empresa.
     * 
     * @param fechaIngreso la fecha en que el empleado comenzó a laborar
     */
    public void setFechaIngreso(LocalDate fechaIngreso) { 
        this.fechaIngreso = fechaIngreso; 
    }
    
    /**
     * Obtiene la lista de empleados del sistema.
     * 
     * @return una lista (ArrayList) con los empleados registrados
     */
    public ArrayList<Empleado> getListaEmpleados() { 
        return listaEmpleados; 
    }
    
    /**
     * Establece la lista completa de empleados del sistema.
     * 
     * <p>Este método reemplaza toda la lista existente de empleados
     * por una nueva lista proporcionada.
     * 
     * @param listaEmpleados la nueva lista de empleados a asignar
     */
    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) { 
        this.listaEmpleados = listaEmpleados; 
    }
    
    // ========================================================================
    // MÉTODOS PÚBLICOS
    // ========================================================================
    
    /**
     * Agrega un empleado a la lista de empleados del sistema.
     * 
     * <p>Este método permite añadir empleados individuales a la lista
     * sin reemplazar los empleados existentes.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Empleado empleadoGerente = new Empleado();
     * Empleado nuevoEmpleado = new Empleado(2, "205670890", "Ana", 
     *                                       "Ramírez", "Castro", 
     *                                       "ana@email.com", "88881234", 
     *                                       750000.0, "Quincenal", 
     *                                       "Asistente", LocalDate.now());
     * empleadoGerente.agregarListaEmpleados(nuevoEmpleado);
     * </pre>
     * 
     * @param empleado el objeto Empleado que se agregará a la lista
     */
    public void agregarListaEmpleados(Empleado empleado) {
        this.listaEmpleados.add(empleado);
    }
    
    /**
     * Obtiene el nombre completo del empleado.
     * 
     * <p>Concatena el nombre y ambos apellidos del empleado en una
     * sola cadena de texto, separados por espacios.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Empleado emp = new Empleado(1, "118520345", "Carlos", "Vargas", 
     *                             "Mora", "carlos@email.com", "88887777", 
     *                             850000.0, "Quincenal", "Contador", 
     *                             LocalDate.of(2023, 3, 15));
     * String nombreCompleto = emp.getNombreCompleto();
     * System.out.println(nombreCompleto); // Imprime: Carlos Vargas Mora
     * </pre>
     * 
     * @return el nombre completo del empleado en formato:
     *         "Nombre Apellido1 Apellido2"
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido1 + " " + apellido2;
    }
    
    /**
     * Obtiene la fecha de ingreso del empleado en formato legible.
     * 
     * <p>Convierte la fecha de ingreso almacenada como LocalDate a una
     * cadena de texto con formato dd/MM/yyyy (día/mes/año), facilitando
     * su presentación en reportes y la interfaz de usuario.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * Empleado emp = new Empleado(1, "118520345", "Laura", "González", 
     *                             "Pérez", "laura@email.com", "88885555", 
     *                             900000.0, "Mensual", "Gerente", 
     *                             LocalDate.of(2023, 5, 20));
     * String fecha = emp.getFechaIngresoFormateada();
     * System.out.println(fecha); // Imprime: 20/05/2023
     * </pre>
     * 
     * @return la fecha de ingreso formateada como String en formato
     *         "dd/MM/yyyy", o una cadena vacía si la fecha es null
     */
    public String getFechaIngresoFormateada() {
        if (fechaIngreso == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaIngreso.format(formatter);
    }
}
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaIngreso.format(formatter);
    }
}
