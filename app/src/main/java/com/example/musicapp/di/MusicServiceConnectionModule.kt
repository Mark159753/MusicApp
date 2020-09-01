package com.example.musicapp.di

import android.content.ComponentName
import android.content.Context
import com.example.musicapp.service.MusicService
import com.example.musicapp.service.common.MusicServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object MusicServiceConnectionModule {

    @Provides
    @Singleton
    fun provideMusicServiceConnection(@ApplicationContext context: Context):MusicServiceConnection{
        return MusicServiceConnection(context, ComponentName(context, MusicService::class.java))
    }
}