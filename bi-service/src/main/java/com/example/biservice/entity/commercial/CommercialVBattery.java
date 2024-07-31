package com.example.biservice.entity.commercial;

import com.example.biservice.entity.Product;
import com.example.biservice.entity.ProductType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commercial_v_battery")
public class CommercialVBattery extends Product {

    @Column(name = "guarantee")
    private int guarantee;

    @Column(name = "warranty")
    private  int warranty;

    @Column(name = "series")
    private String series;

    @Column(name = "ah")
    private  int ah;

    @Column(name = "terminal_layout")
    private String terminalLayout;


@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
@JoinTable(
        name = "commercial_v_model_battery",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "model_id")
)
    private List<CommercialVModel> commercialVModels;

    public CommercialVBattery(Integer productId, String productName, String modelName, String brandName, int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, float mrp, float discountPercentage, int stock, ProductType productType, float rebate, int guarantee, int warranty, String series, int ah, String terminalLayout) {
        super(productId, productName, modelName, brandName, voltage, imageMainUrl, imageOneUrl, imageTwoUrl, imageThreeUrl, mrp, discountPercentage, stock, productType, rebate);
        this.guarantee = guarantee;
        this.warranty = warranty;
        this.series = series;
        this.ah = ah;
        this.terminalLayout = terminalLayout;
    }
}
