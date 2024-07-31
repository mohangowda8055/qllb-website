package com.example.biservice.entity.inverter;

import com.example.biservice.entity.Product;
import com.example.biservice.entity.ProductType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inverter")
public class Inverter extends Product {

    @Column(name = "no_of_battery_req")
    private Integer noOfBatteryReq;

    @Column(name = "warranty")
    private Integer warranty;

    @Column(name = "inverter_type")
    private String inverterType;

    @Column(name = "rec_battery_capacity")
    private String recBatteryCapacity;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "capacity_id")
    private InverterCapacity inverterCapacity;

    public Inverter(Integer productId, String productName, String modelName, String brandName, int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, float mrp, float discountPercentage, int stock, ProductType productType, float rebate, Integer noOfBatteryReq, Integer warranty, String inverterType, String recBatteryCapacity) {
        super(productId, productName, modelName, brandName, voltage, imageMainUrl, imageOneUrl, imageTwoUrl, imageThreeUrl, mrp, discountPercentage, stock, productType, rebate);
        this.noOfBatteryReq = noOfBatteryReq;
        this.warranty = warranty;
        this.inverterType = inverterType;
        this.recBatteryCapacity = recBatteryCapacity;
    }
}
