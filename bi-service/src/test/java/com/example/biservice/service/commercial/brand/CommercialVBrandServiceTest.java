package com.example.biservice.service.commercial.brand;

import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVSegment;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.commercial.CommercialVBrandDto;
import com.example.biservice.repository.commercial.CommercialVBrandRepo;
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

@SpringBootTest
class CommercialVBrandServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CommercialVBrandRepo commercialVBrandRepo;

    @Mock
    private CommercialVSegmentRepo commercialVSegmentRepo;

    @InjectMocks
    private CommercialVBrandServiceImp commercialVBrandServiceImp;

    private CommercialVBrand mockCommercialVBrand;
    private CommercialVBrandDto mockCommercialVBrandDto;

    @BeforeEach
    void setUp() {
        mockCommercialVBrand = CommercialVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        mockCommercialVBrandDto = CommercialVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();

        when(modelMapper.map(Mockito.any(CommercialVBrandDto.class), Mockito.eq(CommercialVBrand.class))).thenReturn(mockCommercialVBrand);
        when(modelMapper.map(Mockito.any(CommercialVBrand.class), Mockito.eq(CommercialVBrandDto.class))).thenReturn(mockCommercialVBrandDto);
    }

    @AfterEach
    void tearDown() {
        mockCommercialVBrand = null;
        mockCommercialVBrandDto = null;
    }

    @Test
    public void commercialVBrandService_createBrand_returnCommercialVBrand(){
        //given
        mockCommercialVBrand = CommercialVBrand.builder()
                .brandName("Brand A")
                .build();
        mockCommercialVBrandDto = CommercialVBrandDto.builder()
                .brandName("Brand A")
                .build();
        CommercialVBrand savedBrand = CommercialVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        CommercialVBrandDto savedBrandDto = CommercialVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        when((modelMapper.map(Mockito.any(CommercialVBrandDto.class), Mockito.eq(CommercialVBrand.class)))).thenReturn(mockCommercialVBrand);
        when((modelMapper.map(Mockito.any(CommercialVBrand.class), Mockito.eq(CommercialVBrandDto.class)))).thenReturn(savedBrandDto);
        when(commercialVBrandRepo.save(Mockito.any(CommercialVBrand.class))).thenReturn(savedBrand);

        //when
        CommercialVBrandDto result = commercialVBrandServiceImp.createBrand(mockCommercialVBrandDto);
        //then
        assertNotNull(result);
        assertEquals(result.getBrandName(),mockCommercialVBrandDto.getBrandName());
    }

    @Test
    public void commercialVBrandService_createBrand_throwUniqueConstraintException(){
        //given
        when(commercialVBrandRepo.save(Mockito.any(CommercialVBrand.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->commercialVBrandServiceImp.createBrand(mockCommercialVBrandDto));
    }

    @Test
    public void commercialVBrandService_findAllBrands_returnEmptyList() {
        //given
        when(commercialVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<CommercialVBrandDto> result = commercialVBrandServiceImp.findAllBrands();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void commercialVBrandService_findAllBrands_returnCommercialVBrandList() {
        //given
        List<CommercialVBrand> commercialVBrands = Arrays.asList(
                CommercialVBrand.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                CommercialVBrand.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(commercialVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(commercialVBrands);
        when(modelMapper.map(Mockito.any(CommercialVBrand.class), Mockito.eq(CommercialVBrandDto.class)))
                .thenAnswer(invocation -> {
                    CommercialVBrand brand = invocation.getArgument(0);
                    return new CommercialVBrandDto(brand.getBrandId(), brand.getBrandName());
                });

        //when
        List<CommercialVBrandDto> result = commercialVBrandServiceImp.findAllBrands();
        //then
        assertEquals(commercialVBrands.size(), result.size());
        assertEquals("Brand A", result.get(0).getBrandName());// Assert sorted order
        assertEquals("Brand B", result.get(1).getBrandName());
    }

    @Test
    public void commercialVBrandService_findBrandById_returnCommercialVBrand(){
        //given
        Integer brandId = 1;
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockCommercialVBrand));

        //when
        CommercialVBrandDto result = commercialVBrandServiceImp.findBrandById(brandId);
        //then
        assertNotNull(result);
        assertEquals(mockCommercialVBrandDto, result);
    }

    @Test
    public void commercialVBrandService_findBrandById_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVBrandServiceImp.findBrandById(brandId));
    }

    @Test
    public void commercialVBrandService_updateBrand_returnCommercialVBrand() {
        //given
        Integer brandId = 1;
        CommercialVBrandDto commercialVBrandDto = CommercialVBrandDto.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();
        CommercialVBrand updatedCommercialVBrand = CommercialVBrand.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();

        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockCommercialVBrand));
        when(modelMapper.map(Mockito.any(CommercialVBrandDto.class), Mockito.eq(CommercialVBrand.class))).thenReturn(updatedCommercialVBrand);
        when(modelMapper.map(Mockito.any(CommercialVBrand.class), Mockito.eq(CommercialVBrandDto.class))).thenReturn(commercialVBrandDto);
        when(commercialVBrandRepo.save(updatedCommercialVBrand)).thenReturn(updatedCommercialVBrand);

        //when
        CommercialVBrandDto result = commercialVBrandServiceImp.updateBrand(brandId, commercialVBrandDto);
        //then
        assertEquals("Updated Brand", result.getBrandName());
    }

    @Test
    public void commercialVBrandService_updateBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVBrandServiceImp.updateBrand(brandId, mockCommercialVBrandDto));
    }

    @Test
    public void commercialVBrandService_deleteBrand_returnVoid() {
        //given
        Integer brandId = 1;
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockCommercialVBrand));
        Mockito.doNothing().when(commercialVBrandRepo).delete(mockCommercialVBrand);

        //when&then
        assertAll(()->commercialVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(commercialVBrandRepo, Mockito.times(1)).delete(mockCommercialVBrand);
    }

    @Test
    public void commercialVBrandService_deleteBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(commercialVBrandRepo, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void commercialVBrandService_addSegmentToBrand_returnVoid() {
        //given
        Integer brandId = 1;
        Integer segmentId = 1;
        CommercialVSegment commercialVSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockCommercialVBrand));
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(commercialVSegment));
        when(commercialVBrandRepo.save(Mockito.any(CommercialVBrand.class))).thenReturn(mockCommercialVBrand);

        //when&then
        assertAll(()->commercialVBrandServiceImp.addSegmentToBrand(brandId,segmentId));
    }

    @Test
    public void commercialVBrandService_addSegmentToBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        Integer segmentId = 1;

        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.empty());
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.empty());
        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVBrandServiceImp.addSegmentToBrand(brandId, segmentId));
        //then
        Mockito.verify(commercialVBrandRepo, Mockito.never()).save(Mockito.any()); // Ensure that save method is never called
    }

    @Test
    public void commercialVBrandService_addSegmentToBrand_noNullBatteriesInModel() {
        //given
        Integer brandId = 1;
        Integer segmentId = 1;
        CommercialVSegment commercialVSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockCommercialVBrand));
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(commercialVSegment));
        when(commercialVBrandRepo.save(Mockito.any(CommercialVBrand.class))).thenReturn(mockCommercialVBrand);

        //when
        commercialVBrandServiceImp.addSegmentToBrand(brandId, segmentId);
        //then
        // Ensure that the list of batteries is initialized before adding the battery
        assertNotNull(mockCommercialVBrand.getCommercialVSegments());
        assertEquals(1, mockCommercialVBrand.getCommercialVSegments().size());
        assertTrue(mockCommercialVBrand.getCommercialVSegments().contains(commercialVSegment));
        Mockito.verify(commercialVBrandRepo, Mockito.times(1)).save(mockCommercialVBrand); // Verify that save method is called once with the updated model
    }
}