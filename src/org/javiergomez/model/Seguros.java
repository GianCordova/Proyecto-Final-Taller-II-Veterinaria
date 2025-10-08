package org.javiergomez.model;

import java.time.LocalDate;

/**
 *
 * @author informatica
 */
public class Seguros {
    private int idSeguro;
    private String nombreSeguro;
    private String tipoSeguro;
    private String cobertura;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double costo;   
    private int idClienteDueno;

    public Seguros() {
    }

    public Seguros(int idSeguro, String nombreSeguro, String tipoSeguro, String cobertura, LocalDate fechaInicio, LocalDate fechaFin, double costo, int idClienteDueno) {
        this.idSeguro = idSeguro;
        this.nombreSeguro = nombreSeguro;
        this.tipoSeguro = tipoSeguro;
        this.cobertura = cobertura;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costo = costo;
        this.idClienteDueno = idClienteDueno;
    }

    public int getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getNombreSeguro() {
        return nombreSeguro;
    }

    public void setNombreSeguro(String nombreSeguro) {
        this.nombreSeguro = nombreSeguro;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getIdClienteDueno() {
        return idClienteDueno;
    }

    public void setIdClienteDueno(int idClienteDueno) {
        this.idClienteDueno = idClienteDueno;
    }
    
   
    @Override
    public String toString() {
        return nombreSeguro + " | " + tipoSeguro + " | " + cobertura + " | " + fechaInicio + " | " + fechaFin + " | " + costo ;
    }
    
    
    
}
