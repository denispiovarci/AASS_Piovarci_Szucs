package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.config.ProductType;
import SK.AASS.TELCO.app.model.Product;
import SK.AASS.TELCO.app.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll(){
        log.info("ProductServiceImpl.getAll()");
        //ChargeCardWorker.start();


        return productRepository.findAll();
    }

    @Override
    public List<Product> getByType(ProductType productType){
        log.info("ProductServiceImpl.getByType()");

        return productRepository.findByProductType(productType);
    }

}
