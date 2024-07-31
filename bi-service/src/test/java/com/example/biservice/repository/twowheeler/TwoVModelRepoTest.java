package com.example.biservice.repository.twowheeler;

import com.example.biservice.entity.twowheeler.TwoVBrand;
import com.example.biservice.entity.twowheeler.TwoVModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TwoVModelRepoTest {

    @Autowired
    private TwoVBrandRepo twoVBrandRepo;
    @Autowired
    private TwoVModelRepo twoVModelRepo;

    @Test
    public void twoVModelRepo_findByTwoVBrandOrderByModelName_returnTwoVModelList(){
        //given
        TwoVBrand twoVBrand = TwoVBrand.builder()
                .brandName("Brand A")
                .build();
        TwoVBrand savedBrand = twoVBrandRepo.save(twoVBrand);

        TwoVModel twoVModel = TwoVModel.builder()
                .modelName("Model A")
                .twoVBrand(savedBrand)
                .build();
        TwoVModel twoVModel1 = TwoVModel.builder()
                .modelName("Model B")
                .twoVBrand(savedBrand)
                .build();
        twoVModelRepo.save(twoVModel1);
        twoVModelRepo.save(twoVModel);

        //when
        List<TwoVModel> twoVModels = twoVModelRepo.findByTwoVBrandOrderByModelName(savedBrand);
        //then
        assertNotNull(twoVModels);
        assertEquals(2, twoVModels.size());
    }
}