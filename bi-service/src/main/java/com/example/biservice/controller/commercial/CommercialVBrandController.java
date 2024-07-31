package com.example.biservice.controller.commercial;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.commercial.CommercialVBrandDto;
import com.example.biservice.service.commercial.brand.CommercialVBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commercialv")
@RequiredArgsConstructor
public class CommercialVBrandController {

    private final CommercialVBrandService commercialVBrandService;


    @PostMapping("/admin/brands")
    public ResponseEntity<ApiResponse<CommercialVBrandDto>> createBrand(@Valid @RequestBody CommercialVBrandDto commercialVBrandDto) {
        CommercialVBrandDto data = commercialVBrandService.createBrand(commercialVBrandDto);
        ApiResponse<CommercialVBrandDto> apiResponse = ApiResponse.<CommercialVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Commercial Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<CommercialVBrandDto>>> findAllBrands(){
        List<CommercialVBrandDto> data = this.commercialVBrandService.findAllBrands();
        ApiResponse<List<CommercialVBrandDto>> apiResponse = ApiResponse.<List<CommercialVBrandDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Commercial Vehicle Brands")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<ApiResponse<CommercialVBrandDto>> findBrandById(@PathVariable Integer brandId){
        CommercialVBrandDto data = this.commercialVBrandService.findBrandById(brandId);
        ApiResponse<CommercialVBrandDto> apiResponse = ApiResponse.<CommercialVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Commercial Vehicle Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/brands/{brandId}")
    public ResponseEntity<ApiResponse<CommercialVBrandDto>> updateBrand(@PathVariable Integer brandId, @Valid @RequestBody CommercialVBrandDto commercialVBrandDto){
        CommercialVBrandDto data = this.commercialVBrandService.updateBrand(brandId, commercialVBrandDto);
        ApiResponse<CommercialVBrandDto> apiResponse = ApiResponse.<CommercialVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Commercial vehicle Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/brands/{brandId}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Integer brandId){
        this.commercialVBrandService.deleteBrand(brandId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Commercial Vehicle Brand")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/admin/brands/{brandId}/segments/{segmentId}")
    public ResponseEntity<ApiResponse<Void>> addSegmentToBrand(@PathVariable Integer brandId, @PathVariable Integer segmentId){
        this.commercialVBrandService.addSegmentToBrand(brandId, segmentId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Mapped Commercial Vehicle Brand and Segment")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
