
package Entidades;
import java.util.ArrayList;

public class Empleado {
    // Identificador único del empleado
    private int id;
    
    // Número de cédula del empleado
    private String cedula;
    
    // Nombre del empleado
    private String nombre;
    
    // Primer apellido del empleado
    private String apellido1;
    
    // Segundo apellido del empleado
    private String apellido2;
    
    // Correo electrónico del empleado
    private String email;
    
    // Número de teléfono del empleado
    private String telefono;
    
    // Salario bruto del empleado
    private double salarioBruto;
    
    // Tipo de planilla a la que pertenece el empleado
    private String tipoPlanilla;
    
    // Puesto o cargo que desempeña el empleado
    private String puesto;
    
    // Lista que almacena objetos de tipo Empleado
    private ArrayList<Empleado> listaEmpleados;
    
    // Constructor con parámetros para inicializar todos los atributos del empleado
    public Empleado(int id, String cedula, String nombre, String apellido1, 
                    String apellido2, String email, String telefono, 
                    double salarioBruto, String tipoPlanilla, String puesto) {
        // Asigna el id recibido
        this.id = id;
        
        // Asigna la cédula recibida
        this.cedula = cedula;
        
        // Asigna el nombre recibido
        this.nombre = nombre;
        
        // Asigna el primer apellido recibido
        this.apellido1 = apellido1;
        
        // Asigna el segundo apellido recibido
        this.apellido2 = apellido2;
        
        // Asigna el email recibido
        this.email = email;
        
        // Asigna el teléfono recibido
        this.telefono = telefono;
        
        // Asigna el salario bruto recibido
        this.salarioBruto = salarioBruto;
        
        // Asigna el tipo de planilla
        this.tipoPlanilla = tipoPlanilla;
        
        // Asigna el puesto del empleado
        this.puesto = puesto;
    }
    
    // Constructor vacío
    public Empleado() {
        // Inicializa la lista de empleados para evitar valores null
        listaEmpleados = new ArrayList<>();
    }
    
    // Devuelve el id del empleado
    public int getId() { 
        return id; 
    }
    
    // Asigna un valor al id del empleado
    public void setId(int id) { 
        this.id = id; 
    }
    
    // Devuelve la cédula del empleado
    public String getCedula() { 
        return cedula; 
    }
    
    // Asigna un valor a la cédula del empleado
    public void setCedula(String cedula) { 
        this.cedula = cedula; 
    }
    
    // Devuelve el nombre del empleado
    public String getNombre() { 
        return nombre; 
    }
    
    // Asigna un valor al nombre del empleado
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    // Devuelve el primer apellido del empleado
    public String getApellido1() { 
        return apellido1; 
    }
    
    // Asigna un valor al primer apellido del empleado
    public void setApellido1(String apellido1) { 
        this.apellido1 = apellido1; 
    }
    
    // Devuelve el segundo apellido del empleado
    public String getApellido2() { 
        return apellido2; 
    }
    
    // Asigna un valor al segundo apellido del empleado
    public void setApellido2(String apellido2) { 
        this.apellido2 = apellido2; 
    }
    
    // Devuelve el correo electrónico del empleado
    public String getEmail() { 
        return email; 
    }
    
    // Asigna un valor al correo electrónico del empleado
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    // Devuelve el teléfono del empleado
    public String getTelefono() { 
        return telefono; 
    }
    
    // Asigna un valor al teléfono del empleado
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }
    
    // Devuelve el salario bruto del empleado
    public double getSalarioBruto() { 
        return salarioBruto; 
    }
    
    // Asigna un valor al salario bruto del empleado
    public void setSalarioBruto(double salarioBruto) { 
        this.salarioBruto = salarioBruto; 
    }
    
    // Devuelve el tipo de planilla del empleado
    public String getTipoPlanilla() { 
        return tipoPlanilla; 
    }
    
    // Asigna un valor al tipo de planilla
    public void setTipoPlanilla(String tipoPlanilla) { 
        this.tipoPlanilla = tipoPlanilla; 
    }
    
    // Devuelve el puesto del empleado
    public String getPuesto() { 
        return puesto; 
    }
    
    // Asigna un valor al puesto del empleado
    public void setPuesto(String puesto) { 
        this.puesto = puesto; 
    }
    
    // Devuelve la lista de empleados
    public ArrayList<Empleado> getListaEmpleados() { 
        return listaEmpleados; 
    }
    
    // Asigna una nueva lista de empleados
    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) { 
        this.listaEmpleados = listaEmpleados; 
    }
    
    // Agrega un empleado a la lista de empleados
    public void agregarListaEmpleados(Empleado empleado) {
        this.listaEmpleados.add(empleado);
    }
    
    // Devuelve el nombre completo del empleado (nombre + apellidos)
    public String getNombreCompleto() {
        return nombre + " " + apellido1 + " " + apellido2;
    }
}
