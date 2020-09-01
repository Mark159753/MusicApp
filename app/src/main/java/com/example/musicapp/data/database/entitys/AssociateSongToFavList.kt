package com.example.musicapp.data.database.entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.musicapp.model.song.Song

@Entity( primaryKeys = ["songId", "songId"],
    foreignKeys = [
        ForeignKey(
            entity = Song::class,
            parentColumns = ["id"],
            childColumns = ["songId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = FavoriteCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryFavListId"],
            onDelete = CASCADE
        )
    ]
)
data class AssociateSongToFavList(
    val songId:Long,
    val categoryFavListId:Long
)