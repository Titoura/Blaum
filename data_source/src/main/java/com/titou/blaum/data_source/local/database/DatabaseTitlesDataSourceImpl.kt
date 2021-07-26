package com.titou.blaum.data_source.local.database

import com.titou.blaum.entities.Title
import com.titou.blaum.data_source.local.database.dao.TitlesDao
import com.titou.blaum.data_source.mapper.toTitleModel
import com.titou.blaum.data_source.mapper.toTitleRoomModel
import com.titou.blaum.use_cases.DatabaseTitlesDataSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent

class DatabaseTitlesDataSourceImpl(
    private val titlesDao: TitlesDao
) : KoinComponent, DatabaseTitlesDataSource {
    override fun getTitles(): Observable<List<Title>> =
        titlesDao.getAll()
            .map { titles->
                titles.map { it.toTitleModel() }
            }

    override fun saveTitles(titles: List<Title>): Single<Unit> = titlesDao.insert(titles.map { it.toTitleRoomModel() })
}