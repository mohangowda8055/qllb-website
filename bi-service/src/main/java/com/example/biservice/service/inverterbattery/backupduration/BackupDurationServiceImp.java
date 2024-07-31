package com.example.biservice.service.inverterbattery.backupduration;

import com.example.biservice.entity.inverterbattery.BackupDuration;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.inverterbattery.BackupDurationDto;
import com.example.biservice.repository.inverterbattery.BackupDurationRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BackupDurationServiceImp implements BackupDurationService{

    private final ModelMapper modelMapper;
    
    private final BackupDurationRepo backupDurationRepo;


    @Override
    public BackupDurationDto createBackupDuration(BackupDurationDto backupDurationDto) {
        BackupDuration backupDuration = this.modelMapper.map(backupDurationDto, BackupDuration.class);
        BackupDuration savedBackupDuration;
        try {
            savedBackupDuration = this.backupDurationRepo.save(backupDuration);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the backup duration - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedBackupDuration, BackupDurationDto.class);
    }

    @Override
    public List<BackupDurationDto> findAllBackupDurations() {
        Sort sort = Sort.by("backupDuration");
        List<BackupDuration> backupDurations = this.backupDurationRepo.findAll(sort);
        return backupDurations.stream().map((backupDuration)->this.modelMapper.map(backupDuration, BackupDurationDto.class)).collect(Collectors.toList());
    }

    @Override
    public BackupDurationDto findBackupDurationById(Integer backupDurationId) {
        BackupDuration backupDuration = this.backupDurationRepo.findById(backupDurationId).orElseThrow(()->new ResourceNotFoundException("BackupDuration not found with id "+backupDurationId));
        return this.modelMapper.map(backupDuration, BackupDurationDto.class);
    }

    @Override
    public BackupDurationDto updateBackupDuration(Integer backupDurationId, BackupDurationDto backupDurationDto) {
        Optional<BackupDuration> backupDurationOptional = this.backupDurationRepo.findById(backupDurationId);
        if(backupDurationOptional.isPresent()) {
            BackupDuration backupDuration = this.modelMapper.map(backupDurationDto, BackupDuration.class);
            backupDuration.setInverterBatteries(backupDurationOptional.get().getInverterBatteries());
            backupDuration.setInverterBatteryWarranties(backupDurationOptional.get().getInverterBatteryWarranties());
            BackupDuration updatedBackupDuration;
            try {
                updatedBackupDuration = this.backupDurationRepo.save(backupDuration);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the backup duration - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBackupDuration, BackupDurationDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update backupDuration - no existing backupDuration found with id "+backupDurationId);
        }
    }

    @Override
    public void deleteBackupDuration(Integer backupDurationId) {
        BackupDuration backupDuration = this.backupDurationRepo.findById(backupDurationId).orElseThrow(()->new ResourceNotFoundException("Cannot delete BackupDuration - no existing BackupDuration found with id "+backupDurationId));
        this.backupDurationRepo.delete(backupDuration);
    }

}
