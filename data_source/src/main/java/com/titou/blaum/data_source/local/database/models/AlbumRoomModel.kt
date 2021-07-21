package com.titou.blaum.data_source.local.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumRoomModel(
    @PrimaryKey
    val id: Int
)

