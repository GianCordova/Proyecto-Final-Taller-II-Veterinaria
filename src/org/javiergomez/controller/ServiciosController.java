
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
public class ServiciosController implements Initializable {
    
    private Main principal;
    @FXML
    private Button btnContacto,btnContactos;

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
    private void clickAction(ActionEvent evento){
    if (evento.getSource() == btnContacto) {
            System.out.println("Nos vamos a contacto");
            principal.Contacto();
    }else if (evento.getSource() == btnContactos) {
            System.out.println("Nos vamos a contacto");
            principal.Contacto();
}
}
}