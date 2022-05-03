package com.cube.remotechains.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cube.remotechains.data.api.RetrofitInstance
import com.cube.remotechains.data.db.RemoteJobDatabase
import com.cube.remotechains.data.model.JobToSave
import com.cube.remotechains.data.model.RemoteJob
import com.cube.remotechains.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository(private val db: RemoteJobDatabase) {

    private val remoteJobService = RetrofitInstance.apiService
    private val remoteJobLiveData : MutableLiveData<RemoteJob> = MutableLiveData()
    private val searchRemoteJobLiveData :MutableLiveData<RemoteJob> = MutableLiveData()
    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse(){
        remoteJobService.getRemoteJob().enqueue(
            object : Callback<RemoteJob> {
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    if(response.body() != null) {
                        remoteJobLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                    remoteJobLiveData.postValue(null)
                    Log.d(TAG,"onFailure:${t.message}")
                }
            }
        )
    }
    fun searchRemoteJob(query: String?){
        remoteJobService.searchRemoteJob(query).enqueue(
            object :Callback<RemoteJob> {
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    if(response.body() != null){
                        searchRemoteJobLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                    searchRemoteJobLiveData.postValue(null)
                    Log.d(TAG,"search fail:${t.message}")
                }
            }
        )
    }

    fun remoteJobResult(): LiveData<RemoteJob>{
        return remoteJobLiveData
    }

    fun remoteJobSearch(): LiveData<RemoteJob>{
        return searchRemoteJobLiveData
    }
    suspend fun insertJob(jobToSave: JobToSave) = db.getRemoteJobDao().insertJob(jobToSave)
    suspend fun deleteJob(jobToSave: JobToSave) = db.getRemoteJobDao().deleteJob(jobToSave)
    fun getAllJobs() = db.getRemoteJobDao().getAllJob()
}