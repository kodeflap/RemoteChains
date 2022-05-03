package com.cube.remotechains.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    @SerializedName("candidate_required_location")
    @Expose
    val candidateRequiredLocation: String,
    @SerializedName("category")
    @Expose
    val category: String,
    @SerializedName("company_logo")
    @Expose
    val companyLogo: String,
    @SerializedName("company_logo_url")
    @Expose
    val companyLogoUrl: String,
    @SerializedName("company_name")
    @Expose
    val companyName: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("job_type")
    @Expose
    val jobType: String,
    @SerializedName("publication_date")
    @Expose
    val publicationDate: String,
    @SerializedName("salary")
    @Expose
    val salary: String,
    @SerializedName("tags")
    @Expose
    val tags: List<String>,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("url")
    @Expose
    val url: String
) : Parcelable