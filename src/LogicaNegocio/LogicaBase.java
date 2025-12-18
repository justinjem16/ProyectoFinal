/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import AccesoDatos.AccesoDatos;

/**
 * Clase base abstracta para todas las lógicas de negocio.
 * 
 * @author Rachell Mora Reyes
 */
public abstract class LogicaBase {
    
    protected AccesoDatos accesoDatos;
    
    public LogicaBase() {
        this.accesoDatos = new AccesoDatos();
    }
}
