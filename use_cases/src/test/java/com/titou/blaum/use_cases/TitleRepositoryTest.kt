package com.titou.blaum.use_cases

import com.titou.blaum.entities.testing.TitleMock
import org.junit.Test
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit

class TitleRepositoryTest : KoinTest {

    private val errorThrowingDatabaseTitlesDataSource: DatabaseTitlesDataSource = DataSourcesStubs.createErrorThrowingDatabaseTitlesDataSourceStub()
    private val emptyDatabaseTitlesDataSource: DatabaseTitlesDataSource = DataSourcesStubs.createEmptyDatabaseTitlesDataSourceStub()
    private val remoteTitlesDataSource: RemoteTitlesDataSource = DataSourcesStubs.createRemoteTitlesDataSourceStub()
    private val errorThrowingRemoteTitlesDataSource: RemoteTitlesDataSource = DataSourcesStubs.createErrorThrowingRemoteTitlesDataSourceStub()


    @Test
    fun fetchingShouldExecuteWithoutError() {
        val titleRepository = TitleRepository(emptyDatabaseTitlesDataSource, remoteTitlesDataSource)
        val query = titleRepository.fetchAndSaveTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertNoErrors()
            ?.assertComplete()
    }


    @Test
    fun shouldGetDataFromRemoteAndSaveInDatabase() {
        val titleRepository = TitleRepository(emptyDatabaseTitlesDataSource, remoteTitlesDataSource)
        val query = titleRepository.fetchAndSaveTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(2, TimeUnit.SECONDS)
            ?.assertNoErrors()
            ?.assertComplete()

        val databaseObserver = emptyDatabaseTitlesDataSource.getTitles()
            .test()

        databaseObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertValue(TitleMock.titles)
            ?.assertNoErrors()
            ?.assertComplete()
    }

    @Test
    fun shouldThrowAndReceiveErrorFromDatabase() {
        val titleRepository = TitleRepository(errorThrowingDatabaseTitlesDataSource, errorThrowingRemoteTitlesDataSource)
        val query = titleRepository.fetchAndSaveTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertError(DataSourcesStubs.defaultError)
    }


}
