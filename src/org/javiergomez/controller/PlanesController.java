
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
 * @author jgome
 */
public class PlanesController implements Initializable {

    
     private Main principal;
    @FXML private Button btngato,regresar,btnPerro;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        @FXML
    private void click(ActionEvent evento) {
        if (evento.getSource() == btngato) {
            System.out.println("Mensaje enviado.");
            principal.Gato();
        }else if (evento.getSource() == regresar) {
            System.out.println("Veterinaria PetSync");
            principal.menuPrincipal();
        }else if (evento.getSource() == btnPerro) {
            System.out.println("Veterinaria PetSync");
            principal.Perro();
        }
    }
}




