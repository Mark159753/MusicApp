package com.example.musicapp.di

import com.example.musicapp.data.musicSource.AppSongDataSource
import com.example.musicapp.data.musicSource.AppSongDataSourceImpl
import com.example.musicapp.data.musicSource.DeviceSongSource
import com.example.musicapp.data.musicSource.DeviceSongSourceImpl
import com.example.musicapp.data.repository.SongRepository
import com.example.musicapp.data.repository.SongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAppSongsDataSource(appSongDataSource: AppSongDataSourceImpl):AppSongDataSource

    @Binds
    abstract fun bindDeviceSongSource(deviceSongSourceImpl: DeviceSongSourceImpl):DeviceSongSource

    @Singleton
    @Binds
    abstract fun bindSongRepository(songRepository: SongRepositoryImpl):SongRepository
}