package com.example.biservice.service.twowheeler.model;

import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.entity.twowheeler.TwoVBrand;
import com.example.biservice.entity.twowheeler.TwoVModel;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.twowheeler.TwoVModelDto;
import com.example.biservice.repository.twowheeler.TwoVBatteryRepo;
import com.example.biservice.repository.twowheeler.TwoVBrandRepo;
import com.example.biservice.repository.twowheeler.TwoVModelRepo;
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
class TwoVModelServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TwoVModelRepo twoVModelRepo;

    @Mock
    private TwoVBrandRepo twoVBrandRepo;

    @Mock
    private TwoVBatteryRepo twoVBatteryRepo;

    @InjectMocks
    private TwoVModelServiceImp twoVModelServiceImp;

    private TwoVModel mockTwoVModel;
    private TwoVModelDto mockTwoVModelDto;

    @BeforeEach
    void setUp() {
        mockTwoVModel = TwoVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        mockTwoVModelDto = TwoVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();

        when(modelMapper.map(Mockito.any(TwoVModelDto.class),Mockito.eq(TwoVModel.class))).thenReturn(mockTwoVModel);
        when(modelMapper.map(Mockito.any(TwoVModel.class),Mockito.eq(TwoVModelDto.class))).thenReturn(mockTwoVModelDto);
    }

    @AfterEach
    void tearDown() {
        mockTwoVModel = null;
        mockTwoVModelDto = null;
    }

    @Test
    public void twoVModelService_createModel_returnTwoVModel(){
        //given
        Integer brandId = 1;
        TwoVBrand mockBrand = TwoVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        mockTwoVModel = TwoVModel.builder()
                .modelName("Model A")
                .build();
        mockTwoVModelDto = TwoVModelDto.builder()
                .modelName("Model A")
                .build();
        TwoVModel savedModel = TwoVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        TwoVModelDto savedModelDto = TwoVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        when((modelMapper.map(Mockito.any(TwoVModelDto.class), Mockito.eq(TwoVModel.class)))).thenReturn(mockTwoVModel);
        when((modelMapper.map(Mockito.any(TwoVModel.class), Mockito.eq(TwoVModelDto.class)))).thenReturn(savedModelDto);
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(twoVModelRepo.save(Mockito.any(TwoVModel.class))).thenReturn(savedModel);

        //when
        TwoVModelDto result = twoVModelServiceImp.createModel(mockTwoVModelDto,brandId);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockTwoVModelDto.getModelName());
    }

    @Test
    public void twoVModelService_createModel_throwUniqueConstraintException(){
        Integer brandId = 1;
        TwoVBrand mockBrand = TwoVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(twoVModelRepo.save(Mockito.any(TwoVModel.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->twoVModelServiceImp.createModel(mockTwoVModelDto,brandId));
    }

    @Test
    public void twoVModelService_createModel_throwResourceNotFoundException(){
        //given
        Integer brandId = 1;
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class,()->twoVModelServiceImp.createModel(mockTwoVModelDto,brandId));
    }

    @Test
    public void twoVModelService_findAllModels_returnEmptyList() {
        //given
        when(twoVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<TwoVModelDto> result = twoVModelServiceImp.findAllModels();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void twoVModelService_findAllModels_returnTwoVModelList() {
        //given
        List<TwoVModel> twoVModels = Arrays.asList(
                TwoVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                TwoVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(twoVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(twoVModels);
        when(modelMapper.map(Mockito.any(TwoVModel.class), Mockito.eq(TwoVModelDto.class)))
                .thenAnswer(invocation -> {
                    TwoVModel model = invocation.getArgument(0);
                    return new TwoVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<TwoVModelDto> result = twoVModelServiceImp.findAllModels();
        //then
        assertEquals(twoVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void twoVModelService_findAllModelsByBrand_returnTwoVModelList(){
        //given
        Integer brandId = 1;
        TwoVBrand mockBrand = TwoVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        List<TwoVModel> twoVModels = Arrays.asList(
                TwoVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                TwoVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(twoVModelRepo.findByTwoVBrandOrderByModelName(Mockito.any(TwoVBrand.class))).thenReturn(twoVModels);
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(modelMapper.map(Mockito.any(TwoVModel.class), Mockito.eq(TwoVModelDto.class)))
                .thenAnswer(invocation -> {
                    TwoVModel model = invocation.getArgument(0);
                    return new TwoVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<TwoVModelDto> result = twoVModelServiceImp.findAllModelsByBrand(brandId);
        //then
        assertEquals(twoVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void twoVModelService_findModelById_returnTwoVModel(){
        //given
        Integer modelId = 1;
        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.of(mockTwoVModel));

        //when
        TwoVModelDto result = twoVModelServiceImp.findModelById(modelId);
        //then
        assertNotNull(result);
        assertEquals(mockTwoVModelDto, result);
    }

    @Test
    public void twoVModelService_findModelById_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVModelServiceImp.findModelById(modelId));
    }

    @Test
    public void twoVModelService_updateModel_returnTwoVModel() {
        //given
        Integer modelId = 1;
        TwoVModelDto twoVModelDto = TwoVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        TwoVModel updatedTwoVModel = TwoVModel.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();

        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.of(mockTwoVModel));
        when(modelMapper.map(Mockito.any(TwoVModelDto.class), Mockito.eq(TwoVModel.class))).thenReturn(updatedTwoVModel);
        when(modelMapper.map(Mockito.any(TwoVModel.class), Mockito.eq(TwoVModelDto.class))).thenReturn(twoVModelDto);
        when(twoVModelRepo.save(Mockito.any(TwoVModel.class))).thenReturn(updatedTwoVModel);

        //when
        TwoVModelDto result = twoVModelServiceImp.updateModel(modelId, twoVModelDto);
        //then
        assertEquals("Updated Model", result.getModelName());
    }

    @Test
    public void twoVModelService_updateModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVModelServiceImp.updateModel(modelId, mockTwoVModelDto));
    }

    @Test
    public void twoVModelService_deleteModel_returnVoid() {
        //given
        Integer modelId = 1;
        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.of(mockTwoVModel));
        Mockito.doNothing().when(twoVModelRepo).delete(mockTwoVModel);

        //when&then
        assertAll(()->twoVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(twoVModelRepo, Mockito.times(1)).delete(mockTwoVModel);
    }

    @Test
    public void twoVModelService_deleteModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(twoVModelRepo, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void twoVModelService_addBatteryToModel_returnVoid() {
        //given
        Integer modelId = 1;
        Integer batteryId = 1;
        TwoVBattery twoVBattery = TwoVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.of(mockTwoVModel));
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(twoVBattery));
        when(twoVModelRepo.save(Mockito.any(TwoVModel.class))).thenReturn(mockTwoVModel);

        //when&then
        assertAll(()->twoVModelServiceImp.addBatteryToModel(modelId,batteryId));
    }

    @Test
    public void twoVModelService_addBatteryToModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        Integer batteryId = 1;

        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.empty());
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());
        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVModelServiceImp.addBatteryToModel(modelId, batteryId));
        //then
        Mockito.verify(twoVModelRepo, Mockito.never()).save(Mockito.any()); // Ensure that save method is never called
    }

    @Test
    public void twoVModelService_addBatteryToModel_noNullBatteriesInModel() {
        //given
        Integer modelId = 1;
        Integer batteryId = 1;
        TwoVBattery twoVBattery = TwoVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        when(twoVModelRepo.findById(modelId)).thenReturn(Optional.of(mockTwoVModel));
        when(twoVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(twoVBattery));
        when(twoVModelRepo.save(Mockito.any(TwoVModel.class))).thenReturn(mockTwoVModel);

        //when
        twoVModelServiceImp.addBatteryToModel(modelId, batteryId);
        //then
        // Ensure that the list of batteries is initialized before adding the battery
        assertNotNull(mockTwoVModel.getTwoVBatteries());
        assertEquals(1, mockTwoVModel.getTwoVBatteries().size());
        assertTrue(mockTwoVModel.getTwoVBatteries().contains(twoVBattery));
        Mockito.verify(twoVModelRepo, Mockito.times(1)).save(mockTwoVModel); // Verify that save method is called once with the updated model
    }
}