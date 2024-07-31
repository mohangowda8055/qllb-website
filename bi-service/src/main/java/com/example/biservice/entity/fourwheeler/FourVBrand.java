package com.example.biservice.entity.fourwheeler;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "four_v_brand")
public class FourVBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name")
    private  String brandName;

    @OneToMany(mappedBy = "fourVBrand", cascade = CascadeType.ALL)
    private List<FourVModel> fourVModels;
}
