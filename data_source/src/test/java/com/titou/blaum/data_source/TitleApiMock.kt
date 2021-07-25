package com.titou.blaum.data_source

import com.titou.blaum.data_source.remote.titles_api.dtos.TitleResponseDto

object TitleApiMock {
    val toJson =
        "{'albumId': 1, 'id': 1,'title': 'accusamus beatae ad facilis cum similique qui sunt','url': 'https://via.placeholder.com/600/92c952','thumbnailUrl': 'https://via.placeholder.com/150/92c952'}," +
                "{'albumId': 1,'id': 2,'title': 'reprehenderit est deserunt velit ipsam','url': 'https://via.placeholder.com/600/771796','thumbnailUrl': 'https://via.placeholder.com/150/771796' }," +
                "{'albumId': 1,'id': 3,'title': 'officia porro iure quia iusto qui ipsa ut modi','url': 'https://via.placeholder.com/600/24f355','thumbnailUrl': 'https://via.placeholder.com/150/24f355' }"

    val TITLES_API_SERVICE_STUB_GET_TITLES_RESPONSE = listOf(
        TitleResponseDto(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            thumbnailUrl = "https://via.placeholder.com/150/92c952",
            url = "https://via.placeholder.com/600/92c952"

        ),
        TitleResponseDto(
            albumId = 1,
            id = 2,
            title = "reprehenderit est deserunt velit ipsam",
            thumbnailUrl = "https://via.placeholder.com/150/771796",
            url = "https://via.placeholder.com/600/771796"

        ),
        TitleResponseDto(
            albumId = 1,
            id = 3,
            title = "officia porro iure quia iusto qui ipsa ut modi",
            thumbnailUrl = "https://via.placeholder.com/150/24f355",
            url = "https://via.placeholder.com/600/24f355"
        )

    )
}