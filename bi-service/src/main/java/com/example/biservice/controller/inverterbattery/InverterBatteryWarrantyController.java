package com.example.biservice.controller.inverterbattery;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.inverterbattery.InverterBatteryWarrantyDto;
import com.example.biservice.service.inverterbattery.inverterbatterywarranty.InverterBatteryWarrantyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inverterbattery")
@RequiredArgsConstructor
public class InverterBatteryWarrantyController {
    
    private final InverterBatteryWarrantyService inverterBatteryWarrantyService;


    @PostMapping("/admin/warranties")
    public ResponseEntity<ApiResponse<InverterBatteryWarrantyDto>> createInverterBatteryWarranty(@Valid @RequestBody InverterBatteryWarrantyDto inverterBatteryWarrantyDto) {
        InverterBatteryWarrantyDto data = inverterBatteryWarrantyService.createInverterBatteryWarranty(inverterBatteryWarrantyDto);
        ApiResponse<InverterBatteryWarrantyDto> apiResponse = ApiResponse.<InverterBatteryWarrantyDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Inverter-Battery Warranty")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/warranties")
    public ResponseEntity<ApiResponse<List<InverterBatteryWarrantyDto>>> findAllInverterBatteryWarranties(){
        List<InverterBatteryWarrantyDto> data = this.inverterBatteryWarrantyService.findAllInverterBatteryWarranties();
        ApiResponse<List<InverterBatteryWarrantyDto>> apiResponse = ApiResponse.<List<InverterBatteryWarrantyDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Inverter-Battery Warranties")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/warranties/{warrantyId}")
    public ResponseEntity<ApiResponse<InverterBatteryWarrantyDto>> findInverterBatteryWarrantyById(@PathVariable Integer warrantyId){
        InverterBatteryWarrantyDto data = this.inverterBatteryWarrantyService.findInverterBatteryWarrantyById(warrantyId);
        ApiResponse<InverterBatteryWarrantyDto> apiResponse = ApiResponse.<InverterBatteryWarrantyDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Inverter-Battery Warranty")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/warranties/{warrantyId}")
    public ResponseEntity<ApiResponse<InverterBatteryWarrantyDto>> updateInverterBatteryWarranty(@PathVariable Integer warrantyId, @Valid @RequestBody InverterBatteryWarrantyDto inverterBatteryWarrantyDto){
        InverterBatteryWarrantyDto data = this.inverterBatteryWarrantyService.updateInverterBatteryWarranty(warrantyId, inverterBatteryWarrantyDto);
        ApiResponse<InverterBatteryWarrantyDto> apiResponse = ApiResponse.<InverterBatteryWarrantyDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Inverter-Battery Warranty")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/warranties/{warrantyId}")
    public ResponseEntity<ApiResponse<Void>> deleteInverterBatteryWarranty(@PathVariable Integer warrantyId){
        this.inverterBatteryWarrantyService.deleteInverterBatteryWarranty(warrantyId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Inverter-Battery Warranty")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
