package com.karim.task.data.repository


import android.util.Log
import androidx.lifecycle.asLiveData
import com.karim.task.data.api.entity.Resource
import com.karim.task.data.api.entity.Status
import com.karim.task.data.api.entity.WebService
import com.karim.task.data.cache.JobsDao
import com.karim.task.data.entity.JobsItem
import com.karim.task.data.entity.JobsResponse
import com.karim.task.data.repository.base.BaseRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobsRepo @Inject constructor(
    private val jobsDao: JobsDao,
    private val webService: WebService
) : BaseRepo() {

    @FlowPreview
    fun getJobs() =
        flow {
            getAPIFlow().collect { result ->
                if (result.status.get() == Status.SUCCESS) {
                    cachePostsResult(result)
                } else {
                    emit(result)
                }
            }
            getCacheFlow().collect { emit(it) }
        }

    private fun getAPIFlow() = loadFromApi(webService::getJobs)

    private suspend fun getCacheFlow() = jobsDao.getAllJobs().transform {
        if (it.isNotEmpty()) {
            emit(Resource.success(it))
        }
    }

    @FlowPreview
    private suspend fun cachePostsResult(result: Resource<*>) {
        withContext(Dispatchers.IO) {
            if (result.status.get() == Status.SUCCESS) {
                //cache success result
                val newData = result.data as List<JobsItem>
                jobsDao.insertAll(newData)

            }
        }
    }

    fun updateFavorite(job: JobsItem) {
        jobsDao.updateFavorite(job)
    }

}


