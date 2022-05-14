package com.cube.remotechains.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cube.remotechains.data.model.JobToSave

@Dao
interface RemoteJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJob(job: JobToSave): Long

    @Query("Select * from job order by id desc")
    fun getAllJob(): LiveData<List<JobToSave>>

    @Delete
    fun deleteJob(job: JobToSave)
}