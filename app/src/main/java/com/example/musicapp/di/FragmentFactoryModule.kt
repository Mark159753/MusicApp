package com.example.musicapp.di

import androidx.fragment.app.FragmentFactory
import com.example.musicapp.ui.fragmentFactory.MainFragmentFactory
import com.example.musicapp.ui.home.adapters.rv.HomeMusicAdapter
import com.example.musicapp.ui.home.adapters.vp.VpAdapterTabs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FragmentFactoryModule {

    @Provides
    @Singleton
    fun provideMainFragmentFactory():FragmentFactory{
        return MainFragmentFactory()
    }
}