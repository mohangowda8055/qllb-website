package com.example.biservice.entity.inverterbattery;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inverter_battery_warranty")
public class InverterBatteryWarranty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warranty_id")
    private Integer warrantyId;

    @Column(name = "warranty")
    private Integer warranty;

    @Column(name = "guarantee")
    private Integer guarantee;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "inv_backup_warranty_battery",
            joinColumns = @JoinColumn(name = "warranty_id"),
            inverseJoinColumns = @JoinColumn(name = "backup_duration_id")
    )
    private List<BackupDuration> backupDurations;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "inv_backup_warranty_battery",
            joinColumns = @JoinColumn(name = "warranty_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<InverterBattery> inverterBatteries;
}
