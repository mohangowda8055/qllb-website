package com.example.biservice.repository.inverterbattery;

import com.example.biservice.entity.inverterbattery.BackupDuration;
import com.example.biservice.entity.inverterbattery.InverterBattery;
import com.example.biservice.entity.inverterbattery.InverterBatteryWarranty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class InverterBatteryRepoTest {

    @Autowired
    private InverterBatteryRepo inverterBatteryRepo;

    @Autowired
    private BackupDurationRepo backupDurationRepo;

    @Autowired
    private InverterBatteryWarrantyRepo inverterBatteryWarrantyRepo;

    @Test
    public void inverterBatteryRepo_findInverterBatteriesByBackupDurationAndWarranty_returnPage(){
        //given
        BackupDuration backupDuration = BackupDuration.builder()
                .backupDuration(2)
                .build();
        BackupDuration savedBackup = backupDurationRepo.save(backupDuration);

        InverterBatteryWarranty inverterBatteryWarranty = InverterBatteryWarranty.builder()
                .warranty(12)
                .guarantee(12)
                .build();
        InverterBatteryWarranty savedWarranty = inverterBatteryWarrantyRepo.save(inverterBatteryWarranty);

        InverterBattery inverterBattery = InverterBattery.builder()
                .modelName("abc")
                .build();
        InverterBattery inverterBattery1 = InverterBattery.builder()
                .modelName("acb")
                .build();
        InverterBattery savedBattery = inverterBatteryRepo.save(inverterBattery1);
        InverterBattery savedBattery1 = inverterBatteryRepo.save(inverterBattery);

        inverterBatteryRepo.addBackupDurationWithWarrantyToBattery(savedBattery.getProductId(), savedBackup.getBackupDurationId(), savedWarranty.getWarrantyId());
        inverterBatteryRepo.addBackupDurationWithWarrantyToBattery(savedBattery1.getProductId(), savedBackup.getBackupDurationId(), savedWarranty.getWarrantyId());

        //when
        Pageable pageable = PageRequest.of(0, 4, Sort.by("modelName"));
        Page<InverterBattery> page = inverterBatteryRepo.findInverterBatteriesByBackupDurationAndWarranty(savedBackup.getBackupDurationId(),savedWarranty.getWarrantyId(),pageable);
        //then
        assertNotNull(page);
        assertEquals(2,page.getContent().size());
    }
}