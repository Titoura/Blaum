package com.titou.blaum.data_source.di

import com.titou.blaum.data_source.remote.titles_api.RetrofitBuilder
import com.titou.blaum.data_source.remote.titles_api.api.TitlesApiService
import com.titou.blaum.data_source.remote.titles_api.requests.RemoteTitlesDataDataSourceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

object TitlesApiModule {
    val module = module {
        single { RetrofitBuilder() }
        single { RemoteTitlesDataDataSourceImpl(get()) } bind(com.titou.blaum.use_cases.RemoteTitlesDataSource::class)
        single {
            get<RetrofitBuilder>().retrofitInstance.create(TitlesApiService::class.java)
        }

    }
}
