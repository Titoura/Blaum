package com.titou.blaum.data_source.remote.titles_api.requests;

import android.util.Log
import com.titou.blaum.entities.Title
import com.titou.blaum.data_source.remote.titles_api.api.TitlesApiService
import com.titou.blaum.data_source.mapper.toTitleModel
import com.titou.blaum.use_cases.RemoteTitlesDataSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.KoinComponent


class RemoteTitlesDataDataSourceImpl(
    private val titlesApiService: TitlesApiService
) : KoinComponent, RemoteTitlesDataSource {

    private val TAG = javaClass.canonicalName

    override fun getTitles(): Observable<List<Title>> {
        return titlesApiService.getTitles()
            .map {
                it.body()
                    ?: throw IllegalStateException("Error parsing getTitles() JSON body")
            }
            .map { titles ->
                titles.map { it.toTitleModel() }
            }.doOnError {
                Log.e(TAG, "Error while getting remote dataSource")
                it.printStackTrace()
            }
            .subscribeOn(Schedulers.io())
    }

}

