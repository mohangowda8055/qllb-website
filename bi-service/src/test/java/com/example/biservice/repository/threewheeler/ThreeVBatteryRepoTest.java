package com.example.biservice.repository.threewheeler;

import com.example.biservice.entity.FuelType;
import com.example.biservice.entity.threewheeler.ThreeVBattery;
import com.example.biservice.entity.threewheeler.ThreeVModel;
import com.example.biservice.repository.FuelTypeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ThreeVBatteryRepoTest {

    @Autowired
    private ThreeVBatteryRepo threeVBatteryRepo;
    
    @Autowired
    private ThreeVModelRepo threeVModelRepo;

    @Autowired
    private FuelTypeRepo fuelTypeRepo;

    @Test
    public void threeVBatteryRepo_findBatteriesByModelAndFuel_returnPage(){
        //given
        ThreeVModel threeVModel = ThreeVModel.builder()
                .modelName("Model A")
                .build();
        ThreeVModel savedModel = threeVModelRepo.save(threeVModel);

        FuelType fuelType = FuelType.builder()
                .fuelTypeId(1)
                .fuelType("PETROL")
                .build();
        FuelType savedFuel = fuelTypeRepo.save(fuelType);

        ThreeVBattery threeVBattery = ThreeVBattery.builder()
                .modelName("abc")
                .productName("product A")
                .build();
        ThreeVBattery threeVBattery1 = ThreeVBattery.builder()
                .modelName("acb")
                .productName("product B")
                .build();
        ThreeVBattery savedBattery = threeVBatteryRepo.save(threeVBattery1);
        ThreeVBattery savedBattery1 = threeVBatteryRepo.save(threeVBattery);

        threeVModelRepo.addBatteryToModel(savedModel.getModelId(), savedFuel.getFuelTypeId(), savedBattery.getProductId());
        threeVModelRepo.addBatteryToModel(savedModel.getModelId(), savedFuel.getFuelTypeId(),savedBattery1.getProductId());

        Pageable pageable = PageRequest.of(0, 4, Sort.by("modelName"));

        //when
        Page<ThreeVBattery> page = threeVBatteryRepo.findBatteriesByModelAndFuel(savedModel.getModelId(),savedFuel.getFuelTypeId(),pageable);

        //then
        assertNotNull(page);
        assertEquals(2,page.getContent().size());
    }
}