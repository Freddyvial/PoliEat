package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ConsultasFactura extends ConexionDataBase {

    public boolean guardar(Factura factura) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("INSERT INTO pedido (nombre,apellidos,tipoDocumento,numeroDocumento,valorTotal,estado,idPedido,idUsuario) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, factura.getNombre());
            ps.setString(2, factura.getApellidos());
            ps.setString(3, factura.getTipoDocumento());
            ps.setString(4, String.valueOf(factura.getNumeroDocumento()));
            ps.setString(5, String.valueOf(factura.getValorTotal()));
            ps.setString(6, factura.getEstado());
            ps.setString(7, String.valueOf(factura.getIdPedido()));
            ps.setString(8, String.valueOf(factura.getIdUsuario()));
           
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;

    }

    public boolean modificar(Factura factura) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("UPDATE pedido SET nombre=?,apellidos=?,tipoDocumento=?,numeroDocumento=?,valorTotal=?,estado=?,idPedido=?,idUsuario=? WHERE id=?");
            ps.setString(1, factura.getNombre());
            ps.setString(2, factura.getApellidos());
            ps.setString(3, factura.getTipoDocumento());
            ps.setString(4, String.valueOf(factura.getNumeroDocumento()));
            ps.setString(5, String.valueOf(factura.getValorTotal()));
            ps.setString(6, factura.getEstado());
            ps.setString(7, String.valueOf(factura.getIdPedido()));
            ps.setString(8, String.valueOf(factura.getIdUsuario()));
            ps.setString(9, String.valueOf(factura.getId()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;
    }

    public boolean eliminar(Factura factura) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM factura WHERE id=? ");
            ps.setString(1, String.valueOf(factura.getId()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;
    }

    public boolean buscar(Factura factura) throws SQLException {
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM factura WHERE id=?");
            ps.setString(1, String.valueOf(factura.getId()));
            rs = ps.executeQuery();

            if (rs.next()) {
                factura.setId(Integer.parseInt(rs.getString("id")));
                factura.setNombre(rs.getString("nombre"));
                factura.setApellidos(rs.getString("apellidos"));
                factura.setTipoDocumento(rs.getString("tipoDocumento"));
                factura.setNumeroDocumento(rs.getInt("numeroDocumento"));
                factura.setValorTotal(rs.getDouble("valorTotal"));
                factura.setEstado(rs.getString("estado"));
                factura.setIdPedido(rs.getInt("idPedido"));
                factura.setIdUsuario(rs.getInt("idUsuario"));
                

            }

        } catch (Exception e) {
            throw new SQLException(e);
        }

        return true;
    }
//    public Double valorTotalPedido(TableModel modeloMenus,int cantidadFilas){
//        Double valorTotal = 0.0;
//        for (int i = 0; i < cantidadFilas; i++) {
//            Double costoMenu =Double.parseDouble(modeloMenus.getValueAt(i, 2).toString());
//            Double cantidad =Double.parseDouble(modeloMenus.getValueAt(i, 1).toString());
//            valorTotal +=costoMenu*cantidad;
//            
//        }
//        
//        
//        
//        return valorTotal;
//    }
    public DefaultTableModel mostrarPedido(Pedido pedido,DefaultTableModel modeloMenus) {       
  
        while (modeloMenus.getColumnCount() == 0) {
           modeloMenus.addColumn("Menu");
        modeloMenus.addColumn("Cantidad");
        modeloMenus.addColumn("Valor Menú");
        }
        

        Object[] filas = new Object[3];
        filas[0] = pedido.getMenuPedido();
        filas[1] = pedido.getCantidad();
        filas[2] = pedido.getCostoMenu();

        modeloMenus.addRow(filas);
        return modeloMenus;

    }

    

    public Double obtenerCostoMenu(String nombre) throws SQLException {
        Double costoMenu = 0.0;
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT precioVenta FROM menu WHERE nombre=?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                costoMenu = rs.getDouble("precioVenta");
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return costoMenu;
    }

    public int obtenerIdPedido(int clave) throws SQLException {
        int idProducto = 0;
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT id FROM pedido WHERE clave=?");
            ps.setString(1, String.valueOf(clave));
            rs = ps.executeQuery();
            while (rs.next()) {
                idProducto = rs.getInt("id");
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return idProducto;
    }

    public int[] obtenerIdMenus(int idPedido) throws SQLException {
        int[] idMenus;
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT idMenu FROM menupedido WHERE idPedido=?");
            ps.setString(1, String.valueOf(idPedido));
            rs = ps.executeQuery();
            idMenus = new int[rs.getMetaData().getColumnCount()];
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    idMenus[i] = rs.getInt("idMenu");
                }

            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return idMenus;
    }

    public String[] obtenerNombreMenus(int[] idMenus) throws SQLException {
        String[] nombreMenus = new String[idMenus.length];
        try (Connection con = ConexionDataBase.getConection();) {
            for (int i = 0; i < idMenus.length; i++) {
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = (PreparedStatement) con.prepareStatement("SELECT nombre FROM menu WHERE id=?");
                ps.setString(1, String.valueOf(idMenus[i]));
                rs = ps.executeQuery();                
                while (rs.next()) {
                    nombreMenus[i]=rs.getString("nombre");

                }
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return nombreMenus;
    }

    public boolean guardarMenuPedido(int[] idMenu, int idPedido) throws SQLException {
        try (Connection con = ConexionDataBase.getConection();) {
            for (int i = 0; i < idMenu.length; i++) {
                PreparedStatement ps;
                ps = (PreparedStatement) con.prepareStatement("INSERT INTO menupedido (idMenu,idPedido) VALUES(?,?)");
                ps.setString(1, String.valueOf(idMenu[i]));
                ps.setString(2, String.valueOf(idPedido));
                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;

    }
    public int[] obtenerCantidades(String cantidades){    
    char[] stringToCharArray = cantidades.toCharArray();
     int[] vectorCantidad = new int[(stringToCharArray.length)];
    for(int i=0; i< (stringToCharArray.length); i++){
        vectorCantidad[i] = Character.getNumericValue(stringToCharArray[(i)]);
       
        
    }
    return vectorCantidad;
}
    
    public boolean modificarMenuPedido(int[] idMenu, int idPedido) throws SQLException {
        try (Connection con = ConexionDataBase.getConection();) {
            for (int i = 0; i < idMenu.length; i++) {
                PreparedStatement ps;
                ps = (PreparedStatement) con.prepareStatement("UPDATE menupedido SET idMenu=? WHERE idPedido=?");
                ps.setString(1, String.valueOf(idMenu[i]));
                ps.setString(2, String.valueOf(idPedido));
                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;

    }
    
    public Usuario buscarUsuario(Usuario usuario) throws SQLException {
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
        return usuario;
    }

}
