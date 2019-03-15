package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConsultasUsuario extends ConexionDataBase {
    

    public boolean Guardar(Usuario usuario) throws SQLException {
       
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("INSERT INTO usuario (tipoDocumento, numeroDocumento,"
                    + " nombre, apellidos) VALUES(?,?,?,?)");
            ps.setString(1, usuario.getTipoDocumento());
            ps.setString(2, String.valueOf(usuario.getNumeroDocumento()));         
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getApellidos());
            
          
            ps.executeUpdate();
            

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true;
        
    }

    public boolean modificar(Usuario usuario) throws SQLException {
        

         try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("UPDATE usuario SET tipoDocumento=?, numeroDocumento=?,"
                    + " nombre=?, apellidos=? WHERE id=?");
            ps.setString(1, usuario.getTipoDocumento());
            ps.setString(2, String.valueOf(usuario.getNumeroDocumento()));         
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getApellidos());            
            ps.setString(6, String.valueOf(usuario.getId()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true;
    }

    public boolean eliminar(Usuario usuario) throws SQLException {
       
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM usuario WHERE id=? ");
            ps.setString(1,String.valueOf(usuario.getId()));           
            ps.executeUpdate();

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true;
    }

    public boolean buscar(Usuario usuario) throws SQLException {
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM usuario WHERE numeroDocumento=?");
            ps.setString(1, String.valueOf(usuario.getNumeroDocumento()));
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario.setId(Integer.parseInt(rs.getString("id")));
                usuario.setTipoDocumento(rs.getString("tipoDocumento"));
                usuario.setNumeroDocumento(rs.getInt("numeroDocumento"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                
                            
            }

        } catch (Exception e) {
            throw  new SQLException(e);
        }
        return true; 
    }
}
