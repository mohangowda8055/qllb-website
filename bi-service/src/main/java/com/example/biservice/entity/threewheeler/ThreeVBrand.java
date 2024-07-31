package com.example.biservice.entity.threewheeler;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "three_v_brand")
public class ThreeVBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name")
    private  String brandName;

    @OneToMany(mappedBy = "threeVBrand", cascade = CascadeType.ALL)
    private List<ThreeVModel> threeVModels;
}
