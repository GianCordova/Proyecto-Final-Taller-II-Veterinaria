/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class Tratamientos {
    private int idTratamiento;
    private int idServicio;
    private int idMedicamento;
    private String nombreTratamiento;
    private String descripcion;

    public Tratamientos() {
    }

    public Tratamientos(int idTratamiento, int idServicio, int idMedicamento, String nombreTratamiento, String descripcion) {
        this.idTratamiento = idTratamiento;
        this.idServicio = idServicio;
        this.idMedicamento = idMedicamento;
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return idTratamiento + " | " +  idServicio + " | " + idMedicamento + " | "  + nombreTratamiento +  " | " + descripcion;
    }
}
