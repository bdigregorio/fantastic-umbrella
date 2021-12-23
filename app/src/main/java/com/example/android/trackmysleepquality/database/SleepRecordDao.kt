/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SleepRecordDao {
    @Insert
    suspend fun insert(sleepRecord: SleepRecordEntity)

    @Update
    suspend fun update(sleepRecord: SleepRecordEntity)

    @Query(value = "SELECT * FROM sleep_quality_history_table WHERE id = :id")
    suspend fun getSleepRecord(id: Long): SleepRecordEntity

    @Query(value = "DELETE FROM sleep_quality_history_table")
    suspend fun clearAllSleepRecords()

    @Query(value = "SELECT * FROM sleep_quality_history_table ORDER BY id DESC")
    fun getAllSleepRecords(): LiveData<List<SleepRecordEntity>>

    @Query(value = "SELECT * FROM sleep_quality_history_table ORDER BY id DESC LIMIT 1")
    suspend fun getMostRecentSleepRecord(): SleepRecordEntity?
}
