package com.titou.blaum.data_source.remote.titles_api.mapper

import com.titou.blaum.data.entity.Title
import com.titou.blaum.data_source.remote.titles_api.dtos.TitleResponseDto

fun TitleResponseDto.toTitleModel() = Title(
    albumId = this.albumId,
    id = this.id,
    title = this.title,
    thumbnailUrl = this.thumbnailUrl,
    url = this.url
)