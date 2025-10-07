
package org.javiergomez.model;

/**
 *
 * @author informatica
 */
public class Mascotas {
      private int idMascota;
      private int  idClienteDueño;
    private String nombreMascota;
    private String sexo;
     private int edad;
      private String raza;
       private String tratamientoPrevio;

    public Mascotas() {
    }

    public Mascotas(int idMascota, int idClienteDueño, String nombreMascota, String sexo, int edad, String raza, String tratamientoPrevio) {
        this.idMascota = idMascota;
        this.idClienteDueño = idClienteDueño;
        this.nombreMascota = nombreMascota;
        this.sexo = sexo;
        this.edad = edad;
        this.raza = raza;
        this.tratamientoPrevio = tratamientoPrevio;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdClienteDueño() {
        return idClienteDueño;
    }

    public void setIdClienteDueño(int idClienteDueño) {
        this.idClienteDueño = idClienteDueño;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTratamientoPrevio() {
        return tratamientoPrevio;
    }

    public void setTratamientoPrevio(String tratamientoPrevio) {
        this.tratamientoPrevio = tratamientoPrevio;
    }

    @Override
    public String toString() {
        return idMascota + " | " +  idClienteDueño + " | " + nombreMascota + " | "  + sexo +  " | " + edad + " | " +  raza + " | " + tratamientoPrevio;
    }
       
}
