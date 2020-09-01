package com.example.musicapp.data.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.example.musicapp.model.song.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {

    val currentPlayList:List<MediaMetadataCompat>

    fun setCurrentPlaylist(list: List<Song>)

    fun loadAllMusic(): Flow<List<Song>>
}