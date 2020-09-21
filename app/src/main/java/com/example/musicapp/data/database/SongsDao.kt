package com.example.musicapp.data.database

import androidx.room.*
import com.example.musicapp.data.database.entitys.AssociateSongToFavList
import com.example.musicapp.data.database.entitys.FavoriteCategory
import com.example.musicapp.data.database.entitys.FavoriteSongs
import com.example.musicapp.model.song.Song

@Dao
abstract class SongsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSong(song:Song):Long

    @Ignore
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSongs(song: List<Song>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertFavCategoryName(categoryName:FavoriteCategory):Long

    @Insert
    abstract  suspend fun insertAssociateSongToFav(favSong:AssociateSongToFavList)

    suspend fun insertSongToFavCategory(songId:Long, categoryName: String){
        val fav = insertFavCategoryName(FavoriteCategory(categoryName))
        insertAssociateSongToFav(AssociateSongToFavList(songId, fav))
    }

    @Query("SELECT * FROM FavoriteCategory WHERE name = :categoryName")
    abstract suspend fun getSongsByFavCategory(categoryName: String):FavoriteCategory

    @Query("SELECT * FROM Song ORDER BY dateModified DESC")
    abstract suspend fun getAllSongs():List<Song>

    @Delete
    abstract suspend fun deleteSong(song: Song)

    @Query("DELETE FROM Song")
    abstract suspend fun deleteAllSongs()

    @Transaction
    open suspend fun updateSongLst(newSongs:List<Song>){
        deleteAllSongs()
        insertSongs(newSongs)
    }

}