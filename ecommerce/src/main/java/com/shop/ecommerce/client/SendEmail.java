package com.shop.ecommerce.client;

import com.shop.ecommerce.dtos.EmailRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailer", url = "${lambda.client.endpoint}")
public interface SendEmail {

    @PostMapping
    String send(EmailRequestDTO data);
}