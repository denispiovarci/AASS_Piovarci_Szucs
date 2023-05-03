package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.config.OrderStatus;
import SK.AASS.TELCO.app.model.Order;
import SK.AASS.TELCO.app.model.OrderProduct;
import SK.AASS.TELCO.app.model.Product;
import SK.AASS.TELCO.app.model.User;
import SK.AASS.TELCO.app.repository.OrderProductRepository;
import SK.AASS.TELCO.app.repository.OrderRepository;
import SK.AASS.TELCO.app.repository.ProductRepository;
import SK.AASS.TELCO.app.repository.UserRepository;
import SK.AASS.TELCO.app.rest.request.OrderCreateRequest;

import SK.AASS.TELCO.app.rest.response.CreateInvoiceResponse;
import SK.AASS.TELCO.app.rest.response.InvoiceOrderProducts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public List<Order> getAll() {
        log.info("OrderServiceImpl.getAll()");

        return orderRepository.findAll();
    }

    @Override
    public String create(OrderCreateRequest request) {
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

        Long orderId = orderRepository.findFirstByOrderByOrderIdDesc().getOrderId();

        for(int i = 0; i< productList.size(); i++){
            Long productId = productList.get(i).getProductId();
            OrderProduct orderProduct = orderProductRepository.getRow(orderId, productId);
            orderProduct.setQuantity(request.getOrderProducts().get(i).getQuantity());
            orderProductRepository.save(orderProduct);
        }

        return "Your order has been submitted";
    }

    @Override
    public String confirm(Long id){
        log.info("OrderServiceImpl.confirm({})", id);

        Order order = orderRepository.findByOrderId(id);

        order.setStatus(OrderStatus.CONFIRMED);
        order.setModified(new Date());
        orderRepository.save(order);

        return "Your order with id " + id + " has been confirmed";
    }

    @Override
    public CreateInvoiceResponse createInvoice(Long id){
        log.info("OrderServiceImpl.createInvoice({})", id);

        Order order = orderRepository.findByOrderId(id);
        CreateInvoiceResponse res = CreateInvoiceResponse.builder()
                .firstName(order.getUser().getFirstName())
                .lastName(order.getUser().getLastName())
                .email(order.getUser().getEmail())
                .tel(order.getUser().getTel())

                .street(order.getUser().getStreet())
                .residenceNumber(order.getUser().getResidenceNumber())
                .city(order.getUser().getCity())
                .postalCode(order.getUser().getPostalCode())

                .orderId(order.getOrderId())
                .orderProducts(order.getProducts().stream().map(product -> {
                    int amount;
                    Long productId = product.getProductId();
                    OrderProduct orderProduct = orderProductRepository.getRow(order.getOrderId(), productId);
                    amount = orderProduct.getQuantity();


                    return InvoiceOrderProducts.builder()
                           .productName(product.getName())
                           .productType(product.getProductType())
                           .amount(amount)
                           .price(product.getPrice())
                           .build();
                }).collect(Collectors.toList()))
                .sum(order.getTotalPrice())
                .build();

        log.info("{}",res.getOrderProducts().size());
        return res;
    }

    @Override
    public String pay(Long id){
        log.info("OrderServiceImpl.pay({})", id);

        Order order = orderRepository.findByOrderId(id);

        order.setStatus(OrderStatus.PAYMENT_RECEIVED);
        order.setModified(new Date());

        return "Your order with id " + id + " has been paid";
    }

    @Override
    public String updateWarehouse(Long id){
        log.info("OrderServiceImpl.updateWarehouse({})", id);

        Order order = orderRepository.findByOrderId(id);
        List<Product> productList = order.getProducts();

        boolean state = productList.stream().allMatch(orderProduct -> {
            Product dbProduct = productRepository.findByProductId(orderProduct.getProductId());
            log.info("dbProduct.getAmount() - {}", dbProduct.getAmount());

            int orderProductAmount = 0;

            Long productId = orderProduct.getProductId();
            OrderProduct currOrderProduct = orderProductRepository.getRow(order.getOrderId(), productId);
            orderProductAmount = currOrderProduct.getQuantity();

            int remainingAmount = dbProduct.getAmount() - orderProductAmount;
            if(remainingAmount >=0) {
                dbProduct.setAmount(remainingAmount);
                productRepository.save(dbProduct);
                return true;
            }
            else{
                return false;
            }
        });

        if(state)
            return "Warehouse has been updated";
        else return "Warehouse has not been updated due to the low amount of products";
    }
}
