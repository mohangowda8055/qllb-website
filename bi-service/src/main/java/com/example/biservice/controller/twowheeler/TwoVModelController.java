package com.example.biservice.controller.twowheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.twowheeler.TwoVModelDto;
import com.example.biservice.service.twowheeler.model.TwoVModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/twov")
@RequiredArgsConstructor
public class TwoVModelController {

    private final TwoVModelService twoVModelService;


    @PostMapping("/admin/brands/{brandId}/models")
    public ResponseEntity<ApiResponse<TwoVModelDto>> createModel(@PathVariable Integer brandId, @Valid @RequestBody TwoVModelDto twoVModelDto){
        TwoVModelDto data = this.twoVModelService.createModel(twoVModelDto, brandId);
        ApiResponse<TwoVModelDto> apiResponse = ApiResponse.<TwoVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Two Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands/{brandId}/models")
    public ResponseEntity<ApiResponse<List<TwoVModelDto>>> findAllModelsByBrand(@PathVariable Integer brandId){
        List<TwoVModelDto> data = this.twoVModelService.findAllModelsByBrand(brandId);
        ApiResponse<List<TwoVModelDto>> apiResponse =  ApiResponse.<List<TwoVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Two Wheeler Models by Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models")
    public ResponseEntity<ApiResponse<List<TwoVModelDto>>> findAllModels(){
        List<TwoVModelDto> data = this.twoVModelService.findAllModels();
        ApiResponse<List<TwoVModelDto>> apiResponse = ApiResponse.<List<TwoVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Two Wheeler Models")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models/{modelId}")
    public ResponseEntity<ApiResponse<TwoVModelDto>> findModelById(@PathVariable Integer modelId){
        TwoVModelDto data = this.twoVModelService.findModelById(modelId);
        ApiResponse<TwoVModelDto> apiResponse = ApiResponse.<TwoVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Two Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<TwoVModelDto>> updateModel(@PathVariable Integer modelId, @Valid @RequestBody TwoVModelDto twoVModelDto){
        TwoVModelDto data = this.twoVModelService.updateModel(modelId, twoVModelDto);
        ApiResponse<TwoVModelDto> apiResponse = ApiResponse.<TwoVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Two Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<Void>> deleteModel(@PathVariable Integer modelId){
        this.twoVModelService.deleteModel(modelId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Two Wheeler Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/models/{modelId}/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> addBatteryToModel(@PathVariable Integer modelId,
                                                                 @PathVariable Integer batteryId){
        this.twoVModelService.addBatteryToModel(modelId, batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Two Wheeler Model and Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
