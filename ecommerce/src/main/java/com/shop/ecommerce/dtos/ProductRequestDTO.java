package com.shop.ecommerce.dtos;

import org.springframework.web.multipart.MultipartFile;

public record ProductRequestDTO(String name, MultipartFile img, String description, Double price, Integer stock) {
}
