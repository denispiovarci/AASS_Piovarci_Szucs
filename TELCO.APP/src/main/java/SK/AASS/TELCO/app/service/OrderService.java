package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.model.Order;
import SK.AASS.TELCO.app.rest.request.OrderCreateRequest;

import java.util.List;

/**
 * Order service interface
 */
public interface OrderService {

    /**
     * Create order
     */
    void create(OrderCreateRequest request);

    /**
     * Gets list of all orders
     */
    List<Order> getAll();


}
