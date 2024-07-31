package com.example.biservice.repository.fourwheeler;

import com.example.biservice.entity.fourwheeler.FourVBattery;
import com.example.biservice.entity.fourwheeler.FourVBrand;
import com.example.biservice.entity.fourwheeler.FourVModel;
import com.example.biservice.entity.FuelType;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FourVModelRepoTest {

    @Autowired
    private FourVModelRepo fourVModelRepo;
    
    @Autowired
    private FourVBrandRepo fourVBrandRepo;

    @Autowired
    private FuelTypeRepo fuelTypeRepo;

    @Autowired
    private FourVBatteryRepo fourVBatteryRepo;

    @Test
    public void fourVModelRepo_findAllByFourVBrandOrderByModelName_returnFourVModelList(){
        //given
        FourVBrand fourVBrand = FourVBrand.builder()
                .brandName("Brand A")
                .build();
        FourVBrand savedBrand = fourVBrandRepo.save(fourVBrand);

        FourVModel fourVModel = FourVModel.builder()
                .modelName("Model A")
                .fourVBrand(savedBrand)
                .build();
        FourVModel fourVModel1 = FourVModel.builder()
                .modelName("Model B")
                .fourVBrand(savedBrand)
                .build();
        fourVModelRepo.save(fourVModel1);
        fourVModelRepo.save(fourVModel);

        //when
        List<FourVModel> fourVModels = fourVModelRepo.findAllByFourVBrandOrderByModelName(savedBrand);

        //then
        assertNotNull(fourVModels);
        assertEquals(2, fourVModels.size());
    }

    @Test
    public void fourVModelRepo_addBatteryToModel_returnVoid(){
        //given
        FourVBattery fourVBattery = FourVBattery.builder()
                .modelName("abc")
                .build();
        FourVBattery savedBattery = fourVBatteryRepo.save(fourVBattery);

        FourVModel fourVModel = FourVModel.builder()
                .modelName("Model A")
                .build();
        FourVModel savedModel = fourVModelRepo.save(fourVModel);

        FuelType fuelType = FuelType.builder()
                .fuelType("Petrol")
                .build();
        FuelType savedFuel = fuelTypeRepo.save(fuelType);

        //when
        fourVModelRepo.addBatteryToModel(savedModel.getModelId(), savedFuel.getFuelTypeId(), savedBattery.getProductId());
        Pageable pageable = PageRequest.of(0, 4, Sort.by("modelName"));
        Page<FourVBattery> page = fourVBatteryRepo.findBatteriesByModelAndFuel(savedModel.getModelId(), savedFuel.getFuelTypeId(), pageable);

        //then
        assertNotNull(page.getContent());
        assertEquals(1, page.getContent().size());
    }
}