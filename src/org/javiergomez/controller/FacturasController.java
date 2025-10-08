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
import org.javiergomez.model.Compras;
import org.javiergomez.model.Facturas;
import org.javiergomez.model.Mascotas;
import org.javiergomez.model.Veterinarios;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class FacturasController implements Initializable {

    private Main principal;
    private Facturas modelo;
    private ObservableList<Facturas> listarFacturas;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

    @FXML
    private TableView<Facturas> tablaFacturas;
    @FXML
    private TableColumn colIdFactura, colIdCompra, colIdVeterinario, colIdMascota, colNitClienteDueno, colTotal;
    @FXML
    private TextField txtBuscar, txtIdFactura, txtIdCompra, txtIdVeterinario, txtIdMascota, txtNitClienteDueno, txtTotal;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<Compras> cbxCompra;
    @FXML
    private ComboBox<Veterinarios> cbxVeterinario;
    @FXML
    private ComboBox<Mascotas> cbxMascota;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaFacturas();
        cargarFacturasEnCampos();
        cargarMascotasEnCampos();
        cargarVeterinariosEnCampos();
        tablaFacturas.setOnMouseClicked(event -> cargarFacturaSeleccionada());

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
        colIdFactura.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("idFactura"));
        colIdCompra.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("idCompra"));
        colIdVeterinario.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("idVeterinario"));
        colIdMascota.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("idMascota"));
        colNitClienteDueno.setCellValueFactory(new PropertyValueFactory<Facturas, String>("nitClienteDueño"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("total"));
    }

    private void cargarTablaFacturas() {
        listarFacturas = FXCollections.observableArrayList(listarFacturas());
        tablaFacturas.setItems(listarFacturas);
        if (!listarFacturas.isEmpty()) {
            tablaFacturas.getSelectionModel().selectFirst();
            cargarFacturaSeleccionada();
        }
    }

    private ArrayList<Facturas> listarFacturas() {
        ArrayList<Facturas> facturas = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarFacturas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                facturas.add(new Facturas(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getDouble(6)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Facturas");
            e.printStackTrace();
        }
        return facturas;
    }

    private void cargarFacturasEnCampos() {
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
private void cargarVeterinariosEnCampos() {
        ObservableList<Veterinarios> veterinariosList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarVeterinarios();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                veterinariosList.add(new Veterinarios(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxVeterinario.setItems(veterinariosList);
}
private void cargarMascotasEnCampos() {
        ObservableList<Mascotas> mascotasList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_ListarMascotas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                mascotasList.add(new Mascotas(
                        rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6), rs.getString(7)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxMascota.setItems(mascotasList);
    }

    private void cargarFacturaSeleccionada() {
        Facturas factura = tablaFacturas.getSelectionModel().getSelectedItem();
        if (factura != null) {
            txtIdFactura.setText(String.valueOf(factura.getIdFactura()));
            txtIdCompra.setText(String.valueOf(factura.getIdCompra()));
            txtIdVeterinario.setText(String.valueOf(factura.getIdVeterinario()));
            txtIdMascota.setText(String.valueOf(factura.getIdMascota()));
            txtNitClienteDueno.setText(factura.getNitClienteDueño());
            txtTotal.setText(String.valueOf(factura.getTotal()));

            for (Compras c : cbxCompra.getItems()) {
                if (c.getIdCompra() == factura.getIdCompra()) {
                    cbxCompra.setValue(c);
                    break;
                }
            }
            for (Veterinarios v : cbxVeterinario.getItems()) {
                if (v.getIdVeterinario() == factura.getIdVeterinario()) {
                    cbxVeterinario.setValue(v);
                    break;
                }
            }
            for (Mascotas m : cbxMascota.getItems()) {
                if (m.getIdMascota() == factura.getIdMascota()) {
                    cbxMascota.setValue(m);
                    break;
                }
            }
        }
    }

    private Facturas obtenerModelo() {
        int idFactura = txtIdFactura.getText().isEmpty() ? 0 : Integer.parseInt(txtIdFactura.getText());
        int idCompra = cbxCompra.getValue() == null ? 0 : cbxCompra.getValue().getIdCompra();
        int idVeterinario = cbxVeterinario.getValue() == null ? 0 : cbxVeterinario.getValue().getIdVeterinario();
        int idMascota = cbxMascota.getValue() == null ? 0 : cbxMascota.getValue().getIdMascota();
        String nitClienteDueno = txtNitClienteDueno.getText();
        double total = txtTotal.getText().isEmpty() ? 0.0 : Double.parseDouble(txtTotal.getText());

        return new Facturas(idFactura, idCompra, idVeterinario, idMascota, nitClienteDueno, total);
    }

    private void agregarFactura() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarFacturas(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdCompra());
            cs.setInt(2, modelo.getIdVeterinario());
            cs.setInt(3, modelo.getIdMascota());
            cs.setString(4, modelo.getNitClienteDueño());
            cs.setDouble(5, modelo.getTotal());
            cs.executeUpdate();
            cargarTablaFacturas();
            System.out.println("Factura agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Factura.");
            e.printStackTrace();
        }
    }

    private void actualizarFactura() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarFacturas(?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdFactura());
            cs.setInt(2, modelo.getIdCompra());
            cs.setInt(3, modelo.getIdVeterinario());
            cs.setInt(4, modelo.getIdMascota());
            cs.setString(5, modelo.getNitClienteDueño());
            cs.setDouble(6, modelo.getTotal());
            cs.executeUpdate();
            cargarTablaFacturas();
            System.out.println("Factura actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Factura.");
            e.printStackTrace();
        }
    }

    private void eliminarFactura() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarFacturas(?);");
            cs.setInt(1, modelo.getIdFactura());
            cs.executeUpdate();
            cargarTablaFacturas();
            System.out.println("Factura eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Factura.");
            e.printStackTrace();
        }
    }

    private void buscarFactura() {
        ArrayList<Facturas> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Facturas f : listarFacturas) {
            if (String.valueOf(f.getIdFactura()).contains(texto) ||
                    (cbxCompra.getValue() != null && String.valueOf(f.getIdCompra()).contains(texto)) ||
                    (cbxVeterinario.getValue() != null && String.valueOf(f.getIdVeterinario()).contains(texto)) ||
                    (cbxMascota.getValue() != null && String.valueOf(f.getIdMascota()).contains(texto)) ||
                    (f.getNitClienteDueño() != null && f.getNitClienteDueño().toLowerCase().contains(texto.toLowerCase())) ||
                    String.valueOf(f.getTotal()).contains(texto)) {
                resultado.add(f);
            }
        }

        tablaFacturas.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaFacturas.getSelectionModel().selectFirst();
            cargarFacturaSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdFactura.clear();
        cbxCompra.setValue(null);
        cbxVeterinario.setValue(null);
        cbxMascota.setValue(null);
        txtNitClienteDueno.clear();
        txtTotal.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdFactura.setDisable(estado);
        cbxCompra.setDisable(estado);
        cbxVeterinario.setDisable(estado);
        cbxMascota.setDisable(estado);
        txtNitClienteDueno.setDisable(estado);
        txtTotal.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdFactura.isDisable();
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
            agregarFactura();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarFactura();
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
        cargarTablaFacturas();
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
        txtIdFactura.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdFactura.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarFactura();
        tipoAccion = Accion.NINGUNA;
        cargarTablaFacturas();
    }

    @FXML
    private void btnBuscar() {
        buscarFactura();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaFacturas.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaFacturas.getSelectionModel().selectPrevious();
            cargarFacturaSeleccionada();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaFacturas.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaFacturas.getItems().size() - 1) {
            tablaFacturas.getSelectionModel().selectNext();
            cargarFacturaSeleccionada();
        }
    }
}