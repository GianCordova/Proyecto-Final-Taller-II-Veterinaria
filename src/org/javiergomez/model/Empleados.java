
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class Empleados {
     private int idEmpleado;
    private String nombreEmpleado;
    private String apellido;

    public Empleados() {
    }

    public Empleados(int idEmpleado, String nombreEmpleado, String apellido) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellido = apellido;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

        @Override
    public String toString() {
        return idEmpleado + " | " + nombreEmpleado + " | " + apellido;
    }
}
