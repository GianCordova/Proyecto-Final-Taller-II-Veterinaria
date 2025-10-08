
package org.javiergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import org.javiergomez.system.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * FXML Controller class
 */
public class InicioSecionController implements Initializable {

    private Main principal;

    @FXML private Button btnRegresar, btnIngresar, btnCrear;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí puedes inicializar cosas si es necesario
    }

    @FXML
    private void clickActionHandler(ActionEvent evento) {
        if (evento.getSource() == btnRegresar) {
            System.out.println("Regresando al inicio");
            principal.pantallaInicio();

        } else if (evento.getSource() == btnCrear) {
            System.out.println("Crear cuenta");
            principal.registrarase();

        } else if (evento.getSource() == btnIngresar) {
            // Validar correo y contraseña
            String correo = txtCorreo.getText();
            String contrasena = txtContrasena.getText();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                System.out.println("Correo o contraseña vacíos");
                return;
            }

            String rol = verificarRol(correo, contrasena);

            if (rol == null) {
                System.out.println("Credenciales incorrectas");
            } else if (rol.equals("admin")) {
                System.out.println("Bienvenido Administrador");
                principal.MenuPrincipalAdministrador(); // Asegúrate de que exista este método en Main.java
            } else if (rol.equals("cliente")) {
                System.out.println("Bienvenido Cliente");
                principal.menuPrincipal(); // Este sería el menú para el cliente
            } else {
                System.out.println("Rol desconocido");
            }
        }
    }

    // Método para verificar el rol en la base de datos
    private String verificarRol(String correo, String contrasena) {
        String rol = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Reemplaza con tu usuario y contraseña de la base de datos
            conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/veterinariadb", "quintom", "admin"
            );

            String sql = "SELECT rol FROM Usuarios WHERE correo = ? AND contraseña = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            rs = stmt.executeQuery();

            if (rs.next()) {
                rol = rs.getString("rol");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rol;
    }
}