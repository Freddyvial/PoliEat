package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultasFactura;
import modelo.ConsultasPedido;
import modelo.ConsultasUsuario;
import modelo.Factura;
import modelo.Pedido;
import modelo.Usuario;
import vista.frmPedido;

public class CtrlPedido implements ActionListener {

    DefaultTableModel modeloMenus = new DefaultTableModel();
    private Pedido modPedido;
    private ConsultasPedido modConsulPedido;
    private frmPedido frmPedido;
    private boolean usuarioBuscado = false;
    private Factura modFactura;
    private ConsultasFactura modConsulFactura;
    private Usuario modUsuario;

    public CtrlPedido(Pedido modPedido, ConsultasPedido modConsulPedido, frmPedido frmPedido, Factura modFactura, ConsultasFactura modConsulFactura, Usuario modUsuario) {
        this.modPedido = modPedido;
        this.modConsulPedido = modConsulPedido;
        this.frmPedido = frmPedido;
        this.frmPedido.btnGuardar.addActionListener(this);
        this.frmPedido.btnModificar.addActionListener(this);
        this.frmPedido.btnEliminar.addActionListener(this);
        this.frmPedido.btnLimpiar.addActionListener(this);
        this.frmPedido.btnBuscar.addActionListener(this);
        this.frmPedido.btnAgregarAlPedido.addActionListener(this);
        this.frmPedido.btnEliminarDelPedido.addActionListener(this);
        this.frmPedido.btnFacturarPedido.addActionListener(this);
        this.modFactura = modFactura;
        this.modConsulFactura = modConsulFactura;        
        this.frmPedido.btnFacturar.addActionListener(this);
        this.frmPedido.btnModificarFac.addActionListener(this);
        this.frmPedido.btnEliminarFac.addActionListener(this);
        this.frmPedido.btnLimpiarFac.addActionListener(this);
        this.frmPedido.btnBuscarFactura.addActionListener(this);
        this.frmPedido.btnBuscarUsuario.addActionListener(this);
        
        
    }

