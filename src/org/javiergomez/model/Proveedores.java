
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class Proveedores {
     private int idProveedor;
    private String nombreProveedor;
    private String nitProveedor;
    private String telefono;
    private String correo;

    public Proveedores() {
    }

    public Proveedores(int idProveedor, String nombreProveedor, String nitProveedor, String telefono, String correo) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.nitProveedor = nitProveedor;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
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
   
    @Override
    public String toString() {
        return idProveedor + " | " + nombreProveedor + " | " + nitProveedor + "  | " + telefono + " | " + correo;
    }
    
}