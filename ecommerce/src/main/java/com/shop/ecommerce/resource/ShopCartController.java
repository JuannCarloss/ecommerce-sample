package com.shop.ecommerce.resource;

import com.shop.ecommerce.models.ShopCart;
import com.shop.ecommerce.services.ShopCartService;
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


@RestController
@RequestMapping("/api/shopcart")
public class ShopCartController extends AbstractController{

    @Autowired
    private ShopCartService service;

    @Tag(name = "shopcart")
    @Operation(summary = "Post a new Order with its products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a new order",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopCart.class)) }),
            @ApiResponse(responseCode = "422", description = "the body that you sent is not valid",
                    content = @Content) })
    @PostMapping("/{id}")
    public ResponseEntity buy(@PathVariable("id") Long id){
        var save = service.buyShopcart(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
}
