package com.example.biservice.repository.twowheeler;

import com.example.biservice.entity.twowheeler.TwoVBrand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TwoVBrandRepoTest {

    @Autowired
    private TwoVBrandRepo twoVBrandRepo;

    @Test
    public void twoVBrandRepo_save_returnTwoVBrand(){
        //given
        TwoVBrand twoVBrand = TwoVBrand.builder()
                .brandName("Brand A")
                .build();

        //when
        TwoVBrand savedBrand = twoVBrandRepo.save(twoVBrand);
        System.out.println(savedBrand.getBrandId());
        System.out.println(savedBrand.getBrandName());
        //then
        assertNotNull(savedBrand);
        assertTrue(savedBrand.getBrandId()>0);
    }

    @Test
    public void twoVBrandRepo_findAll_returnTwoVBrandList(){
        //given
        TwoVBrand twoVBrand = TwoVBrand.builder()
                .brandName("Brand A")
                .build();
        TwoVBrand twoVBrand2 = TwoVBrand.builder()
                .brandName("Brand B")
                .build();
        twoVBrandRepo.save(twoVBrand);
        twoVBrandRepo.save(twoVBrand2);

        //when
        List<TwoVBrand> twoVBrands = twoVBrandRepo.findAll();
        //then
        assertNotNull(twoVBrands);
        assertEquals(2, twoVBrands.size());
    }

    @Test
    public void twoVBrandRepo_findById_returnTwoVBrand(){
        //given
        TwoVBrand twoVBrand = TwoVBrand.builder()
                .brandName("Brand A")
                .build();
        TwoVBrand savedBrand = twoVBrandRepo.save(twoVBrand);

        //when
        TwoVBrand twoVBrand1 = twoVBrandRepo.findById(savedBrand.getBrandId()).get();
        assertNotNull(twoVBrand1);
    }

    @Test
    public void twoVBrandRepo_update_returnTwoVBrand(){
        //given
        TwoVBrand twoVBrand = TwoVBrand.builder()
                .brandName("Brand A")
                .build();
        TwoVBrand savedBrand = twoVBrandRepo.save(twoVBrand);

        //when
        TwoVBrand twoVBrand1 = twoVBrandRepo.findById(savedBrand.getBrandId()).get();
        twoVBrand1.setBrandName("Brand B");
        TwoVBrand updatedBrand = twoVBrandRepo.save(twoVBrand1);
        //then
        assertNotNull(updatedBrand);
        assertEquals(savedBrand.getBrandId(),updatedBrand.getBrandId());
        assertEquals(twoVBrand1.getBrandName(),updatedBrand.getBrandName());
    }

    @Test
    public void twoVBrandRepo_delete_returnVoid(){
        //given
        TwoVBrand twoVBrand = TwoVBrand.builder()
                .brandName("Brand A")
                .build();
        TwoVBrand savedBrand = twoVBrandRepo.save(twoVBrand);

        //when
        TwoVBrand twoVBrand1 = twoVBrandRepo.findById(savedBrand.getBrandId()).get();
        twoVBrandRepo.delete(twoVBrand1);
        Optional<TwoVBrand> optionalTwoVBrand = twoVBrandRepo.findById(savedBrand.getBrandId());
        //then
        assertFalse(optionalTwoVBrand.isPresent());
    }
}