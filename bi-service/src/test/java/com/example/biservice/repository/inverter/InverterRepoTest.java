package com.example.biservice.repository.inverter;

import com.example.biservice.entity.inverter.Inverter;
import com.example.biservice.entity.inverter.InverterCapacity;
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
class InverterRepoTest {

    @Autowired
    private InverterCapacityRepo inverterCapacityRepo;

    @Autowired
    private InverterRepo inverterRepo;

    @Test
    public void inverterRepo_findAllByInverterCapacity_returnPage(){
        //given
        InverterCapacity inverterCapacity = InverterCapacity.builder()
                .capacity(10)
                .build();
        InverterCapacity savedCapacity = inverterCapacityRepo.save(inverterCapacity);

        Inverter inverter = Inverter.builder()
                .modelName("abc")
                .inverterCapacity(savedCapacity)
                .build();
        Inverter inverter1 = Inverter.builder()
                .modelName("acb")
                .inverterCapacity(savedCapacity)
                .build();
        inverterRepo.save(inverter1);
        inverterRepo.save(inverter);

        //when
        Pageable pageable = PageRequest.of(0, 4, Sort.by("modelName"));
        Page<Inverter> page = inverterRepo.findAllByInverterCapacity(savedCapacity,pageable);
        //then
        assertNotNull(page);
        assertEquals(2,page.getContent().size());
    }
}