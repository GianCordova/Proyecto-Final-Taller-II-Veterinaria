package org.javiergomez.system;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.javiergomez.controller.AdopcionConejoController;
import org.javiergomez.controller.AdopcionController;
import org.javiergomez.controller.AdopcionGatoController;
import org.javiergomez.controller.AdopcionOtrosController;
import org.javiergomez.controller.AdopcionPajaroController;
import org.javiergomez.controller.AdopcionPerroController;
import org.javiergomez.controller.AnimalitosController;
import org.javiergomez.controller.CitasController;
import org.javiergomez.controller.ComprasController;
import org.javiergomez.controller.ConsultasController;
import org.javiergomez.controller.ContactoAdoptameController;
import org.javiergomez.controller.ContactoController;
import org.javiergomez.controller.FacturaCompraController;
import org.javiergomez.controller.FacturasController;
import org.javiergomez.controller.GatoController;
import org.javiergomez.controller.GestionController;
import org.javiergomez.controller.InicioSecionController;
import org.javiergomez.controller.MascotasController;
import org.javiergomez.controller.MascotassController;
import org.javiergomez.controller.MedicamentosController;
import org.javiergomez.controller.MenuPrincipalController;
import org.javiergomez.controller.PantallaInicioController;
import org.javiergomez.controller.PerroController;
import org.javiergomez.controller.PlanesController;
import org.javiergomez.controller.RecetaMedicamentoController;
import org.javiergomez.controller.RecetasController;
import org.javiergomez.controller.RegistrarseController;
import org.javiergomez.controller.ServicioController;
import org.javiergomez.controller.ServiciosController;
import org.javiergomez.controller.TratamientoMedicoController;
import org.javiergomez.controller.TratamientosController;
import org.javiergomez.controller.VacunacionesController;
import org.javiergomez.controller.VerCitasController;
import org.javiergomez.controller.VeterinariaController;
import org.javiergomez.controller.VeterinarioController;
import org.javiergomez.controller.VeterinariosController;

/**
 *
 * @author informatica
 */
public class Main extends Application {

    private static String URL = "/org/javiergomez/view/";
    private Stage escenarioPrincipal;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage escenario) throws Exception {
        this.escenarioPrincipal = escenario;

        pantallaInicio();
        escenario.setTitle("PetSync");
        escenario.show();
    }

    public FXMLLoader cambiarEscena(String fxml, double ancho, double alto) {
        FXMLLoader cargadorFXML = null;

        try {

            cargadorFXML = new FXMLLoader(getClass().getResource(URL + fxml));
            Parent archivoFXML = cargadorFXML.load();
            Scene escena = new Scene(archivoFXML, ancho, alto);
            escenarioPrincipal.setScene(escena);
        } catch (Exception ex) {
            ex.printStackTrace();
            // System.out.println("no se puede" + ex.getMessage());
        }
        return cargadorFXML;
    }

    public void inicioSecion() {
        InicioSecionController ic = cambiarEscena("InicioSecion.fxml", 900, 700).getController();
        ic.setPrincipal(this);
    }

    public void menuPrincipal() {
        MenuPrincipalController mpp = cambiarEscena("MenuPrincipal.fxml", 900, 700 ).getController();
        mpp.setPrincipal(this);
    }

    public void pantallaInicio() {
        PantallaInicioController pin = cambiarEscena("PantallaInicio.fxml", 900, 700).getController();
        pin.setPrincipal(this);
    }

    public void registrarase() {
        RegistrarseController rg = cambiarEscena("Registrarse.fxml", 900, 700).getController();
        rg.setPrincipal(this);
    }

    public void Servicios() {
        ServiciosController sc = cambiarEscena("Servicios.fxml", 900, 700).getController();
        sc.setPrincipal(this);
    }

    public void Contacto() {
        ContactoController ct = cambiarEscena("Contacto.fxml", 900, 700).getController();
        ct.setPrincipal(this);
    }

    public void Veterinaria() {
        VeterinariaController vc = cambiarEscena("Veterinaria.fxml", 900, 700).getController();
        vc.setPrincipal(this);
    }

    public void Mascotas() {
        MascotasController mc = cambiarEscena("Mascotas.fxml", 900, 700).getController();
        mc.setPrincipal(this);
    }

    public void Planes() {
        PlanesController pc = cambiarEscena("Planes.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }

    public void Gato() {
        GatoController pc = cambiarEscena("Gato.fxml", 600, 400).getController();
        pc.setPrincipal(this);
    }

    public void Perro() {
        PerroController pc = cambiarEscena("Perro.fxml", 600, 400).getController();
        pc.setPrincipal(this);
    }

    public void Mascotass() {
        MascotassController pc = cambiarEscena("Mascotass.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }

    public void Medicamentos() {
        MedicamentosController pc = cambiarEscena("Medicamentos.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }

    public void Veterinario() {
        VeterinarioController pc = cambiarEscena("Veterinario.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }

    public void Gestion() {
        GestionController pc = cambiarEscena("Gestion.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
      public void Servicio() {
        ServicioController pc = cambiarEscena("Servicio.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Animalitos() {
        AnimalitosController pc = cambiarEscena("Animalitos.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Tratamientos() {
        TratamientosController pc = cambiarEscena("Tratamientos.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Veterinarios() {
        VeterinariosController pc = cambiarEscena("Veterinarios.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Consultas() {
        ConsultasController pc = cambiarEscena("Consultas.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Recetas() {
       RecetasController pc = cambiarEscena("Recetas.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Compras() {
        ComprasController pc = cambiarEscena("Compras.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Vacunaciones() {
        VacunacionesController pc = cambiarEscena("Vacunaciones.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Citas() {
        CitasController pc = cambiarEscena("Citas.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void Facturas() {
        FacturasController pc = cambiarEscena("Facturas.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void FacturaCompra() {
        FacturaCompraController pc = cambiarEscena("FacturaCompra.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void RecetaMedicamento() {
        RecetaMedicamentoController pc = cambiarEscena("RecetaMedicamento.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
        public void TratamientoMedico() {
        TratamientoMedicoController pc = cambiarEscena("TratamientoMedico.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
       public void Adopcion() {
        AdopcionController pc = cambiarEscena("Adopcion.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
       
        public void AdopcionPerro() {
        AdopcionPerroController pc = cambiarEscena("AdopcionPerro.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
         public void AdopcionGato() {
        AdopcionGatoController pc = cambiarEscena("AdopcionGato.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
          public void AdopcionPajaro() {
        AdopcionPajaroController pc = cambiarEscena("AdopcionPajaro.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
           public void AdopcionConejo() {
        AdopcionConejoController pc = cambiarEscena("AdopcionConejo.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
            public void AdopcionOtros() {
        AdopcionOtrosController pc = cambiarEscena("AdopcionOtros.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
            public void ContactoAdoptame() {
        ContactoAdoptameController pc = cambiarEscena("ContactoAdoptame.fxml", 900, 700).getController();
        pc.setPrincipal(this);
    }
                public void VerCitas() {
        VerCitasController pc = cambiarEscena("VerCitas.fxml", 946, 566).getController();
        pc.setPrincipal(this);
    }

}
