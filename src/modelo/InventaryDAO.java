package modelo;

import Ejecutador.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;

public class InventaryDAO {
    //Conexión y ejecución de query sql
    Conexion conexion = new Conexion();
    private static Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    //Read
    public List listar() {
        String sql = "SELECT * FROM producto"; 
        List producto = new ArrayList(); 
        try {
            con = Conexion.iniciarConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Inventary p = new Inventary();
                p.setCodigo(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setPrecio(rs.getInt(3));
                p.setDescripcion(rs.getString(4));
                producto.add(p);                
            }
            
        } catch (Exception e) {
            System.out.println("Error SQL"+e);
        }
        return producto; 
    }
    
    //Create
    public void insertarDatos(Inventary producto) {
        String sql = "insert into producto(nombre, precio, descripcion) values(?, ?,?)";
        try {
            con = Conexion.iniciarConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.executeUpdate();           
        } catch (SQLException e) {
            System.out.println("Error SQL Agregar: " + e); 
        }
    }
    
    //Delete
    public void eliminar(int id) {
        String sql = "delete from producto where codigo="+id;
        try {
            con = Conexion.iniciarConexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminarDAO: "+e);
        }
    }
    
    /*
    Metodo para eliminar por consola, en este caso no nos sirve
    public boolean eliminarDatos(String codigo) throws java.sql.SQLException{
        boolean resp = false;
        try {
                con = conex_inv.iniciarConexion();
                statement = con.createStatement();
                String sql = "DELETE FROM producto WHERE codigo='"  + codigo + "'";
                statement.executeUpdate(sql);
                resp = true;
                statement.close(); 
                
        } catch (SQLException e){
            System.out.println("Error SQL");
        }
     return resp;     
                
    } 
    */
    
    //Update    
    public void editar(Inventary producto){
        String sql = "update producto set Nombre=?, Precio=?, Descripcion=? where Codigo=?";
        try {
            con = Conexion.iniciarConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error al EditarDAO: "+e);
        }
    } 

    /* 
    METODO PARA EDITAR POR CONSOLA
    public boolean actualizarDatos(String nuevoCodigo, String nuevoNombre, String nuevoPrecio, String nuevaDescripcion, String codigoActual){
        boolean resp = false;
        try{
            con = conex_inv.iniciarConexion();
            statement = con.createStatement();
            String sql = "UPDATE producto "
                    + " SET codigo = '" + nuevoCodigo + "', nombre = '" + nuevoNombre + "', precio ='" + nuevoPrecio + "', descripcion ='" + nuevaDescripcion
                    + "' WHERE codigo='" + codigoActual + "'";
            statement.executeUpdate(sql);
            resp = true;
            statement.close();
        } catch (SQLException e){
            System.out.println("Error SQL");
      
        }
        return resp;
    }
    */
}