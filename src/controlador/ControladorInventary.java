package controlador;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Inventary;
import modelo.InventaryDAO;
import vista.InventarioProductos;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;


//Codigo del controlador hecho en base a material audiovisual de internet

public class ControladorInventary implements ActionListener{
    
    private int codigo;
    private String nombre;
    private int precio;
    private String descripcion;
    Inventary mod = new Inventary();
    InventaryDAO modDAO = new InventaryDAO();
    InventarioProductos frm = new InventarioProductos();
    DefaultTableModel modelo = new DefaultTableModel();
    
    
    public ControladorInventary(InventarioProductos frm) {
        this.frm = frm;
        frm.setVisible(true); 
        agregarEventos();
        listarTabla();
        
    }

    private void agregarEventos(){
        this.frm.botonEditar.addActionListener(this);
        this.frm.botonIngresar.addActionListener(this);
        this.frm.botonEliminar.addActionListener(this);
        this.frm.botonLimpiar.addActionListener(this);
        this.frm.botonCerrar.addActionListener(this);
        this.frm.TablaProductos.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                llenarCampos(e);
                
            }
            
        });
 }
    private void listarTabla(){
        String[] titulos = new String[]{"Código", "Nombre", "Precio", "Descripcion"};
        modelo = new DefaultTableModel(titulos, 0);
        List<Inventary> listaProducto = modDAO.listar();
        for (Inventary inventario : listaProducto){
            modelo.addRow(new Object[]{inventario.getCodigo(), inventario.getNombre(), inventario.getPrecio(), inventario.getDescripcion()});//Array unidimensional
        }
        this.frm.TablaProductos.setModel(modelo);
        this.frm.TablaProductos.setPreferredSize(new Dimension(800, modelo.getRowCount() * 16));
    }
    
    private void llenarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        codigo = (int) frm.getTablaProductos().getModel().getValueAt(target.getSelectedRow(), 0);
        frm.name.setText(frm.TablaProductos.getModel().getValueAt(target.getSelectedRow(), 1).toString());
        frm.price.setText(frm.TablaProductos.getModel().getValueAt(target.getSelectedRow(), 2).toString());
        frm.desc.setText(frm.TablaProductos.getModel().getValueAt(target.getSelectedRow(), 3).toString());
    }
    
   private boolean validarDatos(){
    if ("".equals(frm.getname().getText()) || "".equals(frm.getPrice().getText()) || "".equals(frm.getDesc().getText())){
            JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacíos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
}
    return true; 
   }    
   
   private boolean cargarDatos(){
       try{
           nombre = frm.getname().getText();
           precio = Integer.parseInt(frm.getPrice().getText());
           descripcion = frm.getDesc().getText();
           return true;
       }catch(Exception e) {
         JOptionPane.showMessageDialog(null, "Campo precio debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);

           System.out.println("Error al cargar datos"+e);
           return false;
       }
   }
   
   private void insertarProducto(){
       try{
           if(validarDatos()){
               if(cargarDatos()){
                   Inventary producto = new Inventary(nombre, precio, descripcion);
                   modDAO.insertarDatos(producto);
                   JOptionPane.showMessageDialog(null, "Registro exitoso"); 
               }  
           }
       }catch(Exception e) {
           System.out.println("Error Insertar: "+e);
       }finally{
           listarTabla();
       }
   
}
   
    private void editarProducto() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Inventary producto = new Inventary(codigo, nombre, precio, descripcion);
                    modDAO.editar(producto);
                    JOptionPane.showMessageDialog(null, "Registro editado");

                }
            }
        } catch (HeadlessException e) {
            System.out.println("Error al editarC : " + e);
        } finally {
            listarTabla();
        }
    }

    private void eliminarProducto() {
        try {
            if (codigo != 0) {
                modDAO.eliminar(codigo);
                JOptionPane.showMessageDialog(null, "Registro eliminado");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al eliminarC : " + e);
        } finally {
            listarTabla();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource() == frm.getBotonIngresar()){
            insertarProducto();
        }
        if (ae.getSource() == frm.getBotonEditar()){
            editarProducto();
        }
        if (ae.getSource() == frm.getBotonEliminar()){
            eliminarProducto();
        }
        
    }
}