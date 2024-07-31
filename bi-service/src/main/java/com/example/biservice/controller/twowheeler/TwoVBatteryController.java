package com.example.biservice.controller.twowheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.twowheeler.TwoVBatteryDto;
import com.example.biservice.service.twowheeler.battery.TwoVBatteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/twov")
@RequiredArgsConstructor
public class TwoVBatteryController {

    private final TwoVBatteryService twoVBatteryService;


    @PostMapping("/admin/batteries")
    public ResponseEntity<ApiResponse<TwoVBatteryDto>> createBattery(@Valid @RequestBody TwoVBatteryDto twoVBatteryDto){
        TwoVBatteryDto data = this.twoVBatteryService.createBattery(twoVBatteryDto);
        ApiResponse<TwoVBatteryDto> apiResponse = ApiResponse.<TwoVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Two Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/models/{modelId}/batteries")
    public ResponseEntity<PageResponse<TwoVBatteryDto>> findAllBatteriesByModel(@PathVariable Integer modelId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                         @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                         @RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy) {
        PageResponse<TwoVBatteryDto> pageResponse = this.twoVBatteryService.findAllBatteriesByModel(modelId, pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Two Wheeler Batteries by model");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries")
    public ResponseEntity<ApiResponse<List<TwoVBatteryDto>>> findAllBatteries(@RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy) {
        List<TwoVBatteryDto> data = this.twoVBatteryService.findAllBatteries(sortBy);
        ApiResponse<List<TwoVBatteryDto>> apiResponse = ApiResponse.<List<TwoVBatteryDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Two Wheeler Batteries")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<TwoVBatteryDto>> findBatteryById(@PathVariable Integer batteryId){
        TwoVBatteryDto data = this.twoVBatteryService.findBatteryById(batteryId);
        ApiResponse<TwoVBatteryDto> apiResponse = ApiResponse.<TwoVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Two Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<TwoVBatteryDto>> updateBattery(@PathVariable Integer batteryId, @Valid @RequestBody TwoVBatteryDto twoVBatteryDto){
        TwoVBatteryDto data = this.twoVBatteryService.updateBattery(batteryId, twoVBatteryDto);
        ApiResponse<TwoVBatteryDto> apiResponse = ApiResponse.<TwoVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Two Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> deleteBattery(@PathVariable Integer batteryId){
        this.twoVBatteryService.deleteBattery(batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Two Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
