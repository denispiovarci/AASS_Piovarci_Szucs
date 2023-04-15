package SK.AASS.TELCO.app.model;

import SK.AASS.TELCO.app.config.OrderStatus;
import SK.AASS.TELCO.app.config.ProductAccessibility;
import SK.AASS.TELCO.app.config.ProductType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private Date created;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "products")
    @OneToMany
    private List<OrderProduct> productsList;

    @Column(name = "totalPrice")
    private Double totalPrice;


}
