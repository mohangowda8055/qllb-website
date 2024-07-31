package com.example.biservice.controller.threewheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.threewheeler.ThreeVBrandDto;
import com.example.biservice.service.threewheeler.brand.ThreeVBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/threev")
@RequiredArgsConstructor
public class ThreeVBrandController {
    
    private final ThreeVBrandService threeVBrandService;


    @PostMapping("/admin/brands")
    public ResponseEntity<ApiResponse<ThreeVBrandDto>> createBrand(@Valid @RequestBody ThreeVBrandDto threeVBrandDto) {
        ThreeVBrandDto data = threeVBrandService.createBrand(threeVBrandDto);
        ApiResponse<ThreeVBrandDto> apiResponse = ApiResponse.<ThreeVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Three Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<ThreeVBrandDto>>> findAllBrands(){
        List<ThreeVBrandDto> data = this.threeVBrandService.findAllBrands();
        ApiResponse<List<ThreeVBrandDto>> apiResponse = ApiResponse.<List<ThreeVBrandDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Three Wheeler Brands")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<ApiResponse<ThreeVBrandDto>> findBrandById(@PathVariable Integer brandId){
        ThreeVBrandDto data = this.threeVBrandService.findBrandById(brandId);
        ApiResponse<ThreeVBrandDto> apiResponse = ApiResponse.<ThreeVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Three Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/brands/{brandId}")
    public ResponseEntity<ApiResponse<ThreeVBrandDto>> updateBrand(@PathVariable Integer brandId, @Valid @RequestBody ThreeVBrandDto threeVBrandDto){
        ThreeVBrandDto data = this.threeVBrandService.updateBrand(brandId, threeVBrandDto);
        ApiResponse<ThreeVBrandDto> apiResponse = ApiResponse.<ThreeVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Three Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/brands/{brandId}")
    public  ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Integer brandId){
        this.threeVBrandService.deleteBrand(brandId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Three Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
