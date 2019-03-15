package modelo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasMenu extends ConexionDataBase {

    public boolean guardar(Menu menu) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("INSERT INTO menu(nombre,descripcion,precioVenta,foto) VALUES(?,?,?,?)");
            ps.setString(1, menu.getNombre());
            ps.setString(2, menu.getDescripcion());
            ps.setString(3, String.valueOf(menu.getPrecioVenta()));   

            ps.setBinaryStream(4,getInputStreamImage(menu.getFoto()));
            
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;

    }
    
    private InputStream getInputStreamImage(byte[] image){
         return new ByteArrayInputStream(image);
    }

    public boolean modificar(Menu menu) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("UPDATE menu SET nombre=?,descripcion=?,precioVenta=?,foto=? WHERE id=?");
            ps.setString(1, menu.getNombre());
            ps.setString(2, menu.getDescripcion());
            ps.setString(3, String.valueOf(menu.getPrecioVenta()));
            ps.setBinaryStream(4,getInputStreamImage(menu.getFoto()));
            ps.setString(5, String.valueOf(menu.getId()));

            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;
    }

    public boolean eliminar(Menu menu) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM menu WHERE id=? ");
            ps.setString(1, String.valueOf(menu.getId()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;
    }

    public boolean buscar(Menu menu) throws SQLException {
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM menu WHERE nombre=?");
            ps.setString(1, menu.getNombre());
            rs = ps.executeQuery();

            if (rs.next()) {
                menu.setId(Integer.parseInt(rs.getString("id")));
                menu.setNombre(rs.getString("nombre"));
                menu.setDescripcion(rs.getString("descripcion"));
                menu.setPrecioVenta(rs.getDouble("precioVenta"));
                menu.setFoto(rs.getBytes("foto"));
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;
    }
}
