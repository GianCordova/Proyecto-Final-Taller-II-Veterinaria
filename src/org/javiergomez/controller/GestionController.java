
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
import org.javiergomez.model.Proveedores;
import org.javiergomez.system.Main;
/**
 * FXML Controller class
 *
 * @author jgome
 */
public class GestionController implements Initializable {
    
    
    private Main principal;
    private Proveedores modelo;
    private ObservableList<Proveedores> listarProveedores;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;
 
    @FXML
    private Button re;

    @FXML
    private TableView<Proveedores> tablaProveedores;
    @FXML
    private TableColumn colIdProveedor, colNombreProveedor, colNitProveedor, colTelefono, colCorreo;
    @FXML
    private TextField txtBuscar, txtIdProveedor, txtNombreProveedor, txtNitProveedor, txtTelefono, txtCorreo;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaProveedores();
        cargarProveedorSeleccionado(); // Cargar la selección inicial si la hay
        tablaProveedores.setOnMouseClicked(event -> cargarProveedorSeleccionado());

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
        colIdProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("idProveedor"));
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedor"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("correo"));
    }

    private void cargarTablaProveedores() {
        listarProveedores = FXCollections.observableArrayList(listarProveedores());
        tablaProveedores.setItems(listarProveedores);
        if (!listarProveedores.isEmpty()) {
            tablaProveedores.getSelectionModel().selectFirst();
            cargarProveedorSeleccionado();
        }
    }

    private ArrayList<Proveedores> listarProveedores() {
        ArrayList<Proveedores> proveedores = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarProveedores();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                proveedores.add(new Proveedores(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Proveedores");
            e.printStackTrace();
        }
        return proveedores;
    }

    private void cargarProveedorSeleccionado() {
        Proveedores proveedor = tablaProveedores.getSelectionModel().getSelectedItem();
        if (proveedor != null) {
            txtIdProveedor.setText(String.valueOf(proveedor.getIdProveedor()));
            txtNombreProveedor.setText(proveedor.getNombreProveedor());
            txtNitProveedor.setText(proveedor.getNitProveedor());
            txtTelefono.setText(proveedor.getTelefono());
            txtCorreo.setText(proveedor.getCorreo());
        }
    }

    private Proveedores obtenerModelo() {
        int idProveedor = txtIdProveedor.getText().isEmpty() ? 0 : Integer.parseInt(txtIdProveedor.getText());
        String nombreProveedor = txtNombreProveedor.getText();
        String nitProveedor = txtNitProveedor.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();

        return new Proveedores(idProveedor, nombreProveedor, nitProveedor, telefono, correo);
    }

    private void agregarProveedor() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarProveedores(?, ?, ?, ?);");
            cs.setString(1, modelo.getNombreProveedor());
            cs.setString(2, modelo.getNitProveedor());
            cs.setString(3, modelo.getTelefono());
            cs.setString(4, modelo.getCorreo());
            cs.executeUpdate();
            cargarTablaProveedores();
            System.out.println("Proveedor agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Proveedor.");
            e.printStackTrace();
        }
    }

    private void actualizarProveedor() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarProveedores(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdProveedor());
            cs.setString(2, modelo.getNombreProveedor());
            cs.setString(3, modelo.getNitProveedor());
            cs.setString(4, modelo.getTelefono());
            cs.setString(5, modelo.getCorreo());
            cs.executeUpdate();
            cargarTablaProveedores();
            System.out.println("Proveedor actualizado.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Proveedor.");
            e.printStackTrace();
        }
    }

    private void eliminarProveedor() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarProveedores(?);");
            cs.setInt(1, modelo.getIdProveedor());
            cs.executeUpdate();
            cargarTablaProveedores();
            System.out.println("Proveedor eliminado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Proveedor.");
            e.printStackTrace();
        }
    }

    private void buscarProveedor() {
        ArrayList<Proveedores> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Proveedores p : listarProveedores) {
            if (String.valueOf(p.getIdProveedor()).contains(texto) ||
                    (p.getNombreProveedor() != null && p.getNombreProveedor().toLowerCase().contains(texto.toLowerCase())) ||
                    (p.getNitProveedor() != null && p.getNitProveedor().toLowerCase().contains(texto.toLowerCase())) ||
                    (p.getTelefono() != null && p.getTelefono().toLowerCase().contains(texto.toLowerCase())) ||
                    (p.getCorreo() != null && p.getCorreo().toLowerCase().contains(texto.toLowerCase()))) {
                resultado.add(p);
            }
        }

        tablaProveedores.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaProveedores.getSelectionModel().selectFirst();
            cargarProveedorSeleccionado();
        }
    }

    private void limpiarCampos() {
        txtIdProveedor.clear();
        txtNombreProveedor.clear();
        txtNitProveedor.clear();
        txtTelefono.clear();
        txtCorreo.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdProveedor.setDisable(estado);
        txtNombreProveedor.setDisable(estado);
        txtNitProveedor.setDisable(estado);
        txtTelefono.setDisable(estado);
        txtCorreo.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdProveedor.isDisable();
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
            agregarProveedor();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarProveedor();
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
        cargarTablaProveedores();
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
        txtIdProveedor.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdProveedor.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarProveedor();
        tipoAccion = Accion.NINGUNA;
        cargarTablaProveedores();
    }

    @FXML
    private void btnBuscar() {
        buscarProveedor();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaProveedores.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaProveedores.getSelectionModel().selectPrevious();
            cargarProveedorSeleccionado();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaProveedores.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaProveedores.getItems().size() - 1) {
            tablaProveedores.getSelectionModel().selectNext();
            cargarProveedorSeleccionado();
        }
    }
}
