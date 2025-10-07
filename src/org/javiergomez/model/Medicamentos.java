
package org.javiergomez.model;


import java.time.LocalDate;

/**
 *
 * @author jgome
 */
public class Medicamentos {
       private int idMedicamento;
    private String nombreMedicamento;
    private String finalidad;
    private String descripcion;
    private LocalDate fechaVencimiento;

    public Medicamentos() {
    }

    public Medicamentos(int idMedicamento, String nombreMedicamento, String finalidad, String descripcion, LocalDate fechaVencimiento) {
        this.idMedicamento = idMedicamento;
        this.nombreMedicamento = nombreMedicamento;
        this.finalidad = finalidad;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getFinalidad() {
        return finalidad;
    }

    public void setFinalidad(String finalidad) {
        this.finalidad = finalidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
     @Override
    public String toString() {
        return idMedicamento + " | " + nombreMedicamento + " | " + finalidad + " | " + descripcion + " | " +fechaVencimiento ;
    }
}
