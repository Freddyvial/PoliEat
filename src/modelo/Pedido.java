package modelo;

public class Pedido {

    private int id;
    private int clave;
    private String menuPedido;
    private String cantidad;
    private Double costoMenu;
    private Double valorPedido;
    private String estado;
     


    


    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuPedido() {
        return menuPedido;
    }

    public void setMenuPedido(String menuPedido) {
        this.menuPedido = menuPedido;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCostoMenu() {
        return costoMenu;
    }

    public void setCostoMenu(Double costoMenu) {
        this.costoMenu = costoMenu;
    }

    public Double getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(Double valorPedido) {
        this.valorPedido = valorPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
