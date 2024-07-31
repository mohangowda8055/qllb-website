package com.example.biservice.repository.inverterbattery;

import com.example.biservice.entity.inverterbattery.InverterBattery;
import com.example.biservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InverterBatteryRepo extends ProductRepository<InverterBattery> {

    @Query("SELECT b FROM InverterBattery b JOIN b.backupDurations bd JOIN b.inverterBatteryWarranties ibw WHERE bd.backupDurationId = :backupDurationId AND ibw.warrantyId = :warrantyId")
    Page<InverterBattery> findInverterBatteriesByBackupDurationAndWarranty(@Param("backupDurationId") Integer backupDurationId, @Param("warrantyId") Integer warrantyId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value="insert into inv_backup_warranty_battery(backup_duration_id, warranty_id, product_id) values (:backupDurationId, :warrantyId, :batteryId)", nativeQuery = true)
    void addBackupDurationWithWarrantyToBattery(@Param("batteryId") Integer batteryId, @Param("backupDurationId") Integer backupDurationId, @Param("warrantyId") Integer warrantyId);
}
