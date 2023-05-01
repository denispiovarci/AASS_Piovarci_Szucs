package SK.AASS.TELCO.app.rest;

import SK.AASS.TELCO.app.model.Order;
import SK.AASS.TELCO.app.rest.request.OrderCreateRequest;
import SK.AASS.TELCO.app.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telco/api/v1/order")
@AllArgsConstructor
@Slf4j
public class OrderRest {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody OrderCreateRequest request){
        log.info("OrderRest.create({})", request);

        orderService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Order>> getAll(){
        log.info("OrderRest.getAll()");

        return ResponseEntity.ok().body(orderService.getAll());
    }

}
