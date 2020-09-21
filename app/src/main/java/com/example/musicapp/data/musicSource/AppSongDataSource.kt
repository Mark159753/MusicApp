package com.example.musicapp.data.musicSource

import com.example.musicapp.data.database.entitys.AssociateSongToFavList
import com.example.musicapp.data.database.entitys.FavoriteCategory
import com.example.musicapp.data.database.entitys.FavoriteSongs
import com.example.musicapp.model.song.Song

interface AppSongDataSource {

    suspend fun insertSong(song: Song):Long

    suspend fun insertSongs(song: List<Song>)

    suspend fun insertFavCategoryName(categoryName:FavoriteCategory):Long

    suspend fun insertAssociateSongToFav(favSong: AssociateSongToFavList)

    suspend fun insertSongToFavCategory(songId:Long, categoryName: String)

    suspend fun getSongsByFavCategory(categoryName: String): FavoriteCategory

    suspend fun getAllSongs():List<Song>

    suspend fun deleteSong(song:Song)

    suspend fun updateSongList(newSongList:List<Song>)
}