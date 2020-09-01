package com.example.musicapp.service

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.example.musicapp.data.repository.SongRepository
import com.example.musicapp.service.extensions.id
import com.example.musicapp.service.extensions.toMediaSource
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DataSource

class MusicPlaybackPreparer(
    private val repository: SongRepository,
    private val exoPlayer: ExoPlayer,
    private val dataSourceFactory: DataSource.Factory
): MediaSessionConnector.PlaybackPreparer {

    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) {
        //TODO Implement later
    }

    override fun onCommand(
        player: Player,
        controlDispatcher: ControlDispatcher,
        command: String,
        extras: Bundle?,
        cb: ResultReceiver?
    ): Boolean = false

    override fun getSupportedPrepareActions(): Long =
        PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
//                PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH or
//                PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH or
//                PlaybackStateCompat.ACTION_PREPARE_FROM_URI or
//                PlaybackStateCompat.ACTION_PLAY_FROM_URI

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
        val itemToPlay:MediaMetadataCompat? = repository.currentPlayList.find { item ->
            item.id == mediaId
        }
        if (itemToPlay == null) {
            Log.w("MusicPlaybackPreparer", "Content not found: MediaID=$mediaId")

            // TODO: Notify caller of the error.{
        } else{
            val mediaSource = repository.currentPlayList.toMediaSource(dataSourceFactory)

            val initialIndex = repository.currentPlayList.indexOf(itemToPlay)

            exoPlayer.prepare(mediaSource)
            exoPlayer.seekTo(initialIndex, 0)
            exoPlayer.playWhenReady = playWhenReady
        }
    }

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit

    override fun onPrepare(playWhenReady: Boolean) = Unit
}