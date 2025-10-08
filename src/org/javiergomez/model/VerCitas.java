/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

import java.time.LocalDate;

public class VerCitas {
    private int idCita;
    private LocalDate fechaCita;
    private String nombreCliente;
    private String nombreMascota;
    private String nombreServicio;
    private String nombreVeterinario;

    public VerCitas(int idCita, LocalDate fecha, String nombreCliente, String nombreMascota, String nombreServicio, String nombreVeterinario) {
        this.idCita = idCita;
        this.fechaCita = fecha;
        this.nombreCliente = nombreCliente;
        this.nombreMascota = nombreMascota;
        this.nombreServicio = nombreServicio;
        this.nombreVeterinario = nombreVeterinario;
    }

    // Getters y Setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fechaCita;
    }

    public void setFecha(LocalDate fecha) {
        this.fechaCita = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }
}
