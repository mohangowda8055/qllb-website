package com.example.biservice.controller.inverter;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverter.InverterDto;
import com.example.biservice.service.inverter.inverter.InverterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inverter")
@RequiredArgsConstructor
public class InverterController {
    
    private final InverterService inverterService;


    @PostMapping("/admin/capacities/{capacityId}/inverters")
    public ResponseEntity<ApiResponse<InverterDto>> createInverter(@PathVariable Integer capacityId, @Valid @RequestBody InverterDto inverterDto) {
        InverterDto data = inverterService.createInverter(inverterDto, capacityId);
        ApiResponse<InverterDto> apiResponse = ApiResponse.<InverterDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Inverter")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/capacities/{capacityId}/inverters")
    public ResponseEntity<PageResponse<InverterDto>> findAllInvertersByInverterCapacity(@PathVariable Integer capacityId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                      @RequestParam(value = "sortBy", defaultValue = "brandName", required = false) String sortBy){
        PageResponse<InverterDto> pageResponse = this.inverterService.findAllInvertersByInverterCapacity(capacityId, pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Inverters by Inverter Capacity");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/inverters")
    public ResponseEntity<ApiResponse<List<InverterDto>>> findAllInverters(@RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy){
       List<InverterDto> data = this.inverterService.findAllInverters(sortBy);
        ApiResponse<List<InverterDto>> apiResponse = ApiResponse.<List<InverterDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Inverters")
                .build();
       return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/inverters/{inverterId}")
    public ResponseEntity<ApiResponse<InverterDto>> findInverterById(@PathVariable Integer inverterId){
        InverterDto data = this.inverterService.findInverterById(inverterId);
        ApiResponse<InverterDto> apiResponse = ApiResponse.<InverterDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Inverter")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/inverters/{inverterId}")
    public ResponseEntity<ApiResponse<InverterDto>> updateInverter(@PathVariable Integer inverterId, @Valid @RequestBody InverterDto inverterDto){
        InverterDto data = this.inverterService.updateInverter(inverterId, inverterDto);
        ApiResponse<InverterDto> apiResponse = ApiResponse.<InverterDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Inverter")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/inverters/{inverterId}")
    public ResponseEntity<ApiResponse<Void>> deleteInverter(@PathVariable Integer inverterId){
        this.inverterService.deleteInverter(inverterId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Inverter")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
