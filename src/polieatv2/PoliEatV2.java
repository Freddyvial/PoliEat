/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polieatv2;

import controlador.CtrlMenu;
import controlador.CtrlPedido;
import controlador.CtrlProducto;
import controlador.CtrlUsuario;
import java.sql.SQLException;
import modelo.ConsultasFactura;
import modelo.ConsultasMenu;
import modelo.ConsultasPedido;
import modelo.ConsultasProducto;
import modelo.ConsultasUsuario;
import modelo.Factura;
import modelo.Menu;
import modelo.Pedido;
import modelo.Producto;
import modelo.Usuario;
import vista.frmMenu;
import vista.frmPedido;
import vista.frmProducto;
import vista.frmUsuario;

/**
 *
 * @author ASUS
 */
public class PoliEatV2 {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     *
     */
    public static void main(String[] args) throws SQLException {

//        Producto modPro = new Producto();
//        ConsultasProducto modConsulPro = new ConsultasProducto();
//        frmProducto frmPro = new frmProducto();
//        
//        CtrlProducto ctrl = new CtrlProducto(modPro, modConsulPro, frmPro);
//        
//        ctrl.iniciar();
//        frmPro.setVisible(true);
//        Usuario modUsu = new Usuario();
//        ConsultasUsuario modConsulUsu = new ConsultasUsuario();
//        frmUsuario frmUsu = new frmUsuario();
//
//        CtrlUsuario ctrl = new CtrlUsuario(modUsu, modConsulUsu, frmUsu);
//
//        ctrl.iniciar();
//        frmUsu.setVisible(true);
//        Menu modMenu = new Menu();
//        ConsultasMenu modConsulMenu = new ConsultasMenu();
//        frmMenu frmMenu= new frmMenu();
//
//        CtrlMenu ctrl = new CtrlMenu(modMenu, modConsulMenu, frmMenu);
//
//        ctrl.iniciar();
//        frmMenu.setVisible(true);
        Pedido modPedido = new Pedido();
        ConsultasPedido modConsulPedido = new ConsultasPedido();
        frmPedido frmPedido = new frmPedido();
        Factura modFactura = new Factura();
        ConsultasFactura modConsulFactura = new ConsultasFactura();
        Usuario modUsuario = new Usuario();

        CtrlPedido ctrl = new CtrlPedido(modPedido, modConsulPedido, frmPedido, modFactura, modConsulFactura, modUsuario);

        ctrl.iniciar();
        frmPedido.setVisible(true);
    }

}
