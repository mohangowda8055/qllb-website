package com.example.biservice.service.inverterbattery.inverterbatterywarranty;

import com.example.biservice.entity.inverterbattery.InverterBatteryWarranty;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.inverterbattery.InverterBatteryWarrantyDto;
import com.example.biservice.repository.inverterbattery.InverterBatteryWarrantyRepo;
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
public class InverterBatteryWarrantyServiceImp implements InverterBatteryWarrantyService{
    
    private final ModelMapper modelMapper;
    
    private final InverterBatteryWarrantyRepo inverterBatteryWarrantyRepo;


    @Override
    public InverterBatteryWarrantyDto createInverterBatteryWarranty(InverterBatteryWarrantyDto inverterBatteryWarrantyDto) {
        InverterBatteryWarranty inverterBatteryWarranty = this.modelMapper.map(inverterBatteryWarrantyDto, InverterBatteryWarranty.class);
        InverterBatteryWarranty savedInverterBatteryWarranty;
        try {
            savedInverterBatteryWarranty = this.inverterBatteryWarrantyRepo.save(inverterBatteryWarranty);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the inverter-battery-warranty - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedInverterBatteryWarranty, InverterBatteryWarrantyDto.class);
    }

    @Override
    public List<InverterBatteryWarrantyDto> findAllInverterBatteryWarranties() {
        Sort sort = Sort.by("warranty");
        List<InverterBatteryWarranty> inverterBatteryWarranties = this.inverterBatteryWarrantyRepo.findAll(sort);
        return inverterBatteryWarranties.stream().map((inverterBatteryWarranty)->this.modelMapper.map(inverterBatteryWarranty, InverterBatteryWarrantyDto.class)).collect(Collectors.toList());
    }

    @Override
    public InverterBatteryWarrantyDto findInverterBatteryWarrantyById(Integer warrantyId) {
        InverterBatteryWarranty inverterBatteryWarranty = this.inverterBatteryWarrantyRepo.findById(warrantyId).orElseThrow(()->new ResourceNotFoundException("InverterBatteryWarranty not found with id "+warrantyId));
        return this.modelMapper.map(inverterBatteryWarranty, InverterBatteryWarrantyDto.class);
    }

    @Override
    public InverterBatteryWarrantyDto updateInverterBatteryWarranty(Integer warrantyId, InverterBatteryWarrantyDto inverterBatteryWarrantyDto) {
        Optional<InverterBatteryWarranty> inverterBatteryWarrantyOptional = this.inverterBatteryWarrantyRepo.findById(warrantyId);
        if(inverterBatteryWarrantyOptional.isPresent()) {
            InverterBatteryWarranty inverterBatteryWarranty = this.modelMapper.map(inverterBatteryWarrantyDto, InverterBatteryWarranty.class);
            inverterBatteryWarranty.setInverterBatteries(inverterBatteryWarrantyOptional.get().getInverterBatteries());
            inverterBatteryWarranty.setBackupDurations(inverterBatteryWarrantyOptional.get().getBackupDurations());
            InverterBatteryWarranty updatedInverterBatteryWarranty;
            try {
                updatedInverterBatteryWarranty = this.inverterBatteryWarrantyRepo.save(inverterBatteryWarranty);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the inverter-battery-warranty - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedInverterBatteryWarranty, InverterBatteryWarrantyDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update inverterBatteryWarranty - no existing inverterBatteryWarranty found with id "+warrantyId);
        }
    }

    @Override
    public void deleteInverterBatteryWarranty(Integer warrantyId) {
        InverterBatteryWarranty inverterBatteryWarranty = this.inverterBatteryWarrantyRepo.findById(warrantyId).orElseThrow(()->new ResourceNotFoundException("Cannot delete InverterBatteryWarranty - no existing InverterBatteryWarranty found with id "+warrantyId));
        this.inverterBatteryWarrantyRepo.delete(inverterBatteryWarranty);
    }
}
