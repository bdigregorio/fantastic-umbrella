package com.example.android.trackmysleepquality.sleepquality

import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.database.SleepRecordDao

class SleepQualityRepository(
    private val sleepRecordDao: SleepRecordDao
) {
    suspend fun updateSleepRecord(sleepRecord: SleepRecord) {
        sleepRecordDao.update(sleepRecord)
    }

    suspend fun getSleepRecord(id: Long): SleepRecord {
        return sleepRecordDao.getSleepRecord(id)
    }
}