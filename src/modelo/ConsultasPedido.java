package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ConsultasPedido extends ConexionDataBase {

    public boolean guardar(Pedido pedido) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("INSERT INTO pedido (clave,cantidad,valorTotal,estado) VALUES(?,?,?,?)");
            ps.setString(1, String.valueOf(pedido.getClave()));
            ps.setString(2, String.valueOf(pedido.getCantidad()));
            ps.setString(3, String.valueOf(pedido.getValorPedido()));
            ps.setString(4, pedido.getEstado());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;

    }

    public boolean modificar(Pedido pedido) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("UPDATE pedido SET clave=?,cantidad=?,valorTotal=?,estado=? WHERE id=?");
            ps.setString(1, String.valueOf(pedido.getClave()));
            ps.setString(2, pedido.getCantidad());
            ps.setString(3, String.valueOf(pedido.getValorPedido()));
            ps.setString(4, pedido.getEstado());
            ps.setString(5, String.valueOf(pedido.getId()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;
    }

    public boolean eliminar(Pedido pedido) throws SQLException {

        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM pedido WHERE id=? ");
            ps.setString(1, String.valueOf(pedido.getId()));
            ps.executeUpdate();

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return true;
    }

    public boolean buscar(Pedido pedido) throws SQLException {
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM pedido WHERE clave=?");
            ps.setString(1, String.valueOf(pedido.getClave()));
            rs = ps.executeQuery();

            if (rs.next()) {
                pedido.setId(Integer.parseInt(rs.getString("id")));
                pedido.setClave(rs.getInt("clave"));
                pedido.setCantidad(rs.getString("cantidad"));
                pedido.setValorPedido(rs.getDouble("valorTotal"));
                pedido.setEstado(rs.getString("estado"));

            }

        } catch (Exception e) {
            throw new SQLException(e);
        }

        return true;
    }
    public int obtenerClave() throws SQLException {
        int id=0;
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("select id from pedido order by  id desc limit 1");
            
            rs = ps.executeQuery();

            if (rs.next()) {
              id=rs.getInt("id");
                
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        
        
        return id;
    
    }
    public Double valorTotalPedido(TableModel modeloMenus,int cantidadFilas){
        Double valorTotal = 0.0;
        for (int i = 0; i < cantidadFilas; i++) {
            Double costoMenu =Double.parseDouble(modeloMenus.getValueAt(i, 2).toString());
            Double cantidad =Double.parseDouble(modeloMenus.getValueAt(i, 1).toString());
            valorTotal +=costoMenu*cantidad;
            
        }
        
        
        
        return valorTotal;
    }
    public DefaultTableModel agregarAlPedido(Pedido pedido,DefaultTableModel modeloMenus) {       
  
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

    public int obtenerIdMenu(String nombre) throws SQLException {
        int idMenu = 0;
        try (Connection con = ConexionDataBase.getConection();) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = (PreparedStatement) con.prepareStatement("SELECT id FROM menu WHERE nombre=?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                idMenu = rs.getInt("id");
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
        return idMenu;
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

}
