package com.titou.blaum.use_cases

import org.koin.core.KoinComponent


class AlbumRepository(
    private val databaseTitlesSource: DatabaseTitlesSource
    ) : KoinComponent {
}


interface DatabaseTitlesSource {
}

interface RemoteAlbumsSource {
}
