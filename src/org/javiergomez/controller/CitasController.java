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
import java.time.format.DateTimeFormatter;
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
import org.javiergomez.model.Citas;
import org.javiergomez.model.Cliente;
import org.javiergomez.model.Mascotas;
import org.javiergomez.model.servicios;
import org.javiergomez.system.Main;
/**
 * FXML Controller class
 *
 * @author jgome
 */
public class CitasController implements Initializable {

    private Main principal;
    private Citas modelo;
    private ObservableList<Citas> listarCitas;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button re;

    @FXML
    private TableView<Citas> tablaCitas;
    @FXML
    private TableColumn colIdCita, colIdServicio, colFechaCita, colIdClienteDueno, colIdMascota;
    @FXML
    private TextField txtBuscar, txtIdCita, txtIdServicio, txtIdClienteDueno, txtIdMascota;
    @FXML
    private DatePicker dpFechaCita;
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar;

    @FXML
    private ComboBox<servicios> cbxServicio;
    @FXML
    private ComboBox<Cliente> cbxClienteDueno;
    @FXML
    private ComboBox<Mascotas> cbxMascota;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaCitas();
        cargarCitasEnCampos();
        cargarMascotasEnCampos();
        cargarClientesEnCampos();
        tablaCitas.setOnMouseClicked(event -> cargarCitaSeleccionada());

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
        colIdCita.setCellValueFactory(new PropertyValueFactory<Citas, Integer>("idCita"));
        colIdServicio.setCellValueFactory(new PropertyValueFactory<Citas, Integer>("idServicio"));
        colFechaCita.setCellValueFactory(new PropertyValueFactory<Citas, LocalDate>("fechaCita"));
        colIdClienteDueno.setCellValueFactory(new PropertyValueFactory<Citas, Integer>("idClienteDueño"));
        colIdMascota.setCellValueFactory(new PropertyValueFactory<Citas, Integer>("idMascota"));
    }

    private void cargarTablaCitas() {
        listarCitas = FXCollections.observableArrayList(listarCitas());
        tablaCitas.setItems(listarCitas);
        if (!listarCitas.isEmpty()) {
            tablaCitas.getSelectionModel().selectFirst();
            cargarCitaSeleccionada();
        }
    }

    private ArrayList<Citas> listarCitas() {
        ArrayList<Citas> citas = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarCitas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                citas.add(new Citas(
                        rs.getInt(1),
                        rs.getInt(2),
                        LocalDate.parse(rs.getString(3), DateTimeFormatter.ISO_DATE),
                        rs.getInt(4),
                        rs.getInt(5)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Citas");
            e.printStackTrace();
        }
        return citas;
    }

    private void cargarCitasEnCampos() {
        ObservableList<servicios> serviciosList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarServicios();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                serviciosList.add(new servicios(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxServicio.setItems(serviciosList);
}
 private void cargarClientesEnCampos() {
        ObservableList<Cliente> clientesList = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_listarClientesDueños();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                clientesList.add(new Cliente(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxClienteDueno.setItems(clientesList);
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

    private void cargarCitaSeleccionada() {
        Citas cita = tablaCitas.getSelectionModel().getSelectedItem();
        if (cita != null) {
            txtIdCita.setText(String.valueOf(cita.getIdCita()));
            txtIdServicio.setText(String.valueOf(cita.getIdServicio()));
            dpFechaCita.setValue(cita.getFechaCita());
            txtIdClienteDueno.setText(String.valueOf(cita.getIdClienteDueño()));
            txtIdMascota.setText(String.valueOf(cita.getIdMascota()));

            for (servicios s : cbxServicio.getItems()) {
                if (s.getIdServicio() == cita.getIdServicio()) {
                    cbxServicio.setValue(s);
                    break;
                }
            }
            for (Cliente c : cbxClienteDueno.getItems()) {
                if (c.getIdClienteDueño() == cita.getIdClienteDueño()) {
                    cbxClienteDueno.setValue(c);
                    break;
                }
            }
            for (Mascotas m : cbxMascota.getItems()) {
                if (m.getIdMascota() == cita.getIdMascota()) {
                    cbxMascota.setValue(m);
                    break;
                }
            }
        }
    }

    

    private Citas obtenerModelo() {
        int idCita = txtIdCita.getText().isEmpty() ? 0 : Integer.parseInt(txtIdCita.getText());
        int idServicio = cbxServicio.getValue() == null ? 0 : cbxServicio.getValue().getIdServicio();
        LocalDate fechaCita = dpFechaCita.getValue();
        int idClienteDueno = cbxClienteDueno.getValue() == null ? 0 : cbxClienteDueno.getValue().getIdClienteDueño();
        int idMascota = cbxMascota.getValue() == null ? 0 : cbxMascota.getValue().getIdMascota();

        return new Citas(idCita, idServicio, fechaCita, idClienteDueno, idMascota);
    }

    private void agregarCita() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarCitas(?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdServicio());
            cs.setString(2, modelo.getFechaCita().format(DateTimeFormatter.ISO_DATE));
            cs.setInt(3, modelo.getIdClienteDueño());
            cs.setInt(4, modelo.getIdMascota());
            cs.executeUpdate();
            cargarTablaCitas();
            System.out.println("Cita agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar Cita.");
            e.printStackTrace();
        }
    }

    private void actualizarCita() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_actualizarCitas(?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdCita());
            cs.setInt(2, modelo.getIdServicio());
            cs.setString(3, modelo.getFechaCita().format(DateTimeFormatter.ISO_DATE));
            cs.setInt(4, modelo.getIdClienteDueño());
            cs.setInt(5, modelo.getIdMascota());
            cs.executeUpdate();
            cargarTablaCitas();
            System.out.println("Cita actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Cita.");
            e.printStackTrace();
        }
    }

    private void eliminarCita() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarCitas(?);");
            cs.setInt(1, modelo.getIdCita());
            cs.executeUpdate();
            cargarTablaCitas();
            System.out.println("Cita eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Cita.");
            e.printStackTrace();
        }
    }

    private void buscarCita() {
        ArrayList<Citas> resultado = new ArrayList<>();
        String texto = txtBuscar.getText();

        for (Citas c : listarCitas) {
            if (String.valueOf(c.getIdCita()).contains(texto) ||
                    (cbxServicio.getValue() != null && String.valueOf(c.getIdServicio()).contains(texto)) ||
                    (c.getFechaCita() != null && c.getFechaCita().toString().contains(texto)) ||
                    (cbxClienteDueno.getValue() != null && String.valueOf(c.getIdClienteDueño()).contains(texto)) ||
                    (cbxMascota.getValue() != null && String.valueOf(c.getIdMascota()).contains(texto))) {
                resultado.add(c);
            }
        }

        tablaCitas.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaCitas.getSelectionModel().selectFirst();
            cargarCitaSeleccionada();
        }
    }

    private void limpiarCampos() {
        txtIdCita.clear();
        cbxServicio.setValue(null);
        dpFechaCita.setValue(null);
        cbxClienteDueno.setValue(null);
        cbxMascota.setValue(null);
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdCita.setDisable(estado);
        cbxServicio.setDisable(estado);
        dpFechaCita.setDisable(estado);
        cbxClienteDueno.setDisable(estado);
        cbxMascota.setDisable(estado);
    }

    private void habilitarDeshabilitarBotones() {
        boolean desactivado = txtIdCita.isDisable();
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
            agregarCita();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarCita();
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
        cargarTablaCitas();
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
        txtIdCita.setDisable(true); // El ID usualmente es autoincremental
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
        txtIdCita.setDisable(true); // No permitir editar el ID
    }
    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarCita();
        tipoAccion = Accion.NINGUNA;
        cargarTablaCitas();
    }

    @FXML
    private void btnBuscar() {
        buscarCita();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaCitas.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaCitas.getSelectionModel().selectPrevious();
            cargarCitaSeleccionada();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaCitas.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaCitas.getItems().size() - 1) {
            tablaCitas.getSelectionModel().selectNext();
            cargarCitaSeleccionada();
        }
    }
}