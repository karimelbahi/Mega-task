package com.karim.task.presentation.ui.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karim.task.data.api.entity.Resource
import com.karim.task.data.entity.JobsItem
import com.karim.task.domain.JobsHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class JobsModel @Inject constructor(private val handler: JobsHandler) :
    ViewModel() {

    @FlowPreview
    val jobsData = flow {
        emit(Resource.loading(null))
        emitAll(handler.getJobs())
    }.asLiveData()

    fun updateFavorite(job: JobsItem) =handler.updateFavorite(job)
}