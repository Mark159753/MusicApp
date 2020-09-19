package com.example.musicapp.ui.base

import android.support.v4.media.MediaBrowserCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.model.song.Song
import com.example.musicapp.service.MUSIC_BROWSABLE_ROOT
import com.example.musicapp.service.common.MusicServiceConnection

abstract class BaseViewModel() : ViewModel() {

    protected abstract val musicServiceConnection:MusicServiceConnection

    protected val _listOfSongs= MutableLiveData<List<Song>>()
    val listOfSongs: LiveData<List<Song>>
        get() = _listOfSongs

    protected val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback(){
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



    override fun onCleared() {
        super.onCleared()

        // Finally, unsubscribe the media ID that was being watched.
        musicServiceConnection.unsubscribe(MUSIC_BROWSABLE_ROOT, subscriptionCallback)
    }
}