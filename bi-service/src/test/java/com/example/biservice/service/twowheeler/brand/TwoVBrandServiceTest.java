package com.example.biservice.service.twowheeler.brand;

import com.example.biservice.entity.twowheeler.TwoVBrand;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.twowheeler.TwoVBrandDto;
import com.example.biservice.repository.twowheeler.TwoVBrandRepo;
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
class TwoVBrandServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TwoVBrandRepo twoVBrandRepo;

    @InjectMocks
    private TwoVBrandServiceImp twoVBrandServiceImp;

    private TwoVBrand mockTwoVBrand;
    private TwoVBrandDto mockTwoVBrandDto;

    @BeforeEach
    void setUp() {
        mockTwoVBrand = TwoVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        mockTwoVBrandDto = TwoVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();

        when(modelMapper.map(Mockito.any(TwoVBrandDto.class), Mockito.eq(TwoVBrand.class))).thenReturn(mockTwoVBrand);
        when(modelMapper.map(Mockito.any(TwoVBrand.class), Mockito.eq(TwoVBrandDto.class))).thenReturn(mockTwoVBrandDto);
    }

    @AfterEach
    void tearDown() {
        mockTwoVBrand = null;
        mockTwoVBrandDto = null;
    }

    @Test
    public void twoVBrandService_createBrand_returnTwoVBrand(){
        //given
        mockTwoVBrand = TwoVBrand.builder()
                .brandName("Brand A")
                .build();
        mockTwoVBrandDto = TwoVBrandDto.builder()
                .brandName("Brand A")
                .build();
        TwoVBrand savedBrand = TwoVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        TwoVBrandDto savedBrandDto = TwoVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        when((modelMapper.map(Mockito.any(TwoVBrandDto.class), Mockito.eq(TwoVBrand.class)))).thenReturn(mockTwoVBrand);
        when((modelMapper.map(Mockito.any(TwoVBrand.class), Mockito.eq(TwoVBrandDto.class)))).thenReturn(savedBrandDto);
        when(twoVBrandRepo.save(Mockito.any(TwoVBrand.class))).thenReturn(savedBrand);

        //when
        TwoVBrandDto result = twoVBrandServiceImp.createBrand(mockTwoVBrandDto);
        //then
        assertNotNull(result);
        assertEquals(result.getBrandName(),mockTwoVBrandDto.getBrandName());
    }

    @Test
    public void twoVBrandService_createBrand_throwUniqueConstraintException(){
        //given
        when(twoVBrandRepo.save(Mockito.any(TwoVBrand.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->twoVBrandServiceImp.createBrand(mockTwoVBrandDto));
    }

    @Test
    public void twoVBrandService_findAllBrands_returnEmptyList() {
        //given
        when(twoVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<TwoVBrandDto> result = twoVBrandServiceImp.findAllBrands();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void twoVBrandService_findAllBrands_returnTwoVBrandList() {
        //given
        List<TwoVBrand> twoVBrands = Arrays.asList(
                TwoVBrand.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                TwoVBrand.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(twoVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(twoVBrands);
        when(modelMapper.map(Mockito.any(TwoVBrand.class), Mockito.eq(TwoVBrandDto.class)))
                .thenAnswer(invocation -> {
                    TwoVBrand brand = invocation.getArgument(0);
                    return new TwoVBrandDto(brand.getBrandId(), brand.getBrandName());
                });

        //when
        List<TwoVBrandDto> result = twoVBrandServiceImp.findAllBrands();
        //then
        assertEquals(twoVBrands.size(), result.size());
        assertEquals("Brand A", result.get(0).getBrandName());// Assert sorted order
        assertEquals("Brand B", result.get(1).getBrandName());
    }

    @Test
    public void twoVBrandService_findBrandById_returnTwoVBrand(){
        //given
        Integer brandId = 1;
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockTwoVBrand));

        //when
        TwoVBrandDto result = twoVBrandServiceImp.findBrandById(brandId);
        //then
        assertNotNull(result);
        assertEquals(mockTwoVBrandDto, result);
    }

    @Test
    public void twoVBrandService_findBrandById_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVBrandServiceImp.findBrandById(brandId));
    }

    @Test
    public void twoVBrandService_updateBrand_returnTwoVBrand() {
        //given
        Integer brandId = 1;
        TwoVBrandDto twoVBrandDto = TwoVBrandDto.builder()
                                        .brandId(1)
                                        .brandName("Updated Brand")
                                        .build();
        TwoVBrand updatedTwoVBrand = TwoVBrand.builder()
                                        .brandId(1)
                                        .brandName("Updated Brand")
                                        .build();

        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockTwoVBrand));
        when(modelMapper.map(Mockito.any(TwoVBrandDto.class), Mockito.eq(TwoVBrand.class))).thenReturn(updatedTwoVBrand);
        when(modelMapper.map(Mockito.any(TwoVBrand.class), Mockito.eq(TwoVBrandDto.class))).thenReturn(twoVBrandDto);
        when(twoVBrandRepo.save(updatedTwoVBrand)).thenReturn(updatedTwoVBrand);

        //when
        TwoVBrandDto result = twoVBrandServiceImp.updateBrand(brandId, twoVBrandDto);
        //then
        assertEquals("Updated Brand", result.getBrandName());
    }

    @Test
    public void twoVBrandService_updateBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVBrandServiceImp.updateBrand(brandId, mockTwoVBrandDto));
    }

    @Test
    public void twoVBrandService_deleteBrand_returnVoid() {
        //given
        Integer brandId = 1;
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockTwoVBrand));
        Mockito.doNothing().when(twoVBrandRepo).delete(mockTwoVBrand);

        //when&then
        assertAll(()->twoVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(twoVBrandRepo, Mockito.times(1)).delete(mockTwoVBrand);
    }

    @Test
    public void twoVBrandService_deleteBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(twoVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> twoVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(twoVBrandRepo, Mockito.never()).delete(Mockito.any());
    }
}