
package org.javiergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class RegistrarseController implements Initializable {
private Main principal;
    @FXML private Button btnIgrese,btnregresar;
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

  @FXML
    public void clickActionRegistrar(ActionEvent evento){
        if (evento.getSource()==btnIgrese) {
            System.out.println("Nos vamos a ingresar");
            principal.menuPrincipal();
        }else if (evento.getSource()== btnregresar){
            System.out.println("regresando..");
        principal.inicioSecion();
        }
    }
    
}
