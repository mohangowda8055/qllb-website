package com.example.biservice.service.inverterbattery.inverterbatterywarranty;

import com.example.biservice.entity.inverterbattery.InverterBatteryWarranty;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.inverterbattery.InverterBatteryWarrantyDto;
import com.example.biservice.repository.inverterbattery.InverterBatteryWarrantyRepo;
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
class InverterBatteryWarrantyServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private InverterBatteryWarrantyRepo inverterBatteryWarrantyRepo;

    @InjectMocks
    private InverterBatteryWarrantyServiceImp inverterBatteryWarrantyServiceImp;

    private InverterBatteryWarranty mockInverterBatteryWarranty;
    private InverterBatteryWarrantyDto mockInverterBatteryWarrantyDto;

    @BeforeEach
    void setUp() {
        mockInverterBatteryWarranty = InverterBatteryWarranty.builder()
                .warrantyId(1)
                .warranty(12)
                .guarantee(12)
                .build();
        mockInverterBatteryWarrantyDto = InverterBatteryWarrantyDto.builder()
                .warrantyId(1)
                .warranty(12)
                .guarantee(12)
                .build();

        when(modelMapper.map(Mockito.any(InverterBatteryWarrantyDto.class), Mockito.eq(InverterBatteryWarranty.class))).thenReturn(mockInverterBatteryWarranty);
        when(modelMapper.map(Mockito.any(InverterBatteryWarranty.class), Mockito.eq(InverterBatteryWarrantyDto.class))).thenReturn(mockInverterBatteryWarrantyDto);
    }

    @AfterEach
    void tearDown() {
        mockInverterBatteryWarranty = null;
        mockInverterBatteryWarrantyDto = null;
    }

    @Test
    public void inverterBatteryWarrantyService_createWarranty_returnInverterBatteryWarranty(){
        //given
        mockInverterBatteryWarranty = InverterBatteryWarranty.builder()
                .warranty(12)
                .guarantee(12)
                .build();
        mockInverterBatteryWarrantyDto = InverterBatteryWarrantyDto.builder()
                .warranty(12)
                .guarantee(12)
                .build();
        InverterBatteryWarranty savedWarranty = InverterBatteryWarranty.builder()
                .warrantyId(1)
                .warranty(12)
                .guarantee(12)
                .build();
        InverterBatteryWarrantyDto savedWarrantyDto = InverterBatteryWarrantyDto.builder()
                .warrantyId(1)
                .warranty(12)
                .guarantee(12)
                .build();
        when((modelMapper.map(Mockito.any(InverterBatteryWarrantyDto.class), Mockito.eq(InverterBatteryWarranty.class)))).thenReturn(mockInverterBatteryWarranty);
        when((modelMapper.map(Mockito.any(InverterBatteryWarranty.class), Mockito.eq(InverterBatteryWarrantyDto.class)))).thenReturn(savedWarrantyDto);
        when(inverterBatteryWarrantyRepo.save(Mockito.any(InverterBatteryWarranty.class))).thenReturn(savedWarranty);

        //when
        InverterBatteryWarrantyDto result = inverterBatteryWarrantyServiceImp.createInverterBatteryWarranty(mockInverterBatteryWarrantyDto);
        //then
        assertNotNull(result);
        assertEquals(result.getWarranty(),mockInverterBatteryWarrantyDto.getWarranty());
    }

    @Test
    public void inverterBatteryWarrantyService_createWarranty_throwUniqueConstraintException(){
        //given
        when(inverterBatteryWarrantyRepo.save(Mockito.any(InverterBatteryWarranty.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->inverterBatteryWarrantyServiceImp.createInverterBatteryWarranty(mockInverterBatteryWarrantyDto));
    }

    @Test
    public void inverterBatteryWarrantyService_findAllInverterBatteryWarranties_returnEmptyList() {
        //given
        when(inverterBatteryWarrantyRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<InverterBatteryWarrantyDto> result = inverterBatteryWarrantyServiceImp.findAllInverterBatteryWarranties();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void inverterBatteryWarrantyService_findAllInverterBatteryWarranties_returnInverterBatteryWarrantyList() {
        //given
        List<InverterBatteryWarranty> inverterBatteryWarranties = Arrays.asList(
                InverterBatteryWarranty.builder()
                        .warrantyId(1)
                        .warranty(12)
                        .guarantee(12)
                        .build(),
                InverterBatteryWarranty.builder()
                        .warrantyId(2)
                        .warranty(14)
                        .guarantee(14)
                        .build()
        );
        when(inverterBatteryWarrantyRepo.findAll(Mockito.any(Sort.class))).thenReturn(inverterBatteryWarranties);
        when(modelMapper.map(Mockito.any(InverterBatteryWarranty.class), Mockito.eq(InverterBatteryWarrantyDto.class)))
                .thenAnswer(invocation -> {
                    InverterBatteryWarranty inverterBatteryWarranty = invocation.getArgument(0);
                    return new InverterBatteryWarrantyDto(inverterBatteryWarranty.getWarrantyId(), inverterBatteryWarranty.getWarranty(), inverterBatteryWarranty.getGuarantee());
                });

        //when
        List<InverterBatteryWarrantyDto> result = inverterBatteryWarrantyServiceImp.findAllInverterBatteryWarranties();
        //then
        assertEquals(inverterBatteryWarranties.size(), result.size());
        assertEquals(12, result.get(0).getWarranty());// Assert sorted order
        assertEquals(14, result.get(1).getWarranty());
    }

    @Test
    public void inverterBatteryWarrantyService_findWarrantyById_returnInverterBatteryWarranty(){
        //given
        Integer inverterBatteryWarrantyId = 1;
        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.of(mockInverterBatteryWarranty));

        //when
        InverterBatteryWarrantyDto result = inverterBatteryWarrantyServiceImp.findInverterBatteryWarrantyById(inverterBatteryWarrantyId);
        //then
        assertNotNull(result);
        assertEquals(mockInverterBatteryWarrantyDto, result);
    }

    @Test
    public void inverterBatteryWarrantyService_findWarrantyById_throwResourceNotFoundException() {
        //given
        Integer inverterBatteryWarrantyId = 1;
        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterBatteryWarrantyServiceImp.findInverterBatteryWarrantyById(inverterBatteryWarrantyId));
    }

    @Test
    public void inverterBatteryWarrantyService_updateWarranty_returnInverterBatteryWarranty() {
        //given
        Integer inverterBatteryWarrantyId = 1;
        InverterBatteryWarrantyDto inverterBatteryWarrantyDto = InverterBatteryWarrantyDto.builder()
                .warrantyId(1)
                .warranty(24)
                .guarantee(24)
                .build();
        InverterBatteryWarranty updatedInverterBatteryWarranty = InverterBatteryWarranty.builder()
                .warrantyId(1)
                .warranty(24)
                .guarantee(24)
                .build();

        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.of(mockInverterBatteryWarranty));
        when(modelMapper.map(Mockito.any(InverterBatteryWarrantyDto.class), Mockito.eq(InverterBatteryWarranty.class))).thenReturn(updatedInverterBatteryWarranty);
        when(modelMapper.map(Mockito.any(InverterBatteryWarranty.class), Mockito.eq(InverterBatteryWarrantyDto.class))).thenReturn(inverterBatteryWarrantyDto);
        when(inverterBatteryWarrantyRepo.save(updatedInverterBatteryWarranty)).thenReturn(updatedInverterBatteryWarranty);

        //when
        InverterBatteryWarrantyDto result = inverterBatteryWarrantyServiceImp.updateInverterBatteryWarranty(inverterBatteryWarrantyId, inverterBatteryWarrantyDto);
        //then
        assertEquals(24, result.getWarranty());
    }

    @Test
    public void inverterBatteryWarrantyService_updateWarranty_throwResourceNotFoundException() {
        //given
        Integer inverterBatteryWarrantyId = 1;
        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterBatteryWarrantyServiceImp.updateInverterBatteryWarranty(inverterBatteryWarrantyId, mockInverterBatteryWarrantyDto));
    }

    @Test
    public void inverterBatteryWarrantyService_deleteWarranty_returnVoid() {
        //given
        Integer inverterBatteryWarrantyId = 1;
        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.of(mockInverterBatteryWarranty));
        Mockito.doNothing().when(inverterBatteryWarrantyRepo).delete(mockInverterBatteryWarranty);

        //when&then
        assertAll(()->inverterBatteryWarrantyServiceImp.deleteInverterBatteryWarranty(inverterBatteryWarrantyId));
        //then
        Mockito.verify(inverterBatteryWarrantyRepo, Mockito.times(1)).delete(mockInverterBatteryWarranty);
    }

    @Test
    public void inverterBatteryWarrantyService_deleteWarranty_throwResourceNotFoundException() {
        //given
        Integer inverterBatteryWarrantyId = 1;
        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterBatteryWarrantyServiceImp.deleteInverterBatteryWarranty(inverterBatteryWarrantyId));
        //then
        Mockito.verify(inverterBatteryWarrantyRepo, Mockito.never()).delete(Mockito.any());
    }
}