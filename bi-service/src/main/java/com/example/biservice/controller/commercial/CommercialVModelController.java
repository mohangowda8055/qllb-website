package com.example.biservice.controller.commercial;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.commercial.CommercialVModelDto;
import com.example.biservice.service.commercial.model.CommercialVModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commercialv")
@RequiredArgsConstructor
public class CommercialVModelController {

    private final CommercialVModelService commercialVModelService;


    @PostMapping("/admin/brands/{brandId}/segments/{segmentId}/models")
    public ResponseEntity<ApiResponse<CommercialVModelDto>> createModel(@PathVariable Integer brandId, @PathVariable Integer segmentId, @Valid @RequestBody CommercialVModelDto commercialVModelDto){
        CommercialVModelDto data = this.commercialVModelService.createModel(commercialVModelDto, brandId, segmentId);
        ApiResponse<CommercialVModelDto> apiResponse = ApiResponse.<CommercialVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Commercial Vehicle Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands/{brandId}/segments/{segmentId}/models")
    public ResponseEntity<ApiResponse<List<CommercialVModelDto>>> findAllModelsByBrandAndSegment(@PathVariable Integer brandId, @PathVariable Integer segmentId){
        List<CommercialVModelDto> data = this.commercialVModelService.findAllModelsByBrandAndSegment(brandId,segmentId);
        ApiResponse<List<CommercialVModelDto>> apiResponse =  ApiResponse.<List<CommercialVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Commercial Wheeler Models by Brand and Segment")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models")
    public ResponseEntity<ApiResponse<List<CommercialVModelDto>>> findAllModels(){
        List<CommercialVModelDto> data = this.commercialVModelService.findAllModels();
        ApiResponse<List<CommercialVModelDto>> apiResponse = ApiResponse.<List<CommercialVModelDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Commercial Vehicle Models")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/models/{modelId}")
    public ResponseEntity<ApiResponse<CommercialVModelDto>> findModelById(@PathVariable Integer modelId){
        CommercialVModelDto data = this.commercialVModelService.findModelById(modelId);
        ApiResponse<CommercialVModelDto> apiResponse = ApiResponse.<CommercialVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Commercial Vehicle Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<CommercialVModelDto>> updateModel(@PathVariable Integer modelId, @Valid @RequestBody CommercialVModelDto commercialVModelDto){
        CommercialVModelDto data = this.commercialVModelService.updateModel(modelId, commercialVModelDto);
        ApiResponse<CommercialVModelDto> apiResponse = ApiResponse.<CommercialVModelDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Commercial Vehicle Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/models/{modelId}")
    public ResponseEntity<ApiResponse<Void>> deleteModel(@PathVariable Integer modelId){
        this.commercialVModelService.deleteModel(modelId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Commercial Vehicle Model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/models/{modelId}/batteries/{batteryId}")
    public ResponseEntity<ApiResponse<Void>> addBatteryToModel(@PathVariable Integer modelId,
                                                                 @PathVariable Integer batteryId){
        this.commercialVModelService.addBatteryToModel(modelId, batteryId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Commercial Vehicle Model and Battery")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
