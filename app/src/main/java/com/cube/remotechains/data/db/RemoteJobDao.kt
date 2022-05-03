package com.cube.remotechains.data.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cube.remotechains.data.model.JobToSave

interface RemoteJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobToSave): Long

    @Query("Select * from job order by id desc")
    fun getAllJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteJob(job: JobToSave)
}