/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class Producto {
    private int id;
 
    private String nombre;
    private int lote;
    private Date fechaCompra;
    private int cantidad;
    private Double precioCompra;
    private Double precioVenta;
    private int sobrantes;
    private int devoluciones;
    private String tipoConservacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getSobrantes() {
        return sobrantes;
    }

    public void setSobrantes(int sobrantes) {
        this.sobrantes = sobrantes;
    }

    public int getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(int devoluciones) {
        this.devoluciones = devoluciones;
    }

    public String getTipoConservacion() {
        return tipoConservacion;
    }

    public void setTipoConservacion(String tipoConservacion) {
        this.tipoConservacion = tipoConservacion;
    }
    
    
}
