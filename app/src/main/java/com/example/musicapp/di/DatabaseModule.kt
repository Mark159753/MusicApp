package com.example.musicapp.di

import android.content.Context
import androidx.room.Room
import com.example.musicapp.data.database.SongsDB
import com.example.musicapp.data.database.SongsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context):SongsDB{
        return  Room.databaseBuilder(
            context, SongsDB::class.java, "RecipeDB"
        ).fallbackToDestructiveMigration()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSongsDao(db:SongsDB):SongsDao{
        return db.getSongDao()
    }

}