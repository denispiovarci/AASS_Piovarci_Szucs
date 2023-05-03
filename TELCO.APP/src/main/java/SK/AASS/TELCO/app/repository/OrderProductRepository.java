package SK.AASS.TELCO.app.repository;

import SK.AASS.TELCO.app.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    //    @Query(value = "SELECT o FROM OrderProduct o WHERE o.order = ?1 AND o.product = ?2")
    @Query(value = "SELECT op FROM OrderProduct op JOIN op.order o WHERE o.orderId = ?1 AND op.product.productId = ?2")
    OrderProduct getRow(Long orderId, Long productId);


}
