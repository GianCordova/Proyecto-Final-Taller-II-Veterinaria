package org.javiergomez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javiergomez.db.Conexion;
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
    private TableColumn colIdSeguro, colNombreSeguro, colTipoSeguro, colCobertura, colFechaInicio, colFechaFin, colCosto, colIdClienteDueño;
    @FXML
    private TextField txtBuscar, txtIdSeguro, txtNombreSeguro, txtTipoSeguro, txtCobertura, txtCosto, txtIdClienteDueño;
    @FXML
    private DatePicker dpFechaInicio, dpFechaFin; 
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaSeguros();
        cargarSeguroSeleccionado(); 
        tablaSeguros.setOnMouseClicked(event -> cargarSeguroSeleccionado());

        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }
    
    @FXML
    private void cl(ActionEvent evento) {
        if (evento.getSource() == re) {
            System.out.println("Nos vamos a menu principal");
            principal.menuPrincipal();
        }
    }
    
    public void configurarColumnas() {
        colIdSeguro.setCellValueFactory(new PropertyValueFactory<Seguros, Integer>("idSeguro"));
        colNombreSeguro.setCellValueFactory(new PropertyValueFactory<Seguros, String>("nombreSeguro"));
        colTipoSeguro.setCellValueFactory(new PropertyValueFactory<Seguros, String>("tipoSeguro"));
        colCobertura.setCellValueFactory(new PropertyValueFactory<Seguros, String>("cobertura"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Seguros, LocalDate>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<Seguros, LocalDate>("fechaFin"));
        colCosto.setCellValueFactory(new PropertyValueFactory<Seguros, Double>("costo"));
        colIdClienteDueño.setCellValueFactory(new PropertyValueFactory<Seguros, Integer>("idClienteDueno"));
    }

    private void cargarTablaSeguros() {
        listarSeguros = FXCollections.observableArrayList(listarSeguros());
        tablaSeguros.setItems(listarSeguros);
        if (!listarSeguros.isEmpty()) {
            tablaSeguros.getSelectionModel().selectFirst();
            cargarSeguroSeleccionado();
        }
    }
    
    private ArrayList<Seguros> listarSeguros() {
        ArrayList<Seguros> seguros = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarSeguros();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                seguros.add(new Seguros(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate(), 
                        rs.getDate(6).toLocalDate(),
                        rs.getDouble(7),
                        rs.getInt(8)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Seguros");
            e.printStackTrace();
        }
        return seguros;
    }
    
    private void cargarSeguroSeleccionado() {
        Seguros seguro = tablaSeguros.getSelectionModel().getSelectedItem();
        if (seguro != null) {
            txtIdSeguro.setText(String.valueOf(seguro.getIdSeguro()));
            txtNombreSeguro.setText(seguro.getNombreSeguro());
            txtTipoSeguro.setText(seguro.getTipoSeguro());
            txtCobertura.setText(seguro.getCobertura());
            txtCosto.setText(String.valueOf(seguro.getCosto()));
            dpFechaInicio.setValue(seguro.getFechaInicio());
            dpFechaFin.setValue(seguro.getFechaFin());
            txtIdClienteDueño.setText(String.valueOf(seguro.getIdClienteDueno()));
        }
    }

    private Seguros obtenerModelo() {
        int idSeguro = txtIdSeguro.getText().isEmpty() ? 0 : Integer.parseInt(txtIdSeguro.getText());
        String nombreSeguro = txtNombreSeguro.getText();
        String tipoSeguro = txtTipoSeguro.getText();
        String cobertura = txtCobertura.getText();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        double costo = txtCosto.getText().isEmpty() ? 0.0 : Double.parseDouble(txtCosto.getText());
        int idClienteDueno = txtIdClienteDueño.getText().isEmpty() ? 0 : Integer.parseInt(txtIdClienteDueño.getText());
        
        return new Seguros(idSeguro, nombreSeguro, tipoSeguro, cobertura, fechaInicio, fechaFin, costo, idClienteDueno);
    }
    
    private void agregarSeguro() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarSeguro(?, ?, ?, ?, ?, ?, ?);");
            cs.setString(1, modelo.getNombreSeguro());
            cs.setString(2, modelo.getTipoSeguro());
            cs.setString(3, modelo.getCobertura());
            cs.setDate(4, java.sql.Date.valueOf(modelo.getFechaInicio())); 
            cs.setDate(5, java.sql.Date.valueOf(modelo.getFechaFin())); 
            cs.setDouble(6, modelo.getCosto());
            cs.setInt(7, modelo.getIdClienteDueno());
            cs.executeUpdate();
            cargarTablaSeguros();
            System.out.println("Seguro agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Seguro.");
            e.printStackTrace();
        }
    }

    private void actualizarSeguro() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarSeguro(?, ?, ?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdSeguro());
            cs.setString(2, modelo.getNombreSeguro());
            cs.setString(3, modelo.getTipoSeguro());
            cs.setString(4, modelo.getCobertura());
            cs.setDate(5, java.sql.Date.valueOf(modelo.getFechaInicio())); 
            cs.setDate(6, java.sql.Date.valueOf(modelo.getFechaFin())); 
            cs.setDouble(7, modelo.getCosto());
            cs.setInt(8, modelo.getIdClienteDueno());
            cs.executeUpdate();
            cargarTablaSeguros();
            System.out.println("Seguro actualizado.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Seguro.");
            e.printStackTrace();
        }
    }

    private void eliminarSeguro() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarsSeguro(?);");
            cs.setInt(1, modelo.getIdSeguro());
            cs.executeUpdate();
            cargarTablaSeguros();
            System.out.println("Seguro eliminado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Seguro.");
            e.printStackTrace();
        }
    }

    private void buscarSeguros() {
        ArrayList<Seguros> resultado = new ArrayList<>();
        String nombre = txtBuscar.getText();
        for (Seguros m : listarSeguros) {
            if (m.getNombreSeguro().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(m);
            }
        }
        tablaSeguros.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaSeguros.getSelectionModel().selectFirst();
            cargarSeguroSeleccionado();
        }
    }


    private void limpiarCampos() {
        txtIdSeguro.clear();
        txtNombreSeguro.clear();
        txtTipoSeguro.clear();
        txtCobertura.clear();
        dpFechaInicio.setValue(null);
        dpFechaFin.setValue(null);
        txtIdClienteDueño.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdClienteDueño.setDisable(estado);
        txtNombreSeguro.setDisable(estado);
        txtTipoSeguro.setDisable(estado);
        txtCobertura.setDisable(estado);
        dpFechaInicio.setDisable(estado);
        dpFechaFin.setDisable(estado);
        txtIdClienteDueño.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdSeguro.isDisable();
        cambiarEstadoCampos(!desactivado);

        btnAnterior.setDisable(desactivado);
        btnSiguiente.setDisable(desactivado);
        btnNuevo.setDisable(desactivado);
        btnEditar.setDisable(desactivado);
        btnEliminar.setDisable(desactivado);

        btnGuardar.setDisable(!desactivado);
        btnCancelar.setDisable(!desactivado);
    }

    @FXML
    private void btnGuardar() {
        if (tipoAccion == Accion.AGREGAR) {
            agregarSeguro();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarSeguro();
        }
        tipoAccion = Accion.NINGUNA;
        habilitarDeshabilitarBotones();
        limpiarCampos();
    }

    @FXML
    private void btnCancelar() {
        tipoAccion = Accion.NINGUNA;
        habilitarDeshabilitarBotones();
        limpiarCampos();
        cargarTablaSeguros();
    }

    @FXML
    private void btnNuevo() {
        tipoAccion = Accion.AGREGAR;
        limpiarCampos();
        cambiarEstadoCampos(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        txtIdSeguro.setDisable(true); 
    }

    @FXML
    private void btnEditar() {
        tipoAccion = Accion.EDITAR;
        cambiarEstadoCampos(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        txtIdSeguro.setDisable(true); 
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarSeguro();
        tipoAccion = Accion.NINGUNA;
        cargarTablaSeguros();
    }

    @FXML
    private void btnBuscar() {
        buscarSeguros();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaSeguros.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaSeguros.getSelectionModel().selectPrevious();
            cargarSeguroSeleccionado();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaSeguros.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaSeguros.getItems().size() - 1) {
            tablaSeguros.getSelectionModel().selectNext();
            cargarSeguroSeleccionado();
        }
    }
}
    
    
