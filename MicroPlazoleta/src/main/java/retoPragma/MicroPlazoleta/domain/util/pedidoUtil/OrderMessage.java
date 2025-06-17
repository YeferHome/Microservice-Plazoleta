package retoPragma.MicroPlazoleta.domain.util.pedidoUtil;

public class OrderMessage {
    public static String createReadyMessage(String pin) {
        return "Tu pedido está listo. Código de verificación: " + pin;
    }
}
