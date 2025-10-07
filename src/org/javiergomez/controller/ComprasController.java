
package org.javiergomez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javiergomez.db.Conexion;
import org.javiergomez.model.Compras;
import org.javiergomez.model.Medicamentos;
import org.javiergomez.model.Proveedores;
import org.javiergomez.model.Recetas;
import org.javiergomez.model.servicios;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class ComprasController implements Initializable {

  private Main principal;
    private Compras modelo;
    private ObservableList<Compras> listarCompras;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;
    
    @FXML
    private Button re;

   @FXML
    private TableView<Compras> tablaCompras;
    @FXML
    private TableColumn colIdCompra, colIdServicio, colFechaCompra, colIdMedicamento, colIdProveedor, colIdReceta, colSubtotal;
    @FXML
    private TextField txtBuscar, txtIdCompra, txtIdServicio, txtIdMedicamento, txtIdProveedor, txtIdReceta, txtSubtotal;
    @FXML
    private DatePicker dpFechaCompra;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<servicios> cbxServicio;
    @FXML
    private ComboBox<Medicamentos> cbxMedicamento;
    @FXML
    private ComboBox<Proveedores> cbxProveedor;
    @FXML
    private ComboBox<Recetas> cbxReceta;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         configurarColumnas();
        cargarTablaCompras();
        cargarComprasEnCampos();
        cargarCompraSeleccionada();
        cargarMedicamentosEnCampos();
        cargarProveedoresEnCampos();
        cargarRecetasEnCampos();
        tablaCompras.setOnMouseClicked(event -> cargarCompraSeleccionada());

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
        colIdCompra.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("idCompra"));
        colIdServicio.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("idServicio"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, LocalDate>("fechaCompra"));
        colIdMedicamento.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("idMedicamento"));
        colIdProveedor.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("idProveedor"));
        colIdReceta.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("idReceta"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<Compras, Double>("subtotal"));
    }

    private void cargarTablaCompras() {
        listarCompras = FXCollections.observableArrayList(listarCompras());
        tablaCompras.setItems(listarCompras);
        if (!listarCompras.isEmpty()) {
            tablaCompras.getSelectionModel().selectFirst();
            cargarCompraSeleccionada();
        }
    }

    private ArrayList<Compras> listarCompras() {
        ArrayList<Compras> compras = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarCompras();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                compras.add(new Compras(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDate(3).toLocalDate(),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Compras");
            e.printStackTrace();
        }
        return compras;
    }

    private void cargarComprasEnCampos() {
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
 private void cargarProveedoresEnCampos() {
        ObservableList<Proveedores> proveedoresList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarProveedores();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                proveedoresList.add(new Proveedores(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(2),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxProveedor.setItems(proveedoresList);
 }
  private void cargarRecetasEnCampos() {
        ObservableList<Recetas> recetasList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarRecetas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                recetasList.add(new Recetas(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxReceta.setItems(recetasList);
  }

    private void cargarCompraSeleccionada() {
        Compras compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra != null) {
            txtIdCompra.setText(String.valueOf(compra.getIdCompra()));
            txtIdServicio.setText(String.valueOf(compra.getIdServicio()));
            dpFechaCompra.setValue(compra.getFechaCompra());
            txtIdMedicamento.setText(String.valueOf(compra.getIdMedicamento()));
            txtIdProveedor.setText(String.valueOf(compra.getIdProveedor()));
            txtIdReceta.setText(String.valueOf(compra.getIdReceta()));
            txtSubtotal.setText(String.valueOf(compra.getSubtotal()));

            for (servicios s : cbxServicio.getItems()) {
                if (s.getIdServicio() == compra.getIdServicio()) {
                    cbxServicio.setValue(s);
                    break;
                }
            }
            for (Medicamentos m : cbxMedicamento.getItems()) {
                if (m.getIdMedicamento() == compra.getIdMedicamento()) {
                    cbxMedicamento.setValue(m);
                    break;
                }
            }
            for (Proveedores p : cbxProveedor.getItems()) {
                if (p.getIdProveedor() == compra.getIdProveedor()) {
                    cbxProveedor.setValue(p);
                    break;
                }
            }
            for (Recetas r : cbxReceta.getItems()) {
                if (r.getIdReceta() == compra.getIdReceta()) {
                    cbxReceta.setValue(r);
                    break;
                }
            }
        }
    }

    private Compras obtenerModelo() {
        int idCompra = txtIdCompra.getText().isEmpty() ? 0 : Integer.parseInt(txtIdCompra.getText());
        int idServicio = cbxServicio.getValue() == null ? 0 : cbxServicio.getValue().getIdServicio();
        LocalDate fechaCompra = dpFechaCompra.getValue();
        int idMedicamento = cbxMedicamento.getValue() == null ? 0 : cbxMedicamento.getValue().getIdMedicamento();
        int idProveedor = cbxProveedor.getValue() == null ? 0 : cbxProveedor.getValue().getIdProveedor();
        int idReceta = cbxReceta.getValue() == null ? 0 : cbxReceta.getValue().getIdReceta();
        double subtotal = txtSubtotal.getText().isEmpty() ? 0.0 : Double.parseDouble(txtSubtotal.getText());

        return new Compras(idCompra, idServicio, fechaCompra, idMedicamento, idProveedor, idReceta, subtotal);
    }

    private void agregarCompra() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarCompra(?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdServicio());
            cs.setDate(2, Date.valueOf(modelo.getFechaCompra()));
            cs.setInt(3, modelo.getIdMedicamento());
            cs.setInt(4, modelo.getIdProveedor());
            cs.setInt(5, modelo.getIdReceta());
            cs.setDouble(6, modelo.getSubtotal());
            cs.executeUpdate();
            cargarTablaCompras();
            System.out.println("Compra agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Compra.");
            e.printStackTrace();
        }
    }

    private void actualizarCompra() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_editarCompra(?, ?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdCompra());
            cs.setInt(2, modelo.getIdServicio());
            cs.setDate(3, Date.valueOf(modelo.getFechaCompra()));
            cs.setInt(4, modelo.getIdMedicamento());
            cs.setInt(5, modelo.getIdProveedor());
            cs.setInt(6, modelo.getIdReceta());
            cs.setDouble(7, modelo.getSubtotal());
            cs.executeUpdate();
            cargarTablaCompras();
            System.out.println("Compra actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Compra.");
            e.printStackTrace();
        }
    }

    private void eliminarCompra() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarCompra(?);");
            cs.setInt(1, modelo.getIdCompra());
            cs.executeUpdate();
            cargarTablaCompras();
            System.out.println("Compra eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Compra.");
            e.printStackTrace();
        }
    }

    private void buscarCompra() {
        ArrayList<Compras> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Compras c : listarCompras) {
            if (String.valueOf(c.getIdCompra()).contains(texto) ||
                    (cbxServicio.getValue() != null && String.valueOf(c.getIdServicio()).contains(texto)) ||
                    (c.getFechaCompra() != null && c.getFechaCompra().toString().contains(texto)) ||
                    (cbxMedicamento.getValue() != null && String.valueOf(c.getIdMedicamento()).contains(texto)) ||
                    (cbxProveedor.getValue() != null && String.valueOf(c.getIdProveedor()).contains(texto)) ||
                    (cbxReceta.getValue() != null && String.valueOf(c.getIdReceta()).contains(texto)) ||
                    String.valueOf(c.getSubtotal()).contains(texto)) {
                resultado.add(c);
            }
        }

        tablaCompras.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaCompras.getSelectionModel().selectFirst();
            cargarCompraSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdCompra.clear();
        cbxServicio.setValue(null);
        dpFechaCompra.setValue(null);
        cbxMedicamento.setValue(null);
        cbxProveedor.setValue(null);
        cbxReceta.setValue(null);
        txtSubtotal.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdCompra.setDisable(estado);
        cbxServicio.setDisable(estado);
        dpFechaCompra.setDisable(estado);
        cbxMedicamento.setDisable(estado);
        cbxProveedor.setDisable(estado);
        cbxReceta.setDisable(estado);
        txtSubtotal.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdCompra.isDisable();
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
            agregarCompra();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarCompra();
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
        cargarTablaCompras();
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
        txtIdCompra.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdCompra.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarCompra();
        tipoAccion = Accion.NINGUNA;
        cargarTablaCompras();
    }

    @FXML
    private void btnBuscar() {
        buscarCompra();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaCompras.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaCompras.getSelectionModel().selectPrevious();
            cargarCompraSeleccionada();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaCompras.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaCompras.getItems().size() - 1) {
            tablaCompras.getSelectionModel().selectNext();
            cargarCompraSeleccionada();
        }
    }
}

