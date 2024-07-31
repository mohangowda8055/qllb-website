package com.example.biservice.entity.twowheeler;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="two_v_brand")
public class TwoVBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name")
    private  String brandName;

    @OneToMany(mappedBy = "twoVBrand", cascade = CascadeType.ALL)
    private List<TwoVModel> twoVModels;

}
