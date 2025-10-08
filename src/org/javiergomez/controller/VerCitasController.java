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
    private ObservableList<VerCitas> listaCitas;

    @FXML private Button re;
    @FXML private TableView<VerCitas> tablaCitas;
    @FXML private TableColumn colId, colFecha, colCliente, colMascota, colServicio,colVeterinario;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;

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
            // Traemos solo columnas existentes
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall(
                 "SELECT c.idCita, c.fechaCita, " +
            "cl.nombre AS nombreCliente, " +
            "m.nombreMascota, " +
            "s.nombreServicio, " +
            "v.nombreVeterinario " +
            "FROM Citas c " +
            "JOIN ClientesDueños cl ON c.idClienteDueño = cl.idClienteDueño " +
            "JOIN Mascotas m ON c.idMascota = m.idMascota " +
            "JOIN Servicios s ON c.idServicio = s.idServicio " +
            "LEFT JOIN Consultas co ON co.idClienteDueño = c.idClienteDueño " +
            "   AND co.idMascota = c.idMascota " +
            "   AND co.idServicio = c.idServicio " +
            "LEFT JOIN Veterinarios v ON v.idVeterinario = co.idVeterinario"
            );
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                citas.add(new VerCitas(
                        rs.getInt("idCita"),
                        rs.getDate("fechaCita").toLocalDate(),
                        rs.getString("nombreCliente"),
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
         
        }
    }

private void buscarCitas() {
    ArrayList<VerCitas> resultado = new ArrayList<>();
    String textoBusqueda = txtBuscar.getText().toLowerCase();

    for (VerCitas c : listaCitas) {
        String veterinario = c.getNombreVeterinario() != null ? c.getNombreVeterinario().toLowerCase() : "";
        String cliente = c.getNombreCliente() != null ? c.getNombreCliente().toLowerCase() : "";

        if (cliente.contains(textoBusqueda) || veterinario.contains(textoBusqueda)) {
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