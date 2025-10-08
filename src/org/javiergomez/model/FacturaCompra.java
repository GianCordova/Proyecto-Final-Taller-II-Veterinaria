/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class FacturaCompra {
     private int idFacturaCompra;
    private int idFactura;
    private int idCompra;
    private int cantidad;
    private double subtotal;

    public FacturaCompra() {
    }

    public FacturaCompra(int idFacturaCompra, int idFactura, int idCompra, int cantidad, double subtotal) {
        this.idFacturaCompra = idFacturaCompra;
        this.idFactura = idFactura;
        this.idCompra = idCompra;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getIdFacturaCompra() {
        return idFacturaCompra;
    }

    public void setIdFacturaCompra(int idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
}
