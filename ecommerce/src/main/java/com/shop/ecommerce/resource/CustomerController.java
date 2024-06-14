package com.shop.ecommerce.resource;

import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.services.CustomerService;
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

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends AbstractController{

    @Autowired
    private CustomerService service;

    @Tag(name = "CUSTOMER")
    @Operation(summary = "Post a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a new customer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "422", description = "the body that you sent is not valid",
                    content = @Content) })
    @PostMapping
    public ResponseEntity save(@RequestBody Customer entity){
        var save = service.post(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }


    @Tag(name = "CUSTOMER")
    @GetMapping
    public ResponseEntity findAll(){
        List<Customer> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}
