/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.javiergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.javiergomez.model.Medicamentos;
import org.javiergomez.model.Tratamientos;
import org.javiergomez.model.servicios;
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
import org.javiergomez.model.Empleados;
import org.javiergomez.model.Veterinarios;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class VeterinariosController implements Initializable {

    private Main principal;
    private Veterinarios modelo;
    private ObservableList<Veterinarios> listarVeterinarios;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;
    
    @FXML
    private Button re;
@FXML
    private TableView<Veterinarios> tablaVeterinarios;
    @FXML
    private TableColumn colIdVeterinarios, colNombreVeterinarios,colApellidoVeterinarios,colIdEmpleado,colSueldo;
    @FXML
    private TextField txtBuscar,txtIDVeterinarios, txtIDEmpleado, txtnombreVeterinarios,txtapellidoVeterinarios,txtSueldo;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar,VerCita;
    @FXML
    private ComboBox<Empleados> cbxEmpleado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaVeterinarios();
        cargarveterinariosEnTextField();
        tablaVeterinarios.setOnMouseClicked(event -> cargarveterinariosEnCampos());
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }    
    
     @FXML
    private void cl(ActionEvent evento){
    if (evento.getSource() == re) {
            System.out.println("Nos vamos a contacto");
            principal.menuPrincipal();
    }else if (evento.getSource() == VerCita) {
            System.out.println("Enviado a servicios");
            principal.VerCitas();
    }
    }
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    
    public void configurarColumnas() {
        //formato de columnas
        colIdVeterinarios.setCellValueFactory(new PropertyValueFactory<Veterinarios, Integer>("idVeterinario"));
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<Veterinarios, Integer>("idEmpleado"));
        colApellidoVeterinarios.setCellValueFactory(new PropertyValueFactory<Veterinarios, String>("apellidoVeterinario"));
        colNombreVeterinarios.setCellValueFactory(new PropertyValueFactory<Veterinarios, String>("nombreVeterinario"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Veterinarios, Double>("sueldo"));
    }
     
     private void cargarTablaVeterinarios() {
        listarVeterinarios = FXCollections.observableArrayList(listarVeterinarios());
        tablaVeterinarios.setItems(listarVeterinarios);
        if (!listarVeterinarios.isEmpty()) {
            tablaVeterinarios.getSelectionModel().selectFirst();
            cargarveterinariosEnCampos();
        }
    }
      private ArrayList<Veterinarios> listarVeterinarios() {
        ArrayList<Veterinarios> veterinarios = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarVeterinarios();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                veterinarios.add(new Veterinarios(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar veterinarios");
            e.printStackTrace();
        }
        return veterinarios;
    }
      
      private void cargarveterinariosEnTextField() {
        ObservableList<Empleados> empleados = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarEmpleados();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                empleados.add(new Empleados(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxEmpleado.setItems(empleados);
    }
      
       private void cargarveterinariosEnCampos() {
        Veterinarios veterinarios = tablaVeterinarios.getSelectionModel().getSelectedItem();
        if (veterinarios != null) {
            txtIDVeterinarios.setText(String.valueOf(veterinarios.getIdVeterinario()));
            txtIDEmpleado.setText(String.valueOf(veterinarios.getIdEmpleado()));
            txtnombreVeterinarios.setText(veterinarios.getNombreVeterinario());
            txtapellidoVeterinarios.setText(veterinarios.getApellidoVeterinario());
            txtSueldo.setText(String.valueOf(veterinarios.getSueldo()));

            for (Empleados c : cbxEmpleado.getItems()) {
                if (c.getIdEmpleado()== veterinarios.getIdEmpleado()) {
                    cbxEmpleado.setValue(c);
                    break;
                } 
            }
        }
    }
       
       private Veterinarios obtenerModelo() {
        int idveterinario = txtIDVeterinarios.getText().isEmpty() ? 0 : Integer.parseInt(txtIDVeterinarios.getText());
        String nombre = txtnombreVeterinarios.getText();
        String apellido = txtapellidoVeterinarios.getText();
        int idempleado = txtIDEmpleado.getText().isEmpty() ? 0 : Integer.parseInt(txtIDEmpleado.getText());
         double sueldo = txtSueldo.getText().isEmpty() ? 0 : Double.parseDouble(txtSueldo.getText());
        return new Veterinarios(idveterinario, nombre, apellido, idempleado, sueldo);
    }
       
        private void agregarVeterinario() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_AgregarVeterinarios(?, ?, ?, ?);");
            
            cs.setString(1, modelo.getNombreVeterinario());
            cs.setString(2, modelo.getApellidoVeterinario());
            cs.setInt(3, modelo.getIdEmpleado());
            cs.setDouble(4, modelo.getSueldo());
            cs.executeUpdate();
            cargarTablaVeterinarios();
            System.out.println("veterinarios agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar veterinarios.");
            e.printStackTrace();
        }
    }
        
        private void actualizarveterinarios() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ActualizarVeterinarios(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdVeterinario());
            
            cs.setString(2, modelo.getNombreVeterinario());
            cs.setString(3, modelo.getApellidoVeterinario());
            cs.setInt(4, modelo.getIdEmpleado());
            cs.setDouble(5, modelo.getSueldo());
            cs.executeUpdate();
            cargarTablaVeterinarios();
            System.out.println("veterinarios actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar veterinarios.");
            e.printStackTrace();
        }
    }
        
        private void eliminarVeterinario() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_EliminarVeterinarios(?);");
            cs.setInt(1, modelo.getIdVeterinario());
            cs.executeUpdate();
            cargarTablaVeterinarios();
            System.out.println("Veterinarios eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Veterinarios.");
            e.printStackTrace();
        }
    }
     private void buscarVeterinarios() {
        ArrayList<Veterinarios> resultado = new ArrayList<>();
        String nombre = txtBuscar.getText();
        for (Veterinarios m : listarVeterinarios) {
            if (m.getNombreVeterinario().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(m);
            }
        }
        tablaVeterinarios.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaVeterinarios.getSelectionModel().selectFirst();
            cargarveterinariosEnCampos();
        }
    }
     private void limpiarCampos() {
        txtIDVeterinarios.clear();
        txtIDEmpleado.clear();
        txtnombreVeterinarios.clear();
        txtapellidoVeterinarios.clear();
        txtSueldo.clear();
    }
     private void cambiarEstadoCampos(boolean estado) {
        txtIDVeterinarios.setDisable(estado);
        txtIDEmpleado.setDisable(estado);
        txtnombreVeterinarios.setDisable(estado);
        txtapellidoVeterinarios.setDisable(estado);
        txtSueldo.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtnombreVeterinarios.isDisable();
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
            agregarVeterinario();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarveterinarios();
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
        cargarTablaVeterinarios();
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
        eliminarVeterinario();
        tipoAccion = Accion.NINGUNA;
        cargarTablaVeterinarios();
    }

    @FXML
    private void btnBuscar() {
        buscarVeterinarios();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaVeterinarios.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaVeterinarios.getSelectionModel().selectPrevious();
            cargarveterinariosEnCampos();
        }
    }
    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaVeterinarios.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaVeterinarios.getItems().size() - 1) {
            tablaVeterinarios.getSelectionModel().selectNext();
            cargarveterinariosEnCampos();
        }
    }
}
