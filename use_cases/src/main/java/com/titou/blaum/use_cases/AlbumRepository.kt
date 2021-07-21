package com.titou.blaum.use_cases

import com.titou.blaum.data.entity.Title
import io.reactivex.rxjava3.core.Observable
import org.koin.core.KoinComponent


class AlbumRepository(
    private val databaseTitlesDataSource: DatabaseTitlesDataSource,
    private val remoteTitlesDataSource: RemoteTitlesDataSource
    ) : KoinComponent {
        fun getRemoteTitles() = remoteTitlesDataSource.getTitles()
}


interface DatabaseTitlesDataSource {
}

interface RemoteTitlesDataSource {
    fun getTitles() : Observable<List<Title>>
}
