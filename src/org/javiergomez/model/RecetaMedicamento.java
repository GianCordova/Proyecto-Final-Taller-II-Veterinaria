/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class RecetaMedicamento {
     private int idRecetaMedicamento;
    private int idReceta;
    private int idMedicamento;
    private int cantidad;
    private String dosis;
    private String frecuencia;
     private String observaciones;

    public RecetaMedicamento() {
    }

    public RecetaMedicamento(int idRecetaMedicamento, int idReceta, int idMedicamento, int cantidad, String dosis, String frecuencia, String observaciones) {
        this.idRecetaMedicamento = idRecetaMedicamento;
        this.idReceta = idReceta;
        this.idMedicamento = idMedicamento;
        this.cantidad = cantidad;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.observaciones = observaciones;
    }

    public int getIdRecetaMedicamento() {
        return idRecetaMedicamento;
    }

    public void setIdRecetaMedicamento(int idRecetaMedicamento) {
        this.idRecetaMedicamento = idRecetaMedicamento;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
     
     
}
