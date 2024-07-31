package com.example.biservice.service.threewheeler.model;

import com.example.biservice.entity.FuelType;
import com.example.biservice.entity.threewheeler.ThreeVBattery;
import com.example.biservice.entity.threewheeler.ThreeVBrand;
import com.example.biservice.entity.threewheeler.ThreeVModel;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.threewheeler.ThreeVModelDto;
import com.example.biservice.repository.FuelTypeRepo;
import com.example.biservice.repository.threewheeler.ThreeVBatteryRepo;
import com.example.biservice.repository.threewheeler.ThreeVBrandRepo;
import com.example.biservice.repository.threewheeler.ThreeVModelRepo;
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

@SpringBootTest
class ThreeVModelServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ThreeVModelRepo threeVModelRepo;

    @Mock
    private ThreeVBrandRepo threeVBrandRepo;

    @Mock
    private ThreeVBatteryRepo threeVBatteryRepo;

    @Mock
    private FuelTypeRepo fuelTypeRepo;

    @InjectMocks
    private ThreeVModelServiceImp threeVModelServiceImp;

    private ThreeVModel mockThreeVModel;
    private ThreeVModelDto mockThreeVModelDto;

    @BeforeEach
    void setUp() {
        mockThreeVModel = ThreeVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        mockThreeVModelDto = ThreeVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();

        when(modelMapper.map(Mockito.any(ThreeVModelDto.class),Mockito.eq(ThreeVModel.class))).thenReturn(mockThreeVModel);
        when(modelMapper.map(Mockito.any(ThreeVModel.class),Mockito.eq(ThreeVModelDto.class))).thenReturn(mockThreeVModelDto);
    }

    @AfterEach
    void tearDown() {
        mockThreeVModel = null;
        mockThreeVModelDto = null;
    }

    @Test
    public void threeVModelService_createModel_returnThreeVModel(){
        //given
        Integer brandId = 1;
        ThreeVBrand mockBrand = ThreeVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        mockThreeVModel = ThreeVModel.builder()
                .modelName("Model A")
                .build();
        mockThreeVModelDto = ThreeVModelDto.builder()
                .modelName("Model A")
                .build();
        ThreeVModel savedModel = ThreeVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        ThreeVModelDto savedModelDto = ThreeVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        when((modelMapper.map(Mockito.any(ThreeVModelDto.class), Mockito.eq(ThreeVModel.class)))).thenReturn(mockThreeVModel);
        when((modelMapper.map(Mockito.any(ThreeVModel.class), Mockito.eq(ThreeVModelDto.class)))).thenReturn(savedModelDto);
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(threeVModelRepo.save(Mockito.any(ThreeVModel.class))).thenReturn(savedModel);

        //when
        ThreeVModelDto result = threeVModelServiceImp.createModel(mockThreeVModelDto,brandId);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockThreeVModelDto.getModelName());
    }

    @Test
    public void threeVModelService_createModel_throwUniqueConstraintException(){
        Integer brandId = 1;
        ThreeVBrand mockBrand = ThreeVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(threeVModelRepo.save(Mockito.any(ThreeVModel.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->threeVModelServiceImp.createModel(mockThreeVModelDto,brandId));
    }

    @Test
    public void threeVModelService_createModel_throwResourceNotFoundException(){
        //given
        Integer brandId = 1;
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class,()->threeVModelServiceImp.createModel(mockThreeVModelDto,brandId));
    }

    @Test
    public void threeVModelService_findAllModels_returnEmptyList() {
        //given
        when(threeVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<ThreeVModelDto> result = threeVModelServiceImp.findAllModels();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void threeVModelService_findAllModels_returnThreeVModelList() {
        //given
        List<ThreeVModel> threeVModels = Arrays.asList(
                ThreeVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                ThreeVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(threeVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(threeVModels);
        when(modelMapper.map(Mockito.any(ThreeVModel.class), Mockito.eq(ThreeVModelDto.class)))
                .thenAnswer(invocation -> {
                    ThreeVModel model = invocation.getArgument(0);
                    return new ThreeVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<ThreeVModelDto> result = threeVModelServiceImp.findAllModels();
        //then
        assertEquals(threeVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void threeVModelService_findAllModelsByBrand_returnThreeVModelList(){
        //given
        Integer brandId = 1;
        ThreeVBrand mockBrand = ThreeVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        List<ThreeVModel> threeVModels = Arrays.asList(
                ThreeVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                ThreeVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(threeVModelRepo.findAllByThreeVBrandOrderByModelName(Mockito.any(ThreeVBrand.class))).thenReturn(threeVModels);
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(modelMapper.map(Mockito.any(ThreeVModel.class), Mockito.eq(ThreeVModelDto.class)))
                .thenAnswer(invocation -> {
                    ThreeVModel model = invocation.getArgument(0);
                    return new ThreeVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<ThreeVModelDto> result = threeVModelServiceImp.findAllModelsByBrand(brandId);
        //then
        assertEquals(threeVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void threeVModelService_findModelById_returnThreeVModel(){
        //given
        Integer modelId = 1;
        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.of(mockThreeVModel));

        //when
        ThreeVModelDto result = threeVModelServiceImp.findModelById(modelId);
        //then
        assertNotNull(result);
        assertEquals(mockThreeVModelDto, result);
    }

    @Test
    public void threeVModelService_findModelById_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVModelServiceImp.findModelById(modelId));
    }

    @Test
    public void threeVModelService_updateModel_returnThreeVModel() {
        //given
        Integer modelId = 1;
        ThreeVModelDto threeVModelDto = ThreeVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        ThreeVModel updatedThreeVModel = ThreeVModel.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();

        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.of(mockThreeVModel));
        when(modelMapper.map(Mockito.any(ThreeVModelDto.class), Mockito.eq(ThreeVModel.class))).thenReturn(updatedThreeVModel);
        when(modelMapper.map(Mockito.any(ThreeVModel.class), Mockito.eq(ThreeVModelDto.class))).thenReturn(threeVModelDto);
        when(threeVModelRepo.save(Mockito.any(ThreeVModel.class))).thenReturn(updatedThreeVModel);

        //when
        ThreeVModelDto result = threeVModelServiceImp.updateModel(modelId, threeVModelDto);
        //then
        assertEquals("Updated Model", result.getModelName());
    }

    @Test
    public void threeVModelService_updateModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVModelServiceImp.updateModel(modelId, mockThreeVModelDto));
    }

    @Test
    public void threeVModelService_deleteModel_returnVoid() {
        //given
        Integer modelId = 1;
        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.of(mockThreeVModel));
        Mockito.doNothing().when(threeVModelRepo).delete(mockThreeVModel);

        //when&then
        assertAll(()->threeVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(threeVModelRepo, Mockito.times(1)).delete(mockThreeVModel);
    }

    @Test
    public void threeVModelService_deleteModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(threeVModelRepo, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void threeVModelService_addBatteryToModel_returnVoid() {
        //given
        Integer modelId = 1;
        Integer fuelId = 1;
        Integer batteryId = 1;
        ThreeVBattery threeVBattery = ThreeVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        FuelType fuelType = FuelType.builder()
                        .fuelTypeId(1)
                                .fuelType("Petrol")
                                        .build();
        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.of(mockThreeVModel));
        when(fuelTypeRepo.findById(fuelId)).thenReturn(Optional.of(fuelType));
        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(threeVBattery));
        when(threeVModelRepo.save(Mockito.any(ThreeVModel.class))).thenReturn(mockThreeVModel);

        //when&then
        assertAll(()->threeVModelServiceImp.addBatteryToModel(modelId,fuelId,batteryId));
    }

    @Test
    public void threeVModelService_addBatteryToModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        Integer fuelId = 1;
        Integer batteryId = 1;

        when(threeVModelRepo.findById(modelId)).thenReturn(Optional.empty());
        when(threeVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());
        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVModelServiceImp.addBatteryToModel(modelId, fuelId, batteryId));
        //then
        Mockito.verify(threeVModelRepo, Mockito.never()).save(Mockito.any()); // Ensure that save method is never called
    }
}