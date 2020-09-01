package com.example.musicapp.data.musicSource

import android.content.Context
import android.provider.MediaStore
import com.example.musicapp.model.song.Song
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceSongSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
):DeviceSongSource {

    override fun getAllMusic(): List<Song> {
        val listSongs = ArrayList<Song>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val sortOrder = "${MediaStore.Audio.Media.DATE_MODIFIED} DESC"

        val query = context.contentResolver.query(
            uri, projection, selection, null, sortOrder
        )
        query?.use {cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)
            val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (cursor.moveToNext()){
                val id = cursor.getLong(idColumn)
                val album = cursor.getString(albumColumn)
                val albumID = cursor.getLong(albumIdColumn)
                val title = cursor.getString(titleColumn)
                val dateModified = cursor.getLong(dateModifiedColumn)
                val displayName = cursor.getString(displayNameColumn)
                val dataPath = cursor.getString(dataColumn)

                val albumArt = findAlbumArt(albumID)

                listSongs.add(Song(id, album, title, dateModified, displayName, dataPath, null, albumArt))
            }
        }
        return listSongs
    }


    private fun findAlbumArt(album:Long):String?{
        val query = context.contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),
            MediaStore.Audio.Albums._ID + "=?",
            arrayOf(album.toString()),
            null)

        query?.use { cursor ->
            if (cursor.moveToFirst()){
                return cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
            }
        }
        return null
    }
}