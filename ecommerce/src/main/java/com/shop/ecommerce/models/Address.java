package com.shop.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "address")
@Getter
@Setter
@Builder
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
    private Customer customer;

}
