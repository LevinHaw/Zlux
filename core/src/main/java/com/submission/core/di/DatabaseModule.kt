package com.submission.core.di

import android.content.Context
import androidx.room.Room
import com.submission.core.data.datasource.local.room.MovieDao
import com.submission.core.data.datasource.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private val passphrase = SQLiteDatabase.getBytes("zlux".toCharArray())
    private val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "Movie.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()

    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao =
        db.movieDao()
}