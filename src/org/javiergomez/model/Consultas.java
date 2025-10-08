/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author jgome
 */
public class Consultas {

    private int idConsulta;
    private int idServicio;
    private int idClienteDueño;
    private int idMascota;
    private int idTratamiento;
    private int idVeterinario;
    private LocalDate  fechaConsulta;

    public Consultas() {
    }

    public Consultas(int idConsulta, int idServicio, int idClienteDueño, int idMascota, int idTratamiento, int idVeterinario, LocalDate fechaConsulta) {
        this.idConsulta = idConsulta;
        this.idServicio = idServicio;
        this.idClienteDueño = idClienteDueño;
        this.idMascota = idMascota;
        this.idTratamiento = idTratamiento;
        this.idVeterinario = idVeterinario;
        this.fechaConsulta = fechaConsulta;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    
    
    
}
