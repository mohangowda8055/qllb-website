package com.example.biservice.service.fourwheeler.brand;

import com.example.biservice.entity.fourwheeler.FourVBrand;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.fourwheeler.FourVBrandDto;
import com.example.biservice.repository.fourwheeler.FourVBrandRepo;
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
class FourVBrandServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private FourVBrandRepo fourVBrandRepo;

    @InjectMocks
    private FourVBrandServiceImp fourVBrandServiceImp;

    private FourVBrand mockFourVBrand;
    private FourVBrandDto mockFourVBrandDto;

    @BeforeEach
    void setUp() {
        mockFourVBrand = FourVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        mockFourVBrandDto = FourVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();

        when(modelMapper.map(Mockito.any(FourVBrandDto.class), Mockito.eq(FourVBrand.class))).thenReturn(mockFourVBrand);
        when(modelMapper.map(Mockito.any(FourVBrand.class), Mockito.eq(FourVBrandDto.class))).thenReturn(mockFourVBrandDto);
    }

    @AfterEach
    void tearDown() {
        mockFourVBrand = null;
        mockFourVBrandDto = null;
    }

    @Test
    public void fourVBrandService_createBrand_returnFourVBrand(){
        //given
        mockFourVBrand = FourVBrand.builder()
                .brandName("Brand A")
                .build();
        mockFourVBrandDto = FourVBrandDto.builder()
                .brandName("Brand A")
                .build();
        FourVBrand savedBrand = FourVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        FourVBrandDto savedBrandDto = FourVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        when((modelMapper.map(Mockito.any(FourVBrandDto.class), Mockito.eq(FourVBrand.class)))).thenReturn(mockFourVBrand);
        when((modelMapper.map(Mockito.any(FourVBrand.class), Mockito.eq(FourVBrandDto.class)))).thenReturn(savedBrandDto);
        when(fourVBrandRepo.save(Mockito.any(FourVBrand.class))).thenReturn(savedBrand);

        //when
        FourVBrandDto result = fourVBrandServiceImp.createBrand(mockFourVBrandDto);
        //then
        assertNotNull(result);
        assertEquals(result.getBrandName(),mockFourVBrandDto.getBrandName());
    }

    @Test
    public void fourVBrandService_createBrand_throwUniqueConstraintException(){
        //given
        when(fourVBrandRepo.save(Mockito.any(FourVBrand.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->fourVBrandServiceImp.createBrand(mockFourVBrandDto));
    }

    @Test
    public void fourVBrandService_findAllBrands_returnEmptyList() {
        //given
        when(fourVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<FourVBrandDto> result = fourVBrandServiceImp.findAllBrands();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void fourVBrandService_findAllBrands_returnFourVBrandList() {
        //given
        List<FourVBrand> fourVBrands = Arrays.asList(
                FourVBrand.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                FourVBrand.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(fourVBrandRepo.findAll(Mockito.any(Sort.class))).thenReturn(fourVBrands);
        when(modelMapper.map(Mockito.any(FourVBrand.class), Mockito.eq(FourVBrandDto.class)))
                .thenAnswer(invocation -> {
                    FourVBrand brand = invocation.getArgument(0);
                    return new FourVBrandDto(brand.getBrandId(), brand.getBrandName());
                });

        //when
        List<FourVBrandDto> result = fourVBrandServiceImp.findAllBrands();
        //then
        assertEquals(fourVBrands.size(), result.size());
        assertEquals("Brand A", result.get(0).getBrandName());// Assert sorted order
        assertEquals("Brand B", result.get(1).getBrandName());
    }

    @Test
    public void fourVBrandService_findBrandById_returnFourVBrand(){
        //given
        Integer brandId = 1;
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockFourVBrand));

        //when
        FourVBrandDto result = fourVBrandServiceImp.findBrandById(brandId);
        //then
        assertNotNull(result);
        assertEquals(mockFourVBrandDto, result);
    }

    @Test
    public void fourVBrandService_findBrandById_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVBrandServiceImp.findBrandById(brandId));
    }

    @Test
    public void fourVBrandService_updateBrand_returnFourVBrand() {
        //given
        Integer brandId = 1;
        FourVBrandDto fourVBrandDto = FourVBrandDto.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();
        FourVBrand updatedFourVBrand = FourVBrand.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();

        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockFourVBrand));
        when(modelMapper.map(Mockito.any(FourVBrandDto.class), Mockito.eq(FourVBrand.class))).thenReturn(updatedFourVBrand);
        when(modelMapper.map(Mockito.any(FourVBrand.class), Mockito.eq(FourVBrandDto.class))).thenReturn(fourVBrandDto);
        when(fourVBrandRepo.save(updatedFourVBrand)).thenReturn(updatedFourVBrand);

        //when
        FourVBrandDto result = fourVBrandServiceImp.updateBrand(brandId, fourVBrandDto);
        //then
        assertEquals("Updated Brand", result.getBrandName());
    }

    @Test
    public void fourVBrandService_updateBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVBrandServiceImp.updateBrand(brandId, mockFourVBrandDto));
    }

    @Test
    public void fourVBrandService_deleteBrand_returnVoid() {
        //given
        Integer brandId = 1;
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockFourVBrand));
        Mockito.doNothing().when(fourVBrandRepo).delete(mockFourVBrand);

        //when&then
        assertAll(()->fourVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(fourVBrandRepo, Mockito.times(1)).delete(mockFourVBrand);
    }

    @Test
    public void fourVBrandService_deleteBrand_throwResourceNotFoundException() {
        //given
        Integer brandId = 1;
        when(fourVBrandRepo.findById(brandId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> fourVBrandServiceImp.deleteBrand(brandId));
        //then
        Mockito.verify(fourVBrandRepo, Mockito.never()).delete(Mockito.any());
    }
}