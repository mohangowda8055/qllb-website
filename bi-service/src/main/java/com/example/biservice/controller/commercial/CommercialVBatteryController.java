package com.example.biservice.controller.commercial;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.commercial.CommercialVBatteryDto;
import com.example.biservice.service.commercial.battery.CommercialVBatteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commercialv")
@RequiredArgsConstructor
public class CommercialVBatteryController {

    private final CommercialVBatteryService commercialVBatteryService;


    @PostMapping("/admin/batteries")
    public ResponseEntity<ApiResponse<CommercialVBatteryDto>> createBattery(@Valid @RequestBody CommercialVBatteryDto commercialVBatteryDto){
        CommercialVBatteryDto data = this.commercialVBatteryService.createBattery(commercialVBatteryDto);
        ApiResponse<CommercialVBatteryDto> apiResponse = ApiResponse.<CommercialVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Commercial Vehicle Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("models/{modelId}/batteries")
    public ResponseEntity<PageResponse<CommercialVBatteryDto>> findAllBatteriesByModel(@PathVariable Integer modelId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                          @RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy) {
        PageResponse<CommercialVBatteryDto> pageResponse = this.commercialVBatteryService.findAllBatteriesByModel(modelId, pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Commercial Vehicle Battery by Model");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries")
    public ResponseEntity<ApiResponse<List<CommercialVBatteryDto>>> findAllBatteries(@RequestParam(value = "sortBy", defaultValue = "modelName", required = false) String sortBy){
        List<CommercialVBatteryDto> data = this.commercialVBatteryService.findAllBatteries(sortBy);
        ApiResponse<List<CommercialVBatteryDto>> apiResponse = ApiResponse.<List<CommercialVBatteryDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Commercial Vehicle Batteries")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<CommercialVBatteryDto>> findBatteryById(@PathVariable Integer batteryId){
        CommercialVBatteryDto data = this.commercialVBatteryService.findBatteryById(batteryId);
        ApiResponse<CommercialVBatteryDto> apiResponse = ApiResponse.<CommercialVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Commercial Vehicle Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<CommercialVBatteryDto>> updateBattery(@PathVariable Integer batteryId, @Valid @RequestBody CommercialVBatteryDto commercialVBatteryDto){
        CommercialVBatteryDto data = this.commercialVBatteryService.updateBattery(batteryId, commercialVBatteryDto);
        ApiResponse<CommercialVBatteryDto> apiResponse = ApiResponse.<CommercialVBatteryDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Commercial Vehicle Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> deleteBattery(@PathVariable Integer batteryId){
        this.commercialVBatteryService.deleteBattery(batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Commercial Vehicle Battery")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