    public void iniciar() {
        frmPedido.setTitle("Pedidos");
        frmPedido.setLocationRelativeTo(null);
        frmPedido.txtId.setVisible(false);
        frmPedido.lblId.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmPedido.btnAgregarAlPedido) {
            try {
                agregaAlPedido();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == frmPedido.btnEliminarDelPedido) {
            eliminarDelPedido();
        }
        if (e.getSource() == frmPedido.btnFacturarPedido) {
            try {
                facturarPedido();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == frmPedido.btnGuardar) {
            try {
                guardarPedido();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == frmPedido.btnModificar) {
            try {
                modificarPedido();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == frmPedido.btnEliminar) {
            eliminarPedido();
        }

        if (e.getSource() == frmPedido.btnBuscar) {
            buscarPedido();
        }

        if (e.getSource() == frmPedido.btnLimpiar) {
            limpiar();
        }
        if (e.getSource() == frmPedido.btnFacturar) {
            try {
                facturarPedidoFac();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == frmPedido.btnModificarFac) {
            try {
                modificarFactura();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == frmPedido.btnEliminarFac) {
            eliminarFactura();
        }

        if (e.getSource() == frmPedido.btnLimpiarFac) {
            limpiar();
        }

        if (e.getSource() == frmPedido.btnBuscarUsuario) {
            try {
                buscarUsuario();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == frmPedido.btnBuscarFactura) {
            // buscarFactura();
        }

    }

    public void limpiar() {
        frmPedido.txtId.setText(null);
        frmPedido.txtClave.setText(null);
        frmPedido.cbxMenu.setSelectedIndex(0);
        frmPedido.txtValorTotal.setText(null);
        frmPedido.spnCantidadMenu.setValue(0);
        frmPedido.cbxMenu.setSelectedItem("");
        frmPedido.cbxBuscarClave.setSelectedIndex(0);
        //limpiarMenus();

    }
    

    private void guardarPedido() throws SQLException {
        modPedido.setClave(modConsulPedido.obtenerClave()+1);
        modPedido.setMenuPedido(frmPedido.cbxMenu.getSelectedItem().toString());
        modPedido.setCantidad(vectorCantidad());
        modPedido.setValorPedido(Double.valueOf(frmPedido.txtValorTotal.getText()));
        modPedido.setEstado(frmPedido.cbxEstado.getSelectedItem().toString());
        try {
            if (modConsulPedido.guardar(modPedido)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                String[] nombresMenu = vectorNombres();
                int[] idMenus = new int[nombresMenu.length];
                for (int i = 0; i < nombresMenu.length; i++) {
                    idMenus[i] = modConsulPedido.obtenerIdMenu(nombresMenu[i]);
                }
                int idPedido = modConsulPedido.obtenerIdPedido(modPedido.getClave());
                modConsulPedido.guardarMenuPedido(idMenus, idPedido);
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarPedido() throws SQLException {
        modPedido.setId(Integer.parseInt(frmPedido.txtId.getText()));
        modPedido.setClave(modConsulPedido.obtenerClave()+1);
        modPedido.setMenuPedido(frmPedido.cbxMenu.getSelectedItem().toString());
        modPedido.setCantidad(vectorCantidad());
        modPedido.setValorPedido(Double.valueOf(frmPedido.txtValorTotal.getText()));
        modPedido.setEstado(frmPedido.cbxEstado.getSelectedItem().toString());

        try {
            if (modConsulPedido.modificar(modPedido)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                String[] nombresMenu = vectorNombres();
                int[] idMenus = new int[nombresMenu.length];
                for (int i = 0; i < nombresMenu.length; i++) {
                    idMenus[i] = modConsulPedido.obtenerIdMenu(nombresMenu[i]);
                }
                modConsulPedido.modificarMenuPedido(idMenus, modPedido.getId());
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarPedido() {
        modPedido.setId(Integer.parseInt(frmPedido.txtId.getText()));

        try {
            if (modConsulPedido.eliminar(modPedido)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarPedido() {
        modPedido.setClave(Integer.parseInt(frmPedido.cbxBuscarClave.getSelectedItem().toString()));
        try {
            if (modConsulPedido.buscar(modPedido)) {
                frmPedido.txtId.setText(String.valueOf(modPedido.getId()));
                frmPedido.txtClave.setText(String.valueOf(modPedido.getClave()));
                frmPedido.txtValorTotal.setText(String.valueOf(modPedido.getValorPedido()));
                frmPedido.cbxEstado.setSelectedItem(String.valueOf(modPedido.getEstado()));

                //int idPedido=modConsulPedido.obtenerIdPedido(modPedido.getClave());
                int[] idMenus = modConsulPedido.obtenerIdMenus(modPedido.getId());
                String[] nomMenus = modConsulPedido.obtenerNombreMenus(idMenus);
                int[] cantidades = modConsulPedido.obtenerCantidades(modPedido.getCantidad());
                
            

            for (int i = 0; i < nomMenus.length; i++) {
                modPedido.setMenuPedido(nomMenus[i]);
                modPedido.setCantidad(String.valueOf(cantidades[i]));
                Double costo = modConsulPedido.obtenerCostoMenu(nomMenus[i]);
                modPedido.setCostoMenu(costo);
                frmPedido.jtablaMenus.setModel(modConsulPedido.agregarAlPedido(modPedido, modeloMenus));
            }
            int cantidadFilas = frmPedido.jtablaMenus.getRowMargin();
            frmPedido.txtValorTotal.setText(String.valueOf(modConsulPedido.valorTotalPedido(frmPedido.jtablaMenus.getModel(), cantidadFilas)));

        }else {
                JOptionPane.showMessageDialog(null, "No se encontro registro");
                limpiar();
            }
    }
    catch (SQLException ex

    
        ) {
            Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
    }

}

private void agregaAlPedido() throws SQLException {
        modPedido.setMenuPedido(frmPedido.cbxMenu.getSelectedItem().toString());
        modPedido.setCantidad(frmPedido.spnCantidadMenu.getValue().toString());
        modPedido.setCostoMenu(modConsulPedido.obtenerCostoMenu(frmPedido.cbxMenu.getSelectedItem().toString()));
        frmPedido.jtablaMenus.setModel(modConsulPedido.agregarAlPedido(modPedido, modeloMenus));
        int cantidadFilas = frmPedido.jtablaMenus.getRowMargin();
        frmPedido.txtValorTotal.setText(String.valueOf(modConsulPedido.valorTotalPedido(frmPedido.jtablaMenus.getModel(), cantidadFilas)));
    }

    private void eliminarDelPedido() {
        if (frmPedido.jtablaMenus.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Menù", "¡Vaya, ha algo a fallado!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int j = frmPedido.jtablaMenus.getSelectedRow();
            modeloMenus.removeRow(j);
        }
    }

    private String vectorCantidad() {
        String cantidad = "";
        for (int i = 0; i < frmPedido.jtablaMenus.getRowMargin(); i++) {
            cantidad = (frmPedido.jtablaMenus.getModel().getValueAt(i, 1).toString()) + cantidad;
        }

        return cantidad;
    }

    private String[] vectorNombres() {
        String[] cantidad = new String[frmPedido.jtablaMenus.getRowMargin()];
        for (int i = 0; i < frmPedido.jtablaMenus.getRowMargin(); i++) {
            cantidad[i] = (frmPedido.jtablaMenus.getModel().getValueAt(i, 0).toString());
        }

        return cantidad;
    }

    

    private void facturarPedido() throws SQLException {        
      frmPedido.jdFacturar.setLocationRelativeTo(null);
        frmPedido.txtIdFactura.setVisible(false);
        frmPedido.lblIdFactura.setVisible(false);
        frmPedido.jdFacturar.setVisible(true);
        frmPedido.jdFacturar.setTitle("FACTURAR");
        frmPedido.jdFacturar.setSize(600, 400);
        frmPedido.jTablaPedidos.setModel(frmPedido.jtablaMenus.getModel());
        frmPedido.txtClaveFactura.setText(frmPedido.txtClave.getText());
        frmPedido.txtValorFactura.setText(frmPedido.txtValorTotal.getText());
        frmPedido.txtEstadoFactura.setText(frmPedido.cbxEstado.getSelectedItem().toString());
    }
    @SuppressWarnings("null")
    private void facturarPedidoFac() throws SQLException {
        modFactura.setNombre(frmPedido.txtNombre.getText());
        modFactura.setApellidos(frmPedido.txtApellidos.getText());
        modFactura.setTipoDocumento(frmPedido.cbxTipoDocumento.getSelectedItem().toString());
        modFactura.setNumeroDocumento(Integer.parseInt(frmPedido.txtNumDocumento.getText()));
        modFactura.setValorTotal(Double.valueOf(frmPedido.txtValorFactura.getText()));
        modFactura.setEstado(frmPedido.txtEstadoFactura.getText());
        FkFacturas();

    }

    @SuppressWarnings("null")
    public void FkFacturas() throws SQLException, NumberFormatException {
        modFactura.setIdPedido(modConsulFactura.obtenerIdPedido(Integer.parseInt(frmPedido.txtClaveFactura.getText())));
        if (usuarioBuscado == true) {
            Usuario usuario = null;
            usuario.setNumeroDocumento(Integer.parseInt(frmPedido.txtNumDocumento.getText()));
            modFactura.setIdUsuario(modConsulFactura.buscarUsuario(usuario).getId());
        } else {
            Usuario usuario = null;
            usuario.setTipoDocumento(frmPedido.cbxTipoDocumento.getSelectedItem().toString());
            usuario.setNumeroDocumento(Integer.parseInt(frmPedido.txtNumDocumento.getText()));
            usuario.setNombre(frmPedido.txtNombre.getText());
            usuario.setApellidos(frmPedido.txtApellidos.getText());
            ConsultasUsuario guardar = null;
            guardar.Guardar(usuario);
            modFactura.setIdUsuario(modConsulFactura.buscarUsuario(usuario).getId());
        }
    }

    private void modificarFactura() throws SQLException {
        modFactura.setId(Integer.parseInt(frmPedido.txtId.getText()));
        modFactura.setNombre(frmPedido.txtNombre.getText());
        modFactura.setApellidos(frmPedido.txtApellidos.getText());
        modFactura.setTipoDocumento(frmPedido.cbxTipoDocumento.getSelectedItem().toString());
        modFactura.setValorTotal(Double.valueOf(frmPedido.txtValorFactura.getText()));
        modFactura.setEstado(frmPedido.txtEstadoFactura.getText());
        FkFacturas();
        try {
            if (modConsulFactura.modificar(modFactura)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarFactura() {
        modFactura.setId(Integer.parseInt(frmPedido.txtId.getText()));

        try {
            if (modConsulFactura.eliminar(modFactura)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void buscarFactura() {
//        modPedido.setClave(Integer.parseInt(frmPedido.cbxBuscarClave.getSelectedItem().toString()));
//        try {
//            if (modConsulPedido.buscar(modPedido)) {
//                frmPedido.txtId.setText(String.valueOf(modPedido.getId()));
//                frmPedido.txtClave.setText(String.valueOf(modPedido.getClave()));
//                frmPedido.txtValorTotal.setText(String.valueOf(modPedido.getValorPedido()));
//                frmPedido.cbxEstado.setSelectedItem(String.valueOf(modPedido.getEstado()));
//
//                //int idPedido=modConsulPedido.obtenerIdPedido(modPedido.getClave());
//                int[] idMenus = modConsulPedido.obtenerIdMenus(modPedido.getId());
//                String[] nomMenus = modConsulPedido.obtenerNombreMenus(idMenus);
//                int[] cantidades = modConsulPedido.obtenerCantidades(modPedido.getCantidad());
//
//                for (int i = 0; i < nomMenus.length; i++) {
//                    modPedido.setMenuPedido(nomMenus[i]);
//                    modPedido.setCantidad(String.valueOf(cantidades[i]));
//                    Double costo = modConsulPedido.obtenerCostoMenu(nomMenus[i]);
//                    modPedido.setCostoMenu(costo);
//                    frmPedido.jtablaMenus.setModel(modConsulPedido.agregarAlPedido(modPedido, modeloMenus));
//                }
//                int cantidadFilas = frmPedido.jtablaMenus.getRowMargin();
//                frmPedido.txtValorTotal.setText(String.valueOf(modConsulPedido.valorTotalPedido(frmPedido.jtablaMenus.getModel(), cantidadFilas)));
//
//            } else {
//                JOptionPane.showMessageDialog(null, "No se encontro registro");
//                limpiar();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CtrlFactura.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
   
    private void buscarUsuario() throws SQLException {
        Usuario usuarioBuscado = new Usuario();
        
        usuarioBuscado.setNumeroDocumento(Integer.parseInt(frmPedido.txtNumDocumento.getText()));
        
        usuarioBuscado=modConsulFactura.buscarUsuario(usuarioBuscado);
        frmPedido.txtNombre.setText(usuarioBuscado.getNombre());
        frmPedido.txtApellidos.setText(usuarioBuscado.getApellidos());
        frmPedido.cbxTipoDocumento.setSelectedItem(usuarioBuscado.getTipoDocumento());
        frmPedido.txtNumDocumento.setText(String.valueOf(usuarioBuscado.getNumeroDocumento()));
    }

    
    

}
