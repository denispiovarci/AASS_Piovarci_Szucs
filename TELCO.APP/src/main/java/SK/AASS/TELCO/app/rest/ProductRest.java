package SK.AASS.TELCO.app.rest;

import SK.AASS.TELCO.app.config.ProductType;
import SK.AASS.TELCO.app.model.Product;
import SK.AASS.TELCO.app.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telco/api/v1/product")
@AllArgsConstructor
@Slf4j
public class ProductRest {

    private final ProductService productService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Product>> getAll(){
        log.info("ProductRest.getAll()");

        return ResponseEntity.ok().body(productService.getAll());
    }

    @GetMapping("/get/byType/{productType}")
    public ResponseEntity<List<Product>> getByType(@PathVariable ProductType productType){
        log.info("ProductRest.getByType({})", productType);

        return ResponseEntity.ok().body(productService.getByType(productType));
    }
}
