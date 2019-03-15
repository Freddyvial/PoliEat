package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConsultasProducto;
import modelo.Producto;
import vista.frmProducto;

public class CtrlProducto implements ActionListener {

    private Producto modPro;
    private ConsultasProducto modConsulPro;
    private frmProducto frmPro;

    public CtrlProducto(Producto modPro, ConsultasProducto modConsulPro, frmProducto frmPro) {
        this.modPro = modPro;
        this.modConsulPro = modConsulPro;
        this.frmPro = frmPro;
        this.frmPro.btnGuardar.addActionListener(this);
        this.frmPro.btnModificar.addActionListener(this);
        this.frmPro.btnEliminar.addActionListener(this);
        this.frmPro.btnLimpiar.addActionListener(this);
        this.frmPro.btnBuscar.addActionListener(this);
    }

    public void iniciar() {
        frmPro.setTitle("Productos");
        frmPro.setLocationRelativeTo(null);
        frmPro.txtId.setVisible(false);
        frmPro.lblId.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmPro.btnGuardar) {
            guardarProducto();
        }

        if (e.getSource() == frmPro.btnModificar) {
            modificarProducto();
        }

        if (e.getSource() == frmPro.btnEliminar) {
            eliminarProducto();
        }

        if (e.getSource() == frmPro.btnBuscar) {
            buscarProducto();
        }

        if (e.getSource() == frmPro.btnLimpiar) {
            limpiar();
        }

    }

    public void limpiar() {
        frmPro.txtId.setText(null);
        frmPro.txtNombre.setText(null);
        frmPro.txtLote.setText(null);
        frmPro.txtFechaCompra.setText(null);
        frmPro.spnCantidad.setValue(0);
        frmPro.txtPrecioCompra.setText(null);
        frmPro.txtPrecioVenta.setText(null);
        frmPro.spnSobrante.setValue(0);
        frmPro.spnDevoluciones.setValue(0);
        frmPro.txtTipoConservacion.setText(null);
    }

    private void guardarProducto() {
        modPro.setNombre(frmPro.txtNombre.getText());
        modPro.setLote(Integer.parseInt(frmPro.txtLote.getText()));
        modPro.setFechaCompra(Date.valueOf(frmPro.txtFechaCompra.getText()));
        modPro.setCantidad(frmPro.spnCantidad.getValue().hashCode());
        modPro.setPrecioCompra(Double.valueOf(frmPro.txtPrecioCompra.getText()));
        modPro.setPrecioVenta(Double.valueOf(frmPro.txtPrecioVenta.getText()));
        modPro.setSobrantes(frmPro.spnSobrante.getValue().hashCode());
        modPro.setDevoluciones(frmPro.spnDevoluciones.getValue().hashCode());
        modPro.setTipoConservacion(frmPro.txtTipoConservacion.getText());
        try {
            if (modConsulPro.guardar(modPro)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarProducto() {
        modPro.setNombre(frmPro.txtNombre.getText());
        modPro.setLote(Integer.getInteger(frmPro.txtLote.getText()));
        modPro.setFechaCompra(Date.valueOf(frmPro.txtFechaCompra.getText()));
        modPro.setCantidad(frmPro.spnCantidad.getValue().hashCode());
        modPro.setPrecioCompra(Double.valueOf(frmPro.txtPrecioCompra.getText()));
        modPro.setPrecioVenta(Double.valueOf(frmPro.txtPrecioVenta.getText()));
        modPro.setSobrantes(frmPro.spnSobrante.getValue().hashCode());
        modPro.setDevoluciones(frmPro.spnDevoluciones.getValue().hashCode());
        modPro.setTipoConservacion(frmPro.txtTipoConservacion.getText());
        modPro.setId(Integer.getInteger(frmPro.txtId.getText()));

        try {
            if (modConsulPro.modificar(modPro)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarProducto() {
        modPro.setId(Integer.parseInt(frmPro.txtId.getText()));

        try {
            if (modConsulPro.eliminar(modPro)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarProducto() {
        modPro.setNombre(frmPro.cbxBuscar.getSelectedItem().toString());
        try {
            if (modConsulPro.buscar(modPro)) {
                frmPro.txtId.setText(String.valueOf(modPro.getId()));
                frmPro.txtNombre.setText(modPro.getNombre());
                frmPro.txtLote.setText(String.valueOf(modPro.getLote()));
                frmPro.spnCantidad.setValue(modPro.getCantidad());
                frmPro.txtFechaCompra.setText(String.valueOf(modPro.getFechaCompra()));
                frmPro.txtPrecioCompra.setText(String.valueOf(modPro.getPrecioCompra()));
                frmPro.txtPrecioVenta.setText(String.valueOf(modPro.getPrecioVenta()));
                frmPro.spnSobrante.setValue(modPro.getSobrantes());
                frmPro.spnDevoluciones.setValue(modPro.getDevoluciones());
                frmPro.txtTipoConservacion.setText(modPro.getTipoConservacion());

            } else {
                JOptionPane.showMessageDialog(null, "No se encontro registro");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
