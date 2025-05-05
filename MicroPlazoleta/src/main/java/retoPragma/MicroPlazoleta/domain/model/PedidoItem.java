package retoPragma.MicroPlazoleta.domain.model;


public class PedidoItem {
    private Long idPedidoItem;
    private Long idPlato;
    private Long idRestaurante;
    private int cantidad;

    public PedidoItem() {
    }

    public PedidoItem(Long idPedidoItem, Long idPlato, Long idRestaurante, int cantidad) {
        this.idPedidoItem = idPedidoItem;
        this.idPlato = idPlato;
        this.idRestaurante = idRestaurante;
        this.cantidad = cantidad;
    }

    public Long getIdPedidoItem() {
        return idPedidoItem;
    }

    public void setIdPedidoItem(Long idPedidoItem) {
        this.idPedidoItem = idPedidoItem;
    }

    public Long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Long idPlato) {
        this.idPlato = idPlato;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
