package com.example.musicapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicapp.data.database.entitys.AssociateSongToFavList
import com.example.musicapp.data.database.entitys.FavoriteCategory
import com.example.musicapp.model.song.Song

@Database(
    entities = [
        Song::class,
        AssociateSongToFavList::class,
        FavoriteCategory::class
    ],
    version = 1, exportSchema = false
)
abstract class SongsDB:RoomDatabase() {

    abstract fun getSongDao():SongsDao

//    companion object{
//        @Volatile
//        private var instance:SongsDB? = null
//
//        operator fun invoke(context: Context) = instance ?: synchronized(this){
//            instance ?: buildDataBase(context).also { instance = it }
//        }
//
//        private fun buildDataBase(context: Context) = Room.databaseBuilder(
//            context, SongsDB::class.java, "RecipeDB"
//        ).fallbackToDestructiveMigration()
//            .fallbackToDestructiveMigration()
//            .build()
//    }
}