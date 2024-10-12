package com.example.biservice.service.fourwheeler.battery;

import com.example.biservice.entity.fourwheeler.FourVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.fourwheeler.FourVBatteryDto;
import com.example.biservice.repository.fourwheeler.FourVBatteryRepo;
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
class FourVBatteryServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private FourVBatteryRepo fourVBatteryRepo;
    @InjectMocks
    private FourVBatteryServiceImp fourVBatteryServiceImp;

    private FourVBattery mockFourVBattery;
    private FourVBatteryDto mockFourVBatteryDto;

    @BeforeEach
    void setUp() {
        mockFourVBattery = FourVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        mockFourVBatteryDto = FourVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when(modelMapper.map(Mockito.any(FourVBatteryDto.class), Mockito.eq(FourVBattery.class))).thenReturn(mockFourVBattery);
        when(modelMapper.map(Mockito.any(FourVBattery.class), Mockito.eq(FourVBatteryDto.class))).thenReturn(mockFourVBatteryDto);
    }

    @AfterEach
    void tearDown() {
        mockFourVBattery = null;
        mockFourVBatteryDto = null;
    }

    @Test
    public void fourVBatteryService_createBattery_returnFourVBattery(){
        //given
        mockFourVBattery = FourVBattery.builder()
                .modelName("abc")
                .build();
        mockFourVBatteryDto = FourVBatteryDto.builder()
                .modelName("abc")
                .build();
        FourVBattery savedBattery = FourVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        FourVBatteryDto savedBatteryDto = FourVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when((modelMapper.map(Mockito.any(FourVBatteryDto.class), Mockito.eq(FourVBattery.class)))).thenReturn(mockFourVBattery);
        when((modelMapper.map(Mockito.any(FourVBattery.class), Mockito.eq(FourVBatteryDto.class)))).thenReturn(savedBatteryDto);
        when(fourVBatteryRepo.save(Mockito.any(FourVBattery.class))).thenReturn(savedBattery);

        //when
        FourVBatteryDto result = fourVBatteryServiceImp.createBattery(mockFourVBatteryDto);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockFourVBatteryDto.getModelName());
    }

    @Test
    public void fourVBatteryService_createBattery_throwUniqueConstraintException(){
        //given
        when(fourVBatteryRepo.save(Mockito.any(FourVBattery.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->fourVBatteryServiceImp.createBattery(mockFourVBatteryDto));
    }

    @Test
    public void fourVBatteryService_findAllBatteries_returnEmptyList() {
        //given
        when(fourVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<FourVBatteryDto> result = fourVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void fourVBatteryService_findAllBatteries_returnFourVBatteryList() {
        //given
        List<FourVBattery> fourVBatteries = Arrays.asList(
                FourVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                FourVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(fourVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(fourVBatteries);
        when(modelMapper.map(Mockito.any(FourVBattery.class), Mockito.eq(FourVBatteryDto.class)))
                .thenAnswer(invocation -> {
                    FourVBattery battery = invocation.getArgument(0);
                    return FourVBatteryDto.builder()
                            .productId(battery.getProductId())
                            .modelName(battery.getModelName())
                            .build();
                });

        //when
        List<FourVBatteryDto> result = fourVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(fourVBatteries.size(), result.size());
        assertEquals("abc", result.get(0).getModelName());// Assert sorted order
        assertEquals("acb", result.get(1).getModelName());
    }

    @Test
    public void fourVBatteryService_findAllBatteriesByModel_returnFourVBatteryPage() {
        //given
        Integer modelId = 1;
        Integer fuelId = 1;
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "modelName";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        // Mocking repository to return page of FourVBattery objects
        List<FourVBattery> batteries = Arrays.asList(
                FourVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                FourVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        Page<FourVBattery> page = new PageImpl<>(batteries, pageable, batteries.size());
        when(fourVBatteryRepo.findBatteriesByModelAndFuel(modelId,fuelId,pageable)).thenReturn(page);

        // Mocking modelMapper to map FourVBattery to FourVBatteryDto
        List<FourVBatteryDto> batteryDtos = batteries.stream()
                .map(battery -> FourVBatteryDto.builder()
                        .productId(battery.getProductId())
                        .modelName(battery.getModelName())
                        .build())
                .collect(Collectors.toList());
        when(modelMapper.map(Mockito.any(FourVBattery.class), Mockito.eq(FourVBatteryDto.class))).thenAnswer(invocation -> {
            FourVBattery battery = invocation.getArgument(0);
            return FourVBatteryDto.builder()
                    .productId(battery.getProductId())
                    .modelName(battery.getModelName())
                    .build();
        });

        // Calling service method
        PageResponse<FourVBatteryDto> result = fourVBatteryServiceImp.findAllBatteriesByModelAndFuelType(modelId, fuelId, pageNumber, pageSize, sortBy);

        // Asserting result
        assertEquals(page.getNumber(), result.getPageNumber());
        assertEquals(page.getSize(), result.getPageSize());
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(batteryDtos.getFirst().getModelName(), result.getData().getFirst().getModelName());
    }


    @Test
    public void fourVBatteryService_findBatteryById_returnFourVBattery(){
        //given
        Integer batteryId = 1;
        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockFourVBattery));

        //when
        FourVBatteryDto result = fourVBatteryServiceImp.findBatteryById(batteryId);
        //then
        assertNotNull(result);
        assertEquals(mockFourVBatteryDto, result);
    }

    @Test
    public void fourVBatteryService_findBatteryById_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVBatteryServiceImp.findBatteryById(batteryId));
    }

    @Test
    public void fourVBatteryService_updateBattery_returnFourVBattery() {
        //given
        Integer batteryId = 1;
        FourVBatteryDto fourVBatteryDto = FourVBatteryDto.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();
        FourVBattery updatedFourVBattery = FourVBattery.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();

        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockFourVBattery));
        when(modelMapper.map(Mockito.any(FourVBatteryDto.class), Mockito.eq(FourVBattery.class))).thenReturn(updatedFourVBattery);
        when(modelMapper.map(Mockito.any(FourVBattery.class), Mockito.eq(FourVBatteryDto.class))).thenReturn(fourVBatteryDto);
        when(fourVBatteryRepo.save(updatedFourVBattery)).thenReturn(updatedFourVBattery);

        //when
        FourVBatteryDto result = fourVBatteryServiceImp.updateBattery(batteryId, fourVBatteryDto);
        //then
        assertEquals("Updated Battery", result.getModelName());
    }

    @Test
    public void fourVBatteryService_updateBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVBatteryServiceImp.updateBattery(batteryId, mockFourVBatteryDto));
    }

    @Test
    public void fourVBatteryService_deleteBattery_returnVoid() {
        //given
        Integer batteryId = 1;
        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockFourVBattery));
        Mockito.doNothing().when(fourVBatteryRepo).delete(mockFourVBattery);

        //when&then
        assertAll(()->fourVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(fourVBatteryRepo, Mockito.times(1)).delete(mockFourVBattery);
    }

    @Test
    public void fourVBatteryService_deleteBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(fourVBatteryRepo, Mockito.never()).delete(Mockito.any());
    }
}