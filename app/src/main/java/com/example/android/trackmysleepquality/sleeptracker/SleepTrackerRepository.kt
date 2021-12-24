package com.example.android.trackmysleepquality.sleeptracker

import androidx.lifecycle.LiveData
import com.example.android.trackmysleepquality.database.SleepRecord
import com.example.android.trackmysleepquality.database.SleepRecordDao

class SleepTrackerRepository(
    private val sleepRecordDao: SleepRecordDao
) {
    fun getAllRecords(): LiveData<List<SleepRecord>> {
        return sleepRecordDao.getAllSleepRecords()
    }

    suspend fun getCurrentSleep(): SleepRecord? {
        var sleep = sleepRecordDao.getMostRecentSleepRecord()

        if (sleep?.endTime != sleep?.startTime) {
            sleep = null
        }

        return sleep
    }

    suspend fun addNewRecord(currentSleep: SleepRecord) {
        sleepRecordDao.insert(currentSleep)
    }

    suspend fun update(sleepRecord: SleepRecord) {
        sleepRecordDao.update(sleepRecord)
    }

    suspend fun clearAllRecords() {
        sleepRecordDao.clearAllSleepRecords()
    }
}