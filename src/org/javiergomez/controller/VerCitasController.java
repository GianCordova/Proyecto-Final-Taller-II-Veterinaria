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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javiergomez.db.Conexion;
import org.javiergomez.model.VerCitas;
import org.javiergomez.system.Main;

public class VerCitasController implements Initializable {
    private Main principal;
    private VerCitas modelo;
    private ObservableList<VerCitas> listaCitas;

    private enum Accion { AGREGAR, EDITAR, ELIMINAR, NINGUNA }
    private Accion tipoAccion = Accion.NINGUNA;
    
    @FXML private Button re;
    @FXML private TableView<VerCitas> tablaCitas;
    @FXML private TableColumn colId, colFecha, colCliente, colMascota, colServicio, colVeterinario;
    @FXML private TextField txtBuscar, txtId, txtFecha;
    @FXML private Button  btnBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTablaCitas();
        if (tablaCitas != null) {
            tablaCitas.setOnMouseClicked(event -> cargarCitasEnCampos());
        }
        
    }

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @FXML
    private void cl(ActionEvent evento){
        if (evento.getSource() == re) {
            principal.Veterinarios();
        }
    }
    
    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idCita"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colMascota.setCellValueFactory(new PropertyValueFactory<>("nombreMascota"));
        colServicio.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
        colVeterinario.setCellValueFactory(new PropertyValueFactory<>("nombreVeterinario"));
    }

    private void cargarTablaCitas() {
        listaCitas = FXCollections.observableArrayList(listarCitas());
        if (tablaCitas != null) {
            tablaCitas.setItems(listaCitas);
            if (!listaCitas.isEmpty()) {
                tablaCitas.getSelectionModel().selectFirst();
                cargarCitasEnCampos();
            }
        }
    }

    private ArrayList<VerCitas> listarCitas() {
        ArrayList<VerCitas> citas = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarCitas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                citas.add(new VerCitas(
                        rs.getInt("idCita"),
                        rs.getDate("fechaCita").toLocalDate(),
                        rs.getString("nombre"),  // Ajustado al alias correcto
                        rs.getString("nombreMascota"),
                        rs.getString("nombreServicio"),
                        rs.getString("nombreVeterinario")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    private void cargarCitasEnCampos() {
        VerCitas cita = tablaCitas.getSelectionModel().getSelectedItem();
        if (cita != null) {
            txtId.setText(String.valueOf(cita.getIdCita()));
            txtFecha.setText(cita.getFecha().toString());
        }
    }

    private VerCitas obtenerModelo() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        LocalDate fecha = LocalDate.parse(txtFecha.getText());
        VerCitas citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();

        return new VerCitas(
                id,
                fecha,
                citaSeleccionada.getNombreCliente(),
                citaSeleccionada.getNombreMascota(),
                citaSeleccionada.getNombreServicio(),
                citaSeleccionada.getNombreVeterinario()
        );
    }

    private void buscarCitas() {
        ArrayList<VerCitas> resultado = new ArrayList<>();
        String nombreCliente = txtBuscar.getText().toLowerCase();
        for (VerCitas c : listaCitas) {
            if (c.getNombreCliente().toLowerCase().contains(nombreCliente)) {
                resultado.add(c);
            }
        }
        tablaCitas.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaCitas.getSelectionModel().selectFirst();
            cargarCitasEnCampos();
        }
    }

    @FXML
    private void btnBuscar() {
        buscarCitas();
    }
}