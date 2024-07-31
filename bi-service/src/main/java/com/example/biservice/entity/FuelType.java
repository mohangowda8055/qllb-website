package com.example.biservice.entity;

import com.example.biservice.entity.fourwheeler.FourVBattery;
import com.example.biservice.entity.fourwheeler.FourVModel;
import com.example.biservice.entity.threewheeler.ThreeVBattery;
import com.example.biservice.entity.threewheeler.ThreeVModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fuel_type")
public class FuelType {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "fuel_type_id")
    private Integer fuelTypeId;

    @Column(name = "fuel_type")
    private String fuelType;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "four_v_model_fuel",
            joinColumns = @JoinColumn(name = "fuel_type_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private List<FourVModel> fourVModels;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "three_v_model_fuel",
            joinColumns = @JoinColumn(name = "fuel_type_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private List<ThreeVModel> threeVModels;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "four_v_model_fuel_battery",
            joinColumns = @JoinColumn(name = "fuel_type_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<FourVBattery> fourVBatteries;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "three_v_model_fuel_battery",
            joinColumns = @JoinColumn(name = "fuel_type_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ThreeVBattery> threeVBatteries;

}
