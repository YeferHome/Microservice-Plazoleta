package retoPragma.MicroPlazoleta.domain.exception.RestaurantException;

public class DocumentException extends RuntimeException {
    public DocumentException() {
        super("Documento de identidad debe ser un número positivo.");
    }
}
