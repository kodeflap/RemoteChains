package com.cube.remotechains.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cube.remotechains.data.model.JobToSave
import com.cube.remotechains.data.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
) : AndroidViewModel(app) {

    fun remoteJobResult() = remoteJobRepository.remoteJobResult()
    fun searchJob(query: String?) = remoteJobRepository.searchRemoteJob(query)
    fun searchResult() = remoteJobRepository.remoteJobSearch()
    fun insert(job: JobToSave) = viewModelScope.launch { remoteJobRepository.insertJob(job) }
    fun deleteJob(job: JobToSave) = viewModelScope.launch { remoteJobRepository.deleteJob(job) }
    fun getAllJob() = remoteJobRepository.getAllJobs()
}