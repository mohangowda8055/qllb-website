package com.example.biservice.controller.commercial;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.commercial.CommercialVSegmentDto;
import com.example.biservice.service.commercial.segment.CommercialVSegmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commercialv")
@RequiredArgsConstructor
public class CommercialVSegmentController {

    private final CommercialVSegmentService commercialVSegmentService;


    @PostMapping("/admin/segments")
    public ResponseEntity<ApiResponse<CommercialVSegmentDto>> createSegment(@Valid @RequestBody CommercialVSegmentDto commercialVSegmentDto){
        CommercialVSegmentDto data = this.commercialVSegmentService.createSegment(commercialVSegmentDto);
        ApiResponse<CommercialVSegmentDto> apiResponse = ApiResponse.<CommercialVSegmentDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Commercial Wheeler Segment")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands/{brandId}/segments")
    public ResponseEntity<ApiResponse<List<CommercialVSegmentDto>>> findAllSegmentsByBrand(@PathVariable Integer brandId){
        List<CommercialVSegmentDto> data = this.commercialVSegmentService.findAllSegmentsByBrand(brandId);
        ApiResponse<List<CommercialVSegmentDto>> apiResponse = ApiResponse.<List<CommercialVSegmentDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Commercial Vehicle Segments by brand")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/segments")
    public ResponseEntity<ApiResponse<List<CommercialVSegmentDto>>> findAllSegments(){
        List<CommercialVSegmentDto> data = this.commercialVSegmentService.findAllSegments();
        ApiResponse<List<CommercialVSegmentDto>> apiResponse = ApiResponse.<List<CommercialVSegmentDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Commercial Vehicle Segments")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/segments/{segmentId}")
    public ResponseEntity<ApiResponse<CommercialVSegmentDto>> findSegmentById(@PathVariable Integer segmentId){
        CommercialVSegmentDto data = this.commercialVSegmentService.findSegmentById(segmentId);
        ApiResponse<CommercialVSegmentDto> apiResponse = ApiResponse.<CommercialVSegmentDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Commercial Vehicle Segment")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/segments/{segmentId}")
    public ResponseEntity<ApiResponse<CommercialVSegmentDto>> updateSegment(@PathVariable Integer segmentId, @Valid @RequestBody CommercialVSegmentDto commercialVSegmentDto){
        CommercialVSegmentDto data = this.commercialVSegmentService.updateSegment(segmentId, commercialVSegmentDto);
        ApiResponse<CommercialVSegmentDto> apiResponse = ApiResponse.<CommercialVSegmentDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Commercial vehicle Segment")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/segments/{segmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteSegment(@PathVariable Integer segmentId){
        this.commercialVSegmentService.deleteSegment(segmentId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Commercial Vehicle Segment")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
