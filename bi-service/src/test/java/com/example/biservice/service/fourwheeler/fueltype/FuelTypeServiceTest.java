package com.example.biservice.service.fourwheeler.fueltype;

import com.example.biservice.entity.fourwheeler.FourVModel;
import com.example.biservice.entity.FuelType;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.FuelTypeDto;
import com.example.biservice.repository.fourwheeler.FourVModelRepo;
import com.example.biservice.repository.FuelTypeRepo;
import com.example.biservice.service.fueltype.FuelTypeServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class FuelTypeServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private FuelTypeRepo fuelTypeRepo;

    @Mock
    private FourVModelRepo fourVModelRepo;

    @InjectMocks
    private FuelTypeServiceImp fuelTypeServiceImp;

    private FuelType mockFuelType;

    private FuelTypeDto mockFuelTypeDto;

    @BeforeEach
    void setUp() {
        mockFuelType = FuelType.builder()
                .fuelTypeId(1)
                .fuelType("Petrol")
                .build();
        mockFuelTypeDto = FuelTypeDto.builder()
                .fuelTypeId(1)
                .fuelType("Petrol")
                .build();

        when(modelMapper.map(Mockito.any(FuelTypeDto.class),Mockito.eq(FuelType.class))).thenReturn(mockFuelType);
        when(modelMapper.map(Mockito.any(FuelType.class),Mockito.eq(FuelTypeDto.class))).thenReturn(mockFuelTypeDto);
    }

    @AfterEach
    void tearDown() {
        mockFuelType = null;
        mockFuelTypeDto = null;
    }

    @Test
    public void fuelTypeService_findAllFuelTypes_returnEmptyList() {
        //given
        when(fuelTypeRepo.findAll()).thenReturn(List.of());
        //when
        List<FuelTypeDto> result = fuelTypeServiceImp.findAllFuelTypes();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void fuelTypeService_findAllFuelTypes_returnFuelTypeList() {
        List<FuelType> fuelTypes = Arrays.asList(
                FuelType.builder()
                        .fuelTypeId(1)
                        .fuelType("Petrol")
                        .build(),
                FuelType.builder()
                        .fuelTypeId(2)
                        .fuelType("Diesel")
                        .build()
        );
        when(fuelTypeRepo.findAll()).thenReturn(fuelTypes);
        when(modelMapper.map(Mockito.any(FuelType.class), Mockito.eq(FuelTypeDto.class)))
                .thenAnswer(invocation -> {
                    FuelType fuelType = invocation.getArgument(0);
                    return new FuelTypeDto(fuelType.getFuelTypeId(), fuelType.getFuelType());
                });
        //when
        List<FuelTypeDto> result = fuelTypeServiceImp.findAllFuelTypes();
        //then
        assertEquals(fuelTypes.size(),result.size());
        assertEquals("Petrol", result.getFirst().getFuelType());
        assertEquals("Diesel", result.getLast().getFuelType());
    }

    @Test
    public void fuelTypeService_findAllFuelByModel_returnFuelTypeList(){
        //given
        Integer modelId = 1;
        FourVModel mockModel = FourVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        List<FuelType> fuelTypes = Arrays.asList(
                FuelType.builder()
                        .fuelTypeId(1)
                        .fuelType("Petrol")
                        .build(),
                FuelType.builder()
                        .fuelTypeId(2)
                        .fuelType("Diesel")
                        .build()
        );
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.of(mockModel));
        when(fuelTypeRepo.findFuelTypesByFourVModel(anyInt())).thenReturn(fuelTypes);
        when(modelMapper.map(Mockito.any(FuelType.class), Mockito.eq(FuelTypeDto.class)))
                .thenAnswer(invocation -> {
                    FuelType fuelType = invocation.getArgument(0);
                    return new FuelTypeDto(fuelType.getFuelTypeId(), fuelType.getFuelType());
                });

        //when
        List<FuelTypeDto> result = fuelTypeServiceImp.findAllFuelTypesByFourVModel(modelId);
        //then
        assertEquals(fuelTypes.size(),result.size());
        assertEquals("Petrol", result.getFirst().getFuelType());
        assertEquals("Diesel", result.getLast().getFuelType());
    }

    @Test
    public void fuelTypeService_findFuelTypeById_returnFuelType(){
        //given
        Integer fuelTypeId = 1;
        when(fuelTypeRepo.findById(fuelTypeId)).thenReturn(Optional.of(mockFuelType));

        //when
        FuelTypeDto result = fuelTypeServiceImp.findFuelById(fuelTypeId);
        //then
        assertNotNull(result);
        assertEquals(mockFuelTypeDto, result);
    }

    @Test
    public void fuelTypeService_findFuelTypeById_throwResourceNotFoundException() {
        //given
        Integer fuelTypeId = 1;
        when(fuelTypeRepo.findById(fuelTypeId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fuelTypeServiceImp.findFuelById(fuelTypeId));
    }
}