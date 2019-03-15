package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ConsultasProducto extends ConexionDataBase {
    
    Vector productosRecetas =new Vector();
    public boolean guardar(Producto pro) throws SQLException {
       
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("INSERT INTO producto (nombre, lote,"
                    + " fechaCompra, cantidad, precioCompra,precioVenta,sobrantes,devoluciones,tipoConservacion) VALUES(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, pro.getNombre());
            ps.setString(2, String.valueOf(pro.getLote()));
            ps.setDate(3, pro.getFechaCompra());
            ps.setString(4, String.valueOf(pro.getCantidad()));
            ps.setString(5, String.valueOf(pro.getPrecioCompra()));
            ps.setString(6, String.valueOf(pro.getPrecioVenta()));
            ps.setString(7, String.valueOf(pro.getSobrantes()));
            ps.setString(8, String.valueOf(pro.getDevoluciones()));
            ps.setString(9, pro.getTipoConservacion());
            ps.executeUpdate();

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true;
        
    }

    public boolean modificar(Producto pro) throws SQLException {
        

         try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("UPDATE producto SET nombre=?, lote=?,fechaCompra=?, cantidad=?, precioCompra=?,precioVenta=?,sobrantes,devoluciones=?,tipoConservacion=? WHERE id=?");
            ps.setString(1, pro.getNombre());
            ps.setString(2, String.valueOf(pro.getLote()));
            ps.setDate(3, pro.getFechaCompra());
            ps.setString(4, String.valueOf(pro.getCantidad()));
            ps.setString(5, String.valueOf(pro.getPrecioCompra()));
            ps.setString(6, String.valueOf(pro.getPrecioVenta()));
            ps.setString(7, String.valueOf(pro.getSobrantes()));
            ps.setString(8, String.valueOf(pro.getDevoluciones()));
            ps.setString(9, pro.getTipoConservacion());
            ps.setString(10,String.valueOf(pro.getId()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true;
    }

    public boolean eliminar(Producto pro) throws SQLException {
       
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM producto WHERE id=? ");
            ps.setString(1,String.valueOf(pro.getId()));           
            ps.executeUpdate();

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true;
    }

    public boolean buscar(Producto pro) throws SQLException {
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM producto WHERE nombre=?");
            ps.setString(1, pro.getNombre());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                pro.setId(Integer.parseInt(rs.getString("id")));
                pro.setNombre(rs.getString("nombre"));
                pro.setLote(rs.getInt("lote"));
                pro.setFechaCompra(rs.getDate("fechaCompra"));
                pro.setCantidad(rs.getInt("cantidad"));
               pro.setPrecioCompra(rs.getDouble("precioCompra"));
                pro.setPrecioVenta(rs.getDouble("precioVenta"));
                pro.setSobrantes(rs.getInt("sobrantes"));
                pro.setDevoluciones(rs.getInt("devoluciones"));
                pro.setTipoConservacion(rs.getString("tipoConservacion"));
                
            }

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true; 
    }
}
