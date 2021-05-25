package com.karim.task.data.api.entity

import com.karim.task.data.entity.JobsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {


    @GET("positions.json?description=api")
    fun getJobs(): Flow<JobsResponse>

}