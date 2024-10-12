package com.example.biservice.service.threewheeler.battery;

import com.example.biservice.entity.threewheeler.ThreeVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.threewheeler.ThreeVBatteryDto;
import com.example.biservice.repository.threewheeler.ThreeVBatteryRepo;
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

@SpringBootTest(properties = "spring.profiles.active=test")
class ThreeVBatteryServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ThreeVBatteryRepo threeVBatteryRepo;
    @InjectMocks
    private ThreeVBatteryServiceImp threeVBatteryServiceImp;

    private ThreeVBattery mockThreeVBattery;
    private ThreeVBatteryDto mockThreeVBatteryDto;

    @BeforeEach
    void setUp() {
        mockThreeVBattery = ThreeVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        mockThreeVBatteryDto = ThreeVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when(modelMapper.map(Mockito.any(ThreeVBatteryDto.class), Mockito.eq(ThreeVBattery.class))).thenReturn(mockThreeVBattery);
        when(modelMapper.map(Mockito.any(ThreeVBattery.class), Mockito.eq(ThreeVBatteryDto.class))).thenReturn(mockThreeVBatteryDto);
    }

    @AfterEach
    void tearDown() {
        mockThreeVBattery = null;
        mockThreeVBatteryDto = null;
    }

    @Test
    public void threeVBatteryService_createBattery_returnThreeVBattery(){
        //given
        mockThreeVBattery = ThreeVBattery.builder()
                .modelName("abc")
                .build();
        mockThreeVBatteryDto = ThreeVBatteryDto.builder()
                .modelName("abc")
                .build();
        ThreeVBattery savedBattery = ThreeVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        ThreeVBatteryDto savedBatteryDto = ThreeVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when((modelMapper.map(Mockito.any(ThreeVBatteryDto.class), Mockito.eq(ThreeVBattery.class)))).thenReturn(mockThreeVBattery);
        when((modelMapper.map(Mockito.any(ThreeVBattery.class), Mockito.eq(ThreeVBatteryDto.class)))).thenReturn(savedBatteryDto);
        when(threeVBatteryRepo.save(Mockito.any(ThreeVBattery.class))).thenReturn(savedBattery);

        //when
        ThreeVBatteryDto result = threeVBatteryServiceImp.createBattery(mockThreeVBatteryDto);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockThreeVBatteryDto.getModelName());
    }

    @Test
    public void threeVBatteryService_createBattery_throwUniqueConstraintException(){
        //given
        when(threeVBatteryRepo.save(Mockito.any(ThreeVBattery.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->threeVBatteryServiceImp.createBattery(mockThreeVBatteryDto));
    }

    @Test
    public void threeVBatteryService_findAllBatteries_returnEmptyList() {
        //given
        when(threeVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<ThreeVBatteryDto> result = threeVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void threeVBatteryService_findAllBatteries_returnThreeVBatteryList() {
        //given
        List<ThreeVBattery> threeVBatteries = Arrays.asList(
                ThreeVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                ThreeVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(threeVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(threeVBatteries);
        when(modelMapper.map(Mockito.any(ThreeVBattery.class), Mockito.eq(ThreeVBatteryDto.class)))
                .thenAnswer(invocation -> {
                    ThreeVBattery battery = invocation.getArgument(0);
                    return ThreeVBatteryDto.builder()
                            .productId(battery.getProductId())
                            .modelName(battery.getModelName())
                            .build();
                });

        //when
        List<ThreeVBatteryDto> result = threeVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(threeVBatteries.size(), result.size());
        assertEquals("abc", result.get(0).getModelName());// Assert sorted order
        assertEquals("acb", result.get(1).getModelName());
    }

    @Test
    public void threeVBatteryService_findAllBatteriesByModel_returnThreeVBatteryPage() {
        //given
        Integer modelId = 1;
        Integer fuelTypeId = 1;
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "modelName";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        // Mocking repository to return page of ThreeVBattery objects
        List<ThreeVBattery> batteries = Arrays.asList(
                ThreeVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                ThreeVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        Page<ThreeVBattery> page = new PageImpl<>(batteries, pageable, batteries.size());
        when(threeVBatteryRepo.findBatteriesByModelAndFuel(modelId,fuelTypeId, pageable)).thenReturn(page);

        // Mocking modelMapper to map ThreeVBattery to ThreeVBatteryDto
        List<ThreeVBatteryDto> batteryDtos = batteries.stream()
                .map(battery -> ThreeVBatteryDto.builder()
                        .productId(battery.getProductId())
                        .modelName(battery.getModelName())
                        .build())
                .collect(Collectors.toList());
        when(modelMapper.map(Mockito.any(ThreeVBattery.class), Mockito.eq(ThreeVBatteryDto.class))).thenAnswer(invocation -> {
            ThreeVBattery battery = invocation.getArgument(0);
            return ThreeVBatteryDto.builder()
                    .productId(battery.getProductId())
                    .modelName(battery.getModelName())
                    .build();
        });

        // Calling service method
        PageResponse<ThreeVBatteryDto> result = threeVBatteryServiceImp.findAllBatteriesByModelAndFuel(modelId, fuelTypeId, pageNumber, pageSize, sortBy);

        // Asserting result
        assertEquals(page.getNumber(), result.getPageNumber());
        assertEquals(page.getSize(), result.getPageSize());
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(batteryDtos.getFirst().getModelName(), result.getData().getFirst().getModelName());
    }


    @Test
    public void threeVBatteryService_findBatteryById_returnThreeVBattery(){
        //given
        Integer batteryId = 1;
        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockThreeVBattery));

        //when
        ThreeVBatteryDto result = threeVBatteryServiceImp.findBatteryById(batteryId);
        //then
        assertNotNull(result);
        assertEquals(mockThreeVBatteryDto, result);
    }

    @Test
    public void threeVBatteryService_findBatteryById_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVBatteryServiceImp.findBatteryById(batteryId));
    }

    @Test
    public void threeVBatteryService_updateBattery_returnThreeVBattery() {
        //given
        Integer batteryId = 1;
        ThreeVBatteryDto threeVBatteryDto = ThreeVBatteryDto.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();
        ThreeVBattery updatedThreeVBattery = ThreeVBattery.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();

        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockThreeVBattery));
        when(modelMapper.map(Mockito.any(ThreeVBatteryDto.class), Mockito.eq(ThreeVBattery.class))).thenReturn(updatedThreeVBattery);
        when(modelMapper.map(Mockito.any(ThreeVBattery.class), Mockito.eq(ThreeVBatteryDto.class))).thenReturn(threeVBatteryDto);
        when(threeVBatteryRepo.save(updatedThreeVBattery)).thenReturn(updatedThreeVBattery);

        //when
        ThreeVBatteryDto result = threeVBatteryServiceImp.updateBattery(batteryId, threeVBatteryDto);
        //then
        assertEquals("Updated Battery", result.getModelName());
    }

    @Test
    public void threeVBatteryService_updateBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVBatteryServiceImp.updateBattery(batteryId, mockThreeVBatteryDto));
    }

    @Test
    public void threeVBatteryService_deleteBattery_returnVoid() {
        //given
        Integer batteryId = 1;
        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockThreeVBattery));
        Mockito.doNothing().when(threeVBatteryRepo).delete(mockThreeVBattery);

        //when&then
        assertAll(()->threeVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(threeVBatteryRepo, Mockito.times(1)).delete(mockThreeVBattery);
    }

    @Test
    public void threeVBatteryService_deleteBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(threeVBatteryRepo, Mockito.never()).delete(Mockito.any());
    }

}