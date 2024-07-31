package com.example.biservice.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class Token {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "token")
    private String token;


    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
