package com.example.biservice.service.inverter.capacity;

import com.example.biservice.entity.inverter.InverterCapacity;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.inverter.InverterCapacityDto;
import com.example.biservice.repository.inverter.InverterCapacityRepo;
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
class InverterCapacityServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private InverterCapacityRepo inverterCapacityRepo;

    @InjectMocks
    private InverterCapacityServiceImp inverterCapacityServiceImp;

    private InverterCapacity mockInverterCapacity;
    private InverterCapacityDto mockInverterCapacityDto;

    @BeforeEach
    void setUp() {
        mockInverterCapacity = InverterCapacity.builder()
                .capacityId(1)
                .capacity(10)
                .build();
        mockInverterCapacityDto = InverterCapacityDto.builder()
                .capacityId(1)
                .capacity(10)
                .build();

        when(modelMapper.map(Mockito.any(InverterCapacityDto.class), Mockito.eq(InverterCapacity.class))).thenReturn(mockInverterCapacity);
        when(modelMapper.map(Mockito.any(InverterCapacity.class), Mockito.eq(InverterCapacityDto.class))).thenReturn(mockInverterCapacityDto);
    }

    @AfterEach
    void tearDown() {
        mockInverterCapacity = null;
        mockInverterCapacityDto = null;
    }

    @Test
    public void inverterCapacityService_createCapacity_returnInverterCapacity(){
        //given
        mockInverterCapacity = InverterCapacity.builder()
                .capacity(10)
                .build();
        mockInverterCapacityDto = InverterCapacityDto.builder()
                .capacity(10)
                .build();
        InverterCapacity savedCapacity = InverterCapacity.builder()
                .capacityId(1)
                .capacity(10)
                .build();
        InverterCapacityDto savedCapacityDto = InverterCapacityDto.builder()
                .capacityId(1)
                .capacity(10)
                .build();
        when((modelMapper.map(Mockito.any(InverterCapacityDto.class), Mockito.eq(InverterCapacity.class)))).thenReturn(mockInverterCapacity);
        when((modelMapper.map(Mockito.any(InverterCapacity.class), Mockito.eq(InverterCapacityDto.class)))).thenReturn(savedCapacityDto);
        when(inverterCapacityRepo.save(Mockito.any(InverterCapacity.class))).thenReturn(savedCapacity);

        //when
        InverterCapacityDto result = inverterCapacityServiceImp.createCapacity(mockInverterCapacityDto);
        //then
        assertNotNull(result);
        assertEquals(result.getCapacity(),mockInverterCapacityDto.getCapacity());
    }

    @Test
    public void inverterCapacityService_createCapacity_throwUniqueConstraintException(){
        //given
        when(inverterCapacityRepo.save(Mockito.any(InverterCapacity.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->inverterCapacityServiceImp.createCapacity(mockInverterCapacityDto));
    }

    @Test
    public void inverterCapacityService_findAllCapacities_returnEmptyList() {
        //given
        when(inverterCapacityRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());

        //when
        List<InverterCapacityDto> result = inverterCapacityServiceImp.findAllCapacities();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void inverterCapacityService_findAllCapacities_returnInverterCapacityList() {
        //given
        List<InverterCapacity> inverterCapacities = Arrays.asList(
                InverterCapacity.builder()
                        .capacityId(1)
                        .capacity(10)
                        .build(),
                InverterCapacity.builder()
                        .capacityId(2)
                        .capacity(20)
                        .build()
        );
        when(inverterCapacityRepo.findAll(Mockito.any(Sort.class))).thenReturn(inverterCapacities);
        when(modelMapper.map(Mockito.any(InverterCapacity.class), Mockito.eq(InverterCapacityDto.class)))
                .thenAnswer(invocation -> {
                    InverterCapacity capacity = invocation.getArgument(0);
                    return new InverterCapacityDto(capacity.getCapacityId(), capacity.getCapacity());
                });

        //when
        List<InverterCapacityDto> result = inverterCapacityServiceImp.findAllCapacities();
        //then
        assertEquals(inverterCapacities.size(), result.size());
        assertEquals(10, result.get(0).getCapacity());// Assert sorted order
        assertEquals(20, result.get(1).getCapacity());
    }

    @Test
    public void inverterCapacityService_findCapacityById_returnInverterCapacity(){
        //given
        Integer capacityId = 1;
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.of(mockInverterCapacity));

        //when
        InverterCapacityDto result = inverterCapacityServiceImp.findCapacityById(capacityId);
        //then
        assertNotNull(result);
        assertEquals(mockInverterCapacityDto, result);
    }

    @Test
    public void inverterCapacityService_findCapacityById_throwResourceNotFoundException() {
        //given
        Integer capacityId = 1;
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterCapacityServiceImp.findCapacityById(capacityId));
    }

    @Test
    public void inverterCapacityService_updateCapacity_returnInverterCapacity() {
        //given
        Integer capacityId = 1;
        InverterCapacityDto inverterCapacityDto = InverterCapacityDto.builder()
                .capacityId(1)
                .capacity(30)
                .build();
        InverterCapacity updatedInverterCapacity = InverterCapacity.builder()
                .capacityId(1)
                .capacity(30)
                .build();

        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.of(mockInverterCapacity));
        when(modelMapper.map(Mockito.any(InverterCapacityDto.class), Mockito.eq(InverterCapacity.class))).thenReturn(updatedInverterCapacity);
        when(modelMapper.map(Mockito.any(InverterCapacity.class), Mockito.eq(InverterCapacityDto.class))).thenReturn(inverterCapacityDto);
        when(inverterCapacityRepo.save(updatedInverterCapacity)).thenReturn(updatedInverterCapacity);

        //when
        InverterCapacityDto result = inverterCapacityServiceImp.updateCapacity(capacityId, inverterCapacityDto);
        //then
        assertEquals(30, result.getCapacity());
    }

    @Test
    public void inverterCapacityService_updateCapacity_throwResourceNotFoundException() {
        //given
        Integer capacityId = 1;
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterCapacityServiceImp.updateCapacity(capacityId, mockInverterCapacityDto));
    }

    @Test
    public void inverterCapacityService_deleteCapacity_returnVoid() {
        //given
        Integer capacityId = 1;
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.of(mockInverterCapacity));
        Mockito.doNothing().when(inverterCapacityRepo).delete(mockInverterCapacity);

        //when&then
        assertAll(()->inverterCapacityServiceImp.deleteCapacity(capacityId));
        //then
        Mockito.verify(inverterCapacityRepo, Mockito.times(1)).delete(mockInverterCapacity);
    }

    @Test
    public void inverterCapacityService_deleteCapacity_throwResourceNotFoundException() {
        //given
        Integer capacityId = 1;
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterCapacityServiceImp.deleteCapacity(capacityId));
        //then
        Mockito.verify(inverterCapacityRepo, Mockito.never()).delete(Mockito.any());
    }
}