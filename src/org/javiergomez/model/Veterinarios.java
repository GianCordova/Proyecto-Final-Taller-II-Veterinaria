/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class Veterinarios {
     private int idVeterinario;
    private String nombreVeterinario;
    private String apellidoVeterinario;
    private int idEmpleado;
    private double  sueldo;

    public Veterinarios() {
    }

    public Veterinarios(int idVeterinario, String nombreVeterinario, String apellidoVeterinario, int idEmpleado, double sueldo) {
        this.idVeterinario = idVeterinario;
        this.nombreVeterinario = nombreVeterinario;
        this.apellidoVeterinario = apellidoVeterinario;
        this.idEmpleado = idEmpleado;
        this.sueldo = sueldo;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public String getApellidoVeterinario() {
        return apellidoVeterinario;
    }

    public void setApellidoVeterinario(String apellidoVeterinario) {
        this.apellidoVeterinario = apellidoVeterinario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

  @Override
    public String toString() {
        return idVeterinario + " | " +  nombreVeterinario + " | " + apellidoVeterinario + " | "  + idEmpleado +  " | " + sueldo;
    }
    
}
