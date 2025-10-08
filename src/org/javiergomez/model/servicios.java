
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class servicios {
         private int idServicio;
    private String nombreServicio;
    private String descripcion;

    public servicios() {
    }
    
    public servicios(int idServicio, String nombreServicio, String descripcion) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return idServicio + " | " + nombreServicio + " | " + descripcion;
    }
}
