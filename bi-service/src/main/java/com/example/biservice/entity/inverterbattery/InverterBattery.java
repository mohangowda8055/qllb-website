package com.example.biservice.entity.inverterbattery;

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
@Table(name = "inverter_battery")
public class InverterBattery extends Product {

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
            name = "inv_backup_warranty_battery",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "backup_duration_id")
    )
    private List<BackupDuration> backupDurations;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "inv_backup_warranty_battery",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "warranty_id")
    )
    private List<InverterBatteryWarranty> inverterBatteryWarranties;

    public InverterBattery(Integer productId, String productName, String modelName, String brandName, int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, float mrp, float discountPercentage, int stock, ProductType productType, float rebate, int guarantee, int warranty, String series, int ah, String terminalLayout) {
        super(productId, productName, modelName, brandName, voltage, imageMainUrl, imageOneUrl, imageTwoUrl, imageThreeUrl, mrp, discountPercentage, stock, productType, rebate);
        this.guarantee = guarantee;
        this.warranty = warranty;
        this.series = series;
        this.ah = ah;
        this.terminalLayout = terminalLayout;
    }
}
