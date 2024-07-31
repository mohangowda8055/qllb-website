package com.example.biservice.service.inverter.capacity;

import com.example.biservice.entity.inverter.InverterCapacity;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.inverter.InverterCapacityDto;
import com.example.biservice.repository.inverter.InverterCapacityRepo;
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
public class InverterCapacityServiceImp implements InverterCapacityService{

    private final ModelMapper modelMapper;

    private final InverterCapacityRepo inverterCapacityRepo;


    @Override
    public InverterCapacityDto createCapacity(InverterCapacityDto inverterCapacityDto) {
        InverterCapacity inverterCapacity = this.modelMapper.map(inverterCapacityDto, InverterCapacity.class);
        InverterCapacity savedInverterCapacity;
        try {
            savedInverterCapacity = this.inverterCapacityRepo.save(inverterCapacity);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the inverter capacity - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedInverterCapacity, InverterCapacityDto.class);
    }

    @Override
    public List<InverterCapacityDto> findAllCapacities() {
        Sort sort = Sort.by("capacity");
        List<InverterCapacity> inverterCapacities = this.inverterCapacityRepo.findAll(sort);
        return inverterCapacities.stream().map((inverterCapacity)->this.modelMapper.map(inverterCapacity, InverterCapacityDto.class)).collect(Collectors.toList());
    }

    @Override
    public InverterCapacityDto findCapacityById(Integer capacityId) {
        InverterCapacity inverterCapacity = this.inverterCapacityRepo.findById(capacityId).orElseThrow(()->new ResourceNotFoundException("Inverter capacity not found with id "+capacityId));
        return this.modelMapper.map(inverterCapacity, InverterCapacityDto.class);
    }

    @Override
    public InverterCapacityDto updateCapacity(Integer capacityId, InverterCapacityDto inverterCapacityDto) {
        Optional<InverterCapacity> inverterCapacityOptional = this.inverterCapacityRepo.findById(capacityId);
        if(inverterCapacityOptional.isPresent()) {
            InverterCapacity inverterCapacity = this.modelMapper.map(inverterCapacityDto, InverterCapacity.class);
            inverterCapacity.setInverters(inverterCapacityOptional.get().getInverters());
            InverterCapacity updatedInverterCapacity;
            try {
                updatedInverterCapacity = this.inverterCapacityRepo.save(inverterCapacity);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the inverter capacity - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedInverterCapacity, InverterCapacityDto.class);
        }else{
            throw new ResourceNotFoundException("Cannot update Inverter capacity - no existing inverter capacity found with id "+capacityId);
        }
    }

    @Override
    public void deleteCapacity(Integer capacityId) {
        InverterCapacity inverterCapacity = this.inverterCapacityRepo.findById(capacityId).orElseThrow(()->new ResourceNotFoundException("Cannot delete inverter capacity - no existing inverter battery found with id "+capacityId));
        this.inverterCapacityRepo.delete(inverterCapacity);
    }
}
