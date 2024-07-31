package com.example.biservice.service.commercial.battery;

import com.example.biservice.entity.commercial.CommercialVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.commercial.CommercialVBatteryDto;
import com.example.biservice.repository.commercial.CommercialVBatteryRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommercialVBatteryServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CommercialVBatteryRepo commercialVBatteryRepo;
    @InjectMocks
    private CommercialVBatteryServiceImp commercialVBatteryServiceImp;

    private CommercialVBattery mockCommercialVBattery;
    private CommercialVBatteryDto mockCommercialVBatteryDto;

    @BeforeEach
    void setUp() {
        mockCommercialVBattery = CommercialVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        mockCommercialVBatteryDto = CommercialVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when(modelMapper.map(Mockito.any(CommercialVBatteryDto.class), Mockito.eq(CommercialVBattery.class))).thenReturn(mockCommercialVBattery);
        when(modelMapper.map(Mockito.any(CommercialVBattery.class), Mockito.eq(CommercialVBatteryDto.class))).thenReturn(mockCommercialVBatteryDto);
    }

    @AfterEach
    void tearDown() {
        mockCommercialVBattery = null;
        mockCommercialVBatteryDto = null;
    }

    @Test
    public void commercialVBatteryService_createBattery_returnCommercialVBattery(){
        //given
        mockCommercialVBattery = CommercialVBattery.builder()
                .modelName("abc")
                .build();
        mockCommercialVBatteryDto = CommercialVBatteryDto.builder()
                .modelName("abc")
                .build();
        CommercialVBattery savedBattery = CommercialVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        CommercialVBatteryDto savedBatteryDto = CommercialVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when((modelMapper.map(Mockito.any(CommercialVBatteryDto.class), Mockito.eq(CommercialVBattery.class)))).thenReturn(mockCommercialVBattery);
        when((modelMapper.map(Mockito.any(CommercialVBattery.class), Mockito.eq(CommercialVBatteryDto.class)))).thenReturn(savedBatteryDto);
        when(commercialVBatteryRepo.save(Mockito.any(CommercialVBattery.class))).thenReturn(savedBattery);

        //when
        CommercialVBatteryDto result = commercialVBatteryServiceImp.createBattery(mockCommercialVBatteryDto);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockCommercialVBatteryDto.getModelName());
    }

    @Test
    public void commercialVBatteryService_createBattery_throwUniqueConstraintException(){
        //given
        when(commercialVBatteryRepo.save(Mockito.any(CommercialVBattery.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->commercialVBatteryServiceImp.createBattery(mockCommercialVBatteryDto));
    }

    @Test
    public void commercialVBatteryService_findAllBatteries_returnEmptyList() {
        //given
        when(commercialVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<CommercialVBatteryDto> result = commercialVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void commercialVBatteryService_findAllBatteries_returnCommercialVBatteryList() {
        //given
        List<CommercialVBattery> commercialVBatteries = Arrays.asList(
                CommercialVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                CommercialVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(commercialVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(commercialVBatteries);
        when(modelMapper.map(Mockito.any(CommercialVBattery.class), Mockito.eq(CommercialVBatteryDto.class)))
                .thenAnswer(invocation -> {
                    CommercialVBattery battery = invocation.getArgument(0);
                    return CommercialVBatteryDto.builder()
                            .productId(battery.getProductId())
                            .modelName(battery.getModelName())
                            .build();
                });

        //when
        List<CommercialVBatteryDto> result = commercialVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(commercialVBatteries.size(), result.size());
        assertEquals("abc", result.get(0).getModelName());// Assert sorted order
        assertEquals("acb", result.get(1).getModelName());
    }

    @Test
    public void commercialVBatteryService_findAllBatteriesByModel_returnCommercialVBatteryPage() {
        //given
        Integer modelId = 1;
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "modelName";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        // Mocking repository to return page of CommercialVBattery objects
        List<CommercialVBattery> batteries = Arrays.asList(
                CommercialVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                CommercialVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        Page<CommercialVBattery> page = new PageImpl<>(batteries, pageable, batteries.size());
        when(commercialVBatteryRepo.findBatteriesByModel(modelId, pageable)).thenReturn(page);

        // Mocking modelMapper to map CommercialVBattery to CommercialVBatteryDto
        List<CommercialVBatteryDto> batteryDtos = batteries.stream()
                .map(battery -> CommercialVBatteryDto.builder()
                        .productId(battery.getProductId())
                        .modelName(battery.getModelName())
                        .build())
                .collect(Collectors.toList());
        when(modelMapper.map(Mockito.any(CommercialVBattery.class), Mockito.eq(CommercialVBatteryDto.class))).thenAnswer(invocation -> {
            CommercialVBattery battery = invocation.getArgument(0);
            return CommercialVBatteryDto.builder()
                    .productId(battery.getProductId())
                    .modelName(battery.getModelName())
                    .build();
        });

        // Calling service method
        PageResponse<CommercialVBatteryDto> result = commercialVBatteryServiceImp.findAllBatteriesByModel(modelId, pageNumber, pageSize, sortBy);

        // Asserting result
        assertEquals(page.getNumber(), result.getPageNumber());
        assertEquals(page.getSize(), result.getPageSize());
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(batteryDtos.getFirst().getModelName(), result.getData().getFirst().getModelName());
    }


    @Test
    public void commercialVBatteryService_findBatteryById_returnCommercialVBattery(){
        //given
        Integer batteryId = 1;
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockCommercialVBattery));

        //when
        CommercialVBatteryDto result = commercialVBatteryServiceImp.findBatteryById(batteryId);
        //then
        assertNotNull(result);
        assertEquals(mockCommercialVBatteryDto, result);
    }

    @Test
    public void commercialVBatteryService_findBatteryById_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVBatteryServiceImp.findBatteryById(batteryId));
    }

    @Test
    public void commercialVBatteryService_updateBattery_returnCommercialVBattery() {
        //given
        Integer batteryId = 1;
        CommercialVBatteryDto commercialVBatteryDto = CommercialVBatteryDto.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();
        CommercialVBattery updatedCommercialVBattery = CommercialVBattery.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();

        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockCommercialVBattery));
        when(modelMapper.map(Mockito.any(CommercialVBatteryDto.class), Mockito.eq(CommercialVBattery.class))).thenReturn(updatedCommercialVBattery);
        when(modelMapper.map(Mockito.any(CommercialVBattery.class), Mockito.eq(CommercialVBatteryDto.class))).thenReturn(commercialVBatteryDto);
        when(commercialVBatteryRepo.save(updatedCommercialVBattery)).thenReturn(updatedCommercialVBattery);

        //when
        CommercialVBatteryDto result = commercialVBatteryServiceImp.updateBattery(batteryId, commercialVBatteryDto);
        //then
        assertEquals("Updated Battery", result.getModelName());
    }

    @Test
    public void commercialVBatteryService_updateBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVBatteryServiceImp.updateBattery(batteryId, mockCommercialVBatteryDto));
    }

    @Test
    public void commercialVBatteryService_deleteBattery_returnVoid() {
        //given
        Integer batteryId = 1;
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockCommercialVBattery));
        Mockito.doNothing().when(commercialVBatteryRepo).delete(mockCommercialVBattery);

        //when&then
        assertAll(()->commercialVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(commercialVBatteryRepo, Mockito.times(1)).delete(mockCommercialVBattery);
    }

    @Test
    public void commercialVBatteryService_deleteBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(commercialVBatteryRepo, Mockito.never()).delete(Mockito.any());
    }
}