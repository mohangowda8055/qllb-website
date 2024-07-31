package com.example.biservice.controller.fourwheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.fourwheeler.FourVBatteryDto;
import com.example.biservice.service.fourwheeler.battery.FourVBatteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fourv")
@RequiredArgsConstructor
public class FourVBatteryController {

    private final FourVBatteryService fourVBatteryService;


    @PostMapping("/admin/batteries")
    public ResponseEntity<ApiResponse<FourVBatteryDto>> createBattery(@Valid @RequestBody FourVBatteryDto fourVBatteryDto){
        FourVBatteryDto data = this.fourVBatteryService.createBattery(fourVBatteryDto);
        ApiResponse<FourVBatteryDto> apiResponse = ApiResponse.<FourVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Four Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/models/{modelId}/fueltypes/{fuelTypeId}/batteries")
    public ResponseEntity<PageResponse<FourVBatteryDto>> findAllBatteriesByModelAndFuelType(@PathVariable Integer modelId, @PathVariable Integer fuelTypeId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                           @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                           @RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy) {
        PageResponse<FourVBatteryDto> pageResponse = this.fourVBatteryService.findAllBatteriesByModelAndFuelType(modelId, fuelTypeId, pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Four Wheeler Battery by Model and FuelType");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries")
    public ResponseEntity<ApiResponse<List<FourVBatteryDto>>> findAllBatteries(@RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy){
        List<FourVBatteryDto> data = this.fourVBatteryService.findAllBatteries(sortBy);
        ApiResponse<List<FourVBatteryDto>> apiResponse = ApiResponse.<List<FourVBatteryDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Four Wheeler Batteries")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<FourVBatteryDto>> findBatteryById(@PathVariable Integer batteryId){
        FourVBatteryDto data = this.fourVBatteryService.findBatteryById(batteryId);
        ApiResponse<FourVBatteryDto> apiResponse = ApiResponse.<FourVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Four Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<FourVBatteryDto>> updateBattery(@PathVariable Integer batteryId, @Valid @RequestBody FourVBatteryDto fourVBatteryDto){
        FourVBatteryDto data = this.fourVBatteryService.updateBattery(batteryId, fourVBatteryDto);
        ApiResponse<FourVBatteryDto> apiResponse = ApiResponse.<FourVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Four Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> deleteBattery(@PathVariable Integer batteryId){
        this.fourVBatteryService.deleteBattery(batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Four Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
