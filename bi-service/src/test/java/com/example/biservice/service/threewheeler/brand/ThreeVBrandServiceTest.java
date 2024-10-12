package com.example.biservice.service.threewheeler.brand;

import com.example.biservice.entity.threewheeler.ThreeVBrand;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.threewheeler.ThreeVBrandDto;
import com.example.biservice.repository.threewheeler.ThreeVBrandRepo;
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
class ThreeVBrandServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ThreeVBrandRepo threeVBrandRepo;

    @InjectMocks
    private ThreeVBrandServiceImp threeVBrandServiceImp;

    private ThreeVBrand mockThreeVBrand;

    private ThreeVBrandDto mockThreeVBrandDto;

    @BeforeEach
    void setUp() {
        mockThreeVBrand = ThreeVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        mockThreeVBrandDto = ThreeVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();

        when(modelMapper.map(Mockito.any(ThreeVBrandDto.class), Mockito.eq(ThreeVBrand.class))).thenReturn(mockThreeVBrand);
        when(modelMapper.map(Mockito.any(ThreeVBrand.class), Mockito.eq(ThreeVBrandDto.class))).thenReturn(mockThreeVBrandDto);
    }

    @AfterEach
    void tearDown() {
        mockThreeVBrand = null;
        mockThreeVBrandDto = null;
    }

    @Test
    public void threeVBrandService_createBrand_returnThreeVBrand(){
        //given
        mockThreeVBrand = ThreeVBrand.builder()
                .brandName("Brand A")
                .build();
        mockThreeVBrandDto = ThreeVBrandDto.builder()
                .brandName("Brand A")
                .build();
        ThreeVBrand savedBrand = ThreeVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        ThreeVBrandDto savedBrandDto = ThreeVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        when((modelMapper.map(Mockito.any(ThreeVBrandDto.class), Mockito.eq(ThreeVBrand.class)))).thenReturn(mockThreeVBrand);
        when((modelMapper.map(Mockito.any(ThreeVBrand.class), Mockito.eq(ThreeVBrandDto.class)))).thenReturn(savedBrandDto);
        when(threeVBrandRepo.save(Mockito.any(ThreeVBrand.class))).thenReturn(savedBrand);

        //when
        ThreeVBrandDto result = threeVBrandServiceImp.createBrand(mockThreeVBrandDto);
        //then
        assertNotNull(result);
        assertEquals(result.getBrandName(),mockThreeVBrandDto.getBrandName());
    }

    @Test
    public void threeVBrandService_createBrand_throwUniqueConstraintException(){
        //given
        when(threeVBrandRepo.save(Mockito.any(ThreeVBrand.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->threeVBrandServiceImp.createBrand(mockThreeVBrandDto));
    }

    @Test
    public void threeVBrandService_findAllBrands_returnEmptyList() {
        //given
        when(threeVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<ThreeVBrandDto> result = threeVBrandServiceImp.findAllBrands();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void threeVBrandService_findAllBrands_returnThreeVBrandList() {
        //given
        List<ThreeVBrand> threeVBrands = Arrays.asList(
                ThreeVBrand.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                ThreeVBrand.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(threeVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(threeVBrands);
        when(modelMapper.map(Mockito.any(ThreeVBrand.class), Mockito.eq(ThreeVBrandDto.class)))
                .thenAnswer(invocation -> {
                    ThreeVBrand brand = invocation.getArgument(0);
                    return new ThreeVBrandDto(brand.getBrandId(), brand.getBrandName());
                });

        //when
        List<ThreeVBrandDto> result = threeVBrandServiceImp.findAllBrands();
        //then
        assertEquals(threeVBrands.size(), result.size());
        assertEquals("Brand A", result.get(0).getBrandName());// Assert sorted order
        assertEquals("Brand B", result.get(1).getBrandName());
    }

    @Test
    public void threeVBrandService_findBrandById_returnThreeVBrand(){
        //given
        Integer brandId = 1;
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockThreeVBrand));

        //when
        ThreeVBrandDto result = threeVBrandServiceImp.findBrandById(brandId);
        //then
        assertNotNull(result);
        assertEquals(mockThreeVBrandDto, result);
    }

    @Test
    public void threeVBrandService_findBrandById_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVBrandServiceImp.findBrandById(brandId));
    }

    @Test
    public void threeVBrandService_updateBrand_returnThreeVBrand() {
        //given
        Integer brandId = 1;
        ThreeVBrandDto threeVBrandDto = ThreeVBrandDto.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();
        ThreeVBrand updatedThreeVBrand = ThreeVBrand.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();

        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockThreeVBrand));
        when(modelMapper.map(Mockito.any(ThreeVBrandDto.class), Mockito.eq(ThreeVBrand.class))).thenReturn(updatedThreeVBrand);
        when(modelMapper.map(Mockito.any(ThreeVBrand.class), Mockito.eq(ThreeVBrandDto.class))).thenReturn(threeVBrandDto);
        when(threeVBrandRepo.save(updatedThreeVBrand)).thenReturn(updatedThreeVBrand);

        //when
        ThreeVBrandDto result = threeVBrandServiceImp.updateBrand(brandId, threeVBrandDto);
        //then
        assertEquals("Updated Brand", result.getBrandName());
    }

    @Test
    public void threeVBrandService_updateBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVBrandServiceImp.updateBrand(brandId, mockThreeVBrandDto));
    }

    @Test
    public void threeVBrandService_deleteBrand_returnVoid() {
        //given
        Integer brandId = 1;
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockThreeVBrand));
        Mockito.doNothing().when(threeVBrandRepo).delete(mockThreeVBrand);

        //when&then
        assertAll(()->threeVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(threeVBrandRepo, Mockito.times(1)).delete(mockThreeVBrand);
    }

    @Test
    public void threeVBrandService_deleteBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(threeVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> threeVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(threeVBrandRepo, Mockito.never()).delete(Mockito.any());
    }
}