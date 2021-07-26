package com.titou.blaum.data_source

import com.titou.blaum.data_source.TitleApiMock.TITLES_API_SERVICE_STUB_GET_TITLES_RESPONSE
import com.titou.blaum.data_source.remote.titles_api.api.TitlesApiService
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

object Stubs {

    val defaultError = Throwable("Test error")

    fun createTitleApiServiceStubs() : TitlesApiService {
        val titlesApiService = mockk<TitlesApiService>()
        every { titlesApiService.getTitles() } returns Observable.just(Response.success(TITLES_API_SERVICE_STUB_GET_TITLES_RESPONSE))
        return titlesApiService
    }

    fun createErrorThrowingTitleApiServiceStubs() : TitlesApiService {
        val titlesApiService = mockk<TitlesApiService>()
        every { titlesApiService.getTitles() } returns Observable.error(defaultError)
        return titlesApiService
    }

}


