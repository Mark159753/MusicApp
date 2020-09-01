package com.example.musicapp.data.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity( indices = [Index(value = ["name"], unique = true)])
data class FavoriteCategory(
    val name:String,
    @PrimaryKey(autoGenerate = true)
    val id:Long? = null
)