/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.javiergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class AdopcionController implements Initializable {

    private Main principal;
     @FXML private Button btnanimal,perro,gato,pajaro,otros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setPrincipal(Main principal){
        this.principal  = principal;
    }
@FXML
    private void click(ActionEvent evento) {
            if (evento.getSource() == perro) {
            System.out.println("Enviado a servicios");
            principal.AdopcionPerro();
    }   else if (evento.getSource() == gato) {
            System.out.println("Enviado a servicios");
            principal.AdopcionGato();
    }else if (evento.getSource() == pajaro) {
            System.out.println("Enviado a servicios");
            principal.AdopcionPajaro();
    }else if (evento.getSource() == otros) {
            System.out.println("Enviado a servicios");
            principal.AdopcionOtros();
    }else if (evento.getSource() == btnanimal) {
            System.out.println("Enviado a servicios");
            principal.AdopcionConejo();
    }
        }
        }