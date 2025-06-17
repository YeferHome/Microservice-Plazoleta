package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class OrderCanceledException extends RuntimeException {
  public OrderCanceledException() {
    super("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");
  }
}
