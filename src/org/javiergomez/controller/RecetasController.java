
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
import org.javiergomez.model.Mascotas;
import org.javiergomez.model.Medicamentos;
import org.javiergomez.model.Recetas;
import org.javiergomez.model.Veterinarios;
import org.javiergomez.system.Main;
/**
 * FXML Controller class
 *
 * @author jgome
 */
public class RecetasController implements Initializable {

   private Main principal;
    private Recetas modelo;
    private ObservableList<Recetas> listarRecetas;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;
    
    @FXML
    private Button re;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
   @FXML
    private TableView<Recetas> tablaRecetas;
    @FXML
    private TableColumn colIdReceta, colIdMedicamento, colIdMascota, colIdVeterinario;
    @FXML
    private TextField txtBuscar, txtIdReceta, txtIdMedicamento, txtIdMascota, txtIdVeterinario;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<Medicamentos> cbxMedicamento;
    @FXML
    private ComboBox<Mascotas> cbxMascota;
    @FXML
    private ComboBox<Veterinarios> cbxVeterinario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaRecetas();
        cargarRecetasEnCampos();
        cargarmascotasEnCampos();
        cargarveterinarioEnCampos();
        tablaRecetas.setOnMouseClicked(event -> cargarRecetaSeleccionada());

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
        colIdReceta.setCellValueFactory(new PropertyValueFactory<Recetas, Integer>("idReceta"));
        colIdMedicamento.setCellValueFactory(new PropertyValueFactory<Recetas, Integer>("idMedicamento"));
        colIdMascota.setCellValueFactory(new PropertyValueFactory<Recetas, Integer>("idMascota"));
        colIdVeterinario.setCellValueFactory(new PropertyValueFactory<Recetas, Integer>("idVeterinario"));
    }

    private void cargarTablaRecetas() {
        listarRecetas = FXCollections.observableArrayList(listarRecetas());
        tablaRecetas.setItems(listarRecetas);
        if (!listarRecetas.isEmpty()) {
            tablaRecetas.getSelectionModel().selectFirst();
            cargarRecetaSeleccionada();
        }
    }

    private ArrayList<Recetas> listarRecetas() {
        ArrayList<Recetas> recetas = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarRecetas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                recetas.add(new Recetas(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Recetas");
            e.printStackTrace();
        }
        return recetas;
    }

    private void cargarRecetasEnCampos() {
        ObservableList<Medicamentos> medicamentos = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarMedicamentos();");
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
        cbxMedicamento.setItems(medicamentos);
}
 private void cargarmascotasEnCampos() {
        ObservableList<Mascotas> mascotas = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_ListarMascotas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                mascotas.add(new Mascotas(
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
        cbxMascota.setItems(mascotas);
}
 private void cargarveterinarioEnCampos() {
        ObservableList<Veterinarios> veterinarios = FXCollections.observableArrayList();
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
            e.printStackTrace();
        }
        cbxVeterinario.setItems(veterinarios);
    }

    private void cargarRecetaSeleccionada() {
        Recetas recetas = tablaRecetas.getSelectionModel().getSelectedItem();
        if (recetas != null) {
            txtIdReceta.setText(String.valueOf(recetas.getIdReceta()));
            txtIdMedicamento.setText(String.valueOf(recetas.getIdMedicamento()));
            txtIdMascota.setText(String.valueOf(recetas.getIdMascota()));
            txtIdVeterinario.setText(String.valueOf(recetas.getIdVeterinario()));

            for (Medicamentos m : cbxMedicamento.getItems()) {
                if (m.getIdMedicamento() == recetas.getIdMedicamento()) {
                    cbxMedicamento.setValue(m);
                    break;
                }
            }
            for (Mascotas m : cbxMascota.getItems()) {
                if (m.getIdMascota() == recetas.getIdMascota()) {
                    cbxMascota.setValue(m);
                    break;
                }
            }
            for (Veterinarios v : cbxVeterinario.getItems()) {
                if (v.getIdVeterinario() == recetas.getIdVeterinario()) {
                    cbxVeterinario.setValue(v);
                    break;
                }
            }
        }
    }

    
    private Recetas obtenerModelo() {
        int idReceta = txtIdReceta.getText().isEmpty() ? 0 : Integer.parseInt(txtIdReceta.getText());
        int idMedicamento = cbxMedicamento.getValue() == null ? 0 : cbxMedicamento.getValue().getIdMedicamento();
        int idMascota = cbxMascota.getValue() == null ? 0 : cbxMascota.getValue().getIdMascota();
        int idVeterinario = cbxVeterinario.getValue() == null ? 0 : cbxVeterinario.getValue().getIdVeterinario();

        return new Recetas(idReceta, idMedicamento, idMascota, idVeterinario);
    }

    private void agregarReceta() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarReceta(?, ?, ?);");
            cs.setInt(1, modelo.getIdMedicamento());
            cs.setInt(2, modelo.getIdMascota());
            cs.setInt(3, modelo.getIdVeterinario());
            cs.executeUpdate();
            cargarTablaRecetas();
            System.out.println("Receta agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Receta.");
            e.printStackTrace();
        }
    }

    private void actualizarReceta() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_editarReceta(?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdReceta());
            cs.setInt(2, modelo.getIdMedicamento());
            cs.setInt(3, modelo.getIdMascota());
            cs.setInt(4, modelo.getIdVeterinario());
            cs.executeUpdate();
            cargarTablaRecetas();
            System.out.println("Receta actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Receta.");
            e.printStackTrace();
        }
    }

    private void eliminarReceta() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarReceta(?);");
            cs.setInt(1, modelo.getIdReceta());
            cs.executeUpdate();
            cargarTablaRecetas();
            System.out.println("Receta eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Receta.");
            e.printStackTrace();
        }
    }

    private void buscarReceta() {
        ArrayList<Recetas> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Recetas r : listarRecetas()) {
            if (String.valueOf(r.getIdReceta()).contains(texto) ||
                    (cbxMedicamento.getValue() != null && String.valueOf(r.getIdMedicamento()).contains(texto)) ||
                    (cbxMascota.getValue() != null && String.valueOf(r.getIdMascota()).contains(texto)) ||
                    (cbxVeterinario.getValue() != null && String.valueOf(r.getIdVeterinario()).contains(texto))) {
                resultado.add(r);
            }
        }

        tablaRecetas.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaRecetas.getSelectionModel().selectFirst();
            cargarRecetaSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdReceta.clear();
        cbxMedicamento.setValue(null);
        cbxMascota.setValue(null);
        cbxVeterinario.setValue(null);
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdReceta.setDisable(estado);
        cbxMedicamento.setDisable(estado);
        cbxMascota.setDisable(estado);
        cbxVeterinario.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdReceta.isDisable();
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
            agregarReceta();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarReceta();
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
        cargarTablaRecetas();
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
        txtIdReceta.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdReceta.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarReceta();
        tipoAccion = Accion.NINGUNA;
        cargarTablaRecetas();
    }

    @FXML
    private void btnBuscar() {
        buscarReceta();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaRecetas.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaRecetas.getSelectionModel().selectPrevious();
            cargarRecetaSeleccionada();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaRecetas.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaRecetas.getItems().size() - 1) {
            tablaRecetas.getSelectionModel().selectNext();
            cargarRecetasEnCampos();
                    }
    }
}