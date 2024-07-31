package com.example.biservice.repository.commercial;

import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVSegment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CommercialVSegmentRepoTest {

    @Autowired
    private CommercialVSegmentRepo commercialVSegmentRepo;

    @Autowired
    private CommercialVBrandRepo commercialVBrandRepo;

    @Test
    public void commercialVSegmentRepo_findSegmentsByBrand_returnCommercialVSegmentList(){
        //given
        CommercialVBrand commercialVBrand = CommercialVBrand.builder()
                .brandName("Brand A")
                .build();
        CommercialVBrand savedBrand = commercialVBrandRepo.save(commercialVBrand);
        List<CommercialVBrand> brands = new ArrayList<>();
        brands.add(savedBrand);
        CommercialVSegment commercialVSegment = CommercialVSegment.builder()
                .segmentName("Segment A")
                .commercialVBrands(brands)
                .build();
        CommercialVSegment commercialVSegment1 = CommercialVSegment.builder()
                .segmentName("Segment B")
                .commercialVBrands(brands)
                .build();
        commercialVSegmentRepo.save(commercialVSegment1);
        commercialVSegmentRepo.save(commercialVSegment);

        //when
        Sort sort = Sort.by("segmentName");
        List<CommercialVSegment> commercialVSegments = commercialVSegmentRepo.findSegmentsByBrand(savedBrand.getBrandId(),sort);
        //then
        assertNotNull(commercialVSegments);
        assertEquals(2,commercialVSegments.size());
    }
}