package com.example.biservice.controller.threewheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.threewheeler.ThreeVBatteryDto;
import com.example.biservice.service.threewheeler.battery.ThreeVBatteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/threev")
@RequiredArgsConstructor
public class ThreeVBatteryController {

    private final ThreeVBatteryService threeVBatteryService;


    @PostMapping("/admin/batteries")
    public ResponseEntity<ApiResponse<ThreeVBatteryDto>> createBattery(@Valid @RequestBody ThreeVBatteryDto threeVBatteryDto){
        ThreeVBatteryDto data = this.threeVBatteryService.createBattery(threeVBatteryDto);
        ApiResponse<ThreeVBatteryDto> apiResponse = ApiResponse.<ThreeVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Three Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/models/{modelId}/fueltypes/{fuelTypeId}/batteries")
    public ResponseEntity<PageResponse<ThreeVBatteryDto>> findAllBatteriesByModelAndFuel(@PathVariable Integer modelId, @PathVariable Integer fuelTypeId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                         @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                         @RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy) {
        PageResponse<ThreeVBatteryDto> pageResponse = this.threeVBatteryService.findAllBatteriesByModelAndFuel(modelId, fuelTypeId, pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Three Wheeler Batteries by model");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries")
    public ResponseEntity<ApiResponse<List<ThreeVBatteryDto>>> findAllBatteries(@RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy){
        List<ThreeVBatteryDto> data = this.threeVBatteryService.findAllBatteries(sortBy);
        ApiResponse<List<ThreeVBatteryDto>> apiResponse = ApiResponse.<List<ThreeVBatteryDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Three Wheeler Batteries")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<ThreeVBatteryDto>> findBatteryById(@PathVariable Integer batteryId){
        ThreeVBatteryDto data = this.threeVBatteryService.findBatteryById(batteryId);
        ApiResponse<ThreeVBatteryDto> apiResponse = ApiResponse.<ThreeVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Three Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<ThreeVBatteryDto>> updateBattery(@PathVariable Integer batteryId, @Valid @RequestBody ThreeVBatteryDto threeVBatteryDto){
        ThreeVBatteryDto data = this.threeVBatteryService.updateBattery(batteryId, threeVBatteryDto);
        ApiResponse<ThreeVBatteryDto> apiResponse = ApiResponse.<ThreeVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Three Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> deleteBattery(@PathVariable Integer batteryId){
        this.threeVBatteryService.deleteBattery(batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Three Wheeler Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
