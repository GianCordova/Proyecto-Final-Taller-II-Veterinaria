/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javiergomez.db.Conexion;
import org.javiergomez.model.Cliente;
import org.javiergomez.model.Mascotas;
import org.javiergomez.model.servicios;
import org.javiergomez.model.Medicamentos;
import org.javiergomez.model.Tratamientos;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class TratamientosController implements Initializable {
    
    private Main principal;
    private Tratamientos modelo;
    private ObservableList<Tratamientos> listarTratamientos;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;
    
    @FXML
    private Button re;
@FXML
    private TableView<Tratamientos> tablaTratamientos;
    @FXML
    private TableColumn colIdTratamiento, colIdServicio,colIdMedicamento,colNombre,colDescripcion;
    @FXML
    private TextField txtBuscar,txtIDTrata, txtIDsErvicio, txtIdMedicamento,txtNombre,txtDescripcion;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;
    @FXML
    private ComboBox<servicios> cbxServicio;
    @FXML
    private ComboBox<Medicamentos> cbxMedicamentos;
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
         configurarColumnas();
        cargarTablaTratamientos();
        cargarTratamientosEnTextField();
        cargarTratamientoEnTextField();

        tablaTratamientos.setOnMouseClicked(event -> cargarTratamientosEnCampos());

        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }
    
    @FXML
    private void cl(ActionEvent evento){
    if (evento.getSource() == re) {
            System.out.println("Nos vamos a contacto");
            principal.menuPrincipal();
    }
    }
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @FXML
    private void cl() {
        principal.menuPrincipal();
    }
    
     public void configurarColumnas() {
        //formato de columnas
        colIdTratamiento.setCellValueFactory(new PropertyValueFactory<Tratamientos, Integer>("idTratamiento"));
        colIdServicio.setCellValueFactory(new PropertyValueFactory<Tratamientos, Integer>("idServicio"));
        colIdMedicamento.setCellValueFactory(new PropertyValueFactory<Tratamientos, Integer>("idMedicamento"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Tratamientos, String>("nombreTratamiento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Tratamientos, String>("descripcion"));
    }
     
     private void cargarTablaTratamientos() {
        listarTratamientos = FXCollections.observableArrayList(listarTratamientos());
        tablaTratamientos.setItems(listarTratamientos);
        if (!listarTratamientos.isEmpty()) {
            tablaTratamientos.getSelectionModel().selectFirst();
            cargarTratamientosEnCampos();
        }
    }
     
     private ArrayList<Tratamientos> listarTratamientos() {
        ArrayList<Tratamientos> Tratamientos = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarTratamiento();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Tratamientos.add(new Tratamientos(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Tratamientos");
            e.printStackTrace();
        }
        return Tratamientos;
    }
     
     private void cargarTratamientosEnTextField() {
        ObservableList<servicios> servicios = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarServicios();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                servicios.add(new servicios(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxServicio.setItems(servicios);
    }
     
     private void cargarTratamientoEnTextField() {
        ObservableList<Medicamentos> medicamentos = FXCollections.observableArrayList();
        try {
           CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarMedicamentos();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                medicamentos.add(new Medicamentos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxMedicamentos.setItems(medicamentos);
    }
     
     private void cargarTratamientosEnCampos() {
        Tratamientos tratamientos = tablaTratamientos.getSelectionModel().getSelectedItem();
        if (tratamientos != null) {
            txtIDTrata.setText(String.valueOf(tratamientos.getIdTratamiento()));
            txtIDsErvicio.setText(String.valueOf(tratamientos.getIdServicio()));
            txtNombre.setText(tratamientos.getNombreTratamiento());
            txtIdMedicamento.setText(String.valueOf(tratamientos.getIdMedicamento()));
            txtDescripcion.setText(String.valueOf(tratamientos.getDescripcion()));

            for (servicios c : cbxServicio.getItems()) {
                if (c.getIdServicio()== tratamientos.getIdServicio()) {
                    cbxServicio.setValue(c);
                    break;
                } 
            }
            for (Medicamentos c : cbxMedicamentos.getItems()) {
                if (c.getIdMedicamento()== tratamientos.getIdMedicamento()) {
                    cbxMedicamentos.setValue(c);
                    break;
                }
            }
        }
    }
     private Tratamientos obtenerModelo() {
        int idTatamiento = txtIDTrata.getText().isEmpty() ? 0 : Integer.parseInt(txtIDTrata.getText());
        int idServicio = txtIDsErvicio.getText().isEmpty() ? 0 : Integer.parseInt(txtIDsErvicio.getText());
        String nombre = txtNombre.getText();
        String Descripcion = txtDescripcion.getText();
        int idMedicamento = txtIdMedicamento.getText().isEmpty() ? 0 : Integer.parseInt(txtIdMedicamento.getText());
        return new Tratamientos(idTatamiento, idServicio, idMedicamento, nombre, Descripcion);
    }
     
     private void agregarTratamientos() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_AgregarTratamientos(?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdServicio());
            cs.setInt(2, modelo.getIdMedicamento());
            cs.setString(3, modelo.getNombreTratamiento());
            cs.setString(4, modelo.getDescripcion());
            cs.executeUpdate();
            cargarTablaTratamientos();
            System.out.println("Tratamientos agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Tratamientos.");
            e.printStackTrace();
        }
    }
     private void actualizarTratamientos() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ActualizarTratamientos(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdTratamiento());
            cs.setInt(2, modelo.getIdServicio());
            cs.setInt(3, modelo.getIdMedicamento());
            cs.setString(4, modelo.getNombreTratamiento());
            cs.setString(5, modelo.getDescripcion());
            cs.executeUpdate();
            cargarTablaTratamientos();
            System.out.println("Tratamientos actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Tratamientos.");
            e.printStackTrace();
        }
    }
     private void eliminarTratamientos() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ElimarTratamientos(?);");
            cs.setInt(1, modelo.getIdTratamiento());
            cs.executeUpdate();
            cargarTablaTratamientos();
            System.out.println("Tratamientos eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Tratamientos.");
            e.printStackTrace();
        }
    }
     private void buscarTratamientos() {
        ArrayList<Tratamientos> resultado = new ArrayList<>();
        String nombre = txtBuscar.getText();
        for (Tratamientos m : listarTratamientos) {
            if (m.getNombreTratamiento().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(m);
            }
        }
        tablaTratamientos.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaTratamientos.getSelectionModel().selectFirst();
            cargarTratamientosEnCampos();
        }
    }

    private void limpiarCampos() {
        txtIDTrata.clear();
        txtIDsErvicio.clear();
        txtNombre.clear();
        txtIdMedicamento.clear();
        txtDescripcion.clear();
    }
     private void cambiarEstadoCampos(boolean estado) {
        txtIDTrata.setDisable(estado);
        txtNombre.setDisable(estado);
        txtIDsErvicio.setDisable(estado);
        txtIdMedicamento.setDisable(estado);
        txtDescripcion.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtNombre.isDisable();
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
            agregarTratamientos();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarTratamientos();
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
        cargarTablaTratamientos();
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
    }
    @FXML
    private void btnEditar() {
        tipoAccion = Accion.EDITAR;
        cambiarEstadoCampos(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarTratamientos();
        tipoAccion = Accion.NINGUNA;
        cargarTablaTratamientos();
    }

    @FXML
    private void btnBuscar() {
        buscarTratamientos();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaTratamientos.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaTratamientos.getSelectionModel().selectPrevious();
            cargarTablaTratamientos();
        }
    }
    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaTratamientos.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaTratamientos.getItems().size() - 1) {
            tablaTratamientos.getSelectionModel().selectNext();
            cargarTratamientosEnCampos();
        }
    }
}
