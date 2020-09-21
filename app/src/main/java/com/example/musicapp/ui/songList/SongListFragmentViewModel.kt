package com.example.musicapp.ui.songList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.musicapp.service.MUSIC_BROWSABLE_ROOT
import com.example.musicapp.service.common.MusicServiceConnection
import com.example.musicapp.ui.base.BaseViewModel

class SongListFragmentViewModel @ViewModelInject constructor(
    override val musicServiceConnection: MusicServiceConnection
) : BaseViewModel() {

}