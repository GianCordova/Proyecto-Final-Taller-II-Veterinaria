
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea; // Importar TextArea
import org.javiergomez.db.Conexion;
import org.javiergomez.model.servicios;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class ServicioController implements Initializable {

  private Main principal;
    private servicios modelo;
    private ObservableList<servicios> listarServicios;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

     @FXML
    private TableView<servicios> tablaServicios;
    @FXML
    private TableColumn colIdServicio, colNombreServicio, colDescripcion;
    @FXML
    private TextField txtBuscar, txtIdServicio, txtNombreServicio;
    @FXML
    private TextArea txtDescripcion; 
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaServicios();
        cargarServicioSeleccionado(); // Cargar la selección inicial si la hay
        tablaServicios.setOnMouseClicked(event -> cargarServicioSeleccionado());

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
        colIdServicio.setCellValueFactory(new PropertyValueFactory<servicios, Integer>("idServicio"));
        colNombreServicio.setCellValueFactory(new PropertyValueFactory<servicios, String>("nombreServicio"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<servicios, String>("descripcion"));
    }

    private void cargarTablaServicios() {
        listarServicios = FXCollections.observableArrayList(listarServicios());
        tablaServicios.setItems(listarServicios);
        if (!listarServicios.isEmpty()) {
            tablaServicios.getSelectionModel().selectFirst();
            cargarServicioSeleccionado();
        }
    }

    private ArrayList<servicios> listarServicios() {
        ArrayList<servicios> servicios = new ArrayList<>();
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
            System.out.println("Error al listar Servicios");
            e.printStackTrace();
        }
        return servicios;
    }

    private void cargarServicioSeleccionado() {
        servicios servicio = tablaServicios.getSelectionModel().getSelectedItem();
        if (servicio != null) {
            txtIdServicio.setText(String.valueOf(servicio.getIdServicio()));
            txtNombreServicio.setText(servicio.getNombreServicio());
            txtDescripcion.setText(servicio.getDescripcion());
        }
    }

    private servicios obtenerModelo() {
        int idServicio = txtIdServicio.getText().isEmpty() ? 0 : Integer.parseInt(txtIdServicio.getText());
        String nombreServicio = txtNombreServicio.getText();
        String descripcion = txtDescripcion.getText();

        return new servicios(idServicio, nombreServicio, descripcion);
    }

    private void agregarServicio() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_AgreagrServicios(?, ?);");
            cs.setString(1, modelo.getNombreServicio());
            cs.setString(2, modelo.getDescripcion());
            cs.executeUpdate();
            cargarTablaServicios();
            System.out.println("Servicio agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Servicio.");
            e.printStackTrace();
        }
    }

    private void actualizarServicio() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ActualizarServicios(?, ?, ?);");
            cs.setInt(1, modelo.getIdServicio());
            cs.setString(2, modelo.getNombreServicio());
            cs.setString(3, modelo.getDescripcion());
            cs.executeUpdate();
            cargarTablaServicios();
            System.out.println("Servicio actualizado.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Servicio.");
            e.printStackTrace();
        }
    }

    private void eliminarServicio() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_EliminarServicios(?);");
            cs.setInt(1, modelo.getIdServicio());
            cs.executeUpdate();
            cargarTablaServicios();
            System.out.println("Servicio eliminado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Servicio.");
            e.printStackTrace();
        }
    }

    private void buscarServicio() {
        ArrayList<servicios> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (servicios s : listarServicios) {
            if (String.valueOf(s.getIdServicio()).contains(texto) ||
                    (s.getNombreServicio() != null && s.getNombreServicio().toLowerCase().contains(texto.toLowerCase())) ||
                    (s.getDescripcion() != null && s.getDescripcion().toLowerCase().contains(texto.toLowerCase()))) {
                resultado.add(s);
            }
        }

        tablaServicios.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaServicios.getSelectionModel().selectFirst();
            cargarServicioSeleccionado();
        }
    }

    private void limpiarCampos() {
        txtIdServicio.clear();
        txtNombreServicio.clear();
        txtDescripcion.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdServicio.setDisable(estado);
        txtNombreServicio.setDisable(estado);
        txtDescripcion.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdServicio.isDisable();
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
            agregarServicio();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarServicio();
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
        cargarTablaServicios();
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
        txtIdServicio.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdServicio.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarServicio();
        tipoAccion = Accion.NINGUNA;
        cargarTablaServicios();
    }

    @FXML
    private void btnBuscar() {
        buscarServicio();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaServicios.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaServicios.getSelectionModel().selectPrevious();
            cargarServicioSeleccionado();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaServicios.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaServicios.getItems().size() - 1) {
            tablaServicios.getSelectionModel().selectNext();
            cargarServicioSeleccionado();
        }
    }
}