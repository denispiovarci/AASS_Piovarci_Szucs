package SK.AASS.TELCO.app.rest;

import SK.AASS.TELCO.app.model.Order;
import SK.AASS.TELCO.app.rest.request.OrderCreateRequest;
import SK.AASS.TELCO.app.rest.response.CreateInvoiceResponse;
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

    @GetMapping("/get/all")
    public ResponseEntity<List<Order>> getAll(){
        log.info("OrderRest.getAll()");

        return ResponseEntity.ok().body(orderService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody OrderCreateRequest request){
        log.info("OrderRest.create({})", request);

        return ResponseEntity.ok().body(orderService.create(request));
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirm(@PathVariable Long orderId){
        log.info("OrderRest.confirm({})", orderId);

        return ResponseEntity.ok().body(orderService.confirm(orderId));
    }

    @PostMapping("/{orderId}/invoice/create")
    public ResponseEntity<CreateInvoiceResponse> createInvoice(@PathVariable Long orderId){
        log.info("OrderRest.createInvoice({})", orderId);

        return ResponseEntity.ok().body(orderService.createInvoice(orderId));
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<String> pay(@PathVariable Long orderId){
        log.info("OrderRest.pay({})", orderId);

        return ResponseEntity.ok().body(orderService.pay(orderId));
    }

    @PostMapping("/{orderId}/warehouse/update")
    public ResponseEntity<String> updateWarehouse(@PathVariable Long orderId){
        log.info("OrderRest.updateWarehouse({})", orderId);

        return ResponseEntity.ok().body(orderService.updateWarehouse(orderId));
    }
}
