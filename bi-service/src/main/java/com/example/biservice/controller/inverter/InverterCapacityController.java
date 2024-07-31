package com.example.biservice.controller.inverter;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.inverter.InverterCapacityDto;
import com.example.biservice.service.inverter.capacity.InverterCapacityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inverter")
@RequiredArgsConstructor
public class InverterCapacityController {
    
    private final InverterCapacityService inverterCapacityService;


    @PostMapping("/admin/capacities")
    public ResponseEntity<ApiResponse<InverterCapacityDto>> createInverterCapacity(@Valid @RequestBody InverterCapacityDto inverterCapacityDto) {
        InverterCapacityDto data = inverterCapacityService.createCapacity(inverterCapacityDto);
        ApiResponse<InverterCapacityDto> apiResponse = ApiResponse.<InverterCapacityDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Inverter Capacity")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/capacities")
    public ResponseEntity<ApiResponse<List<InverterCapacityDto>>> findAllInverterCapacities(){
        List<InverterCapacityDto> data = this.inverterCapacityService.findAllCapacities();
        ApiResponse<List<InverterCapacityDto>> apiResponse = ApiResponse.<List<InverterCapacityDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Inverter Capacities")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/capacities/{inverterCapacityId}")
    public ResponseEntity<ApiResponse<InverterCapacityDto>> findInverterCapacityById(@PathVariable Integer inverterCapacityId){
        InverterCapacityDto data = this.inverterCapacityService.findCapacityById(inverterCapacityId);
        ApiResponse<InverterCapacityDto> apiResponse = ApiResponse.<InverterCapacityDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Inverter Capacity")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/capacities/{inverterCapacityId}")
    public ResponseEntity<ApiResponse<InverterCapacityDto>> updateInverterCapacity(@PathVariable Integer inverterCapacityId, @Valid @RequestBody InverterCapacityDto inverterCapacityDto){
        InverterCapacityDto data = this.inverterCapacityService.updateCapacity(inverterCapacityId, inverterCapacityDto);
        ApiResponse<InverterCapacityDto> apiResponse = ApiResponse.<InverterCapacityDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Inverter Capacity")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/capacities/{inverterCapacityId}")
    public ResponseEntity<ApiResponse<Void>> deleteInverterCapacity(@PathVariable Integer inverterCapacityId){
        this.inverterCapacityService.deleteCapacity(inverterCapacityId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Inverter Capacity")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
