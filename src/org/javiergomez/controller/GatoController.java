
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
public class GatoController implements Initializable {

    private Main principal;
    @FXML
    private Button gato;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void click(ActionEvent evento) {
        if (evento.getSource() == gato) {
            System.out.println("Mensaje enviado.");
            principal.Planes();
        }
    }
}
