package org.javiergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.javiergomez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {

    private Main principal;
    @FXML
    private Button servicio,btnInicio, btnServicio,bntVete,btnmascota,btnplanes
            ,btnVTN,btnMT,btnMDT,btnGT,btnAnimalito,Tratamientos,Veterinarios,Consultas,Recetas,Compras,Vacunaciones,Citas,Facturas
,FacturaCompra,RecetaMedicamento,TratamientoMedico,Adopcion,opo;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void clickActionInivio(ActionEvent evento) {
        if (evento.getSource() == btnInicio) {
            System.out.println("Nos vamos a pantalla inicio");
            principal.pantallaInicio();
        } else if (evento.getSource() == btnServicio) {
            System.out.println("Nos vamos a servicio");
            principal.Servicios();
        } else if (evento.getSource() == bntVete) {
            System.out.println("Veterinaria PetSync");
            principal.Veterinaria();
        }else if (evento.getSource() == btnmascota) {
            System.out.println("Veterinaria PetSync");
            principal.Mascotas();
        }
        else if (evento.getSource() == btnplanes) {
            System.out.println("Veterinaria PetSync");
            principal.Planes();
        }else if (evento.getSource() == btnVTN) {
            System.out.println("Veterinarinario ");
            principal.Veterinario();
        }else if (evento.getSource() == btnMT) {
            System.out.println("Mascotas ");
            principal.Mascotass();
        }else if (evento.getSource() == btnMDT) {
            System.out.println("Medicamento ");
            principal.Medicamentos();
        }else if (evento.getSource() == btnGT) {
            System.out.println("Gestion ");
            principal.Gestion();
        }else if (evento.getSource() == servicio) {
            System.out.println("Gestion ");
            principal.Servicio();
    }else if (evento.getSource() == btnAnimalito) {
            System.out.println("animal ");
            principal.Animalitos();         
    }else if (evento.getSource() == Tratamientos) {
            System.out.println("animal ");
            principal.Tratamientos();
    }else if (evento.getSource() == Veterinarios) {
            System.out.println("animal ");
            principal.Veterinarios();
    }else if (evento.getSource() == Consultas) {
            System.out.println("animal ");
            principal.Consultas();
    }else if (evento.getSource() == Recetas) {
            System.out.println("animal ");
            principal.Recetas();
    }else if (evento.getSource() == Compras) {
            System.out.println("animal ");
            principal.Compras();
            
    }else if (evento.getSource() == Vacunaciones) {
            System.out.println("animal ");
            principal.Vacunaciones();
    }else if (evento.getSource() == Citas) {
            System.out.println("animal ");
            principal.Citas();
    }else if (evento.getSource() == Facturas) {
            System.out.println("animal ");
            principal.Facturas();
    }else if (evento.getSource() == FacturaCompra) {
            System.out.println("animal ");
            principal.FacturaCompra();
    }else if (evento.getSource() == RecetaMedicamento) {
            System.out.println("animal ");
            principal.RecetaMedicamento();
    }else if (evento.getSource() == TratamientoMedico) {
            System.out.println("animal ");
            principal.TratamientoMedico();
    }else if (evento.getSource() == Adopcion) {
            System.out.println("animal ");
            principal.Adopcion();
    }else if (evento.getSource() == opo) {
            System.out.println("animal ");
            principal.MenuPrincipalAdministrador();
    }
}
}
