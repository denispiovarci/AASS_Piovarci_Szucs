package SK.AASS.TELCO.app.model;

import jakarta.persistence.*;

@Entity
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id;

    @Column(name = "quantity")
    private Integer quantity;

    public OrderProduct(Order order, Product product, Integer quantity) {
        id = new OrderProductId();
        id.setOrderId(order);
        id.setProductId(product);
        this.quantity = quantity;
    }
}
