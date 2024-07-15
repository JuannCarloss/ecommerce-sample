package com.shop.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity(name = "address")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address extends EntityID{

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "street_name")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @OneToOne(mappedBy = "address")
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

}
