
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
public class InicioSecionController implements Initializable {
    private Main principal;
    @FXML private Button btnRegresar, btnIngresar,btnCrear;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    @FXML
    private void clickActionHandler(ActionEvent evento){
        if (evento.getSource()==btnRegresar) {
            System.out.println("Regresando al inicio");
            principal.pantallaInicio();
        }else if (evento.getSource()== btnIngresar){
            System.out.println("nos vamos a menu principal");
        principal.menuPrincipal();
        }else if(evento.getSource()==btnCrear){
            System.out.println("crear cuenta");
            principal.registrarase();
        }
    }
}
