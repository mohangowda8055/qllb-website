package com.example.biservice.service.inverter.inverter;

import com.example.biservice.entity.ProductType;
import com.example.biservice.entity.inverter.Inverter;
import com.example.biservice.entity.inverter.InverterCapacity;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverter.InverterDto;
import com.example.biservice.repository.inverter.InverterCapacityRepo;
import com.example.biservice.repository.inverter.InverterRepo;
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
public class InverterServiceImp implements InverterService{

    private final ModelMapper modelMapper;

    private final InverterRepo inverterRepo;

    private final InverterCapacityRepo inverterCapacityRepo;


    @Override
    public InverterDto createInverter(InverterDto inverterDto, Integer capacityId) {
        InverterCapacity inverterCapacity = this.inverterCapacityRepo.findById(capacityId).orElseThrow(() -> new ResourceNotFoundException("Cannot add inverter - no existing inverter capacity found with id " + capacityId));
        Inverter inverter = this.modelMapper.map(inverterDto, Inverter.class);
        inverter.setProductType(ProductType.INVERTER);
        inverter.setInverterCapacity(inverterCapacity);
        Inverter savedInverter;
        try {
            savedInverter = this.inverterRepo.save(inverter);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the inverter - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedInverter, InverterDto.class);
    }

    @Override
    public PageResponse<InverterDto> findAllInvertersByInverterCapacity(Integer capacityId,Integer pageNumber, Integer pageSize, String sortBy) {
        InverterCapacity inverterCapacity = this.inverterCapacityRepo.findById(capacityId).orElseThrow(()->new ResourceNotFoundException("Cannot find inverters - no existing inverter capacity found with id "+capacityId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Inverter> pageInverter = this.inverterRepo.findAllByInverterCapacity(inverterCapacity,pageable);
        List<Inverter> inverters = pageInverter.getContent();
        List<InverterDto> inverterDtos = inverters.stream().map((inverter)->this.modelMapper.map(inverter, InverterDto.class)).collect(Collectors.toList());
        return PageResponse.<InverterDto>builder()
                .data(inverterDtos)
                .pageNumber(pageInverter.getNumber())
                .pageSize(pageInverter.getSize())
                .totalElements(pageInverter.getTotalElements())
                .totalPages(pageInverter.getTotalPages())
                .build();
    }

    @Override
    public List<InverterDto> findAllInverters(String sortBy) {
        Sort sort = Sort.by(sortBy);
        List<Inverter> inverters = this.inverterRepo.findAll(sort);
        return inverters.stream().map((inverter)->this.modelMapper.map(inverter, InverterDto.class)).collect(Collectors.toList());
    }

    @Override
    public InverterDto findInverterById(Integer inverterId) {
        Inverter inverter = this.inverterRepo.findById(inverterId).orElseThrow(()->new ResourceNotFoundException("Inverter not found with id "+inverterId));
        return this.modelMapper.map(inverter, InverterDto.class);
    }

    @Override
    public InverterDto updateInverter(Integer inverterId, InverterDto inverterDto) {
        Optional<Inverter> inverterOptional = this.inverterRepo.findById(inverterId);
        if(inverterOptional.isPresent()) {
            Inverter inverter = this.modelMapper.map(inverterDto, Inverter.class);
            inverter.setProductType(inverterOptional.get().getProductType());
            inverter.setInverterCapacity(inverterOptional.get().getInverterCapacity());
            Inverter updatedInverter;
            try {
                updatedInverter = this.inverterRepo.save(inverter);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the inverter - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedInverter, InverterDto.class);
        }else{
            throw new ResourceNotFoundException("Cannot update Inverter - no existing inverter found with id "+inverterId);
        }
    }

    @Override
    public void deleteInverter(Integer inverterId) {
        Inverter inverter = this.inverterRepo.findById(inverterId).orElseThrow(()->new ResourceNotFoundException("Cannot delete inverter - inverter id not found with id "+inverterId));
        this.inverterRepo.delete(inverter);
    }
}
