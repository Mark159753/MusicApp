package com.example.musicapp.model.song

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey
    var id:Long = 0,
    var album:String? = null,
    var title:String? = null,
    var dateModified:Long? = null,
    var displayName:String? = null,
    var dataPath:String? = null,
    @Ignore
    var img:Bitmap? = null,
    var albumArtPath:String? = null
)