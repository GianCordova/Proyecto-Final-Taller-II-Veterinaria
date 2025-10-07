/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.javiergomez.model;

/**
 *
 * @author jgome
 */
public class Recetas {
    private int idReceta;
    private int idMedicamento;
    private int idMascota;
    private int idVeterinario;

    public Recetas() {
    }

    public Recetas(int idReceta, int idMedicamento, int idMascota, int idVeterinario) {
        this.idReceta = idReceta;
        this.idMedicamento = idMedicamento;
        this.idMascota = idMascota;
        this.idVeterinario = idVeterinario;
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

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }
    
    @Override
    public String toString() {
        return idReceta + " | " + idMascota + " | " + idMedicamento + " | " + idVeterinario;
    }
    
}
