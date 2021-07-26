package com.titou.blaum.use_cases

import com.titou.blaum.entities.Title
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent


class TitleRepository(
    private val databaseTitlesDataSource: DatabaseTitlesDataSource,
    private val remoteTitlesDataSource: RemoteTitlesDataSource
) : KoinComponent {

    fun getRemoteTitles() = remoteTitlesDataSource.getTitles()
    fun getLocalTitles() = databaseTitlesDataSource.getTitles()

    fun fetchAndSaveTitles() =
        getRemoteTitles()
            .flatMap (
                { titles -> databaseTitlesDataSource.saveTitles(titles).toObservable() },
                { titles, _ -> titles }
            )
            .doOnError {
                it.printStackTrace()
            }
            .onErrorResumeWith(getLocalTitles())
}


interface DatabaseTitlesDataSource {
    fun getTitles(): Observable<List<Title>>
    fun saveTitles(titles: List<Title>): Single<Unit>
}

interface RemoteTitlesDataSource {
    fun getTitles(): Observable<List<Title>>
}
