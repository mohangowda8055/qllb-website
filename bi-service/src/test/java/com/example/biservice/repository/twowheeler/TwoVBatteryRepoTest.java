package com.example.biservice.repository.twowheeler;

import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.entity.twowheeler.TwoVModel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TwoVBatteryRepoTest {

    @Autowired
    private TwoVBatteryRepo twoVBatteryRepo;

    @Autowired
    private TwoVModelRepo twoVModelRepo;

    @Test
    public void twoVBatteryRepo_findBatteriesByModel_returnPage(){
        //given
        TwoVModel twoVModel = TwoVModel.builder()
                .modelName("Model A")
                .build();
        TwoVModel savedModel = twoVModelRepo.save(twoVModel);

        List<TwoVModel> models = new ArrayList<>();
        models.add(savedModel);

        TwoVBattery twoVBattery = TwoVBattery.builder()
                .modelName("abc")
                .twoVModels(models)
                .build();
        TwoVBattery twoVBattery1 = TwoVBattery.builder()
                .modelName("acb")
                .twoVModels(models)
                .build();
        twoVBatteryRepo.save(twoVBattery1);
        twoVBatteryRepo.save(twoVBattery);

        Pageable pageable = PageRequest.of(0, 4, Sort.by("modelName"));

        //when
        Page<TwoVBattery> page = twoVBatteryRepo.findBatteriesByModel(savedModel.getModelId(),pageable);

        //then
        assertNotNull(page);
        assertEquals(2,page.getContent().size());
    }
}