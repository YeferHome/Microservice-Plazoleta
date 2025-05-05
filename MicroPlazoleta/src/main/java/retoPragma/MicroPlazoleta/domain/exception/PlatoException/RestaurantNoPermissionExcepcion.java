package retoPragma.MicroPlazoleta.domain.exception.PlatoException;

public class RestaurantNoPermissionExcepcion extends RuntimeException {
  public RestaurantNoPermissionExcepcion() {
    super("El restaurante asociado al plato no existe.");
  }
}
