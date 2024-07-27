package com.shop.ecommerce.resource;

import com.shop.ecommerce.models.ShopCartItem;
import com.shop.ecommerce.services.ShopCartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/items")
@RestController
public class ShopCartItemController extends AbstractController{

    @Autowired
    private ShopCartItemService shopCartItemService;

    @Tag(name = "SHOPCART_ITEMS")
    @Operation(summary = "Put items in shopcarts")
    @PostMapping("/{id}")
    public ResponseEntity<ShopCartItem> setItem(@PathVariable("id")Long id,
                                                @RequestBody ShopCartItem shopCartItem){
        shopCartItemService.putItemIntoShopCart(shopCartItem, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(shopCartItem);
    }

    @Tag(name = "SHOPCART_ITEMS")
    @Operation(summary = "List all products in a specific shopcart")
    @GetMapping("/{id}")
    public ResponseEntity listAllByShopCartID(@PathVariable("id") Long id){
        var list = shopCartItemService.findAllByShopCartId(id);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "SHOPCART_ITEMS")
    @Operation(summary = "Remove products from a shopcart")
    @DeleteMapping("/{shopcartItemId}")
    public ResponseEntity removeItemFromShopCart(@PathVariable("shopcartItemId") Long id){
        shopCartItemService.removeItemFromShopCart(id);
        return ResponseEntity.ok().build();
    }
}
