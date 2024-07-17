package com.shop.ecommerce.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaHealthCheckController {

    private static final String TOPIC = "order-payment";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @PostMapping
    public String message(String message){
        kafkaTemplate.send(TOPIC, message);
        return "message sent";
    }
}
