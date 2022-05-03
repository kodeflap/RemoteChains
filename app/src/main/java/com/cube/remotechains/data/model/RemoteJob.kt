package com.cube.remotechains.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class RemoteJob(
    @SerializedName("job-count")
    @Expose
    val jobCount: Int,
    @SerializedName("jobs")
    @Expose
    val jobs: List<Job>
)