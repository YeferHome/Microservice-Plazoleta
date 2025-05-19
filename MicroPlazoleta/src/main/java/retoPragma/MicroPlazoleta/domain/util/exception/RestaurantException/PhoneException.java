package retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException;

public class PhoneException extends RuntimeException {
  public PhoneException() {
    super("Teléfono inválido; máximo 13 dígitos y debe iniciar con '+57'.");
  }
}
