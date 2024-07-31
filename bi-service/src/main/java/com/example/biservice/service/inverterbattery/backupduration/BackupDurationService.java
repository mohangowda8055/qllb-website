package com.example.biservice.service.inverterbattery.backupduration;

import com.example.biservice.payload.inverterbattery.BackupDurationDto;

import java.util.List;

public interface BackupDurationService {

    BackupDurationDto createBackupDuration(BackupDurationDto backupDurationDto);

    List<BackupDurationDto> findAllBackupDurations();

    BackupDurationDto findBackupDurationById(Integer backupDurationId);

    BackupDurationDto updateBackupDuration(Integer backupDurationId, BackupDurationDto backupDurationDto);

    void deleteBackupDuration(Integer backupDurationId);

}
