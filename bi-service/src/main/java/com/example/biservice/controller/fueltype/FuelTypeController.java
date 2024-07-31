package com.example.biservice.controller.fueltype;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.FuelTypeDto;
import com.example.biservice.service.fueltype.FuelTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fueltype")
@RequiredArgsConstructor
public class FuelTypeController {

    private final FuelTypeService fuelTypeService;


    @GetMapping("fourvmodels/{modelId}/fueltypes")
    public ResponseEntity<ApiResponse<List<FuelTypeDto>>> findAllFuelTypesByFourVModel(@PathVariable Integer modelId){
        List<FuelTypeDto> data = this.fuelTypeService.findAllFuelTypesByFourVModel(modelId);
        ApiResponse<List<FuelTypeDto>> apiResponse = ApiResponse.<List<FuelTypeDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Fuel Types by model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("threevmodels/{modelId}/fueltypes")
    public ResponseEntity<ApiResponse<List<FuelTypeDto>>> findAllFuelTypesByThreeVModel(@PathVariable Integer modelId){
        List<FuelTypeDto> data = this.fuelTypeService.findAllFuelTypesByThreeVModel(modelId);
        ApiResponse<List<FuelTypeDto>> apiResponse = ApiResponse.<List<FuelTypeDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Fuel Types by model")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/fueltypes")
    public ResponseEntity<ApiResponse<List<FuelTypeDto>>> findAllFuelTypes(){
        List<FuelTypeDto> data = this.fuelTypeService.findAllFuelTypes();
        ApiResponse<List<FuelTypeDto>> apiResponse = ApiResponse.<List<FuelTypeDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Fuel Types")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/fueltypes/{fuelTypeId}")
    public ResponseEntity<ApiResponse<FuelTypeDto>> findFuelTypeById(@PathVariable Integer fuelTypeId){
        FuelTypeDto data = this.fuelTypeService.findFuelById(fuelTypeId);
        ApiResponse<FuelTypeDto> apiResponse = ApiResponse.<FuelTypeDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Fuel Type")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
