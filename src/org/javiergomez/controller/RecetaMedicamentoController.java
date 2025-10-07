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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javiergomez.db.Conexion;
import org.javiergomez.model.Medicamentos;
import org.javiergomez.model.RecetaMedicamento;
import org.javiergomez.model.Recetas;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class RecetaMedicamentoController implements Initializable {

    private Main principal;
    private RecetaMedicamento modelo;
    private ObservableList<RecetaMedicamento> listarRecetasMedicamentos;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

    @FXML
    private TableView<RecetaMedicamento> tablaRecetasMedicamentos;
    @FXML
    private TableColumn colIdRecetaMedicamento, colIdReceta, colIdMedicamento, colCantidad, colDosis, colFrecuencia, colObservaciones;
    @FXML
    private TextField txtBuscar, txtIdRecetaMedicamento, txtIdReceta, txtIdMedicamento, txtCantidad, txtDosis, txtFrecuencia;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<Recetas> cbxReceta;
    @FXML
    private ComboBox<Medicamentos> cbxMedicamento;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaRecetasMedicamentos();
        cargarRecetasMedicamentosEnCampos();
        cargarMedicamentosEnCampos();
        tablaRecetasMedicamentos.setOnMouseClicked(event -> cargarTablaRecetasMedicamentos());

        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }

    @FXML
    private void cl(ActionEvent evento) {
        if (evento.getSource() == re) {
            System.out.println("Nos vamos a contacto");
            principal.menuPrincipal();
        }
    }

    public void configurarColumnas() {
        colIdRecetaMedicamento.setCellValueFactory(new PropertyValueFactory<RecetaMedicamento, Integer>("idRecetaMedicamento"));
        colIdReceta.setCellValueFactory(new PropertyValueFactory<RecetaMedicamento, Integer>("idReceta"));
        colIdMedicamento.setCellValueFactory(new PropertyValueFactory<RecetaMedicamento, Integer>("idMedicamento"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<RecetaMedicamento, Integer>("cantidad"));
        colDosis.setCellValueFactory(new PropertyValueFactory<RecetaMedicamento, String>("dosis"));
        colFrecuencia.setCellValueFactory(new PropertyValueFactory<RecetaMedicamento, String>("frecuencia"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<RecetaMedicamento, String>("observaciones"));
    }

    private void cargarTablaRecetasMedicamentos() {
        listarRecetasMedicamentos = FXCollections.observableArrayList(listarRecetasMedicamentos());
        tablaRecetasMedicamentos.setItems(listarRecetasMedicamentos);
        if (!listarRecetasMedicamentos.isEmpty()) {
            tablaRecetasMedicamentos.getSelectionModel().selectFirst();
            cargarRecetaMedicamentoSeleccionada();
        }
    }

    private ArrayList<RecetaMedicamento> listarRecetasMedicamentos() {
        ArrayList<RecetaMedicamento> recetasMedicamentos = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarRecetasMedicamentos();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                recetasMedicamentos.add(new RecetaMedicamento(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Recetas Medicamentos");
            e.printStackTrace();
        }
        return recetasMedicamentos;
    }

    private void cargarRecetasMedicamentosEnCampos() {
        ObservableList<Recetas> recetasList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarRecetas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                recetasList.add(new Recetas(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxReceta.setItems(recetasList);
    }

    private void cargarMedicamentosEnCampos() {
        ObservableList<Medicamentos> medicamentosList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarMedicamentos();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                medicamentosList.add(new Medicamentos(rs.getInt(1),
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

    private void cargarRecetaMedicamentoSeleccionada() {
        RecetaMedicamento recetaMedicamento = tablaRecetasMedicamentos.getSelectionModel().getSelectedItem();
        if (recetaMedicamento != null) {
            txtIdRecetaMedicamento.setText(String.valueOf(recetaMedicamento.getIdRecetaMedicamento()));
            txtIdReceta.setText(String.valueOf(recetaMedicamento.getIdReceta()));
            txtIdMedicamento.setText(String.valueOf(recetaMedicamento.getIdMedicamento()));
            txtCantidad.setText(String.valueOf(recetaMedicamento.getCantidad()));
            txtDosis.setText(recetaMedicamento.getDosis());
            txtFrecuencia.setText(recetaMedicamento.getFrecuencia());
            txtObservaciones.setText(recetaMedicamento.getObservaciones());

            for (Recetas r : cbxReceta.getItems()) {
                if (r.getIdReceta() == recetaMedicamento.getIdReceta()) {
                    cbxReceta.setValue(r);
                    break;
                }
            }
            for (Medicamentos m : cbxMedicamento.getItems()) {
                if (m.getIdMedicamento() == recetaMedicamento.getIdMedicamento()) {
                    cbxMedicamento.setValue(m);
                    break;
                }
            }
        }
    }

    private RecetaMedicamento obtenerModelo() {
        int idRecetaMedicamento = txtIdRecetaMedicamento.getText().isEmpty() ? 0 : Integer.parseInt(txtIdRecetaMedicamento.getText());
        int idReceta = cbxReceta.getValue() == null ? 0 : cbxReceta.getValue().getIdReceta();
        int idMedicamento = cbxMedicamento.getValue() == null ? 0 : cbxMedicamento.getValue().getIdMedicamento();
        int cantidad = txtCantidad.getText().isEmpty() ? 0 : Integer.parseInt(txtCantidad.getText());
        String dosis = txtDosis.getText();
        String frecuencia = txtFrecuencia.getText();
        String observaciones = txtObservaciones.getText();

        return new RecetaMedicamento(idRecetaMedicamento, idReceta, idMedicamento, cantidad, dosis, frecuencia, observaciones);
    }

    private void agregarRecetaMedicamento() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarRecetasMedicamentos(?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdReceta());
            cs.setInt(2, modelo.getIdMedicamento());
            cs.setInt(3, modelo.getCantidad());
            cs.setString(4, modelo.getDosis());
            cs.setString(5, modelo.getFrecuencia());
            cs.setString(6, modelo.getObservaciones());
            cs.executeUpdate();
            cargarTablaRecetasMedicamentos();
            System.out.println("Receta Medicamento agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Receta Medicamento.");
            e.printStackTrace();
        }
    }

    private void actualizarRecetaMedicamento() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarRecetasMedicamentos(?, ?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdRecetaMedicamento());
            cs.setInt(2, modelo.getIdReceta());
            cs.setInt(3, modelo.getIdMedicamento());
            cs.setInt(4, modelo.getCantidad());
            cs.setString(5, modelo.getDosis());
            cs.setString(6, modelo.getFrecuencia());
            cs.setString(7, modelo.getObservaciones());
            cs.executeUpdate();
            cargarTablaRecetasMedicamentos();
            System.out.println("Receta Medicamento actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Receta Medicamento.");
            e.printStackTrace();
        }
    }

    private void eliminarRecetaMedicamento() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarRecetasMedicamentos(?);");
            cs.setInt(1, modelo.getIdRecetaMedicamento());
            cs.executeUpdate();
            cargarTablaRecetasMedicamentos();
            System.out.println("Receta Medicamento eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Receta Medicamento.");
            e.printStackTrace();
        }
    }

    private void buscarRecetaMedicamento() {
        ArrayList<RecetaMedicamento> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (RecetaMedicamento rm : listarRecetasMedicamentos) {
            if (String.valueOf(rm.getIdRecetaMedicamento()).contains(texto)
                    || (cbxReceta.getValue() != null && String.valueOf(rm.getIdReceta()).contains(texto))
                    || (cbxMedicamento.getValue() != null && String.valueOf(rm.getIdMedicamento()).contains(texto))
                    || String.valueOf(rm.getCantidad()).contains(texto)
                    || (rm.getDosis() != null && rm.getDosis().toLowerCase().contains(texto.toLowerCase()))
                    || (rm.getFrecuencia() != null && rm.getFrecuencia().toLowerCase().contains(texto.toLowerCase()))
                    || (rm.getObservaciones() != null && rm.getObservaciones().toLowerCase().contains(texto.toLowerCase()))) {
                resultado.add(rm);
            }
        }

        tablaRecetasMedicamentos.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaRecetasMedicamentos.getSelectionModel().selectFirst();
            cargarRecetaMedicamentoSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdRecetaMedicamento.clear();
        cbxReceta.setValue(null);
        cbxMedicamento.setValue(null);
        txtCantidad.clear();
        txtDosis.clear();
        txtFrecuencia.clear();
        txtObservaciones.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdRecetaMedicamento.setDisable(estado);
        cbxReceta.setDisable(estado);
        cbxMedicamento.setDisable(estado);
        txtCantidad.setDisable(estado);
        txtDosis.setDisable(estado);
        txtFrecuencia.setDisable(estado);
        txtObservaciones.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdRecetaMedicamento.isDisable();
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
            agregarRecetaMedicamento();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarRecetaMedicamento();
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
        cargarTablaRecetasMedicamentos();
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
        txtIdRecetaMedicamento.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdRecetaMedicamento.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarRecetaMedicamento();
        tipoAccion = Accion.NINGUNA;
        cargarTablaRecetasMedicamentos();
    }

    @FXML
    private void btnBuscar() {
        buscarRecetaMedicamento();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaRecetasMedicamentos.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaRecetasMedicamentos.getSelectionModel().selectPrevious();
            cargarRecetaMedicamentoSeleccionada();
        }
    }

    @FXML
    private void
            btnSiguiente() {
        int selectedIndex = tablaRecetasMedicamentos.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaRecetasMedicamentos.getItems().size() - 1) {
            tablaRecetasMedicamentos.getSelectionModel().selectNext();
            cargarRecetaMedicamentoSeleccionada();
        }
    }
}
