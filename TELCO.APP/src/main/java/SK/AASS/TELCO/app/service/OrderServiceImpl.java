package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.config.OrderStatus;
import SK.AASS.TELCO.app.model.Order;
import SK.AASS.TELCO.app.model.OrderProduct;
import SK.AASS.TELCO.app.model.Product;
import SK.AASS.TELCO.app.model.User;
import SK.AASS.TELCO.app.repository.OrderRepository;
import SK.AASS.TELCO.app.repository.ProductRepository;
import SK.AASS.TELCO.app.repository.UserRepository;
import SK.AASS.TELCO.app.rest.request.OrderCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Order> getAll() {
        log.info("OrderServiceImpl.getAll()");

        return orderRepository.findAll();
    }

    @Override
    public void create(OrderCreateRequest request) {
        log.info("OrderServiceImpl.create({})", request);

        User user = userRepository.findByEmail(request.getUserEmail());
        List<Product> productList = request.getOrderProducts().stream()
                .map(orderProduct -> productRepository.findByProductId(orderProduct.getProduct().getProductId()))
                .collect(Collectors.toList());

        double totalPrice = 0.0;

        for(int i=0; i < productList.size(); i++){
            totalPrice += productList.get(i).getPrice() * request.getOrderProducts().get(i).getQuantity();
        }

        Order order = Order.builder()
                .user(user)
                .products(productList)
                .status(OrderStatus.NEW)
                .totalPrice(totalPrice)
                .created(new Date())
                .modified(new Date())
                .build();

        orderRepository.save(order);
        return;
    }

}
