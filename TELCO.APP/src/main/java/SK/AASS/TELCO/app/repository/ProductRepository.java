package SK.AASS.TELCO.app.repository;

import SK.AASS.TELCO.app.config.ProductType;
import SK.AASS.TELCO.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductType(ProductType productType);
    Product findByProductId(Long id);

}
