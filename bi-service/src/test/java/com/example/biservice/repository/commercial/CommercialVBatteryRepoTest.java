package com.example.biservice.repository.commercial;

import com.example.biservice.entity.commercial.CommercialVBattery;
import com.example.biservice.entity.commercial.CommercialVModel;
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
class CommercialVBatteryRepoTest {

    @Autowired
    private CommercialVModelRepo commercialVModelRepo;

    @Autowired
    private CommercialVBatteryRepo commercialVBatteryRepo;

    @Test
    public void commercialVBatteryRepo_findBatteriesByModel_returnPage(){
        //given
        CommercialVModel commercialVModel = CommercialVModel.builder()
                .modelName("Model A")
                .build();
        CommercialVModel savedModel = commercialVModelRepo.save(commercialVModel);
        List<CommercialVModel> commercialVModels = new ArrayList<>();
        commercialVModels.add(commercialVModel);
        CommercialVBattery commercialVBattery = CommercialVBattery.builder()
                .modelName("abc")
                .commercialVModels(commercialVModels)
                .build();
        CommercialVBattery commercialVBattery1 = CommercialVBattery.builder()
                .modelName("acb")
                .commercialVModels(commercialVModels)
                .build();
        commercialVBatteryRepo.save(commercialVBattery1);
        commercialVBatteryRepo.save(commercialVBattery);

        //when
        Pageable pageable = PageRequest.of(0, 4, Sort.by("modelName"));
        Page<CommercialVBattery> page = commercialVBatteryRepo.findBatteriesByModel(savedModel.getModelId(),pageable);
        //then
        assertNotNull(page);
        assertEquals(2,page.getContent().size());
    }
}