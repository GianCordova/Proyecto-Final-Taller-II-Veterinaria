/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class Vacunaciones {
    private int idVacunacion;
    private int idServicio;
    private int idMascota;
    private int idMedicamento;

    public Vacunaciones() {
    }

    public Vacunaciones(int idVacunacion, int idServicio, int idMascota, int idMedicamento) {
        this.idVacunacion = idVacunacion;
        this.idServicio = idServicio;
        this.idMascota = idMascota;
        this.idMedicamento = idMedicamento;
    }

    public int getIdVacunacion() {
        return idVacunacion;
    }

    public void setIdVacunacion(int idVacunacion) {
        this.idVacunacion = idVacunacion;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
    
    
}
