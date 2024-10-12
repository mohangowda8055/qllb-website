package com.example.biservice.service.twowheeler.battery;

import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.twowheeler.TwoVBatteryDto;
import com.example.biservice.repository.twowheeler.TwoVBatteryRepo;
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
class TwoVBatteryServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TwoVBatteryRepo twoVBatteryRepo;
    @InjectMocks
    private TwoVBatteryServiceImp twoVBatteryServiceImp;

    private TwoVBattery mockTwoVBattery;
    private TwoVBatteryDto mockTwoVBatteryDto;

    @BeforeEach
    void setUp() {
        mockTwoVBattery = TwoVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        mockTwoVBatteryDto = TwoVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when(modelMapper.map(Mockito.any(TwoVBatteryDto.class), Mockito.eq(TwoVBattery.class))).thenReturn(mockTwoVBattery);
        when(modelMapper.map(Mockito.any(TwoVBattery.class), Mockito.eq(TwoVBatteryDto.class))).thenReturn(mockTwoVBatteryDto);
    }

    @AfterEach
    void tearDown() {
        mockTwoVBattery = null;
        mockTwoVBatteryDto = null;
    }

    @Test
    public void twoVBatteryService_createBattery_returnTwoVBattery(){
        //given
        mockTwoVBattery = TwoVBattery.builder()
                .modelName("abc")
                .build();
        mockTwoVBatteryDto = TwoVBatteryDto.builder()
                .modelName("abc")
                .build();
        TwoVBattery savedBattery = TwoVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        TwoVBatteryDto savedBatteryDto = TwoVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when((modelMapper.map(Mockito.any(TwoVBatteryDto.class), Mockito.eq(TwoVBattery.class)))).thenReturn(mockTwoVBattery);
        when((modelMapper.map(Mockito.any(TwoVBattery.class), Mockito.eq(TwoVBatteryDto.class)))).thenReturn(savedBatteryDto);
        when(twoVBatteryRepo.save(Mockito.any(TwoVBattery.class))).thenReturn(savedBattery);

        //when
        TwoVBatteryDto result = twoVBatteryServiceImp.createBattery(mockTwoVBatteryDto);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockTwoVBatteryDto.getModelName());
    }

    @Test
    public void twoVBatteryService_createBattery_throwUniqueConstraintException(){
        //given
        when(twoVBatteryRepo.save(Mockito.any(TwoVBattery.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->twoVBatteryServiceImp.createBattery(mockTwoVBatteryDto));
    }

    @Test
    public void twoVBatteryService_findAllBatteries_returnEmptyList() {
        //given
        when(twoVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<TwoVBatteryDto> result = twoVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void twoVBatteryService_findAllBatteries_returnTwoVBatteryList() {
        //given
        List<TwoVBattery> twoVBatteries = Arrays.asList(
                TwoVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                TwoVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(twoVBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(twoVBatteries);
        when(modelMapper.map(Mockito.any(TwoVBattery.class), Mockito.eq(TwoVBatteryDto.class)))
                .thenAnswer(invocation -> {
                    TwoVBattery battery = invocation.getArgument(0);
                    return TwoVBatteryDto.builder()
                            .productId(battery.getProductId())
                            .modelName(battery.getModelName())
                            .build();
                });

        //when
        List<TwoVBatteryDto> result = twoVBatteryServiceImp.findAllBatteries("modelName");
        //then
        assertEquals(twoVBatteries.size(), result.size());
        assertEquals("abc", result.get(0).getModelName());// Assert sorted order
        assertEquals("acb", result.get(1).getModelName());
    }

    @Test
    public void twoVBatteryService_findAllBatteriesByModel_returnTwoVBatteryPage() {
        //given
        Integer modelId = 1;
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "modelName";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        // Mocking repository to return page of TwoVBattery objects
        List<TwoVBattery> batteries = Arrays.asList(
                TwoVBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                TwoVBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        Page<TwoVBattery> page = new PageImpl<>(batteries, pageable, batteries.size());
        when(twoVBatteryRepo.findBatteriesByModel(modelId, pageable)).thenReturn(page);

        // Mocking modelMapper to map TwoVBattery to TwoVBatteryDto
        List<TwoVBatteryDto> batteryDtos = batteries.stream()
                .map(battery -> TwoVBatteryDto.builder()
                        .productId(battery.getProductId())
                        .modelName(battery.getModelName())
                        .build())
                .collect(Collectors.toList());
        when(modelMapper.map(Mockito.any(TwoVBattery.class), Mockito.eq(TwoVBatteryDto.class))).thenAnswer(invocation -> {
            TwoVBattery battery = invocation.getArgument(0);
            return TwoVBatteryDto.builder()
                    .productId(battery.getProductId())
                    .modelName(battery.getModelName())
                    .build();
        });

        // Calling service method
        PageResponse<TwoVBatteryDto> result = twoVBatteryServiceImp.findAllBatteriesByModel(modelId, pageNumber, pageSize, sortBy);

        // Asserting result
        assertEquals(page.getNumber(), result.getPageNumber());
        assertEquals(page.getSize(), result.getPageSize());
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(batteryDtos.getFirst().getModelName(), result.getData().getFirst().getModelName());
    }


    @Test
    public void twoVBatteryService_findBatteryById_returnTwoVBattery(){
        //given
        Integer batteryId = 1;
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockTwoVBattery));

        //when
        TwoVBatteryDto result = twoVBatteryServiceImp.findBatteryById(batteryId);
        //then
        assertNotNull(result);
        assertEquals(mockTwoVBatteryDto, result);
    }

    @Test
    public void twoVBatteryService_findBatteryById_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVBatteryServiceImp.findBatteryById(batteryId));
    }

    @Test
    public void twoVBatteryService_updateBattery_returnTwoVBattery() {
        //given
        Integer batteryId = 1;
        TwoVBatteryDto twoVBatteryDto = TwoVBatteryDto.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();
        TwoVBattery updatedTwoVBattery = TwoVBattery.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();

        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockTwoVBattery));
        when(modelMapper.map(Mockito.any(TwoVBatteryDto.class), Mockito.eq(TwoVBattery.class))).thenReturn(updatedTwoVBattery);
        when(modelMapper.map(Mockito.any(TwoVBattery.class), Mockito.eq(TwoVBatteryDto.class))).thenReturn(twoVBatteryDto);
        when(twoVBatteryRepo.save(updatedTwoVBattery)).thenReturn(updatedTwoVBattery);

        //when
        TwoVBatteryDto result = twoVBatteryServiceImp.updateBattery(batteryId, twoVBatteryDto);
        //then
        assertEquals("Updated Battery", result.getModelName());
    }

    @Test
    public void twoVBatteryService_updateBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVBatteryServiceImp.updateBattery(batteryId, mockTwoVBatteryDto));
    }

    @Test
    public void twoVBatteryService_deleteBattery_returnVoid() {
        //given
        Integer batteryId = 1;
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockTwoVBattery));
        Mockito.doNothing().when(twoVBatteryRepo).delete(mockTwoVBattery);

        //when&then
        assertAll(()->twoVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(twoVBatteryRepo, Mockito.times(1)).delete(mockTwoVBattery);
    }

    @Test
    public void twoVBatteryService_deleteBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVBatteryServiceImp.deleteBattery(batteryId));
        //then
        Mockito.verify(twoVBatteryRepo, Mockito.never()).delete(Mockito.any());
    }
}