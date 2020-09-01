package com.example.musicapp.data.repository

import android.content.Context
import android.content.Intent
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import com.example.musicapp.data.musicSource.AppSongDataSource
import com.example.musicapp.data.musicSource.DeviceSongSource
import com.example.musicapp.model.song.Song
import com.example.musicapp.service.BROADCAST_PARENT_ID
import com.example.musicapp.service.MUSIC_BROWSABLE_ROOT
import com.example.musicapp.service.UPDATE_BROADCAST
import com.example.musicapp.service.extensions.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSongDataSource: AppSongDataSource,
    private val deviceSongSource: DeviceSongSource
):SongRepository {

    private var _currentPlayList:List<MediaMetadataCompat> = emptyList()
    override val currentPlayList:List<MediaMetadataCompat>
        get() = _currentPlayList

    override fun setCurrentPlaylist(list: List<Song>) {
        _currentPlayList = list.map { song ->
            MediaMetadataCompat.Builder()
                .from(song)
                .build()
        }
    }

    override fun loadAllMusic():Flow<List<Song>>{
        return flow {
            val oldSongs = appSongDataSource.getAllSongs()
            emit(oldSongs)

            val newSongList = deviceSongSource.getAllMusic()
            if (isNeedToUpdate(oldSongs, newSongList)){
                updateAppSongs(newSongList)
                sendBroadcast(MUSIC_BROWSABLE_ROOT)
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun isNeedToUpdate(oldList:List<Song>, newList: List<Song>):Boolean{
        if (oldList.size != newList.size){
            return true
        }
        oldList.forEach {
            if (!newList.contains(it)){
                return true
            }
        }
        newList.forEach {
            if (!oldList.contains(it)){
                return true
            }
        }
        return false
    }

    private suspend fun updateAppSongs(newList:List<Song>){
        appSongDataSource.updateSongList(newList)
    }

    private fun sendBroadcast(parentId:String){
        val intent = Intent(UPDATE_BROADCAST).also {
            it.putExtra(BROADCAST_PARENT_ID, parentId)
        }
        context.sendBroadcast(intent)
    }

    fun MediaMetadataCompat.Builder.from(song: Song): MediaMetadataCompat.Builder {
        id = song.id.toString()
        title = song.title
        artist = song.displayName
        album = song.album
        mediaUri = song.dataPath
        albumArtUri = song.albumArtPath
        flag = MediaBrowserCompat.MediaItem.FLAG_PLAYABLE

        // To make things easier for *displaying* these, set the display properties as well.
        displayTitle = song.title
        displaySubtitle = song.displayName
        displayDescription = song.album
        displayIconUri = song.albumArtPath

        return this
    }

}