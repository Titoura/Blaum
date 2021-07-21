package com.titou.blaum.data_source.remote.titles_api.dtos

data class TitleResponseDto(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)

