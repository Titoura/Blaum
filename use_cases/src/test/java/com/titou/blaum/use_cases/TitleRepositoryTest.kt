package com.titou.blaum.use_cases

import com.titou.blaum.entities.testing.TitleMock
import org.junit.Test
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit

class TitleRepositoryTest : KoinTest {

    private val errorThrowingDatabaseTitlesDataSource: DatabaseTitlesDataSource = DataSourcesStubs.createErrorThrowingDatabaseTitlesDataSourceStub()
    private val emptydatabaseTitlesDataSource: DatabaseTitlesDataSource = DataSourcesStubs.createEmptyDatabaseTitlesDataSourceStub()
    private val fullDatabaseTitlesDataSource: DatabaseTitlesDataSource = DataSourcesStubs.createFullDatabaseTitlesDataSourceStub()
    private val remoteTitlesDataSource: RemoteTitlesDataSource = DataSourcesStubs.createRemoteTitlesDataSourceStub()
    private val errorThrowingRemoteTitlesDataSource: RemoteTitlesDataSource = DataSourcesStubs.createErrorThrowingRemoteTitlesDataSourceStub()


    @Test
    fun `should execute without error`() {
        val titleRepository = TitleRepository(emptydatabaseTitlesDataSource, remoteTitlesDataSource)
        val query = titleRepository.fetchAndSaveTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertNoErrors()
            ?.assertComplete()
    }


    @Test
    fun `should get data from remote and save in database`() {
        val titleRepository = TitleRepository(emptydatabaseTitlesDataSource, remoteTitlesDataSource)
        val query = titleRepository.fetchAndSaveTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(2, TimeUnit.SECONDS)
            ?.assertValue(TitleMock.titles)
            ?.assertNoErrors()
            ?.assertComplete()

        val databaseObserver = emptydatabaseTitlesDataSource.getTitles()
            .test()

        databaseObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertValue(TitleMock.titles)
            ?.assertNoErrors()
            ?.assertComplete()
    }

    @Test
    fun `should get data from database if remote can't`() {

        val titleRepository = TitleRepository(fullDatabaseTitlesDataSource, errorThrowingRemoteTitlesDataSource)
        val query = titleRepository.fetchAndSaveTitles()

        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertValue(TitleMock.titles)
            ?.assertComplete()
    }

    @Test
    fun `should throw and receive error from database`() {
        val titleRepository = TitleRepository(errorThrowingDatabaseTitlesDataSource, errorThrowingRemoteTitlesDataSource)
        val query = titleRepository.fetchAndSaveTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertError(DataSourcesStubs.defaultError)
    }


}
