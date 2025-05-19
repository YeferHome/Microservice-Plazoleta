package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class RestaurantNoPermissionExcepcion extends RuntimeException {
  public RestaurantNoPermissionExcepcion() {
    super("El restaurante asociado al plato no existe.");
  }
}
