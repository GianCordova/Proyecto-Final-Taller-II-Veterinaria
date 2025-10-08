
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
import org.javiergomez.system.Main;


/**
 * FXML Controller class
 *
 * @author jgome
 */
public class MedicamentosController implements Initializable {
    
    private Main principal;
    private Medicamentos modelo;
    private ObservableList<Medicamentos> listarMedicamentos;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;
    
    @FXML
    private Button re;

  @FXML
    private TableView<Medicamentos> tablaMedicamentos;
    @FXML
    private TableColumn colIdMedicamento, colNombreMedicamento, colFinalidad, colDescripcion, colFechaVencimiento;
    @FXML
    private TextField txtBuscar, txtIdMedicamento, txtNombreMedicamento, txtFinalidad;
    @FXML
    private TextArea txtDescripcion; 
    @FXML
    private DatePicker dpFechaVencimiento; 
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaMedicamentos();
        cargarMedicamentoSeleccionado(); 
        tablaMedicamentos.setOnMouseClicked(event -> cargarMedicamentoSeleccionado());

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
        colIdMedicamento.setCellValueFactory(new PropertyValueFactory<Medicamentos, Integer>("idMedicamento"));
        colNombreMedicamento.setCellValueFactory(new PropertyValueFactory<Medicamentos, String>("nombreMedicamento"));
        colFinalidad.setCellValueFactory(new PropertyValueFactory<Medicamentos, String>("finalidad"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Medicamentos, String>("descripcion"));
        colFechaVencimiento.setCellValueFactory(new PropertyValueFactory<Medicamentos, LocalDate>("fechaVencimiento"));
    }

    private void cargarTablaMedicamentos() {
        listarMedicamentos = FXCollections.observableArrayList(listarMedicamentos());
        tablaMedicamentos.setItems(listarMedicamentos);
        if (!listarMedicamentos.isEmpty()) {
            tablaMedicamentos.getSelectionModel().selectFirst();
            cargarMedicamentoSeleccionado();
        }
    }

    private ArrayList<Medicamentos> listarMedicamentos() {
        ArrayList<Medicamentos> medicamentos = new ArrayList<>();
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
            System.out.println("Error al listar Medicamentos");
            e.printStackTrace();
        }
        return medicamentos;
    }

    private void cargarMedicamentoSeleccionado() {
        Medicamentos medicamento = tablaMedicamentos.getSelectionModel().getSelectedItem();
        if (medicamento != null) {
            txtIdMedicamento.setText(String.valueOf(medicamento.getIdMedicamento()));
            txtNombreMedicamento.setText(medicamento.getNombreMedicamento());
            txtFinalidad.setText(medicamento.getFinalidad());
            txtDescripcion.setText(medicamento.getDescripcion());
            dpFechaVencimiento.setValue(medicamento.getFechaVencimiento());
        }
    }

    private Medicamentos obtenerModelo() {
        int idMedicamento = txtIdMedicamento.getText().isEmpty() ? 0 : Integer.parseInt(txtIdMedicamento.getText());
        String nombreMedicamento = txtNombreMedicamento.getText();
        String finalidad = txtFinalidad.getText();
        String descripcion = txtDescripcion.getText();
        LocalDate fechaVencimiento = dpFechaVencimiento.getValue();

        return new Medicamentos(idMedicamento, nombreMedicamento, finalidad, descripcion, fechaVencimiento);
    }

    private void agregarMedicamento() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarMedicamentos(?, ?, ?, ?);");
            cs.setString(1, modelo.getNombreMedicamento());
            cs.setString(2, modelo.getFinalidad());
            cs.setString(3, modelo.getDescripcion());
            cs.setDate(4, java.sql.Date.valueOf(modelo.getFechaVencimiento())); 
            cs.executeUpdate();
            cargarTablaMedicamentos();
            System.out.println("Medicamento agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Medicamento.");
            e.printStackTrace();
        }
    }

    private void actualizarMedicamento() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarMedicamentos(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdMedicamento());
            cs.setString(2, modelo.getNombreMedicamento());
            cs.setString(3, modelo.getFinalidad());
            cs.setString(4, modelo.getDescripcion());
            cs.setDate(5, java.sql.Date.valueOf(modelo.getFechaVencimiento())); 
            cs.executeUpdate();
            cargarTablaMedicamentos();
            System.out.println("Medicamento actualizado.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Medicamento.");
            e.printStackTrace();
        }
    }

    private void eliminarMedicamento() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarMedicamentos(?);");
            cs.setInt(1, modelo.getIdMedicamento());
            cs.executeUpdate();
            cargarTablaMedicamentos();
            System.out.println("Medicamento eliminado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Medicamento.");
            e.printStackTrace();
        }
    }

    private void buscarMedicamento() {
        ArrayList<Medicamentos> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Medicamentos m : listarMedicamentos) {
            if (String.valueOf(m.getIdMedicamento()).contains(texto) ||
                    (m.getNombreMedicamento() != null && m.getNombreMedicamento().toLowerCase().contains(texto.toLowerCase())) ||
                    (m.getFinalidad() != null && m.getFinalidad().toLowerCase().contains(texto.toLowerCase())) ||
                    (m.getDescripcion() != null && m.getDescripcion().toLowerCase().contains(texto.toLowerCase())) ||
                    (m.getFechaVencimiento() != null && m.getFechaVencimiento().toString().contains(texto))) {
                resultado.add(m);
            }
        }

        tablaMedicamentos.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaMedicamentos.getSelectionModel().selectFirst();
            cargarMedicamentoSeleccionado();
        }
    }

    private void limpiarCampos() {
        txtIdMedicamento.clear();
        txtNombreMedicamento.clear();
        txtFinalidad.clear();
        txtDescripcion.clear();
        dpFechaVencimiento.setValue(null);
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdMedicamento.setDisable(estado);
        txtNombreMedicamento.setDisable(estado);
        txtFinalidad.setDisable(estado);
        txtDescripcion.setDisable(estado);
        dpFechaVencimiento.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdMedicamento.isDisable();
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
            agregarMedicamento();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarMedicamento();
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
        cargarTablaMedicamentos();
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
        txtIdMedicamento.setDisable(true); 
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
        txtIdMedicamento.setDisable(true); 
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarMedicamento();
        tipoAccion = Accion.NINGUNA;
        cargarTablaMedicamentos();
    }

    @FXML
    private void btnBuscar() {
        buscarMedicamento();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaMedicamentos.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaMedicamentos.getSelectionModel().selectPrevious();
            cargarMedicamentoSeleccionado();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaMedicamentos.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaMedicamentos.getItems().size() - 1) {
            tablaMedicamentos.getSelectionModel().selectNext();
            cargarMedicamentoSeleccionado();
        }
    }
}