package com.titou.blaum.data_source.remote.titles_api.api

import com.titou.blaum.data_source.remote.titles_api.RetrofitBuilder
import org.koin.core.KoinComponent
import org.koin.core.inject

object TitlesApi : KoinComponent {
    private val retrofit: RetrofitBuilder by inject()
    val RETROFIT_SERVICE: TitlesApiService by lazy {
        retrofit.retrofitInstance.create(
            TitlesApiService::class.java
        )
    }
}
