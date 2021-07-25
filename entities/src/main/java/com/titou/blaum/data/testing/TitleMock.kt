package com.titou.blaum.data.testing

import com.titou.blaum.data.entity.Title

object TitleMock {
    val title1 = Title(
        albumId = 1,
        id = 1,
        title = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://via.placeholder.com/600/92c952",
        thumbnailUrl = "https://via.placeholder.com/150/92c952"
    )
    val title2 = Title(
        albumId = 1,
        id = 2,
        title = "reprehenderit est deserunt velit ipsam",
        url = "https://via.placeholder.com/600/771796",
        thumbnailUrl = "https://via.placeholder.com/150/771796"
    )

    val title3 = Title(
        albumId = 1,
        id = 3,
        title = "officia porro iure quia iusto qui ipsa ut modi",
        url = "https://via.placeholder.com/600/24f355",
        thumbnailUrl = "https://via.placeholder.com/150/24f355"
    )

    val titles = listOf(title1, title2, title3)
}