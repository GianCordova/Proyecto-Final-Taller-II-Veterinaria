package org.javiergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.javiergomez.system.Main;
import org.javiergomez.db.Conexion;
import org.javiergomez.model.Medicamentos;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.ArrayList;

import java.sql.Date; // fecha pero en MySQL
import java.sql.Connection; // Interfaz de Conexion
import java.sql.CallableStatement; // Enunciado de llamada
import java.sql.ResultSet; // Resultado del Result Grid
import java.sql.SQLException; // Excepcion SQL
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.Initializable; // Por si usamos eventos
import javafx.event.ActionEvent; // Interfaz que usamos para heredar controles
import javafx.fxml.FXML; // lector de archivos FXML

import javafx.scene.control.Button; // Boton btn
import javafx.scene.control.TableView; // tabla tabla
import javafx.scene.control.TableColumn; // columna de Tabla col
import javafx.scene.control.DatePicker; // Seleccionador de fecha dp
import javafx.scene.control.TextField; // Campo de texto txt
import javafx.scene.control.cell.PropertyValueFactory; // Formato de celdas

import javafx.collections.FXCollections; // Arreglo o coleccion de JavaFX
import javafx.collections.ObservableList; //Lista Observable
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import org.javiergomez.model.Cliente;
import org.javiergomez.model.Empleados;
import org.javiergomez.system.Main;
import org.javiergomez.model.Mascotas;
import org.javiergomez.model.servicios;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class AnimalitosController implements Initializable {

   private Main principal;
    private Mascotas modelo;
    private ObservableList<Mascotas> listaMascotas;

    private enum Accion {
        AGREGAR, EDITAR, ELIMINAR, NINGUNA;
    }

    private Accion tipoAccion = Accion.NINGUNA;

    @FXML
    private Button btnRe, btnBuscar, btnAnterior, btnSiguiente, btnGuardar, btnCancelar, btnNuevo, btnEditar, btnEliminar;
    @FXML
    private TableColumn colId, colIdDueño, colNombre, colSexo, colEdad, colRaza, colTratamientoPrevio;
    @FXML
    private TableView<Mascotas> tablaMascotas;
    @FXML
    private TextField txtId, txtIdDueño, txtNombre,  txtEdad, txtRaza, txtTratamientoPrevio, txtBuscar;
    @FXML
    private RadioButton rbMacho,rbHembra;
    @FXML
    private ComboBox<Cliente> cbxClientes;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
       configurarColumnas();
        cargarTablaMascotas();
        cargarMascotasEnTextField();

        tablaMascotas.setOnMouseClicked(event -> cargarMascotaEnCampos());

        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }

    @FXML
    private void cli(ActionEvent evento) {
        if (evento.getSource() == btnRe) {
            System.out.println("Nos ");
            principal.menuPrincipal();
        }
    }
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @FXML
    private void cli() {
        principal.menuPrincipal();
    }


    public void configurarColumnas() {
        //formato de columnas
        colId.setCellValueFactory(new PropertyValueFactory<Mascotas, Integer>("idMascota"));
        colIdDueño.setCellValueFactory(new PropertyValueFactory<Mascotas, Integer>("idClienteDueño"));
        colEdad.setCellValueFactory(new PropertyValueFactory<Mascotas, String>("edad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Mascotas, String>("nombreMascota"));
        colRaza.setCellValueFactory(new PropertyValueFactory<Mascotas, String>("raza"));
        colSexo.setCellValueFactory(new PropertyValueFactory<Mascotas, String>("sexo"));
        colTratamientoPrevio.setCellValueFactory(new PropertyValueFactory<Mascotas, String>("tratamientoPrevio"));
    }

    private void cargarTablaMascotas() {
        listaMascotas = FXCollections.observableArrayList(listarMascotas());
        tablaMascotas.setItems(listaMascotas);
        if (!listaMascotas.isEmpty()) {
            tablaMascotas.getSelectionModel().selectFirst();
            cargarMascotaEnCampos();
        }
    }

    private ArrayList<Mascotas> listarMascotas() {
        ArrayList<Mascotas> mascotas = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_ListarMascotas();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                mascotas.add(new Mascotas(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar mascotas");
            e.printStackTrace();
        }
        return mascotas;
    }

    private void cargarMascotasEnTextField() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_listarClientesDueños();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(
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
            e.printStackTrace();
        }
        cbxClientes.setItems(clientes);
    }

    private void cargarMascotaEnCampos() {
        Mascotas mascota = tablaMascotas.getSelectionModel().getSelectedItem();
        if (mascota != null) {
            txtId.setText(String.valueOf(mascota.getIdMascota()));
            txtIdDueño.setText(String.valueOf(mascota.getIdClienteDueño()));
            txtNombre.setText(mascota.getNombreMascota());
            // txtSexo.setText(mascota.getSexo());
            txtEdad.setText(String.valueOf(mascota.getEdad()));
            txtRaza.setText(mascota.getRaza());
            txtTratamientoPrevio.setText(mascota.getTratamientoPrevio());

            for (Cliente c : cbxClientes.getItems()) {
                if (c.getIdClienteDueño()== mascota.getIdClienteDueño()) {
                    cbxClientes.setValue(c);
                    break;
                }
            }
            if (mascota.getSexo().equalsIgnoreCase("Macho")) {
                rbMacho.setSelected(true);
            }else{
                rbHembra.setSelected(true);
                
            }
        }
        
    }

    private Mascotas obtenerModelo() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        int idDueno = txtIdDueño.getText().isEmpty() ? 0 : Integer.parseInt(txtIdDueño.getText());
        String nombre = txtNombre.getText();
         String sexo = rbMacho.isSelected() ? "Macho" : "Hembra";
        int edad = txtEdad.getText().isEmpty() ? 0 : Integer.parseInt(txtEdad.getText());
        String raza = txtRaza.getText();
        String tratamientoPrevio = txtTratamientoPrevio.getText();
        return new Mascotas(idDueno, idDueno, nombre, raza, edad, raza, tratamientoPrevio);
    }

    private void agregarMascota() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_AgregarMascotas(?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdClienteDueño());
            cs.setString(2, modelo.getNombreMascota());
            cs.setString(3, modelo.getSexo());
            cs.setInt(4, modelo.getEdad());
            cs.setString(5, modelo.getRaza());
            cs.setString(6, modelo.getTratamientoPrevio());
            cs.executeUpdate();
            cargarTablaMascotas();
            System.out.println("Mascota agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar mascota.");
            e.printStackTrace();
        }
    }

    private void actualizarMascota() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_ActualizarMscotas(?, ?, ?, ?, ?, ?, ?);");
            cs.setInt(1, modelo.getIdMascota());
            cs.setInt(2, modelo.getIdClienteDueño());
            cs.setString(3, modelo.getNombreMascota());
            cs.setString(4, modelo.getSexo());
            cs.setInt(5, modelo.getEdad());
            cs.setString(6, modelo.getRaza());
            cs.setString(7, modelo.getTratamientoPrevio());
            cs.executeUpdate();
            cargarTablaMascotas();
            System.out.println("Mascota actualizada.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar mascota.");
            e.printStackTrace();
        }
    }

    private void eliminarMascota() {
        modelo = obtenerModelo();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("CALL sp_EliminarMascotas(?);");
            cs.setInt(1, modelo.getIdMascota());
            cs.executeUpdate();
            cargarTablaMascotas();
            System.out.println("Mascota eliminada.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar mascota.");
            e.printStackTrace();
        }
    }

    private void buscarMascotas() {
        ArrayList<Mascotas> resultado = new ArrayList<>();
        String nombre = txtBuscar.getText();
        for (Mascotas m : listaMascotas) {
            if (m.getNombreMascota().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(m);
            }
        }
        tablaMascotas.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaMascotas.getSelectionModel().selectFirst();
            cargarMascotaEnCampos();
        }
    }

    private void limpiarCampos() {
        txtId.clear();
        txtIdDueño.clear();
        txtNombre.clear();
       // txtSexo.clear();
        txtEdad.clear();
        txtRaza.clear();
        txtTratamientoPrevio.clear();
    }

    private void cambiarEstadoCampos(boolean estado) {
        txtIdDueño.setDisable(estado);
        txtNombre.setDisable(estado);
       // txtSexo.setDisable(estado);
        txtEdad.setDisable(estado);
        txtRaza.setDisable(estado);
        txtTratamientoPrevio.setDisable(estado);
        cbxClientes.setDisable(estado);
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
            agregarMascota();
        } else if (tipoAccion == Accion.EDITAR) {
            actualizarMascota();
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
        cargarTablaMascotas();
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
    }

    @FXML
    private void btnEliminar() {
        tipoAccion = Accion.ELIMINAR;
        eliminarMascota();
        tipoAccion = Accion.NINGUNA;
        cargarTablaMascotas();
    }

    @FXML
    private void btnBuscar() {
        buscarMascotas();
    }

    @FXML
    private void btnAnterior() {
        int selectedIndex = tablaMascotas.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            tablaMascotas.getSelectionModel().selectPrevious();
            cargarMascotaEnCampos();
        }
    }

    @FXML
    private void btnSiguiente() {
        int selectedIndex = tablaMascotas.getSelectionModel().getSelectedIndex();
        if (selectedIndex < tablaMascotas.getItems().size() - 1) {
            tablaMascotas.getSelectionModel().selectNext();
            cargarMascotaEnCampos();
        }
    }
}
