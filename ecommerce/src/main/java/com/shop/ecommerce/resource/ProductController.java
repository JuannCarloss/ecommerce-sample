package com.shop.ecommerce.resource;

import com.shop.ecommerce.dtos.ProductRequestDTO;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractController{

    @Autowired
    private ProductService service;

    @Tag(name = "PRODUCT")
    @Operation(summary = "Post a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a new product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "404", description = "product not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "the body that you sent is not valid",
                    content = @Content) })

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity save(@RequestParam("name")String name,
                               @RequestParam("url") MultipartFile url,
                               @RequestParam("description")String description,
                               @RequestParam("price")Double price,
                               @RequestParam("stock")Integer stock){
        ProductRequestDTO product = new ProductRequestDTO(name, url, description, price, stock);
        var save = service.post(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @Tag(name = "PRODUCT")
    @Operation(summary = "Update a existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated a product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "404", description = "product not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "the body that you sent is not valid",
                    content = @Content) })
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Product entity){
        var save = service.updateProduct(id, entity);

        return ResponseEntity.ok(save);
    }

    @Tag(name = "PRODUCT")
    @Operation(summary = "List all products with highest prices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "products with highest price",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) })})
    @GetMapping("highest")
    public ResponseEntity highestPrices(){
        List<Product> list = service.findAllProductsByHighestPrice();

        return ResponseEntity.ok(list);
    }


    @Tag(name = "PRODUCT")
    @Operation(summary = "List all products with lowest prices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "products with lowest price",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) })})
    @GetMapping("lowest")
    public ResponseEntity lowestPrices(){
        List<Product> list = service.findAllProductsByLowestPrice();

        return ResponseEntity.ok(list);
    }

}
