package com.shop.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends EntityID{


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
}
