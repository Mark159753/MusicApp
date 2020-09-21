package com.example.musicapp.data.musicSource

import com.example.musicapp.data.database.SongsDao
import com.example.musicapp.data.database.entitys.AssociateSongToFavList
import com.example.musicapp.data.database.entitys.FavoriteCategory
import com.example.musicapp.data.database.entitys.FavoriteSongs
import com.example.musicapp.model.song.Song
import javax.inject.Inject

class AppSongDataSourceImpl @Inject constructor(
    private val dao: SongsDao
):AppSongDataSource {

    override suspend fun insertSong(song: Song): Long {
        return dao.insertSong(song)
    }

    override suspend fun insertSongs(song: List<Song>) {
        dao.insertSongs(song)
    }

    override suspend fun insertFavCategoryName(categoryName: FavoriteCategory): Long {
        return dao.insertFavCategoryName(categoryName)
    }

    override suspend fun insertAssociateSongToFav(favSong: AssociateSongToFavList) {
        dao.insertAssociateSongToFav(favSong)
    }

    override suspend fun insertSongToFavCategory(songId: Long, categoryName: String) {
        dao.insertSongToFavCategory(songId, categoryName)
    }

    override suspend fun getSongsByFavCategory(categoryName: String): FavoriteCategory {
        return dao.getSongsByFavCategory(categoryName)
    }

    override suspend fun getAllSongs(): List<Song> {
        return dao.getAllSongs()
    }

    override suspend fun deleteSong(song: Song) {
        dao.deleteSong(song)
    }

    override suspend fun updateSongList(newSongList: List<Song>) {
        dao.updateSongLst(newSongList)
    }
}