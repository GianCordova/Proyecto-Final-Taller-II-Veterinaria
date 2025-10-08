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
public class Compras {
    private int idCompra;
    private int idServicio;
    private LocalDate fechaCompra;
    private int idMedicamento;
    private int idProveedor;
    private int idReceta;
    private double subtotal;

    public Compras() {
    }

    public Compras(int idCompra, int idServicio, LocalDate fechaCompra, int idMedicamento, int idProveedor, int idReceta, double subtotal) {
        this.idCompra = idCompra;
        this.idServicio = idServicio;
        this.fechaCompra = fechaCompra;
        this.idMedicamento = idMedicamento;
        this.idProveedor = idProveedor;
        this.idReceta = idReceta;
        this.subtotal = subtotal;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

  @Override
    public String toString() {
        return idCompra + " | " + idServicio + " | " + fechaCompra + " | " + idMedicamento + " | " + idProveedor + " | " + idReceta + " | " +subtotal;
    } 
    
    
}
