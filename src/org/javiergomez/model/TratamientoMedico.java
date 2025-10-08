/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class TratamientoMedico {
    private int idTratamientoMedicamento;
    private int idTratamiento;
    private int idMedicamento;
    private String nombreTratamiento;
     private String descripcion;

    public TratamientoMedico() {
    }

    public TratamientoMedico(int idTratamientoMedicamento, int idTratamiento, int idMedicamento, String nombreTratamiento, String descripcion) {
        this.idTratamientoMedicamento = idTratamientoMedicamento;
        this.idTratamiento = idTratamiento;
        this.idMedicamento = idMedicamento;
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
    }

    public int getIdTratamientoMedicamento() {
        return idTratamientoMedicamento;
    }

    public void setIdTratamientoMedicamento(int idTratamientoMedicamento) {
        this.idTratamientoMedicamento = idTratamientoMedicamento;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
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
     
}
