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
public class ContactoAdoptameController implements Initializable {

    private Main principal;
    @FXML
    private Button btnmensage;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void click(ActionEvent evento) {
        if (evento.getSource() == btnmensage) {
            System.out.println("Mensaje enviado.");
            principal.menuPrincipal();
        }
    }
}
