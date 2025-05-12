package retoPragma.MicroPlazoleta.domain.UseCase;

import org.springframework.data.domain.Page; //SACARLA
import org.springframework.data.domain.PageRequest; //SACARLA


import retoPragma.MicroPlazoleta.domain.api.IPedidoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.PedidoException.PedidoEnProcesoException;
import retoPragma.MicroPlazoleta.domain.exception.PedidoException.PlatoNoPerteneceARestauranteException;
import retoPragma.MicroPlazoleta.domain.exception.RestaurantException.BusinessException;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.model.PedidoItem;
import retoPragma.MicroPlazoleta.domain.spi.IPedidoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;


public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IUsuarioServicePort usuarioServicePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                         IUsuarioServicePort usuarioServicePort,
                         IRestaurantePersistencePort restaurantePersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.usuarioServicePort = usuarioServicePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    @Override
    public Pedido savePedido(Pedido pedido) {

        boolean tienePedidoActivo = pedidoPersistencePort.usuarioTienePedidoActivo(pedido.getIdCliente());

        if (tienePedidoActivo) {
            throw new PedidoEnProcesoException();
        }
//Todos los platos deben ser del mismo restaurante
        for (PedidoItem item : pedido.getItems()) {
            boolean pertenece = restaurantePersistencePort
                    .platoPerteneceARestaurante(item.getIdPlato(), pedido.getIdRestaurante());

            if (!pertenece) {
                throw new PlatoNoPerteneceARestauranteException();
            }
        }

        return pedidoPersistencePort.savePedido(pedido);
    }

    @Override
    public Page<Pedido> getPedidosPorEstados(long restauranteId, EstadoPedido estado, int page, int size) {
        if (!restaurantePersistencePort.elEmpleadoPerteneceAlRestaurante(restauranteId)) {
            throw new BusinessException("El empleado no pertenece a este restaurante.");
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return pedidoPersistencePort.findPedidosPorEstadoYRestaurante(estado, restauranteId, pageRequest);
    }

}
