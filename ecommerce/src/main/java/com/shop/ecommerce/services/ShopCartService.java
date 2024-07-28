package com.shop.ecommerce.services;

import com.shop.ecommerce.client.SendEmail;
import com.shop.ecommerce.dtos.EmailRequestDTO;
import com.shop.ecommerce.dtos.OrderDTO;
import com.shop.ecommerce.dtos.PaymentDTO;
import com.shop.ecommerce.enterprise.OkNoContentException;
import com.shop.ecommerce.enums.OrderStatus;
import com.shop.ecommerce.models.ShopCart;
import com.shop.ecommerce.repositories.CustomerRepository;
import com.shop.ecommerce.repositories.ShopCartRepository;
import com.shop.ecommerce.strategy.NewShopCartValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;


@Service
@RequiredArgsConstructor
public class ShopCartService {

    private static final String TOPIC_PAYMENT = "payment";
    private static final String TOPIC_ORDER = "order";
    private final ShopCartRepository shopCartRepository;
    private final ShopCartItemService shopCartItemService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CustomerRepository customerRepository;
    private final SendEmail sendEmail;
    private final NewShopCartValidationStrategy shopCartValidationStrategy;

    @Transactional
    public ShopCart createShopCart(ShopCart entity){
        return shopCartRepository.save(entity);
    }

    @Transactional
    public ShopCart buyShopcart(Long shopCartId){

        var shopcart = optionalShopCartById(shopCartId);
        shopCartValidationStrategy.validate(shopcart);

        shopCartItemService.deleteAllByShopCartID(shopCartId);

        var customerByID = customerRepository
                .findById(shopcart.getCustomer().getId())
                .orElseThrow(() -> new OkNoContentException("Customer not Found"));

        sendEmail.send(new EmailRequestDTO(customerByID.getEmail(), "ORDER"));

        String date = Timestamp.from(Instant.now()).toString();

        var payment = new PaymentDTO(customerByID.getEmail(), date,shopcart.getTotalPrice());
        var order = new OrderDTO(customerByID.getEmail(), date,OrderStatus.AWAITING_PAYMENT, shopcart.getTotalPrice());

        shopcart.setTotalPrice(0.0);
        kafkaTemplate.send(TOPIC_PAYMENT, payment);
        kafkaTemplate.send(TOPIC_ORDER, order);

        return shopcart;
    }

    public ShopCart optionalShopCartById(Long id){
        return shopCartRepository.findById(id).orElseThrow(() -> new OkNoContentException("ShopCart Not Found"));
    }
}
