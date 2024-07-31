package com.example.biservice.controller.inverterbattery;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.inverterbattery.BackupDurationDto;
import com.example.biservice.service.inverterbattery.backupduration.BackupDurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inverterbattery")
@RequiredArgsConstructor
public class BackupDurationController {
    
    private final BackupDurationService backupDurationService;


    @PostMapping("/admin/backupdurations")
    public ResponseEntity<ApiResponse<BackupDurationDto>> createBackupDuration(@Valid @RequestBody BackupDurationDto backupDurationDto) {
        BackupDurationDto data = backupDurationService.createBackupDuration(backupDurationDto);
        ApiResponse<BackupDurationDto> apiResponse = ApiResponse.<BackupDurationDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Backup Duration")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/backupdurations")
    public ResponseEntity<ApiResponse<List<BackupDurationDto>>> findAllBackupDurations(){
        List<BackupDurationDto> data = this.backupDurationService.findAllBackupDurations();
        ApiResponse<List<BackupDurationDto>> apiResponse = ApiResponse.<List<BackupDurationDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found all Backup Durations")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/backupdurations/{backupDurationId}")
    public ResponseEntity<ApiResponse<BackupDurationDto>> findBackupDurationById(@PathVariable Integer backupDurationId){
        BackupDurationDto data = this.backupDurationService.findBackupDurationById(backupDurationId);
        ApiResponse<BackupDurationDto> apiResponse = ApiResponse.<BackupDurationDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found backup Duration")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/backupdurations/{backupDurationId}")
    public ResponseEntity<ApiResponse<BackupDurationDto>> updateBackupDuration(@PathVariable Integer backupDurationId, @Valid @RequestBody BackupDurationDto backupDurationDto){
        BackupDurationDto data = this.backupDurationService.updateBackupDuration(backupDurationId, backupDurationDto);
        ApiResponse<BackupDurationDto> apiResponse = ApiResponse.<BackupDurationDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Backup Duration")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/backupdurations/{backupDurationId}")
    public ResponseEntity<ApiResponse<Void>> deleteBackupDuration(@PathVariable Integer backupDurationId){
        this.backupDurationService.deleteBackupDuration(backupDurationId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Backup Duration")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
