package com.karim.task.data.cache

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.karim.task.data.entity.JobsItem
import kotlinx.coroutines.flow.Flow


@Dao
interface JobsDao : BaseDao<JobsItem> {

    @Query("SELECT * FROM Jobs")
     fun getAllJobs(): Flow<List<JobsItem>>

    @Update
    fun updateFavorite(jobsItem: JobsItem)

    @Query("DELETE FROM Jobs")
    fun clearAll()
}