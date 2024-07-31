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
@Table(name = "commercial_v_segment")
public class CommercialVSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "segment_id")
    private Integer segmentId;

    @Column(name = "segment_name")
    private  String segmentName;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "commercial_v_brand_segment",
            joinColumns = @JoinColumn(name = "segment_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private List<CommercialVBrand> commercialVBrands;

    @OneToMany(mappedBy = "commercialVSegment", cascade = CascadeType.ALL)
    private List<CommercialVModel> commercialVModels;
}
