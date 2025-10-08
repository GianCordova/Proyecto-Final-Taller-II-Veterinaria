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
public class AdopcionOtrosController implements Initializable {
       private Main principal;
    @FXML
    private Button adoptar,regresar,adoptar2,adoptar3;
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
        if (evento.getSource() == adoptar) {
            System.out.println("Mensaje enviado.");
            principal.ContactoAdoptame();
        }else if (evento.getSource() == regresar) {
            System.out.println("Veterinaria PetSync");
            principal.Adopcion();
        }else  if (evento.getSource() == adoptar2) {
            System.out.println("Mensaje enviado.");
            principal.ContactoAdoptame();
        }else  if (evento.getSource() == adoptar3) {
            System.out.println("Mensaje enviado.");
            principal.ContactoAdoptame();
        }
    }
}
