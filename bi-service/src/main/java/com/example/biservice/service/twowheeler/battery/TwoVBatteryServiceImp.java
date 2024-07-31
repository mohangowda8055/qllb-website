package com.example.biservice.service.twowheeler.battery;

import com.example.biservice.entity.ProductType;
import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.twowheeler.TwoVBatteryDto;
import com.example.biservice.repository.twowheeler.TwoVBatteryRepo;
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
public class TwoVBatteryServiceImp implements TwoVBatteryService{

    private final ModelMapper modelMapper;
    private final TwoVBatteryRepo twoVBatteryRepo;


    @Override
    public TwoVBatteryDto createBattery(TwoVBatteryDto twoVBatteryDto) {
        TwoVBattery twoVBattery = this.modelMapper.map(twoVBatteryDto, TwoVBattery.class);
        twoVBattery.setProductType(ProductType.TWOVBATTERY);
        TwoVBattery savedTwoVBattery;
        try {
            savedTwoVBattery = this.twoVBatteryRepo.save(twoVBattery);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the two wheeler battery - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedTwoVBattery, TwoVBatteryDto.class);
    }

    @Override
    public PageResponse<TwoVBatteryDto> findAllBatteriesByModel(Integer modelId, Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<TwoVBattery> pageBatteries = this.twoVBatteryRepo.findBatteriesByModel(modelId,pageable);
        List<TwoVBattery> twoVBatteries = pageBatteries.getContent();
        List<TwoVBatteryDto> twoVBatteryDtos = twoVBatteries.stream().map((battery)->this.modelMapper.map(battery, TwoVBatteryDto.class)).collect(Collectors.toList());
        return PageResponse.<TwoVBatteryDto>builder()
                .data(twoVBatteryDtos)
                .pageNumber(pageBatteries.getNumber())
                .pageSize(pageBatteries.getSize())
                .totalElements(pageBatteries.getTotalElements())
                .totalPages(pageBatteries.getTotalPages())
                .lastPage(pageBatteries.isLast())
                .build();
    }

    @Override
    public List<TwoVBatteryDto> findAllBatteries(String sortBy) {
        Sort sort = Sort.by(sortBy);
        List<TwoVBattery> twoVBatteries = this.twoVBatteryRepo.findAll(sort);
        return twoVBatteries.stream().map(twoVBattery->this.modelMapper.map(twoVBattery, TwoVBatteryDto.class)).collect(Collectors.toList());
    }

    @Override
    public TwoVBatteryDto findBatteryById(Integer batteryId) {
        TwoVBattery twoVBattery = this.twoVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Two wheeler battery not found with id "+batteryId));
        return this.modelMapper.map(twoVBattery, TwoVBatteryDto.class);
    }

    @Override
    public TwoVBatteryDto updateBattery(Integer batteryId, TwoVBatteryDto twoVBatteryDto) {
        Optional<TwoVBattery> twoVBatteryOptional = this.twoVBatteryRepo.findById(batteryId);
        if(twoVBatteryOptional.isPresent()){
           TwoVBattery twoVBattery = this.modelMapper.map(twoVBatteryDto, TwoVBattery.class);
           twoVBattery.setTwoVModels(twoVBatteryOptional.get().getTwoVModels());
           twoVBattery.setProductType(twoVBatteryOptional.get().getProductType());
           TwoVBattery updatedBattery;
           try {
               updatedBattery = this.twoVBatteryRepo.save(twoVBattery);
           }catch (DataIntegrityViolationException e){
               throw new UniqueConstraintException("An error occurred while updating the two wheeler battery - Duplicate Entry detected");
           }
           return this.modelMapper.map(updatedBattery, TwoVBatteryDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update two wheeler battery - no existing two wheeler battery found with id "+batteryId);
        }
    }

    @Override
    public void deleteBattery(Integer batteryId) {
        TwoVBattery twoVBattery = this.twoVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot delete two wheeler battery - no existing two wheeler battery found with id "+batteryId));
        this.twoVBatteryRepo.delete(twoVBattery);
    }
}
