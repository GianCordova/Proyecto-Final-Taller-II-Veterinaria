
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
public class VeterinariaController implements Initializable {
     private Main principal;
    @FXML private Button btnRegresa;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void clicks(ActionEvent evento) {
        if (evento.getSource() == btnRegresa) {
            System.out.println("regresando.");
            principal.menuPrincipal();
        }
    }
}
