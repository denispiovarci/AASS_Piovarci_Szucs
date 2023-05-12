package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.model.Order;
import SK.AASS.TELCO.app.rest.request.OrderCreateRequest;
import SK.AASS.TELCO.app.rest.response.CreateInvoiceResponse;

import java.util.List;

/**
 * Order service interface
 */
public interface OrderService {

    /**
     * Gets list of all orders
     */
    List<Order> getAll();

    /**
     * Create order
     */
    Long create(OrderCreateRequest request);

    /**
     * Confirm order
     */
    String confirm(Long id);

    /**
     * Generate invoice and return it to the customer
     */
    CreateInvoiceResponse createInvoice(Long id);

    /**
     * Generate invoice and return it to the customer
     */
    String pay(Long id);

    /**
     * Updates warehouse disponsibility based on a paid order
     */
    String updateWarehouse(Long id);
}
