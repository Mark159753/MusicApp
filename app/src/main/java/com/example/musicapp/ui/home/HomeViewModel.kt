package com.example.musicapp.ui.home


import androidx.hilt.lifecycle.ViewModelInject
import com.example.musicapp.service.MUSIC_BROWSABLE_ROOT
import com.example.musicapp.service.common.MusicServiceConnection
import com.example.musicapp.ui.base.BaseViewModel

class HomeViewModel @ViewModelInject constructor(
    override val musicServiceConnection: MusicServiceConnection
) : BaseViewModel() {

}