package com.titou.blaum.data_source.remote.titles_api.api

import com.titou.blaum.data_source.remote.titles_api.dtos.TitleResponseDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET

interface TitlesApiService {
    @GET("https://static.leboncoin.fr/img/shared/technical-test.json")
    fun getTitles(): Observable<Response<List<TitleResponseDto>>>
}
