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

package com.example.android.trackmysleepquality

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepRecordDao
import com.example.android.trackmysleepquality.database.SleepRecordEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sleepRecordDao: SleepRecordDao
    private lateinit var db: SleepDatabase

    private val sampleSleepRecord = SleepRecordEntity(
        id = 12345,
        startTime = System.currentTimeMillis(),
        endTime = System.currentTimeMillis() + 123456L,
        qualityScore = 8
    )

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because we don't need to save the test db to storage
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        sleepRecordDao = db.sleepRecordDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testRetrieveAnInsertedRecord() = runBlocking {
        // given
        sleepRecordDao.insert(sampleSleepRecord)

        // when
        val retrievedRecord = sleepRecordDao.getSleepRecord(sampleSleepRecord.id)

        // then
        Truth.assertThat(retrievedRecord.qualityScore).isEqualTo(sampleSleepRecord.qualityScore)
    }

    @Test
    @Throws(IOException::class)
    fun testUpdateSleepRecord() = runBlocking {
        // given
        val expectedQualityScore = 5
        sleepRecordDao.insert(sampleSleepRecord)

        // when
        sleepRecordDao.update(sampleSleepRecord.copy(qualityScore = expectedQualityScore))

        // then
        val retrievedRecord = sleepRecordDao.getMostRecentSleepRecord()
        Truth.assertThat(retrievedRecord?.qualityScore).isEqualTo(expectedQualityScore)
    }

    @Test
    @Throws(IOException::class)
    fun testClearAllRecords() = runBlocking {
        // given
        Truth.assertThat(sleepRecordDao.getAllSleepRecords().value).isNull()
        sleepRecordDao.insert(sampleSleepRecord)
        sleepRecordDao.insert(sampleSleepRecord.copy(id = 2))
        sleepRecordDao.insert(sampleSleepRecord.copy(id = 3))
        val actualRecords: LiveData<List<SleepRecordEntity>> = sleepRecordDao.getAllSleepRecords()
        Truth.assertThat(actualRecords.getOrAwaitValue()).hasSize(3)

        // when
        sleepRecordDao.clearAllSleepRecords()

        // then
        val retrievedRecords = sleepRecordDao.getAllSleepRecords()
        Truth.assertThat(retrievedRecords.getOrAwaitValue()).isEmpty()
    }
}

