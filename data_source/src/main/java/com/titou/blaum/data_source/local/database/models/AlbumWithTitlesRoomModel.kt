package com.titou.blaum.data_source.local.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "albumWithTitles")
data class AlbumWithTitlesRoomModel(
    @Embedded val id: AlbumRoomModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val titles: List<TitleRoomModel>

)

