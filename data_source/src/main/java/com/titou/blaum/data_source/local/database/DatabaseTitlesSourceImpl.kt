package com.titou.blaum.data_source.local.database

import com.titou.blaum.data_source.local.database.dao.AlbumDao
import com.titou.blaum.use_cases.DatabaseTitlesSource
import org.koin.core.KoinComponent

class DatabaseTitlesSourceImpl(
    private val albumDao: AlbumDao
) : KoinComponent, DatabaseTitlesSource {
}