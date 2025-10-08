
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
import org.javiergomez.db.Conexion;
import org.javiergomez.model.Empleados;
import org.javiergomez.system.Main;
/**
 * FXML Controller class
 *
 * @author jgome
 */
public class MascotassController implements Initializable {

    private Main principal;
    private Empleados modelo;
    private ObservableList<Empleados> listarEmpleados;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }
    private Accion tipoAccion = Accion.NINGUNA;
    
    @FXML
    private Button btnre;
    @FXML
    private TableView<Empleados> tablaEmpleados;
    @FXML
    private TableColumn colIdEmpleado, colNombreEmpleado, colApellido;
    @FXML
    private TextField txtBuscar, txtIdEmpleado, txtNombreEmpleado, txtApellido;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          configurarColumnas();
        cargarTablaEmpleados();
        cargarEmpleadoSeleccionado(); // Cargar la selección inicial si la hay
        tablaEmpleados.setOnMouseClicked(event -> cargarEmpleadoSeleccionado());

        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }    
     @FXML
    public void cli(ActionEvent evento) {
        if (evento.getSource() == btnre) {
            System.out.println("Nos vamos a pantalla inicio");
            principal.menuPrincipal();
}
    }
     public void configurarColumnas() {
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellido"));
    }

    private void cargarTablaEmpleados() {
        listarEmpleados = FXCollections.observableArrayList(listarEmpleados());
        tablaEmpleados.setItems(listarEmpleados);
        if (!listarEmpleados.isEmpty()) {
            tablaEmpleados.getSelectionModel().selectFirst();
            cargarEmpleadoSeleccionado();
        }
    }

    private ArrayList<Empleados> listarEmpleados() {
        ArrayList<Empleados> empleados = new ArrayList<>();
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
            System.out.println("Error al listar Empleados");
            e.printStackTrace();
        }
        return empleados;
    }

    private void cargarEmpleadoSeleccionado() {
        Empleados empleado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (empleado != null) {
            txtIdEmpleado.setText(String.valueOf(empleado.getIdEmpleado()));
            txtNombreEmpleado.setText(empleado.getNombreEmpleado());
            txtApellido.setText(empleado.getApellido());
        }
    }

    private Empleados obtenerModelo() {
        int idEmpleado = txtIdEmpleado.getText().isEmpty() ? 0 : Integer.parseInt(txtIdEmpleado.getText());
        String nombreEmpleado = txtNombreEmpleado.getText();
        String apellido = txtApellido.getText();

        return new Empleados(idEmpleado, nombreEmpleado, apellido);
    }

    private void agregarEmpleado() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarEmpleados(?, ?);");
            cs.setString(1, modelo.getNombreEmpleado());
            cs.setString(2, modelo.getApellido());
            cs.executeUpdate();
            cargarTablaEmpleados();
            System.out.println("Empleado agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Empleado.");
            e.printStackTrace();
        }
    }

    private void actualizarEmpleado() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarEmpleados(?, ?, ?);");
            cs.setInt(1, modelo.getIdEmpleado());
            cs.setString(2, modelo.getNombreEmpleado());
            cs.setString(3, modelo.getApellido());
            cs.executeUpdate();
            cargarTablaEmpleados();
            System.out.println("Empleado actualizado.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Empleado.");
            e.printStackTrace();
        }
    }

    private void eliminarEmpleado() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarEmpleados(?);");
            cs.setInt(1, modelo.getIdEmpleado());
            cs.executeUpdate();
            cargarTablaEmpleados();
            System.out.println("Empleado eliminado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Empleado.");
            e.printStackTrace();
        }
    }

    private void buscarEmpleado() {
        ArrayList<Empleados> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Empleados e : listarEmpleados) {
            if (String.valueOf(e.getIdEmpleado()).contains(texto) ||
                    (e.getNombreEmpleado() != null && e.getNombreEmpleado().toLowerCase().contains(texto.toLowerCase())) ||
                    (e.getApellido() != null && e.getApellido().toLowerCase().contains(texto.toLowerCase()))) {
                resultado.add(e);
            }
        }

        tablaEmpleados.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaEmpleados.getSelectionModel().selectFirst();
            cargarEmpleadoSeleccionado();
        }
    }

    private void limpiarCampos() {
        txtIdEmpleado.clear();
        txtNombreEmpleado.clear();
        txtApellido.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdEmpleado.setDisable(estado);
        txtNombreEmpleado.setDisable(estado);
        txtApellido.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdEmpleado.isDisable();
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
            agregarEmpleado();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarEmpleado();
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
        cargarTablaEmpleados();
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
        txtIdEmpleado.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdEmpleado.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarEmpleado();
        tipoAccion = Accion.NINGUNA;
        cargarTablaEmpleados();
    }

    @FXML
    private void btnBuscar() {
        buscarEmpleado();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaEmpleados.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaEmpleados.getSelectionModel().selectPrevious();
            cargarEmpleadoSeleccionado();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaEmpleados.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaEmpleados.getItems().size() - 1) {
            tablaEmpleados.getSelectionModel().selectNext();
            cargarEmpleadoSeleccionado();
        }
    }
    }
