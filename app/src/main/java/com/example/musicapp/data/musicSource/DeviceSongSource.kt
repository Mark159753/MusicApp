package com.example.musicapp.data.musicSource

import com.example.musicapp.model.song.Song

interface DeviceSongSource {

    fun getAllMusic():List<Song>
}