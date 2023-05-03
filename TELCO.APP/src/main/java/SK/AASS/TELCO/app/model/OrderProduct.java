package SK.AASS.TELCO.app.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ORDER_PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProduct {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long orderProductId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;
}
