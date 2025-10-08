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
import org.javiergomez.model.TratamientoMedico;
import org.javiergomez.model.Tratamientos;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class TratamientoMedicoController implements Initializable {

   private Main principal;
    private TratamientoMedico modelo;
    private ObservableList<TratamientoMedico> listarTratamientosMedicos;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

    @FXML
    private TableView<TratamientoMedico> tablaTratamientosMedicos;
    @FXML
    private TableColumn colIdTratamientoMedicamento, colIdTratamiento, colIdMedicamento, colNombreTratamiento, colDescripcion;
    @FXML
    private TextField txtBuscar, txtIdTratamientoMedicamento, txtIdTratamiento, txtIdMedicamento, txtNombreTratamiento;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<Tratamientos> cbxTratamiento;
    @FXML
    private ComboBox<Medicamentos> cbxMedicamento;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       configurarColumnas();
        cargarTablaTratamientosMedicos();
        cargarTratamientosMedicosEnCampos();
        cargarMedicosEnCampos();
        tablaTratamientosMedicos.setOnMouseClicked(event -> cargarTratamientoMedicoSeleccionado());

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
        colIdTratamientoMedicamento.setCellValueFactory(new PropertyValueFactory<TratamientoMedico, Integer>("idTratamientoMedicamento"));
        colIdTratamiento.setCellValueFactory(new PropertyValueFactory<TratamientoMedico, Integer>("idTratamiento"));
        colIdMedicamento.setCellValueFactory(new PropertyValueFactory<TratamientoMedico, Integer>("idMedicamento"));
        colNombreTratamiento.setCellValueFactory(new PropertyValueFactory<TratamientoMedico, String>("nombreTratamiento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TratamientoMedico, String>("descripcion"));
    }

    private void cargarTablaTratamientosMedicos() {
        listarTratamientosMedicos = FXCollections.observableArrayList(listarTratamientosMedicos());
        tablaTratamientosMedicos.setItems(listarTratamientosMedicos);
        if (!listarTratamientosMedicos.isEmpty()) {
            tablaTratamientosMedicos.getSelectionModel().selectFirst();
            cargarTratamientoMedicoSeleccionado();
        }
    }

    private ArrayList<TratamientoMedico> listarTratamientosMedicos() {
        ArrayList<TratamientoMedico> tratamientosMedicos = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarTratamientosMedicamentos();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                tratamientosMedicos.add(new TratamientoMedico(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Tratamientos Medicos");
            e.printStackTrace();
        }
        return tratamientosMedicos;
    }

    private void cargarTratamientosMedicosEnCampos() {
        ObservableList<Tratamientos> tratamientosList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarTratamiento();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                tratamientosList.add(new Tratamientos(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxTratamiento.setItems(tratamientosList);
}
private void cargarMedicosEnCampos() {
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
                        rs.getDate(5).toLocalDate()
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxMedicamento.setItems(medicamentosList);
    }

    private void cargarTratamientoMedicoSeleccionado() {
        TratamientoMedico tratamientoMedico = tablaTratamientosMedicos.getSelectionModel().getSelectedItem();
        if (tratamientoMedico != null) {
            txtIdTratamientoMedicamento.setText(String.valueOf(tratamientoMedico.getIdTratamientoMedicamento()));
            txtIdTratamiento.setText(String.valueOf(tratamientoMedico.getIdTratamiento()));
            txtIdMedicamento.setText(String.valueOf(tratamientoMedico.getIdMedicamento()));
            txtNombreTratamiento.setText(tratamientoMedico.getNombreTratamiento());
            txtDescripcion.setText(tratamientoMedico.getDescripcion());

            for (Tratamientos t : cbxTratamiento.getItems()) {
                if (t.getIdTratamiento() == tratamientoMedico.getIdTratamiento()) {
                    cbxTratamiento.setValue(t);
                    break;
                }
            }
            for (Medicamentos m : cbxMedicamento.getItems()) {
                if (m.getIdMedicamento() == tratamientoMedico.getIdMedicamento()) {
                    cbxMedicamento.setValue(m);
                    break;
                }
            }
        }
    }

    

    private TratamientoMedico obtenerModelo() {
        int idTratamientoMedicamento = txtIdTratamientoMedicamento.getText().isEmpty() ? 0 : Integer.parseInt(txtIdTratamientoMedicamento.getText());
        int idTratamiento = cbxTratamiento.getValue() == null ? 0 : cbxTratamiento.getValue().getIdTratamiento();
        int idMedicamento = cbxMedicamento.getValue() == null ? 0 : cbxMedicamento.getValue().getIdMedicamento();
        String nombreTratamiento = txtNombreTratamiento.getText();
        String descripcion = txtDescripcion.getText();

        return new TratamientoMedico(idTratamientoMedicamento, idTratamiento, idMedicamento, nombreTratamiento, descripcion);
    }

    private void agregarTratamientoMedico() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarTratamientosMedicamentos(?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdTratamiento());
            cs.setInt(2, modelo.getIdMedicamento());
            cs.setString(3, modelo.getNombreTratamiento());
            cs.setString(4, modelo.getDescripcion());
            cs.executeUpdate();
            cargarTablaTratamientosMedicos();
            System.out.println("Tratamiento Medico agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Tratamiento Medico.");
            e.printStackTrace();
        }
    }

    private void actualizarTratamientoMedico() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarTratamientosMedicamentos(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdTratamientoMedicamento());
            cs.setInt(2, modelo.getIdTratamiento());
            cs.setInt(3, modelo.getIdMedicamento());
            cs.setString(4, modelo.getNombreTratamiento());
            cs.setString(5, modelo.getDescripcion());
            cs.executeUpdate();
            cargarTablaTratamientosMedicos();
            System.out.println("Tratamiento Medico actualizado.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Tratamiento Medico.");
            e.printStackTrace();
        }
    }

    private void eliminarTratamientoMedico() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarTratamientosMedicamentos(?);");
            cs.setInt(1, modelo.getIdTratamientoMedicamento());
            cs.executeUpdate();
            cargarTablaTratamientosMedicos();
            System.out.println("Tratamiento Medico eliminado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Tratamiento Medico.");
            e.printStackTrace();
        }
    }

    private void buscarTratamientoMedico() {
        ArrayList<TratamientoMedico> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (TratamientoMedico tm : listarTratamientosMedicos) {
            if (String.valueOf(tm.getIdTratamientoMedicamento()).contains(texto) ||
                    (cbxTratamiento.getValue() != null && String.valueOf(tm.getIdTratamiento()).contains(texto)) ||
                    (cbxMedicamento.getValue() != null && String.valueOf(tm.getIdMedicamento()).contains(texto)) ||
                    (tm.getNombreTratamiento() != null && tm.getNombreTratamiento().toLowerCase().contains(texto.toLowerCase())) ||
                    (tm.getDescripcion() != null && tm.getDescripcion().toLowerCase().contains(texto.toLowerCase()))) {
                resultado.add(tm);
            }
        }

        tablaTratamientosMedicos.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaTratamientosMedicos.getSelectionModel().selectFirst();
            cargarTratamientoMedicoSeleccionado();
        }
    }

    private void limpiarCampos() {
        txtIdTratamientoMedicamento.clear();
        cbxTratamiento.setValue(null);
        cbxMedicamento.setValue(null);
        txtNombreTratamiento.clear();
        txtDescripcion.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdTratamientoMedicamento.setDisable(estado);
        cbxTratamiento.setDisable(estado);
        cbxMedicamento.setDisable(estado);
        txtNombreTratamiento.setDisable(estado);
        txtDescripcion.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdTratamientoMedicamento.isDisable();
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
            agregarTratamientoMedico();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarTratamientoMedico();
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
        cargarTablaTratamientosMedicos();
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
        txtIdTratamientoMedicamento.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdTratamientoMedicamento.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarTratamientoMedico();
        tipoAccion = Accion.NINGUNA;
        cargarTablaTratamientosMedicos();
    }

    @FXML
    private void btnBuscar() {
        buscarTratamientoMedico();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaTratamientosMedicos.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaTratamientosMedicos.getSelectionModel().selectPrevious();
            cargarTratamientoMedicoSeleccionado();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaTratamientosMedicos.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaTratamientosMedicos.getItems().size() - 1) {
            tablaTratamientosMedicos.getSelectionModel().selectNext();
            cargarTratamientoMedicoSeleccionado();
        }
    }
}
