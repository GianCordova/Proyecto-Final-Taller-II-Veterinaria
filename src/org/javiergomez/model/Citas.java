/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

import java.time.LocalDate;

/**
 *
 * @author jgome
 */
public class Citas {
     private int idCita;
    private int idServicio;
    private LocalDate fechaCita;
    private int idClienteDueño;
    private int idMascota;

    public Citas() {
    }

    public Citas(int idCita, int idServicio, LocalDate fechaCita, int idClienteDueño, int idMascota) {
        this.idCita = idCita;
        this.idServicio = idServicio;
        this.fechaCita = fechaCita;
        this.idClienteDueño = idClienteDueño;
        this.idMascota = idMascota;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public int getIdClienteDueño() {
        return idClienteDueño;
    }

    public void setIdClienteDueño(int idClienteDueño) {
        this.idClienteDueño = idClienteDueño;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }
    
    
}
