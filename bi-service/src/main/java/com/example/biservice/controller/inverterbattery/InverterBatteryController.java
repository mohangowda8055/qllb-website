package com.example.biservice.controller.inverterbattery;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverterbattery.InverterBatteryDto;
import com.example.biservice.service.inverterbattery.inverterbattery.InverterBatteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inverterbattery")
@RequiredArgsConstructor
public class InverterBatteryController {

    private final InverterBatteryService inverterBatteryService;


    @PostMapping("/admin/batteries")
    public ResponseEntity<ApiResponse<InverterBatteryDto>> createBattery(@Valid @RequestBody InverterBatteryDto inverterBatteryDto) {
        InverterBatteryDto data = inverterBatteryService.createInverterBattery(inverterBatteryDto);
        ApiResponse<InverterBatteryDto> apiResponse = ApiResponse.<InverterBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Inverter Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("backupdurations/{backupDurationId}/warranties/{warrantyId}/batteries")
    public ResponseEntity<PageResponse<InverterBatteryDto>> findAllBatteriesByBackupDurationAndWarranty(@PathVariable Integer backupDurationId, @PathVariable Integer warrantyId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                           @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                           @RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy){
        PageResponse<InverterBatteryDto> pageResponse = this.inverterBatteryService.findAllInverterBatteriesByBackupDurationAndWarranty(backupDurationId, warrantyId, pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Inverter Batteries by BackupDuration and Warranty");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries")
    public ResponseEntity<ApiResponse<List<InverterBatteryDto>>> findAllBatteries(@RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy){
        List<InverterBatteryDto> data = this.inverterBatteryService.findAllInverterBatteries(sortBy);
        ApiResponse<List<InverterBatteryDto>> apiResponse = ApiResponse.<List<InverterBatteryDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Inverter Batteries")
                .build();
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<InverterBatteryDto>> findBatteryById(@PathVariable Integer batteryId){
        InverterBatteryDto data = this.inverterBatteryService.findInverterBatteryById(batteryId);
        ApiResponse<InverterBatteryDto> apiResponse = ApiResponse.<InverterBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Inverter Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<InverterBatteryDto>> updateBattery(@PathVariable Integer batteryId, @Valid @RequestBody InverterBatteryDto inverterBatteryDto){
        InverterBatteryDto data = this.inverterBatteryService.updateInverterBattery(batteryId, inverterBatteryDto);
        ApiResponse<InverterBatteryDto> apiResponse = ApiResponse.<InverterBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Inverter Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> deleteBattery(@PathVariable Integer batteryId){
        this.inverterBatteryService.deleteInverterBattery(batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Inverter Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/backupdurations/{backupDurationId}/warranties/{warrantyId}/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> addBackupDurationWithWarrantyToBattery(@PathVariable Integer backupDurationId,
                                                                 @PathVariable Integer warrantyId,
                                                                 @PathVariable Integer batteryId){
        this.inverterBatteryService.addBackupDurationWithWarrantyToBattery(batteryId, backupDurationId, warrantyId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Backup Duration, Warranty and Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
