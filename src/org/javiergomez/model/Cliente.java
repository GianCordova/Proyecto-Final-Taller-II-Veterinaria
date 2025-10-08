
package org.javiergomez.model;
import java.time.LocalDate;
/**
 *
 * @author jgome
 */
public class Cliente {
    private int idClienteDueño;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String nitClienteDueño;
    private String estado;

    public Cliente() {
    }

    public Cliente(int idClienteDueño, String nombre, String apellido, String telefono, String correo, String nitClienteDueño, String estado) {
        this.idClienteDueño = idClienteDueño;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.nitClienteDueño = nitClienteDueño;
        this.estado = estado;
    }

    public int getIdClienteDueño() {
        return idClienteDueño;
    }

    public void setIdClienteDueño(int idClienteDueño) {
        this.idClienteDueño = idClienteDueño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNitClienteDueño() {
        return nitClienteDueño;
    }

    public void setNitClienteDueño(String nitClienteDueño) {
        this.nitClienteDueño = nitClienteDueño;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return idClienteDueño + " | " + nombre + " | " + apellido;
    }
}
