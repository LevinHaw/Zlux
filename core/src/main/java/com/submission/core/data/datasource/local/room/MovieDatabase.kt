package com.submission.core.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.submission.core.data.datasource.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}