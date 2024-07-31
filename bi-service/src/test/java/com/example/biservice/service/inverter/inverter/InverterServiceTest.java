package com.example.biservice.service.inverter.inverter;

import com.example.biservice.entity.inverter.Inverter;
import com.example.biservice.entity.inverter.InverterCapacity;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverter.InverterDto;
import com.example.biservice.repository.inverter.InverterCapacityRepo;
import com.example.biservice.repository.inverter.InverterRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class InverterServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private InverterRepo inverterRepo;
    @Mock
    private InverterCapacityRepo inverterCapacityRepo;
    @InjectMocks
    private InverterServiceImp inverterServiceImp;

    private Inverter mockInverter;
    private InverterDto mockInverterDto;

    @BeforeEach
    void setUp() {
        mockInverter = Inverter.builder()
                .productId(1)
                .modelName("abc")
                .build();
        mockInverterDto = InverterDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when(modelMapper.map(Mockito.any(InverterDto.class), Mockito.eq(Inverter.class))).thenReturn(mockInverter);
        when(modelMapper.map(Mockito.any(Inverter.class), Mockito.eq(InverterDto.class))).thenReturn(mockInverterDto);
    }

    @AfterEach
    void tearDown() {
        mockInverter = null;
        mockInverterDto = null;
    }

    @Test
    public void inverterService_createInverter_returnInverter(){
        //given
        Integer capacityId = 1;
        InverterCapacity inverterCapacity = InverterCapacity.builder()
                .capacityId(1)
                .capacity(10)
                .build();
        mockInverter = Inverter.builder()
                .modelName("abc")
                .build();
        mockInverterDto = InverterDto.builder()
                .modelName("abc")
                .build();
        Inverter savedInverter = Inverter.builder()
                .productId(1)
                .modelName("abc")
                .build();
        InverterDto savedInverterDto = InverterDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when((modelMapper.map(Mockito.any(InverterDto.class), Mockito.eq(Inverter.class)))).thenReturn(mockInverter);
        when((modelMapper.map(Mockito.any(Inverter.class), Mockito.eq(InverterDto.class)))).thenReturn(savedInverterDto);
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.of(inverterCapacity));
        when(inverterRepo.save(Mockito.any(Inverter.class))).thenReturn(savedInverter);

        //when
        InverterDto result = inverterServiceImp.createInverter(mockInverterDto,capacityId);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockInverterDto.getModelName());
    }

    @Test
    public void inverterService_createInverter_throwUniqueConstraintException(){
        //given
        Integer capacityId = 1;
        InverterCapacity inverterCapacity = InverterCapacity.builder()
                .capacityId(1)
                .capacity(10)
                .build();
        when(inverterRepo.save(Mockito.any(Inverter.class))).thenThrow(DataIntegrityViolationException.class);
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.of(inverterCapacity));

        //when&then
        assertThrows(UniqueConstraintException.class, ()->inverterServiceImp.createInverter(mockInverterDto,capacityId));
    }

    @Test
    public void inverterService_findAllBatteries_returnEmptyList() {
        //given
        when(inverterRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<InverterDto> result = inverterServiceImp.findAllInverters("modelName");
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void inverterService_findAllBatteries_returnInverterList() {
        //given
        List<Inverter> twoVBatteries = Arrays.asList(
                Inverter.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                Inverter.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(inverterRepo.findAll(Mockito.any(Sort.class))).thenReturn(twoVBatteries);
        when(modelMapper.map(Mockito.any(Inverter.class), Mockito.eq(InverterDto.class)))
                .thenAnswer(invocation -> {
                    Inverter inverter = invocation.getArgument(0);
                    return InverterDto.builder()
                            .productId(inverter.getProductId())
                            .modelName(inverter.getModelName())
                            .build();
                });

        //when
        List<InverterDto> result = inverterServiceImp.findAllInverters("modelName");
        //then
        assertEquals(twoVBatteries.size(), result.size());
        assertEquals("abc", result.get(0).getModelName());// Assert sorted order
        assertEquals("acb", result.get(1).getModelName());
    }

    @Test
    public void inverterService_findAllBatteriesByModel_returnInverterPage() {
        //given
        Integer capacityId = 1;
        InverterCapacity inverterCapacity = InverterCapacity.builder()
                .capacityId(1)
                .capacity(10)
                .build();
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "modelName";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        // Mocking repository to return page of Inverter objects
        List<Inverter> batteries = Arrays.asList(
                Inverter.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                Inverter.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        Page<Inverter> page = new PageImpl<>(batteries, pageable, batteries.size());
        when(inverterRepo.findAllByInverterCapacity(inverterCapacity, pageable)).thenReturn(page);
        when(inverterCapacityRepo.findById(capacityId)).thenReturn(Optional.of(inverterCapacity));

        // Mocking modelMapper to map Inverter to InverterDto
        List<InverterDto> inverterDtos = batteries.stream()
                .map(inverter -> InverterDto.builder()
                        .productId(inverter.getProductId())
                        .modelName(inverter.getModelName())
                        .build())
                .collect(Collectors.toList());
        when(modelMapper.map(Mockito.any(Inverter.class), Mockito.eq(InverterDto.class))).thenAnswer(invocation -> {
            Inverter inverter = invocation.getArgument(0);
            return InverterDto.builder()
                    .productId(inverter.getProductId())
                    .modelName(inverter.getModelName())
                    .build();
        });

        // Calling service method
        PageResponse<InverterDto> result = inverterServiceImp.findAllInvertersByInverterCapacity(capacityId, pageNumber, pageSize, sortBy);

        // Asserting result
        assertEquals(page.getNumber(), result.getPageNumber());
        assertEquals(page.getSize(), result.getPageSize());
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(inverterDtos.getFirst().getModelName(), result.getData().getFirst().getModelName());
    }


    @Test
    public void inverterService_findInverterById_returnInverter(){
        //given
        Integer inverterId = 1;
        when(inverterRepo.findById(inverterId)).thenReturn(Optional.of(mockInverter));

        //when
        InverterDto result = inverterServiceImp.findInverterById(inverterId);
        //then
        assertNotNull(result);
        assertEquals(mockInverterDto, result);
    }

    @Test
    public void inverterService_findInverterById_throwResourceNotFoundException() {
        //given
        Integer inverterId = 1;
        when(inverterRepo.findById(inverterId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterServiceImp.findInverterById(inverterId));
    }

    @Test
    public void inverterService_updateInverter_returnInverter() {
        //given
        Integer inverterId = 1;
        InverterDto inverterDto = InverterDto.builder()
                .productId(1)
                .modelName("Updated Inverter")
                .build();
        Inverter updatedInverter = Inverter.builder()
                .productId(1)
                .modelName("Updated Inverter")
                .build();

        when(inverterRepo.findById(inverterId)).thenReturn(Optional.of(mockInverter));
        when(modelMapper.map(Mockito.any(InverterDto.class), Mockito.eq(Inverter.class))).thenReturn(updatedInverter);
        when(modelMapper.map(Mockito.any(Inverter.class), Mockito.eq(InverterDto.class))).thenReturn(inverterDto);
        when(inverterRepo.save(updatedInverter)).thenReturn(updatedInverter);

        //when
        InverterDto result = inverterServiceImp.updateInverter(inverterId, inverterDto);
        //then
        assertEquals("Updated Inverter", result.getModelName());
    }

    @Test
    public void inverterService_updateInverter_throwResourceNotFoundException() {
        //given
        Integer inverterId = 1;
        when(inverterRepo.findById(inverterId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterServiceImp.updateInverter(inverterId, mockInverterDto));
    }

    @Test
    public void inverterService_deleteInverter_returnVoid() {
        //given
        Integer inverterId = 1;
        when(inverterRepo.findById(inverterId)).thenReturn(Optional.of(mockInverter));
        Mockito.doNothing().when(inverterRepo).delete(mockInverter);

        //when&then
        assertAll(()->inverterServiceImp.deleteInverter(inverterId));
        //then
        Mockito.verify(inverterRepo, Mockito.times(1)).delete(mockInverter);
    }

    @Test
    public void inverterService_deleteInverter_throwResourceNotFoundException() {
        //given
        Integer inverterId = 1;
        when(inverterRepo.findById(inverterId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterServiceImp.deleteInverter(inverterId));
        //then
        Mockito.verify(inverterRepo, Mockito.never()).delete(Mockito.any());
    }
}