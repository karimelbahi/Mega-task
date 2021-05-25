package com.karim.task.domain

import com.karim.task.data.entity.JobsItem
import com.karim.task.data.repository.JobsRepo
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobsHandler @Inject constructor(private val repo: JobsRepo) {

    @FlowPreview
    fun getJobs() = repo.getJobs()

    fun updateFavorite(job: JobsItem) = repo.updateFavorite(job)


}