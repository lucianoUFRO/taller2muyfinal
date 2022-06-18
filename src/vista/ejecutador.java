
package vista;


import controlador.ControladorInventary;
import modelo.Inventary;
import modelo.InventaryDAO;
import ventanaLogin.ventana2;

/**
 *
 * @author Usuario
 */
public class ejecutador {
    public static void main(String[] args) {
        
    Inventary mod = new Inventary();
    InventaryDAO modDAO = new InventaryDAO();
    InventarioProductos frm = new InventarioProductos();
    ControladorInventary crt = new ControladorInventary(frm);
    ventana2 ven= new ventana2();
    }
}
