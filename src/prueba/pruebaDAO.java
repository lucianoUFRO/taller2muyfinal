
package prueba;
import usuarioDAO.userDAO;

public class pruebaDAO {
     public static void main(String[] args) {
        userDAO user = new userDAO();
        if (user.insertar("admin", "admin")) {
            System.out.println("AGREGADO CON EXITO");
        }else{
            System.out.println("NO SE PUDO INGRESAR");
        }
    }
}
