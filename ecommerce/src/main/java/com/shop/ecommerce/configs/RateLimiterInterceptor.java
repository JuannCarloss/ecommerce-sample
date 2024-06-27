package com.shop.ecommerce.configs;

import com.shop.ecommerce.enterprise.TooManyRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final int MAX_REQUESTS = 10;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIP = getUserIP(request);
        String redisKey = userIP + ":request";

        Long requestCounter = redisTemplate.opsForValue().increment(redisKey, 1);

        if (requestCounter > MAX_REQUESTS){
            throw new TooManyRequestException("You have requested more than 10 times in 2 minutes");
        }

        return true;
    }

    private String getUserIP(HttpServletRequest request){
        return request.getRemoteAddr();
    }

}
