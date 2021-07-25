package com.titou.blaum.use_cases

import com.titou.blaum.data.entity.Title
import com.titou.blaum.data.testing.TitleMock
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

object DataSourcesStubs {

    val defaultError = Throwable("Test error")

    fun createErrorThrowingDatabaseTitlesDataSourceStub(): DatabaseTitlesDataSource {
        val databaseTitlesDataSource = mockk<DatabaseTitlesDataSource>()
        every { databaseTitlesDataSource.saveTitles(TitleMock.titles) } returns Single.just(Unit)
        every { databaseTitlesDataSource.getTitles() } returns Observable.error(defaultError)
        return databaseTitlesDataSource
    }

    fun createEmptyDatabaseTitlesDataSourceStub(): DatabaseTitlesDataSource {
        val databaseTitlesDataSource = mockk<DatabaseTitlesDataSource>()
        var titles = emptyList<Title>()
        every { databaseTitlesDataSource.saveTitles(TitleMock.titles) } returns
                let {
                    titles = TitleMock.titles
                    Single.just(
                        Unit
                    )
                }
        every { databaseTitlesDataSource.getTitles() } returns Observable.just(titles)

        return databaseTitlesDataSource
    }

    fun createFullDatabaseTitlesDataSourceStub(): DatabaseTitlesDataSource {
        val databaseTitlesDataSource = mockk<DatabaseTitlesDataSource>()
        val titles = TitleMock.titles
        every { databaseTitlesDataSource.getTitles() } returns Observable.just(titles)
        every { databaseTitlesDataSource.saveTitles(TitleMock.titles) } returns
                Single.just(
                    Unit
                )

        return databaseTitlesDataSource
    }
    fun createRemoteTitlesDataSourceStub() : RemoteTitlesDataSource{
        val remoteTitlesDataSource = mockk<RemoteTitlesDataSource>()
        every { remoteTitlesDataSource.getTitles() } returns Observable.just(TitleMock.titles)
        return remoteTitlesDataSource
    }
    fun createErrorThrowingRemoteTitlesDataSourceStub(): RemoteTitlesDataSource {
        val remoteTitlesDataSource = mockk<RemoteTitlesDataSource>()
        every { remoteTitlesDataSource.getTitles() } returns Observable.error(defaultError)
        return remoteTitlesDataSource
    }
}


