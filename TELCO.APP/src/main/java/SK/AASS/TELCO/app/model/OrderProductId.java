package SK.AASS.TELCO.app.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Embeddable
@Setter
public class OrderProductId {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
}
