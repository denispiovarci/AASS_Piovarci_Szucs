package SK.AASS.TELCO.app.model;

import SK.AASS.TELCO.app.config.ProductAccessibility;
import SK.AASS.TELCO.app.config.ProductType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "name")
    private String name;

    @Column(name = "accessibility")
    @Enumerated(EnumType.STRING)
    private ProductAccessibility accessibility;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Double price;

}
