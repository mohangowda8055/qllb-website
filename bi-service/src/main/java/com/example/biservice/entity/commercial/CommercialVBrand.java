package com.example.biservice.entity.commercial;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commercial_v_brand")
public class CommercialVBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name")
    private  String brandName;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "commercial_v_brand_segment",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id")
    )
    private List<CommercialVSegment> commercialVSegments;

    @OneToMany(mappedBy = "commercialVBrand", cascade = CascadeType.ALL)
    private List<CommercialVModel> commercialVModels;
}
