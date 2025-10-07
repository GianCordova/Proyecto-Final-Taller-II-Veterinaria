package org.javiergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.javiergomez.model.Medicamentos;
import org.javiergomez.model.Seguros;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class SegurosController implements Initializable {

    private Main principal;
    private Seguros modelo;
    private ObservableList<Seguros> listarSeguros;
    
    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }
    
    private Accion tipoAccion = Accion.NINGUNA;
            
     @FXML 
     private Button re;
     
    @FXML
    private TableView<Seguros> tablaSeguros;
    @FXML
    private TableColumn colIdSeguro, colNombreSeguro, colFinalidad, colDescripcion, colFechaVencimiento;
    @FXML
    private TextField txtBuscar, txtIdMedicamento, txtNombreMedicamento, txtFinalidad;
    @FXML
    private TextArea txtDescripcion; 
    @FXML
    private DatePicker dpFechaVencimiento; 
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
