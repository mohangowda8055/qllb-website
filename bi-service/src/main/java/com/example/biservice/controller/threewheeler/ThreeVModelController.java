package com.example.biservice.controller.threewheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.threewheeler.ThreeVModelDto;
import com.example.biservice.service.threewheeler.model.ThreeVModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/threev")
@RequiredArgsConstructor
public class ThreeVModelController {

    private final ThreeVModelService threeVModelService;


    @PostMapping("/admin/brands/{brandId}/models")
    public ResponseEntity<ApiResponse<ThreeVModelDto>> createModel(@PathVariable Integer brandId, @Valid @RequestBody ThreeVModelDto threeVModelDto){
        ThreeVModelDto data = this.threeVModelService.createModel(threeVModelDto, brandId);
        ApiResponse<ThreeVModelDto> apiResponse = ApiResponse.<ThreeVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Three Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands/{brandId}/models")
    public ResponseEntity<ApiResponse<List<ThreeVModelDto>>> findAllModelsByBrand(@PathVariable Integer brandId){
        List<ThreeVModelDto> data = this.threeVModelService.findAllModelsByBrand(brandId);
        ApiResponse<List<ThreeVModelDto>> apiResponse =  ApiResponse.<List<ThreeVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Three Wheeler Models by Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models")
    public ResponseEntity<ApiResponse<List<ThreeVModelDto>>> findAllModels() {
        List<ThreeVModelDto> data = this.threeVModelService.findAllModels();
        ApiResponse<List<ThreeVModelDto>> apiResponse = ApiResponse.<List<ThreeVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Three Wheeler Models")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models/{modelId}")
    public ResponseEntity<ApiResponse<ThreeVModelDto>> findModelById(@PathVariable Integer modelId){
        ThreeVModelDto data = this.threeVModelService.findModelById(modelId);
        ApiResponse<ThreeVModelDto> apiResponse = ApiResponse.<ThreeVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Three Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<ThreeVModelDto>> updateModel(@PathVariable Integer modelId, @Valid @RequestBody ThreeVModelDto threeVModelDto){
        ThreeVModelDto data = this.threeVModelService.updateModel(modelId, threeVModelDto);
        ApiResponse<ThreeVModelDto> apiResponse = ApiResponse.<ThreeVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Three Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<Void>> deleteModel(@PathVariable Integer modelId){
        this.threeVModelService.deleteModel(modelId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Three Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/models/{modelId}/fueltypes/{fuelTypeId}")
    public ResponseEntity<ApiResponse<Void>> addFuelTypeToModel(@PathVariable Integer modelId,
                                                                @PathVariable Integer fuelTypeId){
        this.threeVModelService.addFuelTypeToModel(modelId, fuelTypeId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Three Wheeler Model and Fuel")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/models/{modelId}/fueltypes/{fuelTypeId}/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> addBatteryToModel(@PathVariable Integer modelId,
                                                               @PathVariable Integer fuelTypeId,
                                                                 @PathVariable Integer batteryId){
        this.threeVModelService.addBatteryToModel(modelId, fuelTypeId, batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Three Wheeler Model and Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
