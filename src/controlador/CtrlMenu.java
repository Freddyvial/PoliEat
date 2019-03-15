package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.ConsultasMenu;
import modelo.Menu;
import vista.frmMenu;

public class CtrlMenu implements ActionListener {

    private Menu modMenu;
    private ConsultasMenu modConsulMenu;
    private frmMenu frmMenu;

    public CtrlMenu(Menu modMenu, ConsultasMenu modConsulMenu, frmMenu frmMenu) {
        this.modMenu = modMenu;
        this.modConsulMenu = modConsulMenu;
        this.frmMenu = frmMenu;
        this.frmMenu.btnGuardar.addActionListener(this);
        this.frmMenu.btnModificar.addActionListener(this);
        this.frmMenu.btnEliminar.addActionListener(this);
        this.frmMenu.btnLimpiar.addActionListener(this);
        this.frmMenu.btnBuscar.addActionListener(this);
        this.frmMenu.btnSeleccionar.addActionListener(this);

    }

    public void iniciar() {
        frmMenu.setTitle("Menú");
        frmMenu.setLocationRelativeTo(null);
        frmMenu.txtId.setVisible(false);
        frmMenu.lblId.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmMenu.btnSeleccionar) {
            seleccionarImage();
        }

        if (e.getSource() == frmMenu.btnGuardar) {
            guardarMenu();
        }

        if (e.getSource() == frmMenu.btnModificar) {
            actualizarMenu();
        }

        if (e.getSource() == frmMenu.btnEliminar) {
            eliminarMenu();
        }

        if (e.getSource() == frmMenu.btnBuscar) {
            buscarManu();
        }

        if (e.getSource() == frmMenu.btnLimpiar) {
            limpiar();
        }
    }

    public void limpiar() {
        frmMenu.txtId.setText(null);
        frmMenu.txtNombre.setText(null);
        frmMenu.txtDescripcion.setText(null);
        frmMenu.txtPrecioVenta.setText(null);

        frmMenu.cbxBuscar.setSelectedItem("");

    }

    private void seleccionarImage() {
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG)", "jpg", "jpeg");
        JFileChooser archivo = new JFileChooser();
        archivo.addChoosableFileFilter(filtro);
        archivo.setMultiSelectionEnabled(false);
        archivo.setDialogTitle("Abrir Archivo");
        byte[] arrayArchivo = null;
        int ventana = archivo.showOpenDialog(null);
        if (ventana == JFileChooser.APPROVE_OPTION) {
            File file = archivo.getSelectedFile();
            if (file != null) {
                try {
                    arrayArchivo = Files.readAllBytes(file.toPath());
                    modMenu.setFoto(arrayArchivo);
                    Image foto = new ImageIcon(modMenu.getFoto()).getImage();
                    foto = foto.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
                    frmMenu.lblMostrarFoto.setIcon(new ImageIcon(foto));
                } catch (IOException ex) {
                    Logger.getLogger(CtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void guardarMenu() {
        modMenu.setNombre(frmMenu.txtNombre.getText());
        modMenu.setDescripcion(frmMenu.txtDescripcion.getText());
        modMenu.setPrecioVenta(Double.valueOf(frmMenu.txtPrecioVenta.getText()));

        try {
            if (modConsulMenu.guardar(modMenu)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarMenu() {
        modMenu.setNombre(frmMenu.txtNombre.getText());
        modMenu.setDescripcion(frmMenu.txtDescripcion.getText());
        modMenu.setPrecioVenta(Double.valueOf((frmMenu.txtPrecioVenta.getText())));
        // modMenu.setFoto(frmMenu.txtFoto.getText());
        modMenu.setId(Integer.parseInt(frmMenu.txtId.getText()));

        try {
            if (modConsulMenu.modificar(modMenu)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarMenu() {
        modMenu.setId(Integer.parseInt(frmMenu.txtId.getText()));

        try {
            if (modConsulMenu.eliminar(modMenu)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarManu() {
        modMenu.setNombre(frmMenu.cbxBuscar.getSelectedItem().toString());

        try {
            if (modConsulMenu.buscar(modMenu)) {
                frmMenu.txtId.setText(String.valueOf(modMenu.getId()));
                frmMenu.txtNombre.setText(modMenu.getNombre());
                frmMenu.txtDescripcion.setText(modMenu.getDescripcion());
                frmMenu.txtPrecioVenta.setText(modMenu.getPrecioVenta().toString());
                //frmMenu.txtFoto.setText(modMenu.getFoto());
                Image foto = new ImageIcon(modMenu.getFoto()).getImage();
                foto = foto.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
                frmMenu.lblMostrarFoto.setIcon(new ImageIcon(foto));

            } else {
                JOptionPane.showMessageDialog(null, "No se encontro registro");
                limpiar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
