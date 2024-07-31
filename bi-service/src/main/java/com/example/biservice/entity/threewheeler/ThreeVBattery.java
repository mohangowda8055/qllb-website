package com.example.biservice.entity.threewheeler;

import com.example.biservice.entity.FuelType;
import com.example.biservice.entity.Product;
import com.example.biservice.entity.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "three_v_battery")
public class ThreeVBattery extends Product {

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

    @ManyToMany(mappedBy = "threeVBatteries", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ThreeVModel> threeVModels;

    @ManyToMany(mappedBy = "threeVBatteries", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<FuelType> fuelTypes;

    public ThreeVBattery(Integer productId, String productName, String modelName, String brandName, int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, float mrp, float discountPercentage, int stock, ProductType productType, float rebate, int guarantee, int warranty, String series, int ah, String terminalLayout) {
        super(productId, productName, modelName, brandName, voltage, imageMainUrl, imageOneUrl, imageTwoUrl, imageThreeUrl, mrp, discountPercentage, stock, productType, rebate);
        this.guarantee = guarantee;
        this.warranty = warranty;
        this.series = series;
        this.ah = ah;
        this.terminalLayout = terminalLayout;
    }
}
