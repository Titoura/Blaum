package com.titou.blaum.data_source.local.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "title")
data class TitleRoomModel(
    @PrimaryKey
    val id : Int,
    val title: String,
    val thumbnailUrl : String,
    val url : String,
    val albumId: Int
)

