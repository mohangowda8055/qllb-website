package com.example.biservice.controller.twowheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.twowheeler.TwoVBrandDto;
import com.example.biservice.service.twowheeler.brand.TwoVBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/twov")
@RequiredArgsConstructor
public class TwoVBrandController {

    private final TwoVBrandService twoVBrandService;


    @PostMapping("/admin/brands")
    public ResponseEntity<ApiResponse<TwoVBrandDto>> createBrand(@Valid @RequestBody TwoVBrandDto twoVBrandDto) {
        TwoVBrandDto data = twoVBrandService.createBrand(twoVBrandDto);
        ApiResponse<TwoVBrandDto> apiResponse = ApiResponse.<TwoVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Two Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<TwoVBrandDto>>> findAllBrands(){
       List<TwoVBrandDto> data = this.twoVBrandService.findAllBrands();
        ApiResponse<List<TwoVBrandDto>> apiResponse = ApiResponse.<List<TwoVBrandDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Two Wheeler Brands")
                .build();
       return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<ApiResponse<TwoVBrandDto>> findBrandById(@PathVariable Integer brandId){
        TwoVBrandDto data = this.twoVBrandService.findBrandById(brandId);
        ApiResponse<TwoVBrandDto> apiResponse = ApiResponse.<TwoVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Two Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/brands/{brandId}")
    public ResponseEntity<ApiResponse<TwoVBrandDto>> updateBrand(@PathVariable Integer brandId, @Valid @RequestBody TwoVBrandDto twoVBrandDto){
        TwoVBrandDto data = this.twoVBrandService.updateBrand(brandId, twoVBrandDto);
        ApiResponse<TwoVBrandDto> apiResponse = ApiResponse.<TwoVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Two Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/brands/{brandId}")
    public  ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Integer brandId){
        this.twoVBrandService.deleteBrand(brandId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Two Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}