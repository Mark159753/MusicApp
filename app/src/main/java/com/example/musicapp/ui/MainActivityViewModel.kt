package com.example.musicapp.ui

import android.support.v4.media.session.PlaybackStateCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.musicapp.service.common.EMPTY_PLAYBACK_STATE
import com.example.musicapp.service.common.MusicServiceConnection
import com.example.musicapp.service.common.NOTHING_PLAYING


class MainActivityViewModel @ViewModelInject constructor(
    musicServiceConnection: MusicServiceConnection
): ViewModel() {

    private val _bottomPlayerProgress = MutableLiveData<Float>()
    val bottomPlayerProgress: LiveData<Float>
        get() = _bottomPlayerProgress


    val playerPlaybackState:LiveData<Int> = Transformations.map(musicServiceConnection.playbackState){
        it.state
    }


    fun setBottomPlayerProgress(p: Float) {
        _bottomPlayerProgress.value = p
    }


}