package com.example.biservice.service.fourwheeler.model;

import com.example.biservice.entity.fourwheeler.FourVBattery;
import com.example.biservice.entity.fourwheeler.FourVBrand;
import com.example.biservice.entity.fourwheeler.FourVModel;
import com.example.biservice.entity.FuelType;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.fourwheeler.FourVModelDto;
import com.example.biservice.repository.fourwheeler.FourVBatteryRepo;
import com.example.biservice.repository.fourwheeler.FourVBrandRepo;
import com.example.biservice.repository.fourwheeler.FourVModelRepo;
import com.example.biservice.repository.FuelTypeRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class FourVModelServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private FourVModelRepo fourVModelRepo;

    @Mock
    private FourVBrandRepo fourVBrandRepo;

    @Mock
    private FuelTypeRepo fuelTypeRepo;

    @Mock
    private FourVBatteryRepo fourVBatteryRepo;

    @InjectMocks
    private FourVModelServiceImp fourVModelServiceImp;

    private FourVModel mockFourVModel;
    private FourVModelDto mockFourVModelDto;

    @BeforeEach
    void setUp() {
        mockFourVModel = FourVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        mockFourVModelDto = FourVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();

        when(modelMapper.map(Mockito.any(FourVModelDto.class),Mockito.eq(FourVModel.class))).thenReturn(mockFourVModel);
        when(modelMapper.map(Mockito.any(FourVModel.class),Mockito.eq(FourVModelDto.class))).thenReturn(mockFourVModelDto);
    }

    @AfterEach
    void tearDown() {
        mockFourVModel = null;
        mockFourVModelDto = null;
    }

    @Test
    public void fourVModelService_createModel_returnFourVModel(){
        //given
        Integer brandId = 1;
        FourVBrand mockBrand = FourVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        mockFourVModel = FourVModel.builder()
                .modelName("Model A")
                .build();
        mockFourVModelDto = FourVModelDto.builder()
                .modelName("Model A")
                .build();
        FourVModel savedModel = FourVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        FourVModelDto savedModelDto = FourVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        when((modelMapper.map(Mockito.any(FourVModelDto.class), Mockito.eq(FourVModel.class)))).thenReturn(mockFourVModel);
        when((modelMapper.map(Mockito.any(FourVModel.class), Mockito.eq(FourVModelDto.class)))).thenReturn(savedModelDto);
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(fourVModelRepo.save(Mockito.any(FourVModel.class))).thenReturn(savedModel);

        //when
        FourVModelDto result = fourVModelServiceImp.createModel(mockFourVModelDto,brandId);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockFourVModelDto.getModelName());
    }

    @Test
    public void fourVModelService_createModel_throwUniqueConstraintException(){
        Integer brandId = 1;
        FourVBrand mockBrand = FourVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(fourVModelRepo.save(Mockito.any(FourVModel.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->fourVModelServiceImp.createModel(mockFourVModelDto,brandId));
    }

    @Test
    public void fourVModelService_createModel_throwResourceNotFoundException(){
        //given
        Integer brandId = 1;
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class,()->fourVModelServiceImp.createModel(mockFourVModelDto,brandId));
    }

    @Test
    public void fourVModelService_findAllModels_returnEmptyList() {
        //given
        when(fourVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<FourVModelDto> result = fourVModelServiceImp.findAllModels();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void fourVModelService_findAllModels_returnFourVModelList() {
        //given
        List<FourVModel> fourVModels = Arrays.asList(
                FourVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                FourVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(fourVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(fourVModels);
        when(modelMapper.map(Mockito.any(FourVModel.class), Mockito.eq(FourVModelDto.class)))
                .thenAnswer(invocation -> {
                    FourVModel model = invocation.getArgument(0);
                    return new FourVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<FourVModelDto> result = fourVModelServiceImp.findAllModels();
        //then
        assertEquals(fourVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void fourVModelService_findAllModelsByBrand_returnFourVModelList(){
        //given
        Integer brandId = 1;
        FourVBrand mockBrand = FourVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        List<FourVModel> fourVModels = Arrays.asList(
                FourVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                FourVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(fourVModelRepo.findAllByFourVBrandOrderByModelName(Mockito.any(FourVBrand.class))).thenReturn(fourVModels);
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(modelMapper.map(Mockito.any(FourVModel.class), Mockito.eq(FourVModelDto.class)))
                .thenAnswer(invocation -> {
                    FourVModel model = invocation.getArgument(0);
                    return new FourVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<FourVModelDto> result = fourVModelServiceImp.findAllModelsByBrand(brandId);
        //then
        assertEquals(fourVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void fourVModelService_findModelById_returnFourVModel(){
        //given
        Integer modelId = 1;
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.of(mockFourVModel));

        //when
        FourVModelDto result = fourVModelServiceImp.findModelById(modelId);
        //then
        assertNotNull(result);
        assertEquals(mockFourVModelDto, result);
    }

    @Test
    public void fourVModelService_findModelById_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVModelServiceImp.findModelById(modelId));
    }

    @Test
    public void fourVModelService_updateModel_returnFourVModel() {
        //given
        Integer modelId = 1;
        FourVModelDto fourVModelDto = FourVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        FourVModel updatedFourVModel = FourVModel.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();

        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.of(mockFourVModel));
        when(modelMapper.map(Mockito.any(FourVModelDto.class), Mockito.eq(FourVModel.class))).thenReturn(updatedFourVModel);
        when(modelMapper.map(Mockito.any(FourVModel.class), Mockito.eq(FourVModelDto.class))).thenReturn(fourVModelDto);
        when(fourVModelRepo.save(Mockito.any(FourVModel.class))).thenReturn(updatedFourVModel);

        //when
        FourVModelDto result = fourVModelServiceImp.updateModel(modelId, fourVModelDto);
        //then
        assertEquals("Updated Model", result.getModelName());
    }

    @Test
    public void fourVModelService_updateModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVModelServiceImp.updateModel(modelId, mockFourVModelDto));
    }

    @Test
    public void fourVModelService_deleteModel_returnVoid() {
        //given
        Integer modelId = 1;
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.of(mockFourVModel));
        Mockito.doNothing().when(fourVModelRepo).delete(mockFourVModel);

        //when&then
        assertAll(()->fourVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(fourVModelRepo, Mockito.times(1)).delete(mockFourVModel);
    }

    @Test
    public void fourVModelService_deleteModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(fourVModelRepo, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void fourVModelService_addFuelTypeToModel_returnVoid() {
        //given
        Integer modelId = 1;
        Integer fuelTypeId = 1;
        FuelType fuelType = FuelType.builder()
                .fuelTypeId(1)
                .fuelType("Petrol")
                .build();
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.of(mockFourVModel));
        when(fuelTypeRepo.findById(fuelTypeId)).thenReturn(Optional.of(fuelType));
        when(fourVModelRepo.save(Mockito.any(FourVModel.class))).thenReturn(mockFourVModel);

        //when&then
        assertAll(()->fourVModelServiceImp.addFuelTypeToModel(modelId,fuelTypeId));
    }

    @Test
    public void fourVModelService_addFuelTypeToModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        Integer fuelTypeId = 1;

        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.empty());
        when(fuelTypeRepo.findById(fuelTypeId)).thenReturn(Optional.empty());
        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVModelServiceImp.addFuelTypeToModel(modelId,fuelTypeId));
        //then
        Mockito.verify(fourVModelRepo, Mockito.never()).save(Mockito.any()); // Ensure that save method is never called
    }

    @Test
    public void fourVModelService_addBatteryToModel_returnVoid() {
        //given
        Integer modelId = 1;
        Integer fuelTypeId = 1;
        Integer batteryId = 1;
        FourVBattery fourVBattery = FourVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        FuelType fuelType = FuelType.builder()
                .fuelTypeId(1)
                .fuelType("Petrol")
                .build();
        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.of(mockFourVModel));
        when(fuelTypeRepo.findById(fuelTypeId)).thenReturn(Optional.of(fuelType));
        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(fourVBattery));
        when(fourVModelRepo.save(Mockito.any(FourVModel.class))).thenReturn(mockFourVModel);

        //when&then
        assertAll(()->fourVModelServiceImp.addBatteryToModel(modelId,fuelTypeId,batteryId));
    }

    @Test
    public void fourVModelService_addBatteryToModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        Integer fuelTypeId = 1;
        Integer batteryId = 1;

        when(fourVModelRepo.findById(modelId)).thenReturn(Optional.empty());
        when(fuelTypeRepo.findById(fuelTypeId)).thenReturn(Optional.empty());
        when(fourVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());
        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVModelServiceImp.addBatteryToModel(modelId,fuelTypeId,batteryId));
        //then
        Mockito.verify(fourVModelRepo, Mockito.never()).save(Mockito.any()); // Ensure that save method is never called
    }
}