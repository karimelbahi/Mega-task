package com.karim.task.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karim.task.data.entity.JobsItem

@Database(entities = [JobsItem::class], version = 1,exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun jobs(): JobsDao

}