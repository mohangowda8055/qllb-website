package com.example.biservice.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressId implements Serializable {

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "user_id")
    private Long userId;
}
