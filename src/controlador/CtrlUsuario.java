package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConsultasUsuario;
import modelo.Usuario;
import vista.frmUsuario;

public class CtrlUsuario implements ActionListener {

    private Usuario modUsuario;
    private ConsultasUsuario modConsulUsuario;
    private frmUsuario frmUsuario;

    public CtrlUsuario(Usuario modUsuario, ConsultasUsuario modConsulUsuario, frmUsuario frmUsuario) {
        this.modUsuario = modUsuario;
        this.modConsulUsuario = modConsulUsuario;
        this.frmUsuario = frmUsuario;
        this.frmUsuario.btnGuardar.addActionListener(this);
        this.frmUsuario.btnEliminar.addActionListener(this);
        this.frmUsuario.btnModificar.addActionListener(this);
        this.frmUsuario.btnLimpiar.addActionListener(this);
        this.frmUsuario.btnBuscar.addActionListener(this);
    }

    public void iniciar() {
        frmUsuario.setTitle("Usuarios");
        frmUsuario.setLocationRelativeTo(null);
        frmUsuario.txtId.setVisible(false);
        frmUsuario.lblId.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmUsuario.btnGuardar) {
            guradarUsuario();
        }

        if (e.getSource() == frmUsuario.btnModificar) {
            modificarUsuario();
        }

        if (e.getSource() == frmUsuario.btnEliminar) {
            eliminarUsuario();
        }

        if (e.getSource() == frmUsuario.btnBuscar) {
            buscarUsuario();
        }

        if (e.getSource() == frmUsuario.btnLimpiar) {
            limpiar();
        }

    }

    public void limpiar() {
        frmUsuario.txtId.setText(null);
        frmUsuario.cbxTipoDocumento.setSelectedItem("");
        frmUsuario.txtNumeroDocumento.setText(null);
        frmUsuario.txtNombre.setText(null);
        frmUsuario.txtApellidos.setText(null);
        

    }

    private void guradarUsuario() {
        modUsuario.setTipoDocumento(frmUsuario.cbxTipoDocumento.getSelectedItem().toString());
        modUsuario.setNumeroDocumento(Integer.parseInt(frmUsuario.txtNumeroDocumento.getText()));
        modUsuario.setNombre(frmUsuario.txtNombre.getText());
        modUsuario.setApellidos(frmUsuario.txtApellidos.getText());
        
        try {
            if (modConsulUsuario.Guardar(modUsuario)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarUsuario() {
        modUsuario.setTipoDocumento(frmUsuario.cbxTipoDocumento.getSelectedItem().toString());
        modUsuario.setNumeroDocumento(Integer.parseInt(frmUsuario.txtNumeroDocumento.getText()));
        modUsuario.setNombre(frmUsuario.txtNombre.getText());
        modUsuario.setApellidos(frmUsuario.txtApellidos.getText());
      
        modUsuario.setId(Integer.parseInt(frmUsuario.txtId.getText()));

        try {
            if (modConsulUsuario.modificar(modUsuario)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarUsuario() {
        modUsuario.setId(Integer.parseInt(frmUsuario.txtId.getText()));

        try {
            if (modConsulUsuario.eliminar(modUsuario)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarUsuario() {
        modUsuario.setNumeroDocumento(Integer.parseInt(frmUsuario.cbxBuscarUsuario.getSelectedItem().toString()));

        try {
            if (modConsulUsuario.buscar(modUsuario)) {
                frmUsuario.txtId.setText(String.valueOf(modUsuario.getId()));
                frmUsuario.cbxTipoDocumento.setSelectedItem(modUsuario.getTipoDocumento());
                frmUsuario.txtNumeroDocumento.setText(String.valueOf(modUsuario.getNumeroDocumento()));
                frmUsuario.txtNombre.setText(modUsuario.getNombre());
                frmUsuario.txtApellidos.setText(modUsuario.getApellidos());
               

            } else {
                JOptionPane.showMessageDialog(null, "No se encontro registro");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
