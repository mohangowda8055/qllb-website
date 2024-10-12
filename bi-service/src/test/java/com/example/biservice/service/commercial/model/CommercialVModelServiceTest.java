package com.example.biservice.service.commercial.model;

import com.example.biservice.entity.commercial.CommercialVBattery;
import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVModel;
import com.example.biservice.entity.commercial.CommercialVSegment;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.commercial.CommercialVModelDto;
import com.example.biservice.repository.commercial.CommercialVBatteryRepo;
import com.example.biservice.repository.commercial.CommercialVBrandRepo;
import com.example.biservice.repository.commercial.CommercialVModelRepo;
import com.example.biservice.repository.commercial.CommercialVSegmentRepo;
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
class CommercialVModelServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CommercialVModelRepo commercialVModelRepo;

    @Mock
    private CommercialVBrandRepo commercialVBrandRepo;

    @Mock
    private CommercialVSegmentRepo commercialVSegmentRepo;

    @Mock
    private CommercialVBatteryRepo commercialVBatteryRepo;

    @InjectMocks
    private CommercialVModelServiceImp commercialVModelServiceImp;

    private CommercialVModel mockCommercialVModel;
    private CommercialVModelDto mockCommercialVModelDto;

    @BeforeEach
    void setUp() {
        mockCommercialVModel = CommercialVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        mockCommercialVModelDto = CommercialVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();

        when(modelMapper.map(Mockito.any(CommercialVModelDto.class),Mockito.eq(CommercialVModel.class))).thenReturn(mockCommercialVModel);
        when(modelMapper.map(Mockito.any(CommercialVModel.class),Mockito.eq(CommercialVModelDto.class))).thenReturn(mockCommercialVModelDto);
    }

    @AfterEach
    void tearDown() {
        mockCommercialVModel = null;
        mockCommercialVModelDto = null;
    }

    @Test
    public void commercialVModelService_createModel_returnCommercialVModel(){
        //given
        Integer brandId = 1;
        Integer segmentId = 1;
        CommercialVBrand mockBrand = CommercialVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        CommercialVSegment mockSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        mockCommercialVModel = CommercialVModel.builder()
                .modelName("Model A")
                .build();
        mockCommercialVModelDto = CommercialVModelDto.builder()
                .modelName("Model A")
                .build();
        CommercialVModel savedModel = CommercialVModel.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        CommercialVModelDto savedModelDto = CommercialVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
        when((modelMapper.map(Mockito.any(CommercialVModelDto.class), Mockito.eq(CommercialVModel.class)))).thenReturn(mockCommercialVModel);
        when((modelMapper.map(Mockito.any(CommercialVModel.class), Mockito.eq(CommercialVModelDto.class)))).thenReturn(savedModelDto);
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(mockSegment));
        when(commercialVModelRepo.save(Mockito.any(CommercialVModel.class))).thenReturn(savedModel);

        //when
        CommercialVModelDto result = commercialVModelServiceImp.createModel(mockCommercialVModelDto,brandId,segmentId);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockCommercialVModelDto.getModelName());
    }

    @Test
    public void commercialVModelService_createModel_throwUniqueConstraintException(){
        Integer brandId = 1;
        Integer segmentId = 1;
        CommercialVBrand mockBrand = CommercialVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        CommercialVSegment mockSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(mockSegment));
        when(commercialVModelRepo.save(Mockito.any(CommercialVModel.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->commercialVModelServiceImp.createModel(mockCommercialVModelDto,brandId,segmentId));
    }

    @Test
    public void commercialVModelService_createModel_throwResourceNotFoundException(){
        //given
        Integer brandId = 1;
        Integer segmentId =1;
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class,()->commercialVModelServiceImp.createModel(mockCommercialVModelDto,brandId,segmentId));
    }

    @Test
    public void commercialVModelService_findAllModels_returnEmptyList() {
        //given
        when(commercialVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<CommercialVModelDto> result = commercialVModelServiceImp.findAllModels();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void commercialVModelService_findAllModels_returnCommercialVModelList() {
        //given
        List<CommercialVModel> commercialVModels = Arrays.asList(
                CommercialVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                CommercialVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(commercialVModelRepo.findAll(Mockito.any(Sort.class))).thenReturn(commercialVModels);
        when(modelMapper.map(Mockito.any(CommercialVModel.class), Mockito.eq(CommercialVModelDto.class)))
                .thenAnswer(invocation -> {
                    CommercialVModel model = invocation.getArgument(0);
                    return new CommercialVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<CommercialVModelDto> result = commercialVModelServiceImp.findAllModels();
        //then
        assertEquals(commercialVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void commercialVModelService_findAllModelsByBrand_returnCommercialVModelList(){
        //given
        Integer brandId = 1;
        Integer segmentId = 1;
        CommercialVBrand mockBrand = CommercialVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        CommercialVSegment mockSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        List<CommercialVModel> commercialVModels = Arrays.asList(
                CommercialVModel.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                CommercialVModel.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(commercialVModelRepo.findAllByCommercialVBrandAndCommercialVSegmentOrderByModelName(Mockito.any(CommercialVBrand.class),Mockito.any(CommercialVSegment.class))).thenReturn(commercialVModels);
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(mockSegment));
        when(modelMapper.map(Mockito.any(CommercialVModel.class), Mockito.eq(CommercialVModelDto.class)))
                .thenAnswer(invocation -> {
                    CommercialVModel model = invocation.getArgument(0);
                    return new CommercialVModelDto(model.getModelId(), model.getModelName());
                });

        //when
        List<CommercialVModelDto> result = commercialVModelServiceImp.findAllModelsByBrandAndSegment(brandId,segmentId);
        //then
        assertEquals(commercialVModels.size(), result.size());
        assertEquals("Model A", result.get(0).getModelName());// Assert sorted order
        assertEquals("Model B", result.get(1).getModelName());
    }

    @Test
    public void commercialVModelService_findModelById_returnCommercialVModel(){
        //given
        Integer modelId = 1;
        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.of(mockCommercialVModel));

        //when
        CommercialVModelDto result = commercialVModelServiceImp.findModelById(modelId);
        //then
        assertNotNull(result);
        assertEquals(mockCommercialVModelDto, result);
    }

    @Test
    public void commercialVModelService_findModelById_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVModelServiceImp.findModelById(modelId));
    }

    @Test
    public void commercialVModelService_updateModel_returnCommercialVModel() {
        //given
        Integer modelId = 1;
        CommercialVModelDto commercialVModelDto = CommercialVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        CommercialVModel updatedCommercialVModel = CommercialVModel.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();

        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.of(mockCommercialVModel));
        when(modelMapper.map(Mockito.any(CommercialVModelDto.class), Mockito.eq(CommercialVModel.class))).thenReturn(updatedCommercialVModel);
        when(modelMapper.map(Mockito.any(CommercialVModel.class), Mockito.eq(CommercialVModelDto.class))).thenReturn(commercialVModelDto);
        when(commercialVModelRepo.save(Mockito.any(CommercialVModel.class))).thenReturn(updatedCommercialVModel);

        //when
        CommercialVModelDto result = commercialVModelServiceImp.updateModel(modelId, commercialVModelDto);
        //then
        assertEquals("Updated Model", result.getModelName());
    }

    @Test
    public void commercialVModelService_updateModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVModelServiceImp.updateModel(modelId, mockCommercialVModelDto));
    }

    @Test
    public void commercialVModelService_deleteModel_returnVoid() {
        //given
        Integer modelId = 1;
        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.of(mockCommercialVModel));
        Mockito.doNothing().when(commercialVModelRepo).delete(mockCommercialVModel);

        //when&then
        assertAll(()->commercialVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(commercialVModelRepo, Mockito.times(1)).delete(mockCommercialVModel);
    }

    @Test
    public void commercialVModelService_deleteModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVModelServiceImp.deleteModel(modelId));
        //then
        Mockito.verify(commercialVModelRepo, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void commercialVModelService_addBatteryToModel_returnVoid() {
        //given
        Integer modelId = 1;
        Integer batteryId = 1;
        CommercialVBattery commercialVBattery = CommercialVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.of(mockCommercialVModel));
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(commercialVBattery));
        when(commercialVModelRepo.save(Mockito.any(CommercialVModel.class))).thenReturn(mockCommercialVModel);

        //when&then
        assertAll(()->commercialVModelServiceImp.addBatteryToModel(modelId,batteryId));
    }

    @Test
    public void commercialVModelService_addBatteryToModel_throwResourceNotFoundException() {
        //given
        Integer modelId = 1;
        Integer batteryId = 1;

        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.empty());
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());
        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVModelServiceImp.addBatteryToModel(modelId, batteryId));
        //then
        Mockito.verify(commercialVModelRepo, Mockito.never()).save(Mockito.any()); // Ensure that save method is never called
    }

    @Test
    public void commercialVModelService_addBatteryToModel_noNullBatteriesInModel() {
        //given
        Integer modelId = 1;
        Integer batteryId = 1;
        CommercialVBattery commercialVBattery = CommercialVBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        when(commercialVModelRepo.findById(modelId)).thenReturn(Optional.of(mockCommercialVModel));
        when(commercialVBatteryRepo.findById(batteryId)).thenReturn(Optional.of(commercialVBattery));
        when(commercialVModelRepo.save(Mockito.any(CommercialVModel.class))).thenReturn(mockCommercialVModel);

        //when
        commercialVModelServiceImp.addBatteryToModel(modelId, batteryId);
        //then
        // Ensure that the list of batteries is initialized before adding the battery
        assertNotNull(mockCommercialVModel.getCommercialVBatteries());
        assertEquals(1, mockCommercialVModel.getCommercialVBatteries().size());
        assertTrue(mockCommercialVModel.getCommercialVBatteries().contains(commercialVBattery));
        Mockito.verify(commercialVModelRepo, Mockito.times(1)).save(mockCommercialVModel); // Verify that save method is called once with the updated model
    }
}