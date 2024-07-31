package com.example.biservice.repository.commercial;

import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVModel;
import com.example.biservice.entity.commercial.CommercialVSegment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CommercialVModelRepoTest {

    @Autowired
    private CommercialVModelRepo commercialVModelRepo;

    @Autowired
    private CommercialVBrandRepo commercialVBrandRepo;

    @Autowired
    private CommercialVSegmentRepo commercialVSegmentRepo;

    @Test
    public void commercialVModelRepo_findAllByCommercialVBrandAndCommercialVSegmentOrderByModelName_returnCommercialVModelList(){
        //given
        CommercialVBrand commercialVBrand = CommercialVBrand.builder()
                .brandName("Brand A")
                .build();
        CommercialVBrand savedBrand = commercialVBrandRepo.save(commercialVBrand);

        CommercialVSegment commercialVSegment = CommercialVSegment.builder()
                .segmentName("Segment A")
                .build();
        CommercialVSegment savedSegment = commercialVSegmentRepo.save(commercialVSegment);

        CommercialVModel commercialVModel = CommercialVModel.builder()
                .modelName("Model A")
                .commercialVBrand(savedBrand)
                .commercialVSegment(savedSegment)
                .build();
        CommercialVModel commercialVModel1 = CommercialVModel.builder()
                .modelName("Model B")
                .commercialVBrand(savedBrand)
                .commercialVSegment(savedSegment)
                .build();
        commercialVModelRepo.save(commercialVModel1);
        commercialVModelRepo.save(commercialVModel);

        //when
        List<CommercialVModel> commercialVModelList = commercialVModelRepo.findAllByCommercialVBrandAndCommercialVSegmentOrderByModelName(savedBrand,savedSegment);
        //then
        assertNotNull(commercialVModelList);
        assertEquals(2,commercialVModelList.size());
    }
}