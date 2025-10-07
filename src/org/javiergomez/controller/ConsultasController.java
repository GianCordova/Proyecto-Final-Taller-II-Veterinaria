/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javiergomez.db.Conexion;
import org.javiergomez.model.Cliente;
import org.javiergomez.model.Consultas;
import org.javiergomez.model.Mascotas;
import org.javiergomez.model.servicios;
import org.javiergomez.model.Tratamientos;
import org.javiergomez.model.Veterinarios;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author jgome
 */
public class ConsultasController implements Initializable {

    private Main principal;
    private Consultas modelo;
    private ObservableList<Consultas> listarConsultas;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

    @FXML
    private TableView<Consultas> tablaConsultas;
    @FXML
    private TableColumn colIdConsulta, colIdServicio, colIdClienteDueño, colIdMascota, colIdTratamiento, colIdVeterinario, colFechaConsulta;
    @FXML
    private TextField txtBuscar, txtIdConsulta;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<servicios> cbxServicio;
    @FXML
    private ComboBox<Cliente> cbxClienteDueño;
    @FXML
    private ComboBox<Mascotas> cbxMascota;
    @FXML
    private ComboBox<Tratamientos> cbxTratamiento;
    @FXML
    private ComboBox<Veterinarios> cbxVeterinario;
    @FXML
    private DatePicker dpFechaConsulta;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaConsultas();
        cargarConsultasEnCampos(); 
        cargarVeterinarioEnCampos();
        cargarClienteEnCampos();
        cargarMascotasEnCampos();
        cargarTratamientosEnCampos();
        tablaConsultas.setOnMouseClicked(event -> cargarConsultaSeleccionada());

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
        colIdConsulta.setCellValueFactory(new PropertyValueFactory<Consultas, Integer>("idConsulta"));
        colIdServicio.setCellValueFactory(new PropertyValueFactory<Consultas, Integer>("idServicio"));
        colIdClienteDueño.setCellValueFactory(new PropertyValueFactory<Consultas, Integer>("idClienteDueño"));
        colIdMascota.setCellValueFactory(new PropertyValueFactory<Consultas, Integer>("idMascota"));
        colIdTratamiento.setCellValueFactory(new PropertyValueFactory<Consultas, Integer>("idTratamiento"));
        colIdVeterinario.setCellValueFactory(new PropertyValueFactory<Consultas, Integer>("idVeterinario"));
        colFechaConsulta.setCellValueFactory(new PropertyValueFactory<Consultas, LocalDate>("fechaConsulta"));
    }

    private void cargarTablaConsultas() {
        listarConsultas = FXCollections.observableArrayList(listarConsultas());
        tablaConsultas.setItems(listarConsultas);
        if (!listarConsultas.isEmpty()) {
            tablaConsultas.getSelectionModel().selectFirst();
            cargarConsultaSeleccionada();
        }
    }

    private ArrayList<Consultas> listarConsultas() {
        ArrayList<Consultas> consultas = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarConsultas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                consultas.add(new Consultas(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDate(7).toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Consultas");
            e.printStackTrace();
        }
        return consultas;
    }

    private void cargarConsultasEnCampos() {

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
    private void cargarClienteEnCampos() {    
        ObservableList<Cliente> clientesList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarClientesDueños();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                clientesList.add(new Cliente(
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4), 
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxClienteDueño.setItems(clientesList);
}
private void cargarMascotasEnCampos() {

        ObservableList<Mascotas> mascotasList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarMascotas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {

                mascotasList.add(new Mascotas(
                        rs.getInt(1),
                        rs.getInt(2), 
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5), 
                        rs.getString(6),
                        rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxMascota.setItems(mascotasList);
}
private void cargarTratamientosEnCampos() {
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
private void cargarVeterinarioEnCampos() {
        // Cargar ComboBox de Veterinarios
        ObservableList<Veterinarios> veterinariosList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarVeterinarios();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                veterinariosList.add(new Veterinarios(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3), 
                        rs.getInt(4), 
                        rs.getDouble(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxVeterinario.setItems(veterinariosList);
    }

    private void cargarConsultaSeleccionada() {
        Consultas consulta = tablaConsultas.getSelectionModel().getSelectedItem();
        if (consulta != null) {
            txtIdConsulta.setText(String.valueOf(consulta.getIdConsulta()));

            for (servicios s : cbxServicio.getItems()) {
                if (s.getIdServicio() == consulta.getIdServicio()) {
                    cbxServicio.setValue(s);
                    break;
                }
            }
            for (Cliente cd : cbxClienteDueño.getItems()) {
                if (cd.getIdClienteDueño()== consulta.getIdClienteDueño()) {
                    cbxClienteDueño.setValue(cd);
                    break;
                }
            }
            for (Mascotas m : cbxMascota.getItems()) {
                if (m.getIdMascota() == consulta.getIdMascota()) {
                    cbxMascota.setValue(m);
                    break;
                }
            }
            for (Tratamientos t : cbxTratamiento.getItems()) {
                if (t.getIdTratamiento() == consulta.getIdTratamiento()) {
                    cbxTratamiento.setValue(t);
                    break;
                }
            }
            for (Veterinarios v : cbxVeterinario.getItems()) {
                if (v.getIdVeterinario() == consulta.getIdVeterinario()) {
                    cbxVeterinario.setValue(v);
                    break;
                }
            }
            dpFechaConsulta.setValue(consulta.getFechaConsulta());
        }
    }

    private Consultas obtenerModelo() {
        int idConsulta = txtIdConsulta.getText().isEmpty() ? 0 : Integer.parseInt(txtIdConsulta.getText());
        int idServicio = cbxServicio.getValue() == null ? 0 : cbxServicio.getValue().getIdServicio();
        int idClienteDueño = cbxClienteDueño.getValue() == null ? 0 : cbxClienteDueño.getValue().getIdClienteDueño();
        int idMascota = cbxMascota.getValue() == null ? 0 : cbxMascota.getValue().getIdMascota();
        int idTratamiento = cbxTratamiento.getValue() == null ? 0 : cbxTratamiento.getValue().getIdTratamiento();
        int idVeterinario = cbxVeterinario.getValue() == null ? 0 : cbxVeterinario.getValue().getIdVeterinario();
        LocalDate fechaConsulta = dpFechaConsulta.getValue();

        return new Consultas(idConsulta, idServicio, idClienteDueño, idMascota, idTratamiento, idVeterinario, fechaConsulta);
    }

    private void agregarConsulta() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarConsultas(?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdServicio());
            cs.setInt(2, modelo.getIdClienteDueño());
            cs.setInt(3, modelo.getIdMascota());
            cs.setInt(4, modelo.getIdTratamiento());
            cs.setInt(5, modelo.getIdVeterinario());
            cs.setDate(6, java.sql.Date.valueOf(modelo.getFechaConsulta()));
            cs.executeUpdate();
            cargarTablaConsultas();
            System.out.println("Consulta agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Consulta.");
            e.printStackTrace();
        }
    }

    private void actualizarConsulta() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_editarConsultas(?, ?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdConsulta());
            cs.setInt(2, modelo.getIdServicio());
            cs.setInt(3, modelo.getIdClienteDueño());
            cs.setInt(4, modelo.getIdMascota());
            cs.setInt(5, modelo.getIdTratamiento());
            cs.setInt(6, modelo.getIdVeterinario());
            cs.setDate(7, java.sql.Date.valueOf(modelo.getFechaConsulta()));
            cs.executeUpdate();
            cargarTablaConsultas();
            System.out.println("Consulta actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Consulta.");
            e.printStackTrace();
        }
    }

    private void eliminarConsulta() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarConsultas(?);");
            cs.setInt(1, modelo.getIdConsulta());
            cs.executeUpdate();
            cargarTablaConsultas();
            System.out.println("Consulta eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Consulta.");
            e.printStackTrace();
        }
    }

    private void buscarConsulta() {
        ArrayList<Consultas> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Consultas c : listarConsultas) {
            if (String.valueOf(c.getIdConsulta()).contains(texto) ||
                    (cbxServicio.getValue() != null && String.valueOf(c.getIdServicio()).contains(texto)) ||
                    (cbxClienteDueño.getValue() != null && String.valueOf(c.getIdClienteDueño()).contains(texto)) ||
                    (cbxMascota.getValue() != null && String.valueOf(c.getIdMascota()).contains(texto)) ||
                    (cbxTratamiento.getValue() != null && String.valueOf(c.getIdTratamiento()).contains(texto)) ||
                    (cbxVeterinario.getValue() != null && String.valueOf(c.getIdVeterinario()).contains(texto)) ||
                    (c.getFechaConsulta() != null && c.getFechaConsulta().toString().contains(texto))) {
                resultado.add(c);
            }
        }

        tablaConsultas.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaConsultas.getSelectionModel().selectFirst();
            cargarConsultaSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdConsulta.clear();
        cbxServicio.setValue(null);
        cbxClienteDueño.setValue(null);
        cbxMascota.setValue(null);
        cbxTratamiento.setValue(null);
        cbxVeterinario.setValue(null);
        dpFechaConsulta.setValue(null);
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdConsulta.setDisable(estado);
        cbxServicio.setDisable(estado);
        cbxClienteDueño.setDisable(estado);
        cbxMascota.setDisable(estado);
        cbxTratamiento.setDisable(estado);
        cbxVeterinario.setDisable(estado);
        dpFechaConsulta.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdConsulta.isDisable();
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
            agregarConsulta();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarConsulta();
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
        cargarTablaConsultas();
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
        txtIdConsulta.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdConsulta.setDisable(true); // No permitir editar el ID
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarConsulta();
        tipoAccion = Accion.NINGUNA;
        cargarTablaConsultas();
    }

    @FXML
    private void btnBuscar() {
        buscarConsulta();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaConsultas.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaConsultas.getSelectionModel().selectPrevious();
            cargarConsultaSeleccionada();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaConsultas.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaConsultas.getItems().size() - 1) {
            tablaConsultas.getSelectionModel().selectNext();
            cargarConsultaSeleccionada();
        }
    }
}