package com.example.musicapp.data.database.entitys

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.musicapp.model.song.Song

data class FavoriteSongs(
    @Embedded
    val favoriteCategory: FavoriteCategory,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryFavListId",
        associateBy = Junction(AssociateSongToFavList::class)
    )
    val songs:List<Song>
)