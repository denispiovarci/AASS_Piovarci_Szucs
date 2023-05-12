package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.config.ProductType;
import SK.AASS.TELCO.app.model.Product;

import java.util.List;

/**
 * Product service interface
 */
public interface ProductService {

    /**
     * Gets list of all products
     *
     */
    List<Product> getAll();

    /**
     * Gets list of all products by its type
     *
     */
    List<Product> getByType(ProductType productType);



}
