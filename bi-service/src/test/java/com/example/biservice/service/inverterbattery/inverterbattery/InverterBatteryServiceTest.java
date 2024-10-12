package com.example.biservice.service.inverterbattery.inverterbattery;

import com.example.biservice.entity.inverterbattery.BackupDuration;
import com.example.biservice.entity.inverterbattery.InverterBattery;
import com.example.biservice.entity.inverterbattery.InverterBatteryWarranty;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverterbattery.InverterBatteryDto;
import com.example.biservice.repository.inverterbattery.BackupDurationRepo;
import com.example.biservice.repository.inverterbattery.InverterBatteryRepo;
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
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class InverterBatteryServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private InverterBatteryRepo inverterBatteryRepo;
    @Mock
    private BackupDurationRepo backupDurationRepo;
    @Mock
    private InverterBatteryWarrantyRepo inverterBatteryWarrantyRepo;
    @InjectMocks
    private InverterBatteryServiceImp inverterBatteryServiceImp;

    private InverterBattery mockInverterBattery;
    private InverterBatteryDto mockInverterBatteryDto;

    @BeforeEach
    void setUp() {
        mockInverterBattery = InverterBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        mockInverterBatteryDto = InverterBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when(modelMapper.map(Mockito.any(InverterBatteryDto.class), Mockito.eq(InverterBattery.class))).thenReturn(mockInverterBattery);
        when(modelMapper.map(Mockito.any(InverterBattery.class), Mockito.eq(InverterBatteryDto.class))).thenReturn(mockInverterBatteryDto);
    }

    @AfterEach
    void tearDown() {
        mockInverterBattery = null;
        mockInverterBatteryDto = null;
    }

    @Test
    public void inverterBatteryService_createInverterBattery_returnInverterBattery(){
        //given
        mockInverterBattery = InverterBattery.builder()
                .modelName("abc")
                .build();
        mockInverterBatteryDto = InverterBatteryDto.builder()
                .modelName("abc")
                .build();
        InverterBattery savedBattery = InverterBattery.builder()
                .productId(1)
                .modelName("abc")
                .build();
        InverterBatteryDto savedBatteryDto = InverterBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .build();

        when((modelMapper.map(Mockito.any(InverterBatteryDto.class), Mockito.eq(InverterBattery.class)))).thenReturn(mockInverterBattery);
        when((modelMapper.map(Mockito.any(InverterBattery.class), Mockito.eq(InverterBatteryDto.class)))).thenReturn(savedBatteryDto);
        when(inverterBatteryRepo.save(Mockito.any(InverterBattery.class))).thenReturn(savedBattery);

        //when
        InverterBatteryDto result = inverterBatteryServiceImp.createInverterBattery(mockInverterBatteryDto);
        //then
        assertNotNull(result);
        assertEquals(result.getModelName(),mockInverterBatteryDto.getModelName());
    }

    @Test
    public void inverterBatteryService_createInverterBattery_throwUniqueConstraintException(){
        //given
        when(inverterBatteryRepo.save(Mockito.any(InverterBattery.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->inverterBatteryServiceImp.createInverterBattery(mockInverterBatteryDto));
    }

    @Test
    public void inverterBatteryService_findAllInverterBatteries_returnEmptyList() {
        //given
        when(inverterBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<InverterBatteryDto> result = inverterBatteryServiceImp.findAllInverterBatteries("modelName");
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void inverterBatteryService_findAllInverterBatteries_returnInverterBatteryList() {
        //given
        List<InverterBattery> inverterBatteries = Arrays.asList(
                InverterBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                InverterBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(inverterBatteryRepo.findAll(Mockito.any(Sort.class))).thenReturn(inverterBatteries);
        when(modelMapper.map(Mockito.any(InverterBattery.class), Mockito.eq(InverterBatteryDto.class)))
                .thenAnswer(invocation -> {
                    InverterBattery battery = invocation.getArgument(0);
                    return InverterBatteryDto.builder()
                            .productId(battery.getProductId())
                            .modelName(battery.getModelName())
                            .build();
                });

        //when
        List<InverterBatteryDto> result = inverterBatteryServiceImp.findAllInverterBatteries("modelName");
        //then
        assertEquals(inverterBatteries.size(), result.size());
        assertEquals("abc", result.get(0).getModelName());// Assert sorted order
        assertEquals("acb", result.get(1).getModelName());
    }

    @Test
    public void inverterBatteryService_findAllInverterBatteriesByBackupDurationAndWarranty_returnInverterBatteryPage() {
        //given
        Integer backupDurationId = 1;
        Integer warrantyId = 1;
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "modelName";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        // Mocking repository to return page of InverterBattery objects
        List<InverterBattery> batteries = Arrays.asList(
                InverterBattery.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                InverterBattery.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        Page<InverterBattery> page = new PageImpl<>(batteries, pageable, batteries.size());
        when(inverterBatteryRepo.findInverterBatteriesByBackupDurationAndWarranty(backupDurationId,warrantyId,pageable)).thenReturn(page);

        // Mocking modelMapper to map InverterBattery to InverterBatteryDto
        List<InverterBatteryDto> batteryDtos = batteries.stream()
                .map(battery -> InverterBatteryDto.builder()
                        .productId(battery.getProductId())
                        .modelName(battery.getModelName())
                        .build())
                .collect(Collectors.toList());
        when(modelMapper.map(Mockito.any(InverterBattery.class), Mockito.eq(InverterBatteryDto.class))).thenAnswer(invocation -> {
            InverterBattery battery = invocation.getArgument(0);
            return InverterBatteryDto.builder()
                    .productId(battery.getProductId())
                    .modelName(battery.getModelName())
                    .build();
        });

        // Calling service method
        PageResponse<InverterBatteryDto> result = inverterBatteryServiceImp.findAllInverterBatteriesByBackupDurationAndWarranty(backupDurationId, warrantyId, pageNumber, pageSize, sortBy);

        // Asserting result
        assertEquals(page.getNumber(), result.getPageNumber());
        assertEquals(page.getSize(), result.getPageSize());
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(batteryDtos.getFirst().getModelName(), result.getData().getFirst().getModelName());
    }


    @Test
    public void inverterBatteryService_findInverterBatteryById_returnInverterBattery(){
        //given
        Integer batteryId = 1;
        when(inverterBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockInverterBattery));

        //when
        InverterBatteryDto result = inverterBatteryServiceImp.findInverterBatteryById(batteryId);
        //then
        assertNotNull(result);
        assertEquals(mockInverterBatteryDto, result);
    }

    @Test
    public void inverterBatteryService_findInverterBatteryById_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(inverterBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterBatteryServiceImp.findInverterBatteryById(batteryId));
    }

    @Test
    public void inverterBatteryService_updateInverterBattery_returnInverterBattery() {
        //given
        Integer batteryId = 1;
        InverterBatteryDto inverterBatteryDto = InverterBatteryDto.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();
        InverterBattery updatedInverterBattery = InverterBattery.builder()
                .productId(1)
                .modelName("Updated Battery")
                .build();

        when(inverterBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockInverterBattery));
        when(modelMapper.map(Mockito.any(InverterBatteryDto.class), Mockito.eq(InverterBattery.class))).thenReturn(updatedInverterBattery);
        when(modelMapper.map(Mockito.any(InverterBattery.class), Mockito.eq(InverterBatteryDto.class))).thenReturn(inverterBatteryDto);
        when(inverterBatteryRepo.save(updatedInverterBattery)).thenReturn(updatedInverterBattery);

        //when
        InverterBatteryDto result = inverterBatteryServiceImp.updateInverterBattery(batteryId, inverterBatteryDto);
        //then
        assertEquals("Updated Battery", result.getModelName());
    }

    @Test
    public void inverterBatteryService_updateInverterBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(inverterBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterBatteryServiceImp.updateInverterBattery(batteryId, mockInverterBatteryDto));
    }

    @Test
    public void inverterBatteryService_deleteInverterBattery_returnVoid() {
        //given
        Integer batteryId = 1;
        when(inverterBatteryRepo.findById(batteryId)).thenReturn(Optional.of(mockInverterBattery));
        Mockito.doNothing().when(inverterBatteryRepo).delete(mockInverterBattery);

        //when&then
        assertAll(()->inverterBatteryServiceImp.deleteInverterBattery(batteryId));
        //then
        Mockito.verify(inverterBatteryRepo, Mockito.times(1)).delete(mockInverterBattery);
    }

    @Test
    public void inverterBatteryService_deleteInverterBattery_throwResourceNotFoundException() {
        //given
        Integer batteryId = 1;
        when(inverterBatteryRepo.findById(batteryId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterBatteryServiceImp.deleteInverterBattery(batteryId));
        //then
        Mockito.verify(inverterBatteryRepo, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void inverterBatteryService_addBackupDurationWithWarrantyToBattery_returnVoid() {
        //given
        Integer backupDurationId = 1;
        Integer inverterBatteryWarrantyId = 1;
        Integer inverterBatteryId = 1;
        BackupDuration backupDuration = BackupDuration.builder()
                .backupDurationId(1)
                .backupDuration(3)
                .build();
        InverterBatteryWarranty inverterBatteryWarranty = InverterBatteryWarranty.builder()
                .warrantyId(1)
                .warranty(24)
                .guarantee(24)
                .build();
        when(inverterBatteryRepo.findById(inverterBatteryId)).thenReturn(Optional.of(mockInverterBattery));
        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.of(backupDuration));
        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.of(inverterBatteryWarranty));
        when(inverterBatteryRepo.save(Mockito.any(InverterBattery.class))).thenReturn(mockInverterBattery);

        //when&then
        assertAll(()->inverterBatteryServiceImp.addBackupDurationWithWarrantyToBattery(inverterBatteryId,backupDurationId,inverterBatteryWarrantyId));
    }

    @Test
    public void inverterBatteryService_addBackupDurationWithWarrantyToBattery_throwResourceNotFoundException() {
        //given
        Integer backupDurationId = 1;
        Integer inverterBatteryWarrantyId = 1;
        Integer inverterBatteryId = 1;

        when(inverterBatteryRepo.findById(inverterBatteryId)).thenReturn(Optional.empty());
        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.empty());
        when(inverterBatteryWarrantyRepo.findById(inverterBatteryWarrantyId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> inverterBatteryServiceImp.addBackupDurationWithWarrantyToBattery(inverterBatteryId,backupDurationId,inverterBatteryWarrantyId));
        //then
        Mockito.verify(inverterBatteryRepo, Mockito.never()).save(Mockito.any()); // Ensure that save method is never called
    }
}