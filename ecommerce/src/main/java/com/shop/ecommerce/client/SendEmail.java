package com.shop.ecommerce.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.ecommerce.dtos.EmailRequestDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendEmail {

    @Value("${lambda.client.endpoint}")
    private String lambdaEndpoint;

    public void send(EmailRequestDTO data) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        ObjectMapper mapper = new ObjectMapper();

        var json = mapper.writeValueAsString(data);

        HttpPost httpPost = new HttpPost(lambdaEndpoint);
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                System.out.println("Lambda response:");
                System.out.println(EntityUtils.toString(responseEntity));
            }
        }finally {
            httpClient.close();
        }
    }
}

