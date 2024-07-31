package com.example.biservice.repository.inverterbattery;

import com.example.biservice.entity.inverterbattery.BackupDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupDurationRepo extends JpaRepository<BackupDuration, Integer> {
}
