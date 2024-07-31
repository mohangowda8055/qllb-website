package com.example.biservice.service.inverterbattery.backupduration;

import com.example.biservice.entity.inverterbattery.BackupDuration;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.inverterbattery.BackupDurationDto;
import com.example.biservice.repository.inverterbattery.BackupDurationRepo;
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
class BackupDurationServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BackupDurationRepo backupDurationRepo;

    @InjectMocks
    private BackupDurationServiceImp backupDurationServiceImp;

    private BackupDuration mockBackupDuration;
    private BackupDurationDto mockBackupDurationDto;

    @BeforeEach
    void setUp() {
        mockBackupDuration = BackupDuration.builder()
                .backupDurationId(1)
                .backupDuration(2)
                .build();
        mockBackupDurationDto = BackupDurationDto.builder()
                .backupDurationId(1)
                .backupDuration(2)
                .build();

        when(modelMapper.map(Mockito.any(BackupDurationDto.class), Mockito.eq(BackupDuration.class))).thenReturn(mockBackupDuration);
        when(modelMapper.map(Mockito.any(BackupDuration.class), Mockito.eq(BackupDurationDto.class))).thenReturn(mockBackupDurationDto);
    }

    @AfterEach
    void tearDown() {
        mockBackupDuration = null;
        mockBackupDurationDto = null;
    }

    @Test
    public void backupDurationService_createBackupDuration_returnBackupDuration(){
        //given
        mockBackupDuration = BackupDuration.builder()
                .backupDuration(2)
                .build();
        mockBackupDurationDto = BackupDurationDto.builder()
                .backupDuration(2)
                .build();
        BackupDuration savedBackupDuration = BackupDuration.builder()
                .backupDurationId(1)
                .backupDuration(2)
                .build();
        BackupDurationDto savedBackupDurationDto = BackupDurationDto.builder()
                .backupDurationId(1)
                .backupDuration(2)
                .build();
        when((modelMapper.map(Mockito.any(BackupDurationDto.class), Mockito.eq(BackupDuration.class)))).thenReturn(mockBackupDuration);
        when((modelMapper.map(Mockito.any(BackupDuration.class), Mockito.eq(BackupDurationDto.class)))).thenReturn(savedBackupDurationDto);
        when(backupDurationRepo.save(Mockito.any(BackupDuration.class))).thenReturn(savedBackupDuration);

        //when
        BackupDurationDto result = backupDurationServiceImp.createBackupDuration(mockBackupDurationDto);
        //then
        assertNotNull(result);
        assertEquals(result.getBackupDuration(),mockBackupDurationDto.getBackupDuration());
    }

    @Test
    public void backupDurationService_createBackupDuration_throwUniqueConstraintException(){
        //given
        when(backupDurationRepo.save(Mockito.any(BackupDuration.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->backupDurationServiceImp.createBackupDuration(mockBackupDurationDto));
    }

    @Test
    public void backupDurationService_findAllBackupDurations_returnEmptyList() {
        //given
        when(backupDurationRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<BackupDurationDto> result = backupDurationServiceImp.findAllBackupDurations();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void backupDurationService_findAllBackupDurations_returnBackupDurationList() {
        //given
        List<BackupDuration> backupDurations = Arrays.asList(
                BackupDuration.builder()
                        .backupDurationId(1)
                        .backupDuration(2)
                        .build(),
                BackupDuration.builder()
                        .backupDurationId(2)
                        .backupDuration(4)
                        .build()
        );
        when(backupDurationRepo.findAll(Mockito.any(Sort.class))).thenReturn(backupDurations);
        when(modelMapper.map(Mockito.any(BackupDuration.class), Mockito.eq(BackupDurationDto.class)))
                .thenAnswer(invocation -> {
                    BackupDuration backupDuration = invocation.getArgument(0);
                    return new BackupDurationDto(backupDuration.getBackupDurationId(), backupDuration.getBackupDuration());
                });

        //when
        List<BackupDurationDto> result = backupDurationServiceImp.findAllBackupDurations();
        //then
        assertEquals(backupDurations.size(), result.size());
        assertEquals(2, result.get(0).getBackupDuration());// Assert sorted order
        assertEquals(4, result.get(1).getBackupDuration());
    }

    @Test
    public void backupDurationService_findBackupDurationById_returnBackupDuration(){
        //given
        Integer backupDurationId = 1;
        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.of(mockBackupDuration));

        //when
        BackupDurationDto result = backupDurationServiceImp.findBackupDurationById(backupDurationId);
        //then
        assertNotNull(result);
        assertEquals(mockBackupDurationDto, result);
    }

    @Test
    public void backupDurationService_findBackupDurationById_throwResourceNotFoundException() {
        //given
        Integer backupDurationId = 1;
        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> backupDurationServiceImp.findBackupDurationById(backupDurationId));
    }

    @Test
    public void backupDurationService_updateBackupDuration_returnBackupDuration() {
        //given
        Integer backupDurationId = 1;
        BackupDurationDto backupDurationDto = BackupDurationDto.builder()
                .backupDurationId(1)
                .backupDuration(3)
                .build();
        BackupDuration updatedBackupDuration = BackupDuration.builder()
                .backupDurationId(1)
                .backupDuration(3)
                .build();

        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.of(mockBackupDuration));
        when(modelMapper.map(Mockito.any(BackupDurationDto.class), Mockito.eq(BackupDuration.class))).thenReturn(updatedBackupDuration);
        when(modelMapper.map(Mockito.any(BackupDuration.class), Mockito.eq(BackupDurationDto.class))).thenReturn(backupDurationDto);
        when(backupDurationRepo.save(updatedBackupDuration)).thenReturn(updatedBackupDuration);

        //when
        BackupDurationDto result = backupDurationServiceImp.updateBackupDuration(backupDurationId, backupDurationDto);
        //then
        assertEquals(3, result.getBackupDuration());
    }

    @Test
    public void backupDurationService_updateBackupDuration_throwResourceNotFoundException() {
        //given
        Integer backupDurationId = 1;
        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> backupDurationServiceImp.updateBackupDuration(backupDurationId, mockBackupDurationDto));
    }

    @Test
    public void backupDurationService_deleteBackupDuration_returnVoid() {
        //given
        Integer backupDurationId = 1;
        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.of(mockBackupDuration));
        Mockito.doNothing().when(backupDurationRepo).delete(mockBackupDuration);

        //when&then
        assertAll(()->backupDurationServiceImp.deleteBackupDuration(backupDurationId));
        //then
        Mockito.verify(backupDurationRepo, Mockito.times(1)).delete(mockBackupDuration);
    }

    @Test
    public void backupDurationService_deleteBackupDuration_throwResourceNotFoundException() {
        //given
        Integer backupDurationId = 1;
        when(backupDurationRepo.findById(backupDurationId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> backupDurationServiceImp.deleteBackupDuration(backupDurationId));
        //then
        Mockito.verify(backupDurationRepo, Mockito.never()).delete(Mockito.any());
    }
}