package com.titou.blaum.data_source.local.database

import com.titou.blaum.data_source.local.database.dao.AlbumDao
import com.titou.blaum.use_cases.DatabaseTitlesDataSource
import org.koin.core.KoinComponent

class DatabaseTitlesDataSourceImpl(
    private val albumDao: AlbumDao
) : KoinComponent, DatabaseTitlesDataSource {
}