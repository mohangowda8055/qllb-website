package com.example.biservice.controller.fourwheeler;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.fourwheeler.FourVBrandDto;
import com.example.biservice.service.fourwheeler.brand.FourVBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fourv")
@RequiredArgsConstructor
public class FourVBrandController {

    private final FourVBrandService fourVBrandService;


    @PostMapping("/admin/brands")
    public ResponseEntity<ApiResponse<FourVBrandDto>> createBrand(@Valid @RequestBody FourVBrandDto fourVBrandDto) {
        FourVBrandDto data = fourVBrandService.createBrand(fourVBrandDto);
        ApiResponse<FourVBrandDto> apiResponse = ApiResponse.<FourVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Four Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<FourVBrandDto>>> findAllBrands(){
        List<FourVBrandDto> data = this.fourVBrandService.findAllBrands();
        ApiResponse<List<FourVBrandDto>> apiResponse = ApiResponse.<List<FourVBrandDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Four Wheeler Brands")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<ApiResponse<FourVBrandDto>> findBrandById(@PathVariable Integer brandId){
        FourVBrandDto data = this.fourVBrandService.findBrandById(brandId);
        ApiResponse<FourVBrandDto> apiResponse = ApiResponse.<FourVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Four Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/brands/{brandId}")
    public ResponseEntity<ApiResponse<FourVBrandDto>> updateBrand(@PathVariable Integer brandId, @Valid @RequestBody FourVBrandDto fourVBrandDto){
        FourVBrandDto data = this.fourVBrandService.updateBrand(brandId, fourVBrandDto);
        ApiResponse<FourVBrandDto> apiResponse = ApiResponse.<FourVBrandDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Four Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/brands/{brandId}")
    public  ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Integer brandId){
        this.fourVBrandService.deleteBrand(brandId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Four Wheeler Brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
