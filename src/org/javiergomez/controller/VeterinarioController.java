
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
import org.javiergomez.model.Cliente; 
import org.javiergomez.system.Main;
import javax.swing.JOptionPane; 


/**
 * FXML Controller class
 *
 * @author jgome
 */
public class VeterinarioController implements Initializable {

     private Main principal;
    private Cliente modelo; 
    private ObservableList<Cliente> listarClientes;

    private enum Accion {
        AGREGAR, EDITAR, NINGUNA
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re; 

    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn colIdClienteDueño, colNombre, colApellido, colTelefono, colCorreo, colNitClienteDueño, colEstado;
    @FXML
    private TextField txtBuscar, txtIdClienteDueño, txtNombre, txtApellido, txtTelefono, txtCorreo, txtNitClienteDueño;
    
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaClientes();

        if (!listarClientes.isEmpty()) {
            tablaClientes.getSelectionModel().selectFirst();
            cargarClienteSeleccionado();
        }
        tablaClientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { 
                cargarClienteSeleccionado();
            }
        });

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
        colIdClienteDueño.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("idClienteDueño"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("correo"));
        colNitClienteDueño.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nitClienteDueño"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Cliente, String>("estado"));
    }

    private void cargarTablaClientes() {
        listarClientes = FXCollections.observableArrayList(listarClientes());
        tablaClientes.setItems(listarClientes);
        if (!listarClientes.isEmpty()) {
            tablaClientes.getSelectionModel().selectFirst();
            cargarClienteSeleccionado();
        } else {
            limpiarCampos(); 
        }
    }

    private ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> Cliente = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarClientesDueños();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Cliente.add(new Cliente(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5), 
                        rs.getString(6), 
                        rs.getString(7)  
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Clientes");
            e.printStackTrace();
        }
        return Cliente;
    }

    private void cargarClienteSeleccionado() {
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            txtIdClienteDueño.setText(String.valueOf(cliente.getIdClienteDueño()));
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtTelefono.setText(cliente.getTelefono());
            txtCorreo.setText(cliente.getCorreo());
            txtNitClienteDueño.setText(cliente.getNitClienteDueño());
        } else {
            limpiarCampos(); 
        }
    }

    private Cliente obtenerModelo() {
        int idClienteDueño = txtIdClienteDueño.getText().isEmpty() ? 0 : Integer.parseInt(txtIdClienteDueño.getText());
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String nitClienteDueño = txtNitClienteDueño.getText();
        String estado = ""; 

        return new Cliente(idClienteDueño, nombre, apellido, telefono, correo, nitClienteDueño, estado);
    }

    private void agregarCliente() {
        modelo = obtenerModelo();
        try {
         
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarClienteDueños(?, ?, ?, ?, ?, ?);");
            cs.setString(1, modelo.getNombre());
            cs.setString(2, modelo.getApellido());
            cs.setString(3, modelo.getTelefono());
            cs.setString(4, modelo.getCorreo());
            cs.setString(5, modelo.getNitClienteDueño());
            cs.setString(6, "Activo"); 
            cs.executeUpdate();
            cargarTablaClientes();
            System.out.println("Cliente agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Cliente.");
            e.printStackTrace();
        }
    }

    private void actualizarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            modelo = obtenerModelo();
            try {
     
                CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarClientesDueños(?, ?, ?, ?, ?, ?, ?);");
                cs.setInt(1, modelo.getIdClienteDueño());
                cs.setString(2, modelo.getNombre());
                cs.setString(3, modelo.getApellido());
                cs.setString(4, modelo.getTelefono());
                cs.setString(5, modelo.getCorreo());
                cs.setString(6, modelo.getNitClienteDueño());
                cs.setString(7, clienteSeleccionado.getEstado());
                cs.executeUpdate();
                cargarTablaClientes();
                System.out.println("Cliente actualizado.");
            } catch (SQLException e) {
                System.out.println("Error al actualizar Cliente.");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para editar.", "Cliente no seleccionado", JOptionPane.WARNING_MESSAGE);
        }
    }

   
    private void eliminarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            if (JOptionPane.showConfirmDialog(null, "¿Estás seguro de cambiar el estado a Inactivo del cliente " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellido() + "?", "Confirmar eliminación (cambio de estado)", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                  
                    CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarClienteDueño(?);");
                    cs.setInt(1, clienteSeleccionado.getIdClienteDueño());
                    cs.executeUpdate();
                    cargarTablaClientes();
                    System.out.println("Estado del cliente cambiado a Inactivo.");
                } catch (SQLException e) {
                    System.out.println("Error al cambiar el estado del Cliente.");
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para cambiar su estado.", "Cliente no seleccionado", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void buscarCliente() {
        ArrayList<Cliente> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Cliente c : listarClientes) {
            if (String.valueOf(c.getIdClienteDueño()).contains(texto) ||
                    (c.getNombre() != null && c.getNombre().toLowerCase().contains(texto.toLowerCase())) ||
                    (c.getApellido() != null && c.getApellido().toLowerCase().contains(texto.toLowerCase())) ||
                    (c.getTelefono() != null && c.getTelefono().toLowerCase().contains(texto.toLowerCase())) ||
                    (c.getCorreo() != null && c.getCorreo().toLowerCase().contains(texto.toLowerCase())) ||
                    (c.getNitClienteDueño() != null && c.getNitClienteDueño().toLowerCase().contains(texto.toLowerCase())) ||
                    (c.getEstado() != null && c.getEstado().toLowerCase().contains(texto.toLowerCase()))) {
                resultado.add(c);
            }
        }

        tablaClientes.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaClientes.getSelectionModel().selectFirst();
            cargarClienteSeleccionado();
        } else {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtIdClienteDueño.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtNitClienteDueño.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdClienteDueño.setDisable(estado);
        txtNombre.setDisable(estado);
        txtApellido.setDisable(estado);
        txtTelefono.setDisable(estado);
        txtCorreo.setDisable(estado);
        txtNitClienteDueño.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtNombre.isDisable(); 

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
            agregarCliente();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarCliente();
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
        cargarTablaClientes(); 
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
        txtIdClienteDueño.setDisable(true);
    }

    @FXML
    private void btnEditar() {
        if (tablaClientes.getSelectionModel().getSelectedItem() != null) {
            tipoAccion = Accion.EDITAR;
            cambiarEstadoCampos(false); 
            btnGuardar.setDisable(false);
            btnCancelar.setDisable(false);
            btnNuevo.setDisable(true);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
            txtIdClienteDueño.setDisable(true); 
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para editar.", "Cliente no seleccionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    @FXML
    private void btnEliminar() {
        
        eliminarCliente();
       
    }

    @FXML
    private void btnBuscar() {
        buscarCliente();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaClientes.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaClientes.getSelectionModel().selectPrevious();
            cargarClienteSeleccionado();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaClientes.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaClientes.getItems().size() - 1) {
            tablaClientes.getSelectionModel().selectNext();
            cargarClienteSeleccionado();
        }
    }
}
