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
import org.javiergomez.model.FacturaCompra;
import org.javiergomez.model.Facturas;
import org.javiergomez.model.Compras;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class FacturaCompraController implements Initializable {

     private Main principal;
    private FacturaCompra modelo;
    private ObservableList<FacturaCompra> listarFacturasCompras;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

    @FXML
    private TableView<FacturaCompra> tablaFacturasCompras;
    @FXML
    private TableColumn colIdFacturaCompra, colIdFactura, colIdCompra, colCantidad, colSubtotal;
    @FXML
    private TextField txtBuscar, txtIdFacturaCompra, txtIdFactura, txtIdCompra, txtCantidad, txtSubtotal;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<Facturas> cbxFactura;
    @FXML
    private ComboBox<Compras> cbxCompra;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaFacturasCompras();
        cargarFacturasComprasEnCampos();
        cargarComprasEnCampos();
        tablaFacturasCompras.setOnMouseClicked(event -> cargarFacturaCompraSeleccionada());

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
        colIdFacturaCompra.setCellValueFactory(new PropertyValueFactory<FacturaCompra, Integer>("idFacturaCompra"));
        colIdFactura.setCellValueFactory(new PropertyValueFactory<FacturaCompra, Integer>("idFactura"));
        colIdCompra.setCellValueFactory(new PropertyValueFactory<FacturaCompra, Integer>("idCompra"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<FacturaCompra, Integer>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<FacturaCompra, Double>("subtotal"));
    }

    private void cargarTablaFacturasCompras() {
        listarFacturasCompras = FXCollections.observableArrayList(listarFacturasCompras());
        tablaFacturasCompras.setItems(listarFacturasCompras);
        if (!listarFacturasCompras.isEmpty()) {
            tablaFacturasCompras.getSelectionModel().selectFirst();
            cargarFacturaCompraSeleccionada();
        }
    }

    private ArrayList<FacturaCompra> listarFacturasCompras() {
        ArrayList<FacturaCompra> facturasCompras = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarFacturasCompras();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                facturasCompras.add(new FacturaCompra(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDouble(5)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Facturas Compras");
            e.printStackTrace();
        }
        return facturasCompras;
    }

    private void cargarFacturasComprasEnCampos() {
        ObservableList<Facturas> facturasList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarFacturas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                facturasList.add(new Facturas(
                        rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
                        rs.getString(5), rs.getDouble(6)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxFactura.setItems(facturasList);
}
        private void cargarComprasEnCampos() {
        ObservableList<Compras> comprasList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarCompras();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                comprasList.add(new Compras(
                        rs.getInt(1), rs.getInt(2), rs.getDate(3).toLocalDate(), rs.getInt(4),
                        rs.getInt(5), rs.getInt(6), rs.getDouble(7)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxCompra.setItems(comprasList);
    }

    private void cargarFacturaCompraSeleccionada() {
        FacturaCompra facturaCompra = tablaFacturasCompras.getSelectionModel().getSelectedItem();
        if (facturaCompra != null) {
            txtIdFacturaCompra.setText(String.valueOf(facturaCompra.getIdFacturaCompra()));
            txtIdFactura.setText(String.valueOf(facturaCompra.getIdFactura()));
            txtIdCompra.setText(String.valueOf(facturaCompra.getIdCompra()));
            txtCantidad.setText(String.valueOf(facturaCompra.getCantidad()));
            txtSubtotal.setText(String.valueOf(facturaCompra.getSubtotal()));

            for (Facturas f : cbxFactura.getItems()) {
                if (f.getIdFactura() == facturaCompra.getIdFactura()) {
                    cbxFactura.setValue(f);
                    break;
                }
            }
            for (Compras c : cbxCompra.getItems()) {
                if (c.getIdCompra() == facturaCompra.getIdCompra()) {
                    cbxCompra.setValue(c);
                    break;
                }
            }
        }
    }

    private FacturaCompra obtenerModelo() {
        int idFacturaCompra = txtIdFacturaCompra.getText().isEmpty() ? 0 : Integer.parseInt(txtIdFacturaCompra.getText());
        int idFactura = cbxFactura.getValue() == null ? 0 : cbxFactura.getValue().getIdFactura();
        int idCompra = cbxCompra.getValue() == null ? 0 : cbxCompra.getValue().getIdCompra();
        int cantidad = txtCantidad.getText().isEmpty() ? 0 : Integer.parseInt(txtCantidad.getText());
        double subtotal = txtSubtotal.getText().isEmpty() ? 0.0 : Double.parseDouble(txtSubtotal.getText());

        return new FacturaCompra(idFacturaCompra, idFactura, idCompra, cantidad, subtotal);
    }

    private void agregarFacturaCompra() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarFacturasCompras(?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdFactura());
            cs.setInt(2, modelo.getIdCompra());
            cs.setInt(3, modelo.getCantidad());
            cs.setDouble(4, modelo.getSubtotal());
            cs.executeUpdate();
            cargarTablaFacturasCompras();
            System.out.println("Factura Compra agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Factura Compra.");
            e.printStackTrace();
        }
    }

    private void actualizarFacturaCompra() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarFacturasCompras(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdFacturaCompra());
            cs.setInt(2, modelo.getIdFactura());
            cs.setInt(3, modelo.getIdCompra());
            cs.setInt(4, modelo.getCantidad());
            cs.setDouble(5, modelo.getSubtotal());
            cs.executeUpdate();
            cargarTablaFacturasCompras();
            System.out.println("Factura Compra actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Factura Compra.");
            e.printStackTrace();
        }
    }

    private void eliminarFacturaCompra() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarFacturasCompras(?);");
            cs.setInt(1, modelo.getIdFacturaCompra());
            cs.executeUpdate();
            cargarTablaFacturasCompras();
            System.out.println("Factura Compra eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Factura Compra.");
            e.printStackTrace();
        }
    }

    private void buscarFacturaCompra() {
        ArrayList<FacturaCompra> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (FacturaCompra fc : listarFacturasCompras) {
            if (String.valueOf(fc.getIdFacturaCompra()).contains(texto) ||
                    (cbxFactura.getValue() != null && String.valueOf(fc.getIdFactura()).contains(texto)) ||
                    (cbxCompra.getValue() != null && String.valueOf(fc.getIdCompra()).contains(texto)) ||
                    String.valueOf(fc.getCantidad()).contains(texto) ||
                    String.valueOf(fc.getSubtotal()).contains(texto)) {
                resultado.add(fc);
            }
        }

        tablaFacturasCompras.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaFacturasCompras.getSelectionModel().selectFirst();
            cargarFacturaCompraSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdFacturaCompra.clear();
        cbxFactura.setValue(null);
        cbxCompra.setValue(null);
        txtCantidad.clear();
        txtSubtotal.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdFacturaCompra.setDisable(estado);
        cbxFactura.setDisable(estado);
        cbxCompra.setDisable(estado);
        txtCantidad.setDisable(estado);
        txtSubtotal.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdFacturaCompra.isDisable();
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
            agregarFacturaCompra();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarFacturaCompra();
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
        cargarTablaFacturasCompras();
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
        txtIdFacturaCompra.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdFacturaCompra.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarFacturaCompra();
        tipoAccion = Accion.NINGUNA;
        cargarTablaFacturasCompras();
    }

    @FXML
    private void btnBuscar() {
        buscarFacturaCompra();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaFacturasCompras.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaFacturasCompras.getSelectionModel().selectPrevious();
            cargarFacturaCompraSeleccionada();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaFacturasCompras.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaFacturasCompras.getItems().size() - 1) {
            tablaFacturasCompras.getSelectionModel().selectNext();
            cargarFacturaCompraSeleccionada();
        }
    }
}