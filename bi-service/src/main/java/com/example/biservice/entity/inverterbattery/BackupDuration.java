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
@Table(name = "backup_duration")
public class BackupDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "backup_duration_id")
    private Integer backupDurationId;

    @Column(name = "backup_duration")
    private float backupDuration;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "inv_backup_warranty_battery",
            joinColumns = @JoinColumn(name = "backup_duration_id"),
            inverseJoinColumns = @JoinColumn(name = "warranty_id")
    )
    private List<InverterBatteryWarranty> inverterBatteryWarranties;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "inv_backup_warranty_battery",
            joinColumns = @JoinColumn(name = "backup_duration_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<InverterBattery> inverterBatteries;
}
