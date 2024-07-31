package com.example.biservice.controller.fourwheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.fourwheeler.FourVModelDto;
import com.example.biservice.service.fourwheeler.model.FourVModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fourv")
@RequiredArgsConstructor
public class FourVModelController {
    
    private final FourVModelService fourVModelService;


    @PostMapping("/admin/brands/{brandId}/models")
    public ResponseEntity<ApiResponse<FourVModelDto>> createModel(@PathVariable Integer brandId, @Valid @RequestBody FourVModelDto fourVModelDto){
        FourVModelDto data = this.fourVModelService.createModel(fourVModelDto, brandId);
        ApiResponse<FourVModelDto> apiResponse = ApiResponse.<FourVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Four Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands/{brandId}/models")
    public ResponseEntity<ApiResponse<List<FourVModelDto>>> findAllModelsByBrand(@PathVariable Integer brandId){
        List<FourVModelDto> data = this.fourVModelService.findAllModelsByBrand(brandId);
        ApiResponse<List<FourVModelDto>> apiResponse =  ApiResponse.<List<FourVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Four Wheeler Models by Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models")
    public ResponseEntity<ApiResponse<List<FourVModelDto>>> findAllModels(){
        List<FourVModelDto> data = this.fourVModelService.findAllModels();
        ApiResponse<List<FourVModelDto>> apiResponse = ApiResponse.<List<FourVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Four Wheeler Models")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models/{modelId}")
    public ResponseEntity<ApiResponse<FourVModelDto>> findModelById(@PathVariable Integer modelId){
        FourVModelDto data = this.fourVModelService.findModelById(modelId);
        ApiResponse<FourVModelDto> apiResponse = ApiResponse.<FourVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Four Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<FourVModelDto>> updateModel(@PathVariable Integer modelId, @Valid @RequestBody FourVModelDto fourVModelDto){
        FourVModelDto data = this.fourVModelService.updateModel(modelId, fourVModelDto);
        ApiResponse<FourVModelDto> apiResponse = ApiResponse.<FourVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Four Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<Void>> deleteModel(@PathVariable Integer modelId){
        this.fourVModelService.deleteModel(modelId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Four Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/models/{modelId}/fueltypes/{fuelTypeId}")
    public ResponseEntity<ApiResponse<Void>> addFuelTypeToModel(@PathVariable Integer modelId,
                                                               @PathVariable Integer fuelTypeId){
        this.fourVModelService.addFuelTypeToModel(modelId, fuelTypeId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Four Wheeler Model and Fuel")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/models/{modelId}/fueltypes/{fuelTypeId}/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> addBatteryToModel(@PathVariable Integer modelId,
                                                                 @PathVariable Integer fuelTypeId,
                                                                 @PathVariable Integer batteryId){
        this.fourVModelService.addBatteryToModel(modelId, fuelTypeId, batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Four Wheeler Model, Fuel and Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
