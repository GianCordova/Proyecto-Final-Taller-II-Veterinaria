/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.javiergomez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.javiergomez.model.Mascotas;
import org.javiergomez.model.Medicamentos;
import org.javiergomez.model.Vacunaciones;
import org.javiergomez.model.servicios;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class VacunacionesController implements Initializable {

    private Main principal;
    private Vacunaciones modelo;
    private ObservableList<Vacunaciones> listarVacunaciones;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

   @FXML
    private TableView<Vacunaciones> tablaVacunaciones;
    @FXML
    private TableColumn colIdVacunacion, colIdServicio, colIdMascota, colIdMedicamento;
    @FXML
    private TextField txtBuscar, txtIdVacunacion, txtIdServicio, txtIdMascota, txtIdMedicamento;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<servicios> cbxServicio;
    @FXML
    private ComboBox<Mascotas> cbxMascota;
    @FXML
    private ComboBox<Medicamentos> cbxMedicamento;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaVacunaciones();
        cargarVacunacionSeleccionada();
        cargarVacunacionSeleccionada();
        cargarMascotasEnCampos();
        cargarMedicamentosEnCampos();
        tablaVacunaciones.setOnMouseClicked(event -> cargarVacunacionSeleccionada());

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
     public void configurarColumnas() {
        colIdVacunacion.setCellValueFactory(new PropertyValueFactory<Vacunaciones, Integer>("idVacunacion"));
        colIdServicio.setCellValueFactory(new PropertyValueFactory<Vacunaciones, Integer>("idServicio"));
        colIdMascota.setCellValueFactory(new PropertyValueFactory<Vacunaciones, Integer>("idMascota"));
        colIdMedicamento.setCellValueFactory(new PropertyValueFactory<Vacunaciones, Integer>("idMedicamento"));
    }

    private void cargarTablaVacunaciones() {
        listarVacunaciones = FXCollections.observableArrayList(listarVacunaciones());
        tablaVacunaciones.setItems(listarVacunaciones);
        if (!listarVacunaciones.isEmpty()) {
            tablaVacunaciones.getSelectionModel().selectFirst();
            cargarVacunacionSeleccionada();
        }
    }

    private ArrayList<Vacunaciones> listarVacunaciones() {
        ArrayList<Vacunaciones> vacunaciones = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarVacunaciones();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                vacunaciones.add(new Vacunaciones(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Vacunaciones");
            e.printStackTrace();
        }
        return vacunaciones;
    }

    private void cargarVacunacionSeleccionada() {
        ObservableList<servicios> serviciosList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarServicios();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                serviciosList.add(new servicios(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxServicio.setItems(serviciosList);
}
private void cargarMascotasEnCampos() {
        ObservableList<Mascotas> mascotasList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_ListarMascotas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                mascotasList.add(new Mascotas(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxMascota.setItems(mascotasList);
}
private void cargarMedicamentosEnCampos() {
        ObservableList<Medicamentos> medicamentosList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarMedicamentos();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                medicamentosList.add(new Medicamentos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxMedicamento.setItems(medicamentosList);
    }

    

    private Vacunaciones obtenerModelo() {
        int idVacunacion = txtIdVacunacion.getText().isEmpty() ? 0 : Integer.parseInt(txtIdVacunacion.getText());
        int idServicio = cbxServicio.getValue() == null ? 0 : cbxServicio.getValue().getIdServicio();
        int idMascota = cbxMascota.getValue() == null ? 0 : cbxMascota.getValue().getIdMascota();
        int idMedicamento = cbxMedicamento.getValue() == null ? 0 : cbxMedicamento.getValue().getIdMedicamento();

        return new Vacunaciones(idVacunacion, idServicio, idMascota, idMedicamento);
    }

    private void agregarVacunacion() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarVacunacion(?, ?, ?);");
            cs.setInt(1, modelo.getIdServicio());
            cs.setInt(2, modelo.getIdMascota());
            cs.setInt(3, modelo.getIdMedicamento());
            cs.executeUpdate();
            cargarTablaVacunaciones();
            System.out.println("Vacunacion agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Vacunacion.");
            e.printStackTrace();
        }
    }

    private void actualizarVacunacion() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_editarVacunacion(?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdVacunacion());
            cs.setInt(2, modelo.getIdServicio());
            cs.setInt(3, modelo.getIdMascota());
            cs.setInt(4, modelo.getIdMedicamento());
            cs.executeUpdate();
            cargarTablaVacunaciones();
            System.out.println("Vacunacion actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Vacunacion.");
            e.printStackTrace();
        }
    }

    private void eliminarVacunacion() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarVacunacion(?);");
            cs.setInt(1, modelo.getIdVacunacion());
            cs.executeUpdate();
            cargarTablaVacunaciones();
            System.out.println("Vacunacion eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Vacunacion.");
            e.printStackTrace();
        }
    }

    private void buscarVacunacion() {
        ArrayList<Vacunaciones> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Vacunaciones v : listarVacunaciones) {
            if (String.valueOf(v.getIdVacunacion()).contains(texto) ||
                    (cbxServicio.getValue() != null && String.valueOf(v.getIdServicio()).contains(texto)) ||
                    (cbxMascota.getValue() != null && String.valueOf(v.getIdMascota()).contains(texto)) ||
                    (cbxMedicamento.getValue() != null && String.valueOf(v.getIdMedicamento()).contains(texto))) {
                resultado.add(v);
            }
        }

        tablaVacunaciones.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaVacunaciones.getSelectionModel().selectFirst();
            cargarVacunacionSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdVacunacion.clear();
        cbxServicio.setValue(null);
        cbxMascota.setValue(null);
        cbxMedicamento.setValue(null);
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdVacunacion.setDisable(estado);
        cbxServicio.setDisable(estado);
        cbxMascota.setDisable(estado);
        cbxMedicamento.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdVacunacion.isDisable();
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
            agregarVacunacion();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarVacunacion();
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
        cargarTablaVacunaciones();
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
        txtIdVacunacion.setDisable(true); 
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
        txtIdVacunacion.setDisable(true); 
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarVacunacion();
        tipoAccion = Accion.NINGUNA;
        cargarTablaVacunaciones();
    }

    @FXML
    private void btnBuscar() {
        buscarVacunacion();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaVacunaciones.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaVacunaciones.getSelectionModel().selectPrevious();
            cargarVacunacionSeleccionada();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaVacunaciones.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaVacunaciones.getItems().size() - 1) {
          tablaVacunaciones.getSelectionModel().selectNext();
            cargarVacunacionSeleccionada();
        }
    }
}
