package com.example.biservice.service.inverterbattery.inverterbattery;

import com.example.biservice.entity.ProductType;
import com.example.biservice.entity.inverterbattery.InverterBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverterbattery.InverterBatteryDto;
import com.example.biservice.repository.inverterbattery.BackupDurationRepo;
import com.example.biservice.repository.inverterbattery.InverterBatteryRepo;
import com.example.biservice.repository.inverterbattery.InverterBatteryWarrantyRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InverterBatteryServiceImp implements InverterBatteryService{

    private final ModelMapper modelMapper;

    private final InverterBatteryRepo inverterBatteryRepo;

    private final BackupDurationRepo backupDurationRepo;

    private final InverterBatteryWarrantyRepo inverterBatteryWarrantyRepo;


    @Override
    public InverterBatteryDto createInverterBattery(InverterBatteryDto inverterBatteryDto) {
        InverterBattery inverterBattery = this.modelMapper.map(inverterBatteryDto, InverterBattery.class);
        inverterBattery.setProductType(ProductType.INVERTERBATTERY);
        InverterBattery savedInverterBattery;
        try {
            savedInverterBattery = this.inverterBatteryRepo.save(inverterBattery);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the inverter battery - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedInverterBattery, InverterBatteryDto.class);
    }

    @Override
    public PageResponse<InverterBatteryDto> findAllInverterBatteriesByBackupDurationAndWarranty(Integer backupDurationId, Integer warrantyId, Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<InverterBattery> pageInverterBatteries = this.inverterBatteryRepo.findInverterBatteriesByBackupDurationAndWarranty(backupDurationId,warrantyId,pageable);
        List<InverterBattery> inverterBatteries = pageInverterBatteries.getContent();
        List<InverterBatteryDto> inverterBatteryDtos = inverterBatteries.stream().map((inverterBattery)->this.modelMapper.map(inverterBattery, InverterBatteryDto.class)).collect(Collectors.toList());
        return PageResponse.<InverterBatteryDto>builder()
                .data(inverterBatteryDtos)
                .pageNumber(pageInverterBatteries.getNumber())
                .pageSize(pageInverterBatteries.getSize())
                .totalElements(pageInverterBatteries.getTotalElements())
                .totalPages(pageInverterBatteries.getTotalPages())
                .build();
    }

    @Override
    public List<InverterBatteryDto> findAllInverterBatteries(String sortBy) {
        Sort sort = Sort.by(sortBy);
        List<InverterBattery> inverterBatteries = this.inverterBatteryRepo.findAll(sort);
        return inverterBatteries.stream().map((inverterBattery)->this.modelMapper.map(inverterBattery, InverterBatteryDto.class)).collect(Collectors.toList());
    }

    @Override
    public InverterBatteryDto findInverterBatteryById(Integer batteryId) {
        InverterBattery inverterBattery = this.inverterBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("InverterBattery not found with id "+batteryId));
        return this.modelMapper.map(inverterBattery, InverterBatteryDto.class);
    }

    @Override
    public InverterBatteryDto updateInverterBattery(Integer batteryId, InverterBatteryDto inverterBatteryDto) {
        Optional<InverterBattery> inverterBatteryOptional = this.inverterBatteryRepo.findById(batteryId);
        if(inverterBatteryOptional.isPresent()) {
            InverterBattery inverterBattery = this.modelMapper.map(inverterBatteryDto, InverterBattery.class);
            inverterBattery.setProductType(inverterBatteryOptional.get().getProductType());
            inverterBattery.setInverterBatteryWarranties(inverterBatteryOptional.get().getInverterBatteryWarranties());
            inverterBattery.setBackupDurations(inverterBatteryOptional.get().getBackupDurations());
            InverterBattery updatedInverterBattery;
            try {
                updatedInverterBattery = this.inverterBatteryRepo.save(inverterBattery);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the inverter battery - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedInverterBattery, InverterBatteryDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update inverterBattery - no existing inverterBattery found with id "+batteryId);
        }
    }

    @Override
    public void deleteInverterBattery(Integer batteryId) {
        InverterBattery inverterBattery = this.inverterBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot delete InverterBattery - no existing InverterBattery found with id "+batteryId));
        this.inverterBatteryRepo.delete(inverterBattery);
    }

    @Override
    public void addBackupDurationWithWarrantyToBattery(Integer batteryId, Integer backupDurationId, Integer warrantyId) {
        this.inverterBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot map InverterBattery with backup duration and warranty - no existing InverterBattery found with id "+batteryId));
        this.inverterBatteryWarrantyRepo.findById(warrantyId).orElseThrow(()->new ResourceNotFoundException("Cannot map InverterBattery with backup duration and warranty - no existing InverterBattery Warranty found with id "+warrantyId));
        this.backupDurationRepo.findById(backupDurationId).orElseThrow(()->new ResourceNotFoundException("Cannot map InverterBattery with backup duration and warranty - no existing BackupDuration found with id "+backupDurationId));
        try {
            this.inverterBatteryRepo.addBackupDurationWithWarrantyToBattery(batteryId, backupDurationId, warrantyId);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("An error occurred while mapping the inverter battery, warranty and backup-duration - Duplicate Entry detected");
        }
    }
}
