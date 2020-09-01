package com.example.musicapp.ui.home


import android.content.Context
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.musicapp.model.song.Song
import com.example.musicapp.service.MUSIC_BROWSABLE_ROOT
import com.example.musicapp.service.common.MusicServiceConnection
import com.example.musicapp.service.extensions.id
import com.example.musicapp.service.extensions.isPlayEnabled
import com.example.musicapp.service.extensions.isPlaying
import com.example.musicapp.service.extensions.isPrepared

class HomeViewModel @ViewModelInject constructor(
    musicServiceConnection: MusicServiceConnection
) : ViewModel() {


    private val _listOfSongs= MutableLiveData<List<Song>>()
    val listOfSongs:LiveData<List<Song>>
        get() = _listOfSongs

    private val subscriptionCallback = object :MediaBrowserCompat.SubscriptionCallback(){
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            val itemList = children.map {
                Song(
                    it.mediaId!!.toLong(),
                    it.description.subtitle.toString(),
                    it.description.title.toString(),
                    null,
                    it.description.description.toString(),
                    it.description.mediaUri?.path,
                    null,
                    it.description.iconUri?.path
                )
            }
            _listOfSongs.postValue(itemList)
        }
    }

    private val musicServiceConnection = musicServiceConnection.also {
        it.subscribe(MUSIC_BROWSABLE_ROOT, subscriptionCallback)
    }



    override fun onCleared() {
        super.onCleared()

        // Finally, unsubscribe the media ID that was being watched.
        musicServiceConnection.unsubscribe(MUSIC_BROWSABLE_ROOT, subscriptionCallback)
    }


}