package com.titou.blaum.data_source.mapper

import com.titou.blaum.entities.Title
import com.titou.blaum.data_source.local.database.models.TitleRoomModel
import com.titou.blaum.data_source.remote.titles_api.dtos.TitleResponseDto

fun TitleResponseDto.toTitleModel() = Title(
    albumId = this.albumId,
    id = this.id,
    title = this.title,
    thumbnailUrl = this.thumbnailUrl,
    url = this.url
)

fun TitleRoomModel.toTitleModel() = Title(
    albumId = this.albumId,
    id = this.id,
    title = this.title,
    thumbnailUrl = this.thumbnailUrl,
    url = this.url
)

fun Title.toTitleRoomModel() = TitleRoomModel(
    albumId = this.albumId,
    id = this.id,
    title = this.title,
    thumbnailUrl = this.thumbnailUrl,
    url = this.url
)