package com.example.biservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "voltage")
    private int voltage;

    @Column(name = "image_main_url")
    private  String imageMainUrl;

    @Column(name = "image_one_url")
    private  String imageOneUrl;

    @Column(name = "image_two_url")
    private  String imageTwoUrl;

    @Column(name = "image_three_url")
    private  String imageThreeUrl;

    @Column(name = "mrp")
    private  float mrp;

    @Column(name = "discount_percentage")
    private float discountPercentage;

    @Column(name = "stock")
    private int stock;

    @Enumerated(value = EnumType.STRING)
    @Column(name="product_type")
    private  ProductType productType;

    @Column(name = "rebate")
    private float rebate;

    public Product(String productName, String modelName, String brandName, int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, float mrp, float discountPercentage, int stock, ProductType productType, float rebate) {
        this.productName = productName;
        this.modelName = modelName;
        this.brandName = brandName;
        this.voltage = voltage;
        this.imageMainUrl = imageMainUrl;
        this.imageOneUrl = imageOneUrl;
        this.imageTwoUrl = imageTwoUrl;
        this.imageThreeUrl = imageThreeUrl;
        this.mrp = mrp;
        this.discountPercentage = discountPercentage;
        this.stock = stock;
        this.productType = productType;
        this.rebate = rebate;
    }
}
