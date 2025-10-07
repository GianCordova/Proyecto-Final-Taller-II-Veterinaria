
package org.javiergomez.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author jgome
 */
public class Conexion {
     private static Conexion instancia;
    private Connection conexion;
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/veterinariadb?useSSL=false";
    private static final String USER = "quintom";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public void Conexion() {
        conectar();
    }

    public void conectar() {
        try {
            Class.forName(DRIVER).newInstance();
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("conexion realizada con exito.");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | SQLException ex) {
            System.out.println("error al conectar!");
            ex.printStackTrace();
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conectar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }
}

