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
public class PantallaInicioController implements Initializable {

    private Main principal;
    @FXML
    private MenuItem btnIniciarSesion, btnregresar;
    @FXML
    private Button ingresar;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void clickActionSecion(ActionEvent evento) {
        if (evento.getSource() == btnIniciarSesion) {
            System.out.println("Nos vamos a Iniciar Sesion");
            principal.inicioSecion();
        } else if (evento.getSource() == ingresar) {
            System.out.println(" Iniciar Sesion");
            principal.inicioSecion();
        }
    }
}
