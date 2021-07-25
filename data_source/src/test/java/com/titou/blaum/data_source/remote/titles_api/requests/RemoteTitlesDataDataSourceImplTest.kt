package com.titou.blaum.data_source.remote.titles_api.requests

import com.titou.blaum.data.testing.TitleMock
import com.titou.blaum.data_source.Stubs
import com.titou.blaum.data_source.remote.titles_api.api.TitlesApiService
import org.junit.Test
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit


class RemoteTitlesDataDataSourceImplTest : KoinTest {

    private val titlesApiService: TitlesApiService = Stubs.createTitleApiServiceStubs()
    private val errorThrowingTitlesApiService: TitlesApiService = Stubs.createErrorThrowingTitleApiServiceStubs()


    @Test
    fun `should execute without error`() {
        val remoteTitlesDataSourceImpl = RemoteTitlesDataDataSourceImpl(titlesApiService)
        val query = remoteTitlesDataSourceImpl.getTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertNoErrors()
            ?.assertComplete()
    }


    @Test
    fun `should get correct and parsed data`() {
        val remoteTitlesDataSourceImpl = RemoteTitlesDataDataSourceImpl(titlesApiService)
        val query = remoteTitlesDataSourceImpl.getTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertValue(TitleMock.titles)
            ?.assertNoErrors()
            ?.assertComplete()
    }

    @Test
    fun `should throw and get correct error`() {

        val remoteTitlesDataSourceImpl = RemoteTitlesDataDataSourceImpl(errorThrowingTitlesApiService)
        val query = remoteTitlesDataSourceImpl.getTitles()
        val testObserver = query
            .test()

        testObserver
            ?.awaitDone(4, TimeUnit.SECONDS)
            ?.assertError(Stubs.defaultError)
    }
}
