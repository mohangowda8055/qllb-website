package com.example.biservice.repository.threewheeler;

import com.example.biservice.entity.threewheeler.ThreeVBrand;
import com.example.biservice.entity.threewheeler.ThreeVModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ThreeVModelRepoTest {

    @Autowired
    private ThreeVBrandRepo threeVBrandRepo;

    @Autowired
    private ThreeVModelRepo threeVModelRepo;

    @Test
    public void threeVModelRepo_findAllByThreeVBrandOrderByModelName_returnThreeVModelList(){
        //given
        ThreeVBrand threeVBrand = ThreeVBrand.builder()
                .brandName("Brand A")
                .build();
        ThreeVBrand savedBrand = threeVBrandRepo.save(threeVBrand);

        ThreeVModel threeVModel = ThreeVModel.builder()
                .modelName("Model A")
                .threeVBrand(savedBrand)
                .build();
        ThreeVModel threeVModel1 = ThreeVModel.builder()
                .modelName("Model B")
                .threeVBrand(savedBrand)
                .build();
        threeVModelRepo.save(threeVModel1);
        threeVModelRepo.save(threeVModel);

        //when
        List<ThreeVModel> threeVModelList = threeVModelRepo.findAllByThreeVBrandOrderByModelName(savedBrand);
        //then
        assertNotNull(threeVModelList);
        assertEquals(2,threeVModelList.size());
    }
}