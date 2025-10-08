/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class Facturas {
    private int idFactura;
    private int idCompra;
    private int idVeterinario;
    private int idMascota;
    private String nitClienteDueño;
    private double total;

    public Facturas() {
    }

    public Facturas(int idFactura, int idCompra, int idVeterinario, int idMascota, String nitClienteDueño, double total) {
        this.idFactura = idFactura;
        this.idCompra = idCompra;
        this.idVeterinario = idVeterinario;
        this.idMascota = idMascota;
        this.nitClienteDueño = nitClienteDueño;
        this.total = total;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNitClienteDueño() {
        return nitClienteDueño;
    }

    public void setNitClienteDueño(String nitClienteDueño) {
        this.nitClienteDueño = nitClienteDueño;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
      @Override
    public String toString() {
        return idFactura + " | " + idCompra + " | " + idVeterinario + " | " + idMascota + " | " + nitClienteDueño + " | " + total;
    } 
    
}
